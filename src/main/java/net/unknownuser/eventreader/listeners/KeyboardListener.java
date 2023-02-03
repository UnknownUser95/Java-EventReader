package net.unknownuser.eventreader.listeners;

import net.unknownuser.eventreader.InputEvent;
import net.unknownuser.eventreader.Listener;
import net.unknownuser.eventreader.codes.Value;

public interface KeyboardListener extends Listener {
	public void keyPressed(InputEvent event);
	public void keyReleased(InputEvent event);
	public void otherInput(InputEvent event);
	
	public default void sortEvent(InputEvent event) {
		switch(event.value()) {
		case Value.KEY_PRESSED -> keyPressed(event);
		case Value.KEY_RELEASED -> keyReleased(event);
		default -> otherInput(event);
		}
	}
}
