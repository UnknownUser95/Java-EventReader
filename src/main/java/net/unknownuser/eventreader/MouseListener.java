package net.unknownuser.eventreader;

import net.unknownuser.eventreader.codes.Type;

public interface MouseListener extends KeyboardListener {
	public void move(InputEvent event);
	
	@Override
	default void sortEvent(InputEvent event) {
		if(event.type() == Type.RELATIVE_AXIS_CHANGED) {
			move(event);
		} else {
			KeyboardListener.super.sortEvent(event);
		}
	}
}
