package net.unknownuser.eventreader.mouse;

import java.util.*;

public enum MouseCode {
	MOVEMENT_X(0x00),
	MOVEMENT_Y(0x01),
	MOVEMENT_Z(0x02),
	MOVEMENT_RELATIVE_X(0x03),
	MOVEMENT_RELATIVE_Y(0x04),
	MOVEMENT_RELATIVE_Z(0x05),
	SCROLL_HWHEEL(0x06),
	SCROLL_WHEEL(0x08),
	SCROLL_HWHEEL_HI_RES(0x0b),
	SCROLL_WHEEL_HI_RES(0x0c);
	
	public final short value;
	
	MouseCode(final int value) {
		this.value = (short) value;
	}
	
	public static Optional<MouseCode> fromShort(final short value) {
		for (MouseCode button : values()) {
			if (button.value == value) {
				return Optional.of(button);
			}
		}
		
		return Optional.empty();
	}
}
