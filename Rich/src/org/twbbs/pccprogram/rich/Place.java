package org.twbbs.pccprogram.rich;

import java.util.Objects;

/**
 * The data of a place.
 * 
 * @author johnchen902
 */
public class Place {
	private String name;

	/**
	 * Create a place with specific name.
	 * 
	 * @param name
	 *            its name
	 */
	public Place(String name) {
		this.name = Objects.requireNonNull(name);
	}

	/**
	 * Get the name of this place.
	 * 
	 * @return its name
	 */
	public String getName() {
		return name;
	}
}
