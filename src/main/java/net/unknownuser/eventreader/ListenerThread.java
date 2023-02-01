package net.unknownuser.eventreader;

import java.util.concurrent.LinkedBlockingDeque;

public class ListenerThread implements Runnable {
	private final EventReader<?> reader;
	private final Listener listener;
	
	private final LinkedBlockingDeque<InputEvent> events = new LinkedBlockingDeque<>();
	
	protected ListenerThread(EventReader<?> reader, Listener listener) {
		super();
		this.reader = reader;
		this.listener = listener;
	}
	
	protected void addEvent(InputEvent event) {
		events.add(event);
	}
	
	@Override
	public void run() {
		while(!reader.isDiscarded()) {
			try {
				InputEvent event = events.take();
				
				try {
					listener.sortEvent(event);
				} catch(Exception exc) {
					System.err.println("error during key handling: " + exc.getLocalizedMessage());
				}
			} catch(InterruptedException exc) {}
		}
	}
}
