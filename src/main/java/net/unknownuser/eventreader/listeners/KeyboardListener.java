package net.unknownuser.eventreader.listeners;

import net.unknownuser.eventreader.*;
import net.unknownuser.eventreader.codes.*;

import java.util.*;

public interface KeyboardListener extends Listener {
	void keyPressed(InputEvent event);
	void keyReleased(InputEvent event);
	void otherInput(InputEvent event);
	
	default void handleEvent(InputEvent event) {
		Optional<Value> value = Value.fromInt(event.value());
		
		if (value.isEmpty()) {
			otherInput(event);
			return;
		}
		
		if (value.get() == Value.KEY_PRESSED) {
			keyPressed(event);
		} else {
			keyReleased(event);
		}
	}
}
