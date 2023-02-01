package net.unknownuser.eventreader.codes;

public abstract class Type {
	public static final byte SEPARATOR = 0x00;
	public static final byte KEY_CHANGED = 0x01;
	public static final byte RELATIVE_AXIS_CHANGED = 0x02;
	public static final byte ABSOLUTE_AXIS_CHANGED = 0x03;
	public static final byte MISCELLANEOUS_DATA = 0x04;
	public static final byte BINARY_STATE_INPUT_SWITCH = 0x05;
	public static final byte LED = 0x11;
	public static final byte SOUND = 0x12;
	public static final byte AUTOREPEAT = 0x14;
	public static final byte FORCE_FEEDBACK = 0x15;
	public static final byte POWER = 0x16;
	public static final byte FORCE_FEEDBACK_STATUS = 0x17;
}