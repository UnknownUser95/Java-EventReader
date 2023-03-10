package net.unknownuser.eventreader;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public class ThreadLauncher implements Runnable {
	private final EventReader reader;
	
	private final LinkedBlockingDeque<InputEvent> events = new LinkedBlockingDeque<>();
	private final ArrayList<Tuple<Thread, ListenerThread>> listeners = new ArrayList<>();
	
	public ThreadLauncher(EventReader reader) {
		super();
		this.reader = reader;
	}
	
	protected void addListener(Listener listener) {
		ListenerThread listenerThr = new ListenerThread(reader, listener);
		Thread thr = new Thread(listenerThr, "listener");
		listeners.add(new Tuple<>(thr, listenerThr));
		thr.start();
	}
	
	protected void addEvent(InputEvent event) {
		events.add(event);
	}
	
	protected void discard() {
		for(var thr : listeners) {
			thr.a.interrupt();
		}
	}
	
	@Override
	public void run() {
		while(!reader.isDiscarded()) {
			try {
				InputEvent event = events.take();

				for(var thr : listeners) {
					thr.b.addEvent(event);
				}
			} catch(InterruptedException exc) {}
		}
	}
}
