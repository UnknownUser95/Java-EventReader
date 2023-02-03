package net.unknownuser.eventreader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import net.unknownuser.eventreader.codes.Type;

public class EventReader implements Runnable {
	
	private boolean discarded = false;
	private boolean filterSeparators = false;
	private boolean filterMiscellaneous = false;
	private boolean isMouseReader;
	
	private final File input;
	private final Tuple<Thread, ThreadLauncher> launcher;
	private BufferedInputStream iStream;
	
	protected EventReader(File eventFile, boolean filterSeparators, boolean filterMiscellaneous) {
		
		input = eventFile;
		isMouseReader = eventFile.getName().endsWith("-event-mouse");
		
		this.filterSeparators = filterSeparators;
		this.filterMiscellaneous = filterMiscellaneous;
		
		ThreadLauncher thrLauncher = new ThreadLauncher(this);
		Thread thr = new Thread(thrLauncher, "launcher");
		launcher = new Tuple<>(thr, thrLauncher);
		
		new Thread(this, "reader").start();
		thr.start();
	}
	
	public void addListener(Listener listener) {
		launcher.b.addListener(listener);
	}
	
	public static Optional<EventReader> runAtEventNumber(int eventNumber, boolean filterSeparators, boolean filterMiscellaneous) {
		return validate("/dev/input/event" + eventNumber, filterSeparators, filterMiscellaneous);
	}
	
	public static Optional<EventReader> runAtEventID(String id, boolean filterSeparators, boolean filterMiscellaneous) {
		return validate("/dev/input/by-id/" + id, filterSeparators, filterMiscellaneous);
	}
	
	public static Optional<EventReader> runAtEventPath(String path, boolean filterSeparators, boolean filterMiscellaneous) {
		return validate("/dev/input/by-path/" + path, filterSeparators, filterMiscellaneous);
	}
	
	private static Optional<EventReader> validate(String path, boolean filterSeparators, boolean filterMiscellaneous) {
		File file = new File(path);
		if(!(file.exists() && file.canRead())) {
			System.out.println(String.format("'%s' does not exist or is not readable", file.getAbsolutePath()));
			return Optional.empty();
		}
		
		return Optional.of(new EventReader(file, filterSeparators, filterMiscellaneous));
	}
	
	/**
	 * Discards this EventReader. The reader thread cannot be stopped on its own and needs one new
	 * input event to be properly stopped.<br>
	 * A discarded EventReader has no use.
	 */
	public void discard() {
		if(discarded) {
			return;
		}
		
		discarded = true;
		
		try {
			iStream.close();
		} catch(IOException exc) {
			System.err.println("error while closing input stream: " + exc.getLocalizedMessage());
		}
		
		launcher.b.discard();
		launcher.a.interrupt();
	}
	
	public boolean isDiscarded() {
		return discarded;
	}
	
	@Override
	public void run() {
		if(discarded) {
			return;
		}
		
		try {
			iStream = new BufferedInputStream(new FileInputStream(input));
		} catch(IOException exc) {
			System.err.println("could not open file stream: " + exc.getLocalizedMessage());
			discard();
			return;
		}
		
		try {
			while(!discarded) {
				byte[] bytes = new byte[24];
				iStream.read(bytes, 0, bytes.length);
				var<?> inputEvent = inputToEvent(bytes);
				
				if(inputEvent.isEmpty()) {
					discard();
					break;
				}
				
				InputEvent event = inputEvent.get();
				
				if((filterSeparators && event.isSeparator()) || (filterMiscellaneous && event.type() == Type.MISCELLANEOUS_DATA)) {
					continue;
				}
				
				launcher.b.addEvent(event);
			}
		} catch(IOException exc) {
			if(!isDiscarded())
				System.err.println("error while reading: " + exc.getLocalizedMessage());
		}
	}
	
	/**
	 * Turns read bytes into an {@link InputEvent#InputEvent(long, long, short, short, int)
	 * InputEvent}.
	 * 
	 * @param bytes The read bytes.
	 * 
	 * @return An optional containing the InputEvent, if the data is 24 bytes long. An empty
	 *         optional otherwise.
	 */
	public static Optional<InputEvent> inputToEvent(byte[] bytes) {
		if(bytes.length != 24) {
			return Optional.empty();
		}
		
//		for(int i = 0; i < 24; i++) {
//			System.out.printf("%d: %s -- %s%n", i, Integer.toHexString(bytes[i]).toUpperCase(), Integer.toBinaryString(bytes[i]));
//		}
		
		long secs = combine(bytes, 0, 7);
		long usecs = combine(bytes, 8, 15);
		short type = (short) combine(bytes, 16, 17);
		short code = (short) combine(bytes, 18, 19);
		int value = (int) combine(bytes, 20, 23);
		
		return Optional.of(new InputEvent(secs, usecs, type, code, value));
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
		int diff = to - from;
		long result = 0;
		for(int i = diff; i >= 0; i--) {
			result += ((long) bytes[i + from] & 0xFF) << (8 * i);
		}
		return result;
	}
	
	/**
	 * Gets all available keyboards from {@code /dev/input/by-id}. Keyboard events end with
	 * {@code -event-kbd}.
	 * 
	 * @return The list of available Keyboards.
	 */
	public static List<String> getKeyboardsByID() {
		ArrayList<String> ids = new ArrayList<>();
		File[] files = new File("/dev/input/by-id/").listFiles(file -> file.getName().endsWith("-event-kbd"));
		ids.addAll(Arrays.asList(files).stream().map(File::getName).toList());
		return ids;
	}
	
	/**
	 * Gets all available mice from {@code /dev/input/by-id}. Mouse events end with
	 * {@code -event-mouse}.
	 * 
	 * @return The list of available Mice.
	 */
	public static List<String> getMiceByID() {
		ArrayList<String> ids = new ArrayList<>();
		File[] files = new File("/dev/input/by-id/").listFiles(file -> file.getName().endsWith("-event-mouse"));
		ids.addAll(Arrays.asList(files).stream().map(File::getName).toList());
		return ids;
	}
	
	public boolean isMouseReader() {
		return isMouseReader;
	}
}
