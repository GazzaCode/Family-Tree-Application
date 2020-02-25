package Assignment2;

import java.io.Serializable;
import java.util.ArrayList;


public class Person implements Serializable
{
	//Describe a person
	//Used in client as Node<Person>
	//All nodes have a parent and children and data, Person is the data in this case.
	
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String surnameAtBirth;
	private String surnameAfterMarriage;
	private String gender;
	
	private Address address;
	
	private String lifeDescription;
	
	private ArrayList<Person> children = new ArrayList<>();
	private ArrayList<Person> parents = new ArrayList<>();
	private Person spouse = null;
	
	
	public Person(String name, String surname, String surnameMarriage, String gen, Address add, String lifeDesc)
	{
		this.firstName = name;
		this.surnameAtBirth = surname;
		this.surnameAfterMarriage = surnameMarriage;
		this.gender = gen;
		this.address = add;
		this.lifeDescription = lifeDesc;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurnameAtBirth() {
		return surnameAtBirth;
	}

	public void setSurnameAtBirth(String surnameAtBirth) {
		this.surnameAtBirth = surnameAtBirth;
	}

	public String getSurnameAfterMarriage() {
		return surnameAfterMarriage;
	}

	public void setSurnameAfterMarriage(String surnameAfterMarriage) {
		this.surnameAfterMarriage = surnameAfterMarriage;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getLifeDescription() {
		return lifeDescription;
	}

	public void setLifeDescription(String lifeDescription) {
		this.lifeDescription = lifeDescription;
	}
	
	public void addSpouse(Person spouse)
	{
		this.spouse = spouse;
	}
	
	Person getSpouse()
	{
		return this.spouse;
	}
	
	public void addParent(Person parent)
	{
		if(this.parents.size() >= 2)
		{
			System.out.println("Cannot add more than two parents");
		}
		else
			this.parents.add(parent);
	}
	
	ArrayList<Person> getParents()
	{
		return this.parents;
	}
	
	public void addChild(Person child)
	{
		this.children.add(child);
	}
	
	public ArrayList<Person> getChildren() {
		return children;
	}
	
	public void setChildren(ArrayList<Person> children) {
		this.children = children;
	}
	
	@Override
	public String toString() {
		//return firstName + ", " + surnameAtBirth + ", "
		//		+ surnameAfterMarriage + ", " + gender + ", " + lifeDescription + ", "
		//		+ '\n' + "		" + address;
		
		return firstName;
	}


}
