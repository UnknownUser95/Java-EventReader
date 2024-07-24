package net.unknownuser.eventreader.codes;

import java.util.*;

public enum SyncEvents {
	/**
	 * Used for synchronization and separation of events into packages.<br>
	 * e.g.: a mouse can send REL_X and REL_Y signals followed by REPORT. The REL_X and REL_Y are separate events, but
	 * belong to the same movement.
	 */
	REPORT(0x0),
	CONFIG(0x1),
	MULTITOUCH_REPORT(0x2),
	DROPPED(0x3);
	
	public final short value;
	
	SyncEvents(int value) {
		this.value = (short) value;
	}
	
	public static Optional<SyncEvents> fromShort(short value) {
		for (SyncEvents event : values()) {
			if (event.value == value) {
				return Optional.of(event);
			}
		}
		
		return Optional.empty();
	}
}