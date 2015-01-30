package org.apache.log4j;

import org.apache.log4j.spi.LoggingEvent;

@SuppressWarnings("serial")
public class MockEvent extends LoggingEvent {

	public MockEvent() {
		super("", new Category("as"), 1, new Priority(), new Object(),
				new Throwable());
	}
}
