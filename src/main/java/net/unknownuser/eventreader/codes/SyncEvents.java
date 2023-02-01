package net.unknownuser.eventreader.codes;

public abstract class SyncEvents {
	/**
	 * Used for synchronization and separation of events into packages.<br>
	 * e.g.: a mouse can send REL_X and REL_Y signals followed by REPORT. The REL_X and REL_Y are
	 * separate events, but belong to the same movement.
	 */
	public static final byte REPORT = 0x0;
	public static final byte CONFIG = 0x1;
	public static final byte MULTITOUCH_REPORT = 0x2;
	public static final byte DROPPED = 0x3;
}