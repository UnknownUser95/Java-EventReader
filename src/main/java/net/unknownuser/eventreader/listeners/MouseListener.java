package net.unknownuser.eventreader.listeners;

import net.unknownuser.eventreader.*;
import net.unknownuser.eventreader.codes.*;
import net.unknownuser.eventreader.mouse.*;

import java.util.*;

public interface MouseListener extends Listener {
	void move(InputEvent event);
	void scroll(InputEvent event);
	void buttonPressed(InputEvent event);
	void buttonReleased(InputEvent event);
	void otherInput(InputEvent event);
	
	@Override
	default void handleEvent(InputEvent event) {
		if (event.type() == Type.RELATIVE_AXIS_CHANGED.value) {
			final MouseCode mouseCode = MouseCode.fromShort(event.code()).get();
			
			// yes, there are this many scroll wheel types
			if (mouseCode == MouseCode.SCROLL_WHEEL || mouseCode == MouseCode.SCROLL_HWHEEL || mouseCode == MouseCode.SCROLL_WHEEL_HI_RES || mouseCode == MouseCode.SCROLL_HWHEEL_HI_RES) {
				scroll(event);
			} else {
				move(event);
			}
		} else {
			Optional<Value> value = Value.fromInt(event.value());
			
			if (value.isEmpty()) {
				otherInput(event);
				return;
			}
			
			if (value.get() == Value.KEY_PRESSED) {
				buttonPressed(event);
			} else {
				buttonReleased(event);
			}
		}
	}
}
