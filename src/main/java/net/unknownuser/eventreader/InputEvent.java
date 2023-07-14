package net.unknownuser.eventreader;

/**
 * The struct input_event as a Java record.
 * 
 * <pre>
 * [====64====][====64====][=16=][=16=][==32==] => 192 bits
 * [---- 8----][---- 8----][- 2-][- 2-][-- 4--] -> 24 bytes
 * time                    type  code  value
 * sec         microsec
 * </pre>
 */
public record InputEvent(long secs, long usecs, short type, short code, int value) {
	/**
	 * Returns whether this event is a separator event.<br>
	 * Separator events are input events, where the type, code, and value field are 0.
	 * 
	 * @return {@code true} if the event is a separator, {@code false} otherwise.
	 */
	public boolean isSeparator() {
		return type == 0 && code == 0 && value == 0;
	}
}