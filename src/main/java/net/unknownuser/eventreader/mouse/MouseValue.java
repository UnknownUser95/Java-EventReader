package net.unknownuser.eventreader.mouse;

import java.util.*;

public enum MouseValue {
	MOVE_UP(-1),
	MOVE_DOWN(1),
	MOVE_LEFT(-1),
	MOVE_RIGHT(1),
	PICK_UP(2),
	SET_DOWN(-2);
	
	public final int value;
	
	MouseValue(final int value) {
		this.value = value;
	}
	
	public static Optional<MouseValue> fromInt(final int value) {
		for (MouseValue button : values()) {
			if (button.value == value) {
				return Optional.of(button);
			}
		}
		
		return Optional.empty();
	}
}
