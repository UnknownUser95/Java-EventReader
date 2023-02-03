package net.unknownuser.eventreader.listeners;

import net.unknownuser.eventreader.InputEvent;
import net.unknownuser.eventreader.Listener;
import net.unknownuser.eventreader.codes.Type;
import net.unknownuser.eventreader.codes.Value;
import net.unknownuser.eventreader.mouse.Code;

public interface MouseListener extends Listener {
	public void move(InputEvent event);
	public void scroll(InputEvent event);
	public void buttonPressed(InputEvent event);
	public void buttonReleased(InputEvent event);
	public void otherInput(InputEvent event);
	
	@Override
	default void sortEvent(InputEvent event) {
		if(event.type() == Type.RELATIVE_AXIS_CHANGED) {
			if(event.code() == Code.SCROLL_ABS || event.code() == Code.SCROLL_REL) {
				scroll(event);
			} else {
				move(event);
			}
		} else {
			switch(event.value()) {
			case Value.KEY_PRESSED -> buttonPressed(event);
			case Value.KEY_RELEASED -> buttonReleased(event);
			default -> otherInput(event);
			}
		}
	}
}
