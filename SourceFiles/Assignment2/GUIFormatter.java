package Assignment2;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GUIFormatter 
{
		GUIFormatter()
		{}
	
		//Method to return a text and textField with correct formatting
		TextField textAndInput(String textName, VBox vContainer, String data)
		{
			HBox container = new HBox();
			
			Text text = new Text(textName);
			TextField textField = new TextField(data);
			
			textField.setMinWidth(150);
			
			container.setAlignment(Pos.BASELINE_RIGHT);
			container.setSpacing(10);
			container.setPadding(new Insets(5));
			//container.setPadding(new Insets(10, 50, 50, 50));
			container.getChildren().add(text);
			container.getChildren().add(textField);
			
			vContainer.getChildren().add(container);
			
			return textField;
		}
		

		//Method to return a text and combobox with correct formatting
		ComboBox<String> textAndCombo(String textName, VBox vContainer, ArrayList<String> data)
		{
			HBox container = new HBox();
			
			Text text = new Text(textName);
		
			ComboBox<String> combo = new ComboBox<String>();
			for(String name: data)
			{
				combo.getItems().add(name);
			}
			combo.setMinWidth(150);
			
			container.setAlignment(Pos.BASELINE_RIGHT);
			container.setSpacing(10);
			container.setPadding(new Insets(5));
			//container.setPadding(new Insets(10, 50, 50, 50));
			container.getChildren().add(text);
			container.getChildren().add(combo);
			
			vContainer.getChildren().add(container);
			
			return combo;
		}
}
