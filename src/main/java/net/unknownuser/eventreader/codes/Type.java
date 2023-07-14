package net.unknownuser.eventreader.codes;

public abstract class Type {
	public static final short SEPARATOR					= 0x00;
	public static final short KEY_CHANGED				= 0x01;
	public static final short RELATIVE_AXIS_CHANGED		= 0x02;
	public static final short ABSOLUTE_AXIS_CHANGED		= 0x03;
	public static final short MISCELLANEOUS_DATA		= 0x04;
	public static final short BINARY_STATE_INPUT_SWITCH	= 0x05;
	public static final short LED						= 0x11;
	public static final short SOUND						= 0x12;
	public static final short AUTOREPEAT				= 0x14;
	public static final short FORCE_FEEDBACK			= 0x15;
	public static final short POWER						= 0x16;
	public static final short FORCE_FEEDBACK_STATUS		= 0x17;
}