package net.unknownuser.eventreader;

import net.unknownuser.eventreader.codes.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class EventReader implements Runnable {
	
	private final boolean             filterSeparators;
	private final boolean             filterMiscellaneous;
	private final File                input;
	private final ExecutorService     executor  = Executors.newSingleThreadExecutor();
	private final ArrayList<Listener> listeners = new ArrayList<>();
	private       boolean             discarded = false;
	private       BufferedInputStream inputStream;
	
	protected EventReader(File eventFile, boolean filterSeparators, boolean filterMiscellaneous) {
		this.input               = eventFile;
		this.filterSeparators    = filterSeparators;
		this.filterMiscellaneous = filterMiscellaneous;
		
		new Thread(this, "reader").start();
	}
	
	public static Optional<EventReader> runAtEventNumber(
		int eventNumber, boolean filterSeparators, boolean filterMiscellaneous
	) {
		return validate("/dev/input/event" + eventNumber, filterSeparators, filterMiscellaneous);
	}
	
	public static Optional<EventReader> runAtEventID(
		String id, boolean filterSeparators, boolean filterMiscellaneous
	) {
		return validate("/dev/input/by-id/" + id, filterSeparators, filterMiscellaneous);
	}
	
	public static Optional<EventReader> runAtEventPath(
		String path, boolean filterSeparators, boolean filterMiscellaneous
	) {
		return validate("/dev/input/by-path/" + path, filterSeparators, filterMiscellaneous);
	}
	
	private static Optional<EventReader> validate(String path, boolean filterSeparators, boolean filterMiscellaneous) {
		File file = new File(path);
		if (!(file.exists() && file.canRead())) {
			System.out.printf("'%s' does not exist or is not readable%n", file.getAbsolutePath());
			return Optional.empty();
		}
		
		return Optional.of(new EventReader(file, filterSeparators, filterMiscellaneous));
	}
	
	/**
	 * Turns read bytes into an {@link InputEvent#InputEvent(long, long, short, short, int) InputEvent}.
	 *
	 * @param bytes The read bytes. Must be 24 bytes in length.
	 *
	 * @return An optional containing the InputEvent, if the data is 24 bytes long. An empty optional otherwise.
	 */
	public static InputEvent inputToEvent(byte[] bytes) {
		long  secs  = combine(bytes, 0, 7);
		long  usecs = combine(bytes, 8, 15);
		short type  = (short) combine(bytes, 16, 17);
		short code  = (short) combine(bytes, 18, 19);
		int   value = (int) combine(bytes, 20, 23);
		
		return new InputEvent(secs, usecs, type, code, value);
	}
	
	/**
	 * Combines a specific amount of bytes in the correct order for input events.
	 *
	 * @param bytes The bytes from the input event.
	 * @param from  The beginning of the to be combined part.
	 * @param to    The end of the to be combined part.
	 *
	 * @return The value of the combined bytes.
	 */
	public static long combine(byte[] bytes, int from, int to) {
		// byte order is reversed
		int  diff   = to - from;
		long result = 0;
		for (int i = diff; i >= 0; i--) {
			result += ((long) bytes[i + from] & 0xFF) << (8 * i);
		}
		return result;
	}
	
	/**
	 * Gets all available keyboards from {@code /dev/input/by-id}. Keyboard events end with {@code -event-kbd}.
	 *
	 * @return The list of available Keyboards.
	 */
	public static List<String> getKeyboardsByID() {
		return filterWithinID("-event-kbd");
	}
	
	/**
	 * Gets all available mice from {@code /dev/input/by-id}. Mouse events end with {@code -event-mouse}.
	 *
	 * @return The list of available Mice.
	 */
	public static List<String> getMiceByID() {
		return filterWithinID("-event-mouse");
	}
	
	/**
	 * Lists all devices based on the filter (usually {@code -event-mouse} or {@code -event-kbd}).
	 *
	 * @param filter All device files have to end with the filter.
	 *
	 * @return All devices matching the filter.
	 */
	private static List<String> filterWithinID(String filter) {
		File[] files = new File("/dev/input/by-id/").listFiles(file -> file.getName().endsWith(filter));
		return Arrays.stream(files).map(File::getName).toList();
	}
	
	public void addListener(Listener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Discards this EventReader. The reader thread cannot be stopped on its own and needs one new input event to be
	 * properly stopped.<br>
	 * A discarded EventReader has no use.
	 */
	public void discard() {
		if (discarded) {
			return;
		}
		
		listeners.clear();
		executor.shutdown();
		
		try {
			inputStream.close();
		} catch (IOException exc) {
			System.err.println("error while closing input stream: " + exc.getLocalizedMessage());
		}
		
		if (!executor.shutdownNow().isEmpty()) {
			System.out.println("Some tasks have not been completed, shutting down anyway");
		}
		
		discarded = true;
	}
	
	public boolean isDiscarded() {
		return discarded;
	}
	
	@Override
	public void run() {
		if (discarded) {
			return;
		}
		
		try {
			inputStream = new BufferedInputStream(new FileInputStream(input));
		} catch (IOException exc) {
			System.err.println("could not open file stream: " + exc.getLocalizedMessage());
			discard();
			return;
		}
		
		try {
			while (!discarded) {
				byte[] bytes = new byte[InputEvent.BYTES];
				
				int numBytes;
				if ((numBytes = inputStream.read(bytes, 0, bytes.length)) != InputEvent.BYTES) {
					System.out.printf("Wrong amount of bytes read. Expected %d, got %d%n", InputEvent.BYTES, numBytes);
					break;
				}
				
				InputEvent event = inputToEvent(bytes);
				
				if ((filterSeparators && event.isSeparator()) || (filterMiscellaneous && event.type() == Type.MISCELLANEOUS_DATA.value)) {
					continue;
				}
				
				listeners.forEach(listener -> executor.execute(() -> listener.handleEvent(event)));
			}
		} catch (IOException exc) {
			if (!isDiscarded()) {
				System.err.println("error while reading: " + exc.getLocalizedMessage());
			}
		}
	}
}
