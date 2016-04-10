import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * Creates the panel of the database program and its individual components including labels, 
 * text fields, and buttons. Together, the components allow the user to add, modify or 
 * traverse the address book. A label displays the current record the user is on out of 
 * the total number of records the user has saved. Before adding a new record or saving a 
 * previous record, the program checks to see if the record is not blank and the user
 * has entered proper information within the text fields. If invalid information is entered
 * into a text field, it will be stored as blank without warning.
 * 
 * @author Richard Dang 
 * @version 1.0, Feb. 25, 2014
 */

public class AddressBook extends JPanel implements ActionListener
{
  /**
   * Allows access to SpringLayout methods.
   */
  SpringLayout layout;
  /**
   * Stores the name entered in the first name text field.
   */
  String first = "";
  /**
   * Stores the name entered in the last name text field.
   */
  String last = "";
  /**
   * Stores the email entered in the email text field.
   */
  String email = "";
  /**
   * Stores the phone number entered in the phone number text field.
   */ 
  String phone = "";
  /**
   * Allows access to JTextField methods.
   */
  JTextField firstNameField;
  /**
   * Allows access to JTextField methods.
   */
  JTextField  lastNameField;
  /**
   * Allows access to JTextField methods.
   */
  JTextField emailField;
  /**
   * Allows access to JTextField methods.
   */
  JTextField  phoneField;
  /**
   * Allows access to PersonRecord methods using an ArrayList of PersonRecord.
   */
  ArrayList <PersonRecord> person = new ArrayList <PersonRecord> (); 
  /**
   * Stores the current record the user is viewing.
   */
  static int currentRecord =0;
  /**
   * Allows access to JLabel methods.
   */
  JLabel currentTotalRecord;
  
  /**
   * Class constuctor that adds the address book components to the panel.
   */
  public AddressBook ()
  {
    labelTextField ();
  }
  
  /**
   * Creates and adds labels, text fields and buttons to the panel and lays them out accordingly 
   * to appear like an address book. 
   * 
   * @param firstNameLabel reference: Allows access to JLabel methods.
   * @param lastNameLabel reference: Allows access to JLabel methods.
   * @param emailLabel reference: Allows access to JLabel methods.
   * @param phoneLabel reference: Allows access to JLabel methods.
   * @param addRecordButton reference: Allows access to JButton methods.
   * @param nextRecordButton reference: Allows access to JButton methods.
   * @param prevRecordButton reference: Allows access to JButton methods.
   * @param updateRecordButton reference: Allows access to JButton methods.
   * @param deleteRecordButton reference: Allows access to JButton methods.
   */
  public void labelTextField ()
  {
    JLabel firstNameLabel = new JLabel ("First Name: ");
    JLabel lastNameLabel = new JLabel ("Last Name: ");
    JLabel emailLabel = new JLabel ("Email: ");
    JLabel phoneLabel = new JLabel ("Phone: ");
    currentTotalRecord = new JLabel ("Current Record: "+ (currentRecord +1) +" / Total Saved: " + PersonRecord.totalRecords);
    
    firstNameField = new JTextField (20);
    lastNameField = new JTextField (20);
    emailField = new JTextField (20);
    phoneField = new JTextField (20); 
    
    JButton addRecordButton = new JButton ("Add Record");
    JButton nextRecordButton = new JButton ("Next Record");
    JButton prevRecordButton = new JButton ("Previous Record");
    JButton updateRecordButton = new JButton ("Update Record");
    JButton deleteRecordButton = new JButton ("Delete Record");
    
    layout = new SpringLayout ();
    setLayout (layout);
    
    add (firstNameLabel);
    add (firstNameField);
    add (lastNameLabel);
    add (lastNameField);  
    add (emailLabel);
    add (emailField);    
    add (phoneLabel);
    add (phoneField);
    add (currentTotalRecord);
    add (addRecordButton);
    add (nextRecordButton);
    add (prevRecordButton);
    add (updateRecordButton);
    add (deleteRecordButton);
    
    addRecordButton.addActionListener (this);
    nextRecordButton.addActionListener (this);
    prevRecordButton.addActionListener(this);
    updateRecordButton.addActionListener(this);
    deleteRecordButton.addActionListener(this);
    
    layout.putConstraint (SpringLayout.NORTH, firstNameLabel ,15, SpringLayout.NORTH, this );
    layout.putConstraint (SpringLayout.WEST, firstNameLabel ,20, SpringLayout.WEST, this );
    
    layout.putConstraint (SpringLayout.NORTH, firstNameField ,15, SpringLayout.NORTH, this );
    layout.putConstraint (SpringLayout.WEST, firstNameField ,120, SpringLayout.WEST, this );
    
    layout.putConstraint (SpringLayout.WEST, lastNameLabel ,20, SpringLayout.WEST, this );
    layout.putConstraint (SpringLayout.NORTH, lastNameLabel ,20, SpringLayout.SOUTH, firstNameLabel );
    
    layout.putConstraint (SpringLayout.WEST, lastNameField ,120, SpringLayout.WEST, this );
    layout.putConstraint (SpringLayout.NORTH, lastNameField ,15, SpringLayout.SOUTH, firstNameField );
    
    layout.putConstraint (SpringLayout.WEST, emailLabel ,20, SpringLayout.WEST, this );
    layout.putConstraint (SpringLayout.NORTH, emailLabel ,20, SpringLayout.SOUTH, lastNameLabel );
    
    layout.putConstraint (SpringLayout.WEST, emailField ,120, SpringLayout.WEST, this );
    layout.putConstraint (SpringLayout.NORTH, emailField ,15, SpringLayout.SOUTH, lastNameField );
    
    layout.putConstraint (SpringLayout.WEST, phoneLabel ,20, SpringLayout.WEST, this );
    layout.putConstraint (SpringLayout.NORTH, phoneLabel ,20, SpringLayout.SOUTH, emailLabel );
    
    layout.putConstraint (SpringLayout.WEST, phoneField ,120, SpringLayout.WEST, this );
    layout.putConstraint (SpringLayout.NORTH, phoneField ,15, SpringLayout.SOUTH, emailField );
    
    layout.putConstraint (SpringLayout.WEST, addRecordButton ,50, SpringLayout.WEST, this );
    layout.putConstraint (SpringLayout.NORTH, addRecordButton ,80, SpringLayout.SOUTH, phoneField );
    
    layout.putConstraint (SpringLayout.WEST, nextRecordButton ,210, SpringLayout.WEST, this );
    layout.putConstraint (SpringLayout.NORTH, nextRecordButton ,15, SpringLayout.SOUTH, addRecordButton );
    
    layout.putConstraint (SpringLayout.WEST, prevRecordButton ,30, SpringLayout.WEST, this );
    layout.putConstraint (SpringLayout.NORTH, prevRecordButton ,15, SpringLayout.SOUTH, addRecordButton );
    
    layout.putConstraint (SpringLayout.WEST, currentTotalRecord ,100, SpringLayout.WEST, this );
    layout.putConstraint (SpringLayout.NORTH, currentTotalRecord ,15, SpringLayout.SOUTH, phoneField );
    
    layout.putConstraint (SpringLayout.WEST, updateRecordButton ,50, SpringLayout.EAST, addRecordButton );
    layout.putConstraint (SpringLayout.NORTH, updateRecordButton ,80, SpringLayout.SOUTH, phoneField );
    
    layout.putConstraint (SpringLayout.WEST, deleteRecordButton ,120, SpringLayout.WEST, this );
    layout.putConstraint (SpringLayout.NORTH, deleteRecordButton ,40, SpringLayout.SOUTH, phoneField );
  }
  
  /**
   * Stores the the information the user has entered in the textfields and checks
   * to see if they have entered a valid phone number and email. If not, the program
   * will store those fields as blank.
   */
  public void checkFields ()
  {
    first = firstNameField.getText ().trim();
    last = lastNameField.getText ().trim ();
    email = emailField.getText ().trim();
    phone = phoneField.getText ().trim();
    
    if (DataCheck.checkPhone (phone) == false)
      phone = "";
    if (DataCheck.checkEmail (email) == false)
      email = "";
  }
  
  /**
   * When the add record button is pressed, the program creates a new record and stores
   * the information the user entered in the text fields then wipes the text fields for the 
   * next record to be entered. The user may also use the previous/next record buttons to 
   * view records that they have already created. If they reach the total number of records they 
   * have created, pressing the next button will move to the first record and vice versa. 
   * After a record has been created, they may modify the record using the update record button. 
   * Using the delete record button will allow the user to remove an existing record as long 
   * as there are still records to delete and the record isn't already blank. The previous record
   * will then be displayed to the user or if there are no records left to display, a blank record will be displayed.
   * The current/total record counter label is updated after any of the five buttons are pressed.
   * 
   * @param ae reference: Allows access to ActionEvent methods.
   */
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getActionCommand ().equals ("Add Record") && PersonRecord.totalRecords < 50 )
    {
      checkFields ();
      
      if (!first.equals("") || !last.equals("") || !email.equals("")  || !phone.equals("")  )
      {
        currentRecord = PersonRecord.totalRecords;   
        
        person.add (new PersonRecord (first,last,email,phone));
        person.get (currentRecord).setFirst (first);
        person.get (currentRecord).setLast (last);
        person.get (currentRecord).setPhone (phone);
        
        PersonRecord.totalRecords++;
        currentRecord++;
        
        first = "";
        last = "";
        email = "";
        phone = "";
        
        firstNameField.setText ("");
        lastNameField.setText ("");
        emailField.setText ("");
        phoneField.setText ("");
        DatabaseApp.changesSaved = false;
      }
    }      
    
    if (ae.getActionCommand ().equals ("Previous Record"))
    {
      if (currentRecord == 0)
        currentRecord = PersonRecord.totalRecords;
      
      if (currentRecord > 0 )
        currentRecord--; 
      
      if (person.isEmpty () == false)
      {
        firstNameField.setText (person.get (currentRecord). getFirstName());
        lastNameField.setText (person.get (currentRecord). getLastName());
        emailField.setText (person.get (currentRecord). getEmail());
        phoneField.setText (person.get (currentRecord). getPhone());
      }
    }    
    
    if (ae.getActionCommand ().equals ("Next Record"))
    {
      if (currentRecord == PersonRecord.totalRecords || currentRecord + 1 == PersonRecord.totalRecords)
        currentRecord = -1;
      
      if (currentRecord < PersonRecord.totalRecords)
        currentRecord++; 
      
      if (person.isEmpty () == false)
      {
        firstNameField.setText (person.get (currentRecord). getFirstName());
        lastNameField.setText (person.get (currentRecord). getLastName());
        emailField.setText (person.get (currentRecord). getEmail());
        phoneField.setText (person.get (currentRecord). getPhone());
      }
    }
    
    if (ae.getActionCommand ().equals ("Update Record") && currentRecord < 50)
    {   
      if (person.isEmpty () == false)
      {
        checkFields ();
        
        if (!first.equals("") || !last.equals("") || !email.equals("")  || !phone.equals("") )
        {
          
          person.get (currentRecord).setFirst (first);
          person.get (currentRecord).setLast (last);
          person.get (currentRecord).setPhone (phone);
          person.get (currentRecord).setEmail (email);
          
          firstNameField.setText (person.get (currentRecord). getFirstName());
          lastNameField.setText (person.get (currentRecord). getLastName());
          emailField.setText (person.get (currentRecord). getEmail());
          phoneField.setText (person.get (currentRecord). getPhone()); 
          
          DatabaseApp.changesSaved = false;
        }   
      }
    }
    
    if (ae.getActionCommand ().equals ("Delete Record") && PersonRecord.totalRecords > 0 && currentRecord < PersonRecord.totalRecords)
    { 
      person.remove (currentRecord);
     
      if (currentRecord == 0)
       currentRecord = PersonRecord.totalRecords - 1;
      
      if (currentRecord > 0)
        currentRecord--; 
      
      PersonRecord.totalRecords --;
      
      if (person.isEmpty () == false)
      {
        firstNameField.setText (person.get (currentRecord). getFirstName());
        lastNameField.setText (person.get (currentRecord). getLastName());
        emailField.setText (person.get (currentRecord). getEmail());
        phoneField.setText (person.get (currentRecord). getPhone());
      }
      else
      {
        firstNameField.setText ("");
        lastNameField.setText ("");
        emailField.setText ("");
        phoneField.setText ("");
      }
      DatabaseApp.changesSaved = false;
    }
    updateCurrentTotal ();
  }
  
  /**
   * A label that is used as a counter to display to the user the current record they are 
   * viewing out of however many records they have already saved and is constantly updated.
   * The user may only create a maximum of 50 records before the counter displays MAX. 
   */
  public void updateCurrentTotal ()
  {
    remove (currentTotalRecord);
    repaint ();
    revalidate ();
    if (currentRecord < 50)
      currentTotalRecord = new JLabel ("Current Record: "+ (currentRecord +1) +" / Total Saved: " + PersonRecord.totalRecords);
    else
      currentTotalRecord = new JLabel ("Current Record: MAX / Total Saved: MAX");      
    add (currentTotalRecord);
    layout.putConstraint (SpringLayout.WEST, currentTotalRecord ,100, SpringLayout.WEST, this );
    layout.putConstraint (SpringLayout.NORTH, currentTotalRecord ,15, SpringLayout.SOUTH, phoneField );
    
  }
}