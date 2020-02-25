package Assignment2;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CreateRoot extends VBox
{
	GUIFormatter formatter = new GUIFormatter();
	

	FamilyTree newFam = new FamilyTree();
	
	//Make these global to the class so the values can be read later.
	TextField name = null;
	TextField surnameBM = null;
	TextField surnameAM = null;
	//TextField gender = null;
	ComboBox<String> gender = null;
	TextField info = null;
	
	TextField streetNumber = null;
	TextField streetName = null;
	TextField suburb = null;
	TextField postcode = null;
	
	Button addRoot = null;
	
	Person newRoot;
	Address newAddress;
	
	
	CreateRoot()
	{
		name = formatter.textAndInput("First name", this, "");
		surnameBM = formatter.textAndInput("Surname before marriage", this, "");
		surnameAM = formatter.textAndInput("Surname after marriage", this, "");
		ArrayList<String> genders = new ArrayList<String>();
		genders.add("Male");
		genders.add("Female");
		gender = formatter.textAndCombo("Gender", this, genders);
		info = formatter.textAndInput("Description", this, "");
		
		
		streetNumber = formatter.textAndInput("Street number", this, "");
		streetName = formatter.textAndInput("Street name", this, "");
		suburb = formatter.textAndInput("Suburb", this, "");
		postcode = formatter.textAndInput("Postcode", this, "");
		
		//TextField parent = textAndInput("Parent", newPane);
		addRoot = new Button("Add root");
		
		getChildren().add(addRoot);
	}
	
	Address getAddress()
	{
		newAddress = new Address(Integer.valueOf(streetNumber.getText()), streetName.getText(), suburb.getText(), Integer.valueOf(postcode.getText()));
		
		return newAddress;
	}
	
	Person getPerson()
	{
		newRoot = new Person(name.getText(), surnameBM.getText(), surnameAM.getText(),  gender.getSelectionModel().getSelectedItem().toString() , 
								getAddress(), info.getText());
		
		return newRoot;
	}
	
	VBox getCreateRootGUI()
	{
		return this;
	}
	
	Button getAddRootButton()
	{
		return addRoot;
	}
	
}
