package net.unknownuser.eventreader;

class Test implements KeyListener {
	
	public static void main(String[] args) {
		System.out.println("available keyboards:");
		System.out.println(EventReader.getKeyboardsByID());
		System.out.println("available mice:");
		System.out.println(EventReader.getMiceByID());

		System.out.println("starting");
		// usb-CHERRY_CHERRY_Keyboard-event-kbd
		var maybeReader = EventReader.runAtEventID("usb-CHERRY_CHERRY_Keyboard-event-kbd", true);
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
	public void keyPressed(InputEvent key) {
		System.out.println("pressed:  " + key);
	}

	@Override
	public void keyReleased(InputEvent key) {
		System.out.println("released: " + key);
	}
	
	@Override
	public void otherInput(InputEvent event) {
		System.out.println("other:    " + event);
	}
}
