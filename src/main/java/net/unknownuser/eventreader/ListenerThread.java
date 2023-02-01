package net.unknownuser.eventreader;

import java.util.concurrent.LinkedBlockingDeque;

import net.unknownuser.eventreader.codes.Value;

public class ListenerThread implements Runnable {
	private final EventReader reader;
	private final KeyListener listener;
	
	private final LinkedBlockingDeque<InputEvent> events = new LinkedBlockingDeque<>();
	
	protected ListenerThread(EventReader reader, KeyListener listener) {
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
					switch(event.value()) {
					case Value.KEY_PRESSED -> listener.keyPressed(event);
					case Value.KEY_RELEASED -> listener.keyReleased(event);
					default -> listener.otherInput(event);
				
					}
				} catch(Exception exc) {
					System.err.println("error during key handling: " + exc.getLocalizedMessage());
				}
			} catch(InterruptedException exc) {}
		}
	}
}
