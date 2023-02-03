package net.unknownuser.eventreader;

import net.unknownuser.eventreader.listeners.MouseListener;

class Test implements MouseListener {
	
	public static void main(String[] args) {
		System.out.println("available keyboards:");
		System.out.println(EventReader.getKeyboardsByID());
		System.out.println("available mice:");
		System.out.println(EventReader.getMiceByID());

		System.out.println("starting");
//		String id = "usb-CHERRY_CHERRY_Keyboard-event-kbd";
		String id = "usb-Razer_Razer_Basilisk_V2-event-mouse";
		var maybeReader = EventReader.runAtEventID(id, true, true);
		if(maybeReader.isEmpty()) {
			System.err.println("no reader");
			return;
		}
		maybeReader.get().addListener(new Test());
		
		try {
			Thread.sleep(10000);
		} catch(InterruptedException ignore) {}
		maybeReader.get().discard();
		System.out.println("done");
	}

	@Override
	public void buttonPressed(InputEvent key) {
		System.out.println("pressed:  " + key);
	}

	@Override
	public void buttonReleased(InputEvent key) {
		System.out.println("released: " + key);
	}
	
	@Override
	public void otherInput(InputEvent event) {
		System.out.println("other:    " + event);
	}
	
	@Override
	public void move(InputEvent event) {
		System.out.println("move:     " + event);
	}

	@Override
	public void scroll(InputEvent event) {
		System.out.println("scroll    " + event);
	}
}
