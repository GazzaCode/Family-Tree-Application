package Assignment2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import Assignment2.Person;

public class FamilyTree implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	private Person root;
	
	public FamilyTree()
	{
		root = null;
	}
	
	public void setRoot(Person root)
	{
		this.root = root;
	}
	
	public Person getRoot()
	{
		return root;
	}
	
	
	FamilyTree loadFamily(File selectedFile)
	{
		 try {
			 
	            FileInputStream fileIn = new FileInputStream(selectedFile);
	            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
	 
	            Person iRoot = (Person) objectIn.readObject();
	    		
	    		root = iRoot;
	            
	            System.out.println("The Object has been read from the file");
	            objectIn.close();
	            return this;
	 
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            return null;
	        }
	}
	
	void saveFamily(File selectedFolder)
	{
		try {
			 
            FileOutputStream fileOut = new FileOutputStream(selectedFolder);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            
            if(root!= null)
    		{
            	objectOut.writeObject(root);
    		}
            
            if(root.getSpouse() != null)
            {
            	objectOut.writeObject(root.getSpouse());
            }
    		
    		for(Person parent: root.getParents())
    		{
    			objectOut.writeObject(parent);
    		}
            
    		
    		for(Person child: root.getChildren())
    		{
    			objectOut.writeObject(child);
    		}
    		
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}	
}
