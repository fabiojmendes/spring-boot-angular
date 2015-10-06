package org.demo.rest;

/**
 * @author Fabio Mendes <fabio.mendes@navita.com.br> Oct 5, 2015
 *
 */
public class ResourceForm {

	String name;

	/** */
	public ResourceForm() {
	}

	/** */
	public ResourceForm(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
