package net.unknownuser.eventreader;

class Test implements MouseListener {
	
	public static void main(String[] args) {
		System.out.println("available keyboards:");
		System.out.println(EventReader.getKeyboardsByID());
		System.out.println("available mice:");
		System.out.println(EventReader.getMiceByID());

		System.out.println("starting");
		Tuple<String, Class<KeyboardListener>> listener = new Tuple<>("usb-CHERRY_CHERRY_Keyboard-event-kbd", KeyboardListener.class);
//		Tuple<String, Class<MouseListener>> listener = new Tuple<>("usb-Razer_Razer_Basilisk_V2-event-mouse", MouseListener.class);
		var maybeReader = EventReader.runAtEventID(listener.a, true, false, listener.b);
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
	
	@Override
	public void move(InputEvent event) {
		System.out.println("move:     " + event);
	}
}
