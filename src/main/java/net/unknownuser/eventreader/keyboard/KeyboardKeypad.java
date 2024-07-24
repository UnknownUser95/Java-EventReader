package net.unknownuser.eventreader.keyboard;

import java.util.*;

/**
 * code
 */
public enum KeyboardKeypad {
	NUMBER_0(82),
	NUMBER_1(79),
	NUMBER_2(80),
	NUMBER_3(81),
	NUMBER_4(75),
	NUMBER_5(76),
	NUMBER_6(77),
	NUMBER_7(71),
	NUMBER_8(72),
	NUMBER_9(73),
	
	DOT(83),
	PLUS(78),
	MINUS(74),
	ASTERISK(55),
	SLASH(98),
	COMMA(121),
	ENTER(96),
	
	EQUAL(117),
	PLUSMINUS(118);
	
	public final short value;
	
	KeyboardKeypad(int value) {
		this.value = (short) value;
	}
	
	public static Optional<KeyboardKeypad> fromShort(short value) {
		for (KeyboardKeypad key : values()) {
			if (key.value == value) {
				return Optional.of(key);
			}
		}
		
		return Optional.empty();
	}
}
