package Assignment2;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

//This class creates the GUI needed to edit a family member. It returns a VBOX with the GUI items
//Needs the selected person as it needs to return the edited details
//Editing the relative textboxes will do nothing.
//Not a singleton as the data needs to be refreshed each time.
public class EditNode extends VBox
{
	//These are added to this and seperated with the VBox constructor
	//Will hold personal details textboxes
	VBox personDetailsBox = new VBox();
	//Will hold address details textboxes
	VBox addressDetailsBox = new VBox();
	//Will hold relation details textboxes
	VBox relationDetailsBox = new VBox();
	
	GUIFormatter formatter = new GUIFormatter();
	
	//Make these global to the class so the values can be read later.
	TextField name = null;
	TextField surnameBM = null;
	TextField surnameAM = null;
	ComboBox<String> gender = null;
	TextField info = null;
	
	TextField streetNumber = null;
	TextField streetName = null;
	TextField suburb = null;
	TextField postcode = null;
	
	Button save;
	
	//Store selected into a new person, this will guarentee selected cannot be tampered with in the main GUI
	Person edittedPerson;
	
	EditNode(Person selected)
	{
		
		super(35);
		this.setPadding(new Insets(0, 20, 0, 0));
		edittedPerson = selected;
		
		//PERSONAL INFO
		name = new TextField(edittedPerson.getFirstName());
		surnameBM = new TextField(edittedPerson.getSurnameAtBirth());
		surnameAM = new TextField(edittedPerson.getSurnameAfterMarriage());
		
		ArrayList<String> genders = new ArrayList<String>();
		genders.add("Male");
		genders.add("Female");
		gender = new ComboBox<String>();
		
		for(String gen: genders)
		{
			gender.getItems().add(gen);
		}
		
		gender.getSelectionModel().select(selected.getGender());
		
		info = new TextField(edittedPerson.getLifeDescription());
		
		personDetailsBox.getChildren().add(name);
		personDetailsBox.getChildren().add(surnameBM);
		personDetailsBox.getChildren().add(surnameAM);
		personDetailsBox.getChildren().add(gender);
		personDetailsBox.getChildren().add(info);
		
		//ADDRESS INFO
		streetNumber = new TextField(Integer.toString(edittedPerson.getAddress().getStreetNumber()));
		streetName = new TextField(edittedPerson.getAddress().getStreetName());
		suburb = new TextField(edittedPerson.getAddress().getSuburb());
		postcode = new TextField(Integer.toString(edittedPerson.getAddress().getPostCode()));
		
		addressDetailsBox.getChildren().add(streetNumber);
		addressDetailsBox.getChildren().add(streetName);
		addressDetailsBox.getChildren().add(suburb);
		addressDetailsBox.getChildren().add(postcode);
		
		//RELATION INFO
		TextField mother = new TextField();
		TextField father= new TextField();
		TextField spouse= new TextField();
		TextField children= new TextField();
		
		if(edittedPerson.getParents() != null)
		{
			mother.setText(edittedPerson.getParents().toString());
		}
		if(edittedPerson.getParents() != null)
		{
			father.setText(edittedPerson.getParents().toString());
		}
		if(edittedPerson.getSpouse() != null)
		{
			spouse.setText(edittedPerson.getSpouse().toString());
		}
		if(edittedPerson.getChildren() != null)
		{
			children.setText(edittedPerson.getChildren().toString());
		}
		
		relationDetailsBox.getChildren().add(mother);
		relationDetailsBox.getChildren().add(father);
		relationDetailsBox.getChildren().add(spouse);
		relationDetailsBox.getChildren().add(children);
		
		save = new Button("Save");
		relationDetailsBox.getChildren().add(save);
		
		//ADD ALL ITEMS TO THIS
		getChildren().add(personDetailsBox);
		getChildren().add(addressDetailsBox);
		getChildren().add(relationDetailsBox);
	}
	
	//Return the save button to add a listener to it
	Button getSaveButton()
	{
		return save;
	}
	
	//returns the updated person
	Person getPerson()
	{
		edittedPerson.setFirstName(name.getText());
		edittedPerson.setSurnameAtBirth(surnameBM.getText());
		edittedPerson.setSurnameAfterMarriage(surnameAM.getText());
		edittedPerson.setGender(gender.getSelectionModel().getSelectedItem().toString());
		edittedPerson.setLifeDescription(info.getText());
		
		Address tempAdd = new Address(Integer.valueOf(streetNumber.getText()), streetName.getText(), suburb.getText(), Integer.valueOf(postcode.getText()));
		edittedPerson.setAddress(tempAdd);
		
		return edittedPerson;
	}
	
	VBox getEditNodeGUI()
	{
		return this;
	}
}
