package net.unknownuser.eventreader.impl;

import net.unknownuser.eventreader.*;
import net.unknownuser.eventreader.listeners.*;
import net.unknownuser.eventreader.mouse.*;

class ReaderImpl implements MouseListener {
	
	public static void main(String[] args) {
		/*
		 * This is a live test an EventReader.
		 */
		System.out.println("available keyboards:");
		System.out.println(EventReader.getKeyboardsByID());
		System.out.println("available mice:");
		System.out.println(EventReader.getMiceByID());
		
		System.out.println("starting");
		//		String id		   = "usb-CHERRY_CHERRY_Keyboard-event-kbd";
		String id          = "usb-Razer_Razer_Basilisk_V2-event-mouse";
		var    maybeReader = EventReader.runAtEventID(id, true, true);
		if (maybeReader.isEmpty()) {
			System.err.println("no reader, check id");
			return;
		}
		maybeReader.get().addListener(new ReaderImpl());
		
		try {
			Thread.sleep(10 * 1000L);
		} catch (InterruptedException ignore) {
		}
		
		maybeReader.get().discard();
		
		System.out.println("done");
	}
	
	@Override
	public void buttonPressed(InputEvent key) {
		System.out.printf("pressed:  %s (%s)%n", key, MouseButton.fromShort(key.code()).get());
	}
	
	@Override
	public void buttonReleased(InputEvent key) {
		System.out.printf("pressed:  %s (%s)%n", key, MouseButton.fromShort(key.code()).get());
	}
	
	@Override
	public void otherInput(InputEvent event) {
		System.out.println("other:    " + event);
	}
	
	@Override
	public void move(InputEvent event) {
		System.out.printf("move:     %s by %d%n", MouseCode.fromShort(event.code()).get(), event.value());
	}
	
	@Override
	public void scroll(InputEvent event) {
		System.out.println("scroll:   " + event);
	}
}
