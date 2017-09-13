package org.example.ws.model;

import java.math.BigInteger;
import java.util.List;

public class Course {
	
	private BigInteger id;

	private String Name;
	
	private List<Greeting> greet;	
	
	public Course(){
		
	}
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public List<Greeting> getGreet() {
		return greet;
	}

	public void setGreet(List<Greeting> greet) {
		this.greet = greet;
	}

}
