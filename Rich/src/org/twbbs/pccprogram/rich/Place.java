package org.twbbs.pccprogram.rich;

import java.util.Objects;

public class Place {
	private String name;

	public Place(String name) {
		this.name = Objects.requireNonNull(name);
	}

	public String getName() {
		return name;
	}
}
