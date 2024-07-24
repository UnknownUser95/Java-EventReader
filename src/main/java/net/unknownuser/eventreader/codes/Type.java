package net.unknownuser.eventreader.codes;

import java.util.*;

public enum Type {
	SEPARATOR(0x00),
	KEY_CHANGED(0x01),
	RELATIVE_AXIS_CHANGED(0x02),
	ABSOLUTE_AXIS_CHANGED(0x03),
	MISCELLANEOUS_DATA(0x04),
	BINARY_STATE_INPUT_SWITCH(0x05),
	LED(0x11),
	SOUND(0x12),
	AUTOREPEAT(0x14),
	FORCE_FEEDBACK(0x15),
	POWER(0x16),
	FORCE_FEEDBACK_STATUS(0x17);
	
	public final short value;
	
	Type(int value) {
		this.value = (short) value;
	}
	
	public static Optional<Type> fromShort(short value) {
		for (Type type : values()) {
			if (type.value == value) {
				return Optional.of(type);
			}
		}
		
		return Optional.empty();
	}
}