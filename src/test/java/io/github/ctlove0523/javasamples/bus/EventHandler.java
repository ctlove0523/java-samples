package io.github.ctlove0523.javasamples.bus;

import java.util.ArrayList;
import java.util.List;

class EventHandler implements Handler {
	private List<String> messages = new ArrayList<>();

	@Override
	public void handle(Event event) {
		messages.add(event.getContent().toString());
	}

	public List<String> getMessages() {
		return new ArrayList<>(messages);
	}
}
