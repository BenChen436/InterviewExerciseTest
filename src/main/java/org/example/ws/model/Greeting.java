package org.example.ws.model;

import java.math.BigInteger;

public class Greeting {

	private String text;
	
	private BigInteger id;
	
	public Greeting(){
		
	}
	
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
