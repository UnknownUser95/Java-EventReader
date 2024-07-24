package net.unknownuser.eventreader.codes;

import java.util.*;

public enum Sound {
	SND_CLICK(0x00),
	SND_BELL(0x01),
	SND_TONE(0x02);
	
	public final short value;
	
	Sound(int value) {
		this.value = (short) value;
	}
	
	public static Optional<Sound> fromShort(short value) {
		for (Sound sound : values()) {
			if (sound.value == value) {
				return Optional.of(sound);
			}
		}
		
		return Optional.empty();
	}
}
