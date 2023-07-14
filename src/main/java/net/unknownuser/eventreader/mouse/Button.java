package net.unknownuser.eventreader.mouse;

/**
 * code-type.
 */
public abstract class Button {
	private Button() {}
	
	public static final short LEFT	  = 0x110;
	public static final short RIGHT	  = 0x111;
	public static final short MIDDLE  = 0x112;
	public static final short SIDE	  = 0x113;
	public static final short EXTRA	  = 0x114;
	public static final short FORWARD = 0x115;
	public static final short BACK	  = 0x116;
	public static final short TASK	  = 0x117;
}
