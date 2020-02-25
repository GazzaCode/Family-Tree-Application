package Assignment2;


import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI extends Application
{
	final Image male = new Image(getClass().getResourceAsStream("/images/male.png"));
	final Image female =  new Image(getClass().getResourceAsStream("/images/female.png"));
	final Image nonPerson =  new Image(getClass().getResourceAsStream("/images/noGender.png"));
	
	private static GUI single_instance = null; 
	GUIFormatter formatter = new GUIFormatter();
	//The family tree to be inserted into the TreeView.
	private FamilyTree fam = new FamilyTree(); 
	
	//Root of the FamilyTree
	private TreeItem<Person> root;
	
	//This is the main container to hold all GUI items
	private BorderPane pane = new BorderPane();
	
		//The main treeView to be shown, this is the left border of pane
		private TreeView<Person> treeView = new TreeView<Person>(); 
		
		private VBox defaultView = new VBox();
		
		//this will hold the entire right of the GUI, this is the right border of pane 
		private BorderPane detailsBox = new BorderPane();
		
			//Where the person details are shown, this is set to the right side of detailsBox
			private TextArea details;
			
				Button editDetails = null;
				Button addRelative = null;
				
			//The text to identify what data is being displayed, this is set to the left side of details box
			private VBox detailsTitle;
			
			//This is set to the bottom of detailsBox
			private HBox bottomButtons = new HBox();
		
		//Where search, create and load buttons are. this is the top border of pane
		private VBox top = new VBox();
		
			//load and save button, these are set in top
			private Button loadButton;
			private Button saveButton;
		
		//Info pane to show the current operation of the program, this is the bottom of pane
		private HBox infoPane = new HBox();
		private Text infoText = new Text("Program loaded");
	
		
	//This is used for viewing and editing a person in the treeView
	private Person selected;
	
	//Constructor will setup the entire main GUI
	public GUI()
	{
		//Bottom (details) of pane
		infoPane.getChildren().add(infoText);
		infoPane.setMinHeight(10);
		
		//bottomButtons GUI -----------------------------
		editDetails = new Button("Edit details");
		
		editDetails.setOnAction(e -> {
			editNode(selected);
		});
		
		addRelative = new Button("Add relatives");
		
		addRelative.setOnAction(e -> {
			addRelative();
		});
		
		bottomButtons.getChildren().add(editDetails);
		bottomButtons.getChildren().add(addRelative);
		//end of bottomButtons GUI -----------------------------
		
		//The view when the program is first loaded
		Button defaultLoadButton = new Button("Add root person");
		Text defaultText = new Text("Load a tree or add a new root person");
		
		defaultLoadButton.setOnAction(e -> {
					createRoot();
		});
		
		defaultView.getChildren().add(defaultLoadButton);
		defaultView.getChildren().add(defaultText);
		defaultText.setFont(Font.font ("Verdana", 15));
		//-------------------------------------------------------
		
		//gui for the top --------------------------------
		Text title = new Text("Welcome to the Family Tree Application");
		title.setFont(Font.font ("Verdana", 20));
		
		HBox buttons = new HBox();
		
		loadButton = new Button("Load Tree");
		saveButton = new Button("Save Tree");
		
		Button createTree = new Button("Create New Tree");
		

		createTree.setOnAction(e -> {
			createRoot();
		});
		
		buttons.getChildren().add(loadButton);
		buttons.getChildren().add(saveButton);
		buttons.getChildren().add(createTree);
		
		top.getChildren().add(title);
		top.getChildren().add(buttons);
		// ----------------------------------------------------------------
		
		//Details title gui --------------------------------
		detailsTitle = new VBox(8.5);
		detailsTitle.autosize();
		detailsTitle.setDisable(true);
		detailsTitle.setMaxWidth(175);
		detailsTitle.setMinWidth(175);
		
		Text firstName = new Text("First name : ");
		Text surnameBM = new Text("Surname before marriage : ");
		Text surnameAM = new Text("Surname after marriage : ");
		Text gender = new Text("Gender : ");
		Text description = new Text("Life description : ");
		
		//detailsTitle.getChildren().add(personalTitle);
		detailsTitle.getChildren().add(firstName);
		detailsTitle.getChildren().add(surnameBM);
		detailsTitle.getChildren().add(surnameAM);
		detailsTitle.getChildren().add(gender);
		detailsTitle.getChildren().add(description);
		
		//Add address info gui
		Text addressTitle = new Text("Addess info");
		addressTitle.setFont(Font.font ("Verdana", 20));
		Text streetNum = new Text("Street number : ");
		Text streetName = new Text("Street name : ");
		Text suburb = new Text("Suburb : ");
		Text postcode = new Text("Postcode : ");
		
		detailsTitle.getChildren().add(addressTitle);
		detailsTitle.getChildren().add(streetNum);
		detailsTitle.getChildren().add(streetName);
		detailsTitle.getChildren().add(suburb);
		detailsTitle.getChildren().add(postcode);
		
		//relative info guid
		Text relativeTitle = new Text("Relative info");
		relativeTitle.setFont(Font.font ("Verdana", 20));
		Text father = new Text("Parents : ");
		Text spouse = new Text("Spouse : ");
		Text children = new Text("Children : ");
		
		detailsTitle.getChildren().add(relativeTitle);
		detailsTitle.getChildren().add(father);
		detailsTitle.getChildren().add(spouse);
		detailsTitle.getChildren().add(children);
		
		detailsTitle.setPadding(new Insets(8));
		//------------------------------------------------
		
		//details GUI --------------------------------
		details = new TextArea();
		details.autosize();
		details.setMaxWidth(150);
		details.setMinWidth(150);
		
		detailsBox.setLeft(defaultView);
	    detailsBox.autosize();
	    detailsBox.setPadding(new Insets(0,20,0,0));
	    //------------------------------------------------
	    
	    //set items to the pain
	    pane.setTop(top);
	    pane.autosize();
	    //pane.setLeft(treeView);
	    pane.setRight(detailsBox);
	    pane.setBottom(infoPane);
	    pane.setCenter(treeView);
	    
	    //treeView settings
	    treeView.autosize();
	    treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateSelectedItem(newValue));
	}
	
	//Return an instance of the main GUI
	public static GUI getInstance() 
    { 
        if (single_instance == null) 
            single_instance = new GUI(); 
  
        return single_instance; 
    } 

	//Set the load and save button event handlers and stage the main application thread (with GUI setup in constructor)
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		
		
		saveButton.setOnAction(e -> {
			FileChooser chooser = new FileChooser();
			File initialDirectory = new File(".");
			chooser.setInitialDirectory(initialDirectory);
			
			if(root != null)
			{
				infoText.setText("Saving root: " + root.getValue().getFirstName());
				File selectedFile = chooser.showSaveDialog(primaryStage);
				
				if(selectedFile != null)
				{
					fam.saveFamily(selectedFile);
					infoText.setText("Family tree: " + root.getValue().getFirstName() + " has been saved to file");
				}
				else
				{
					System.out.println("No save location selected");
					infoText.setText("Failed : no save location set " + root.getValue().getFirstName());
				}	
			}
			else
			{
				infoText.setText("Failed : attempted to save empty tree");
				System.out.println("No tree loaded to save");
			}
				
			
		});
		
		
		loadButton.setOnAction(e -> {
			FileChooser chooser = new FileChooser();
			File initialDirectory = new File(".");
			chooser.setInitialDirectory(initialDirectory);
			infoText.setText("Selecting family to load");
			File selectedFile = chooser.showOpenDialog(primaryStage);
			
			
			if(selectedFile != null)
			{
				fam = fam.loadFamily(selectedFile);
				treeView.refresh();
				
				//Create a treeItem root with the root of family, this is just the name of the root person
				Person treeRoot = fam.getRoot();
				root = new TreeItem<Person>(treeRoot);
				root.setExpanded(true);
				
				//addFamToTreeView will recursively add every child of a root and add them to the treeView
				addFamToTreeView(root, fam.getRoot());
				
				//set the root item of the tree view
				treeView.setRoot(root);
				
				infoText.setText("Family loaded : " + fam.getRoot());
			}
			else
				infoText.setText("Failed : no load location set");
				
		});
		
		//javafx stuff to show the screen	
	    Scene scene = new Scene(pane);
	    
	    primaryStage.setHeight(700);
	    //primaryStage.setMinWidth(600);
	    //primaryStage.setMaxWidth(600);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Family Tree Application");
	    primaryStage.show();
	
	}
	
	//Event handler for treeView onClick
	private void updateSelectedItem(TreeItem<Person> newValue) 
	{      
		try
		{
			 TreeItem<Person> item = (TreeItem<Person>)newValue;
			 String text = "";
			 if(item.getValue() instanceof Person)
			 {
				 text += item.getValue().getFirstName() + '\n' + '\n';
				 text += item.getValue().getSurnameAtBirth() + '\n' + '\n';
				 text += item.getValue().getSurnameAfterMarriage() + '\n'+ '\n';
				 text += item.getValue().getGender() + '\n'+ '\n';
				 text += item.getValue().getLifeDescription() + '\n' + '\n' + '\n';
				 
				 text += Integer.valueOf(item.getValue().getAddress().getStreetNumber());
				 text += "" + '\n' + '\n'; //Not sure why but wouldent let me create a new line after adding street number
				 text += item.getValue().getAddress().getStreetName() + '\n' + '\n';
				 text += item.getValue().getAddress().getSuburb() + '\n' + '\n';
				 text += item.getValue().getAddress().getPostCode();
				 
				 
				 text += "" + '\n' + '\n' + '\n' + '\n';
				 text += item.getValue().getParents().toString() + '\n' + '\n';
				 
				 if( item.getValue().getSpouse() != null)
				 {
					 text += item.getValue().getSpouse().toString() + '\n' + '\n';
				 }
				 else text += "" + '\n' + '\n';
					 
				
				 text += item.getValue().getChildren().toString() + '\n' + '\n';
				 
				 details.setText(text);
				 
				 details.setFont(Font.font("Verdana", 11));
				 Text personalTitle = new Text( "Personal info");
				personalTitle.setFont(Font.font ("Verdana", 20));
					
				editDetails.setDisable(false);
				addRelative.setDisable(false);
				detailsBox.setTop(personalTitle);
				detailsBox.setLeft(detailsTitle);
				detailsBox.setRight(details);
				detailsBox.setBottom(bottomButtons);
				
				infoText.setText("Display details for : " + item.getValue().getFirstName());
				selected = item.getValue();
			 }
			 else
			 {
				 editDetails.setDisable(true);
				 addRelative.setDisable(true);
				 text = "No person selected";
				 details.setText(text);
				 detailsBox.setRight(details);
				 infoText.setText("Display details for : no family memeber selected");
			 }
				 
				 
			
		}
		catch(NullPointerException e)
		{
			
		}
		catch(ClassCastException e)
		{
			
		}
	}
	
	//Creates a displays the GUI to create a new root
	public void createRoot()
	{
		infoText.setText("Creating new tree...");
		
		CreateRoot createRoot = new CreateRoot();
		FamilyTree newFam = new FamilyTree();
		VBox createRootGUI = createRoot.getCreateRootGUI();
		
		Scene newScene = new Scene(createRootGUI);
		
		Stage stage = new Stage();
		stage.setScene(newScene);
		stage.setTitle("Create a new root");
		stage.show();
		
		Button addRoot = createRoot.getAddRootButton();
		
		addRoot.setOnAction(e -> {
			
			Person rootPerson = createRoot.getPerson();

			newFam.setRoot(rootPerson);
			
			TreeItem<Person> newRoot = new TreeItem<Person>(newFam.getRoot());
			root = newRoot;
			if(rootPerson.getGender().equals("Female"))
			{
				ImageView gend = createImageView(female);
				newRoot.setGraphic(gend);
			}
			else if(rootPerson.getGender().equals("Male"))
			{
				ImageView gend = createImageView(male);
				newRoot.setGraphic(gend);
			}
			
			treeView.setRoot(root);
			
			addFamToTreeView(newRoot, rootPerson);
			
			fam = newFam;
			
			
			infoText.setText("New tree : " + newFam.getRoot() + " added");
			
			stage.close();
		});
	}
	
	//Creates and displays the GUI to create a person and add it as relative
	private void addRelative()
	{
		infoText.setText("Creating new tree...");
	
		ArrayList<String> types = new ArrayList<String>();
		types.add("Parent");
		types.add("Spouse");
		types.add("Child");
		
		CreateRelative createRel = new CreateRelative(types);
		VBox newPane = createRel.getCreateRootGUI();
		Button addRel = createRel.getAddRelButton();
		Stage stage = new Stage();
		
		Scene newScene = new Scene(newPane);
		stage.setScene(newScene);
		stage.setTitle("Add a new relative");
		stage.show();
		
		infoText.setText("Adding relative to : " + selected.getFirstName());
		
		addRel.setOnAction(e -> {
			Person person = createRel.getPerson();
		
			if(createRel.getRelationType() == "Parent")
			{
				person.addChild(selected);
				selected.addParent(person);
			}
			else if(createRel.getRelationType()  == "Spouse")
			{
				person.addSpouse(selected);
				selected.addSpouse(person);
			}
			else if(createRel.getRelationType()  == "Child")
			{
				person.addParent(selected);
				selected.addChild(person);
			}
			
			TreeItem<Person> rootHolder = treeView.getRoot();

			rootHolder.getChildren().clear();//Clear all children before re creating tree
			
			addFamToTreeView(treeView.getRoot(), fam.getRoot()); //re-create tree with new family member added to the tree
			
			infoText.setText("Relative : " + person.getFirstName() + " added");
			
			stage.close();
		});
	}
	
	//Creates and shows the GUI for editing a person
	void editNode(Person node)
	{
		infoText.setText("Editing : " + node.getFirstName());
	
		EditNode editNode = new EditNode(selected);
		VBox editDetails = editNode.getEditNodeGUI();
		
		Button save = editNode.getSaveButton();
		detailsBox.setRight(editDetails);
		
		save.setOnAction(e -> {
			selected.setFirstName(editNode.getPerson().getFirstName());
			selected.setSurnameAtBirth(editNode.getPerson().getSurnameAtBirth());
			selected.setSurnameAfterMarriage(editNode.getPerson().getSurnameAfterMarriage());
			selected.setGender(editNode.getPerson().getGender());
			selected.setLifeDescription(editNode.getPerson().getLifeDescription());
			
			Address tempAdd = new Address(Integer.valueOf(editNode.getPerson().getAddress().getStreetNumber()), 
										editNode.getPerson().getAddress().getStreetName(), 
										editNode.getPerson().getAddress().getSuburb(), Integer.valueOf(editNode.getPerson().getAddress().getPostCode()));
			selected.setAddress(tempAdd);
			
			detailsBox.setRight(details);
			TreeItem<Person> temp = treeView.getSelectionModel().getSelectedItem();
			
			if(selected.getGender().equals("Female"))
			{
				ImageView gend = createImageView(female);
				temp.setGraphic(gend);
			}
			else if(selected.getGender().equals("Male"))
			{
				ImageView gend = createImageView(male);
				temp.setGraphic(gend);
			}
			
			updateSelectedItem(temp);

			treeView.refresh();
		});
	}
	
	//This method will ad a FamilyTree object to the tree, this is also used for updating the tree 
	//after a FamilyTree has be altered
	@SuppressWarnings({ "unchecked", "rawtypes" }) //Suppress creating the headers for the treeView (They dont hold a person)
	TreeItem<Person> addFamToTreeView(TreeItem<Person> root, Person pRoot)
	{
		if(pRoot != null)
		{
			//---------------- Add parents ---------------------------------
			TreeItem<Person> parentTitle = null;
			if(pRoot.getParents().size() <= 0 ||pRoot.getParents() == null)
			{
				parentTitle = null;
			}
			else
			{
				parentTitle = new TreeItem("Parent");
				ImageView gend = createImageView(nonPerson);
				parentTitle.setGraphic(gend);
			}
					
				
			for(Person parent: pRoot.getParents())
			{
				TreeItem<Person> parentData = new TreeItem<Person>(parent);
				if(parent.getGender().equals("Female"))
				{
					ImageView gend = createImageView(female);
					parentData.setGraphic(gend);
				}
				else if(parent.getGender().equals("Male"))
				{
					ImageView gend = createImageView(male);
					parentData.setGraphic(gend);
				}
				
				
				parentTitle.setExpanded(true);
				parentTitle.getChildren().add(parentData);
			}
			
			if(parentTitle != null)
			{
				root.getChildren().add(parentTitle);
			}
	
			
			//---------------- Add spouse ---------------------------------
					TreeItem<Person> spouseTitle = null;
					if(pRoot.getSpouse() != null)
					{
						spouseTitle = new TreeItem("Spouse");
						TreeItem<Person> spouse = new TreeItem<Person>(pRoot.getSpouse());
						if(pRoot.getSpouse().getGender().equals("Female"))
						{
							ImageView gend = createImageView(female);
							spouse.setGraphic(gend);
						}
						else if(pRoot.getSpouse().getGender().equals("Male"))
						{
							ImageView gend = createImageView(male);
							spouse.setGraphic(gend);
						}
						
						spouseTitle.setExpanded(true);
						spouseTitle.getChildren().add(spouse);
						
					}
					else
						spouseTitle = null;
					
					if(spouseTitle != null)
					{
						root.getChildren().add(spouseTitle);
					}
			
			//------------------ Add children ---------------------
			TreeItem<Person> title = null;
			if(pRoot.getChildren().size() <= 0 || pRoot.getChildren() == null)
			{
				title = null;
			}
			else
				title = new TreeItem("Children");
			
	
			for(Person child: pRoot.getChildren())
			{
				TreeItem<Person> treeItem = new TreeItem<Person>(child);
				if(child.getGender().equals("Female"))
				{
					ImageView gend = createImageView(female);
					treeItem.setGraphic(gend);
				}
				else if(child.getGender().equals("Male"))
				{
					ImageView gend = createImageView(male);
					treeItem.setGraphic(gend);
				}
				
				title.setExpanded(true);
				title.getChildren().add(treeItem);
				treeItem = addFamToTreeView(treeItem, child);
				
			}
	
			if(title != null)
			{
				root.getChildren().add(title);
			}
	
		}

		return root;
	}

	private ImageView createImageView(Image image)
	{
		 ImageView iv = new ImageView();
		 iv.setImage(image);
		 iv.setFitWidth(16);
		 iv.setFitHeight(16);
		 
		 return iv;
	}
		
	public static void main(String[] args) {

		GUI main = GUI.getInstance();
		main.beginGUI(args);

	}
	
	//Method to call the start method
	public void beginGUI(String[] args)
	{
		Application.launch();
	}
	
}