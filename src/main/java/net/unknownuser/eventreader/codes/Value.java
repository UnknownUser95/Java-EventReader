package net.unknownuser.eventreader.codes;

import java.util.*;

public enum Value {
	KEY_PRESSED(0x1),
	KEY_RELEASED(0x0);
	
	public final int value; // NOSONAR
	
	Value(final int value) {
		this.value = value;
	}
	
	public static Optional<Value> fromInt(int value) {
		for (Value v : values()) {
			if (v.value == value) {
				return Optional.of(v);
			}
		}
		
		return Optional.empty();
	}
}
