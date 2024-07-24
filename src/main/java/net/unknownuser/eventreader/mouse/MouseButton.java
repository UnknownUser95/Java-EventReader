package net.unknownuser.eventreader.mouse;

import java.util.*;

/**
 * code-type.
 */
public enum MouseButton {
	LEFT(0x110),
	RIGHT(0x111),
	MIDDLE(0x112),
	SIDE(0x113),
	EXTRA(0x114),
	FORWARD(0x115),
	BACK(0x116),
	TASK(0x117);
	
	public final short value;
	
	MouseButton(int value) {
		this.value = (short) value;
	}
	
	public static Optional<MouseButton> fromShort(final short value) {
		for (MouseButton button : values()) {
			if (button.value == value) {
				return Optional.of(button);
			}
		}
		
		return Optional.empty();
	}
}
