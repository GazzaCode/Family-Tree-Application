package Assignment2;

import java.io.Serializable;

public class Address implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	private int streetNumber;
	private String streetName;
	private String suburb;
	private int postCode;
	
	public Address(int streetNumber, String streetName, String suburb, int postCode) {
		super();
		this.streetNumber = streetNumber;
		this.streetName = streetName;
		this.suburb = suburb;
		this.postCode = postCode;
	}
	
	public int getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getSuburb() {
		return suburb;
	}
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
	public int getPostCode() {
		return postCode;
	}
	public void setPostCode(int postCode) {
		this.postCode = postCode;
	}
	
	
	@Override
	public String toString() {
		return streetNumber + ", " + streetName + ", " + suburb
				+ ", " + postCode;
	}
	
}
