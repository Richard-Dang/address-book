import java.awt.*;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * Creates a database program in the form of an address book which allows users to create, open, and save
 * address books. Users may create one address book at a time and may add or modify records in it which
 * consist of four entry fields: first name, last name, email and phone number. They can also move back and 
 * forth between records in the current address book and view saved entries. Emails and phone numbers entered
 * must fulfill a certain criteria in order to be saved or else the program will save the entries as blank. 
 * In addition, only a max of 50 records may be created at any time per address book. Correctly entered
 * names and phone numbers will automatically be formatted by the program accordingly and users are only
 * required to enter one of four fields in order to add a new record. Only .xyz files created by this program
 * may be opened and users cannot save blank address books.
 * 
 * @author Richard Dang 
 * @version 1.0, Feb. 25, 2014
 */

public class DatabaseApp extends JFrame implements ActionListener
{
  /**
   * Allows access to AddressBook methods.
   */
  AddressBook a = new AddressBook ();
  /**
   * Stores the file name that the user opens/saves through JFileChooser.
   */
  File fileName;
  /**
   * Stores the choice (yes/no/cancel) the user chooses when asked by confirm dialog.
   */
  int yesNoCancel=0;
  /**
   * Allows access to JFileChooser methods.
   */
  JFileChooser chooser  = new JFileChooser (".\\");
  /**
   *  Allows access to FileNameExtensionFilter methods.
   */
  FileNameExtensionFilter filter = new FileNameExtensionFilter ("XYZ Address Book Files" , "xyz");
  /**
   * Stores result from JFileChooser dialogs.
   */
  int result;
  /**
   * Allows access to PrintWriter methods.
   */
  PrintWriter output; 
  /**
   * Checks if the user has made any changes to their addressbook.
   */
  static boolean changesSaved = false;
  /**
   * Checks if the user has saved their current addressbook in order to allow overwritting.
   */
  boolean saveAsCheck = false;
  /**
   * Stores the string equivalent of fileName in order to add extension.
   */
  String fileStr;
  
  /**
   * Class constructor that creates a frame called "Address Book" 
   * along with menus and their corresponding menu items. 
   * 
   * @param quitItem reference: Allows access to JMenuItem methods.
   * @param saveItem reference: Allows access to JMenuItem methods.
   * @param saveAsItem reference: Allows access to JMenuItem methods.
   * @param openItem reference: Allows access to JMenuItem methods.
   * @param newItem reference: Allows access to JMenuItem methods.
   * @param helpItem reference: Allows access to JMenuItem methods.
   * @param aboutItem reference: Allows access to JMenuItem methods.
   * @param fileMenu reference: Allows access to JMenu methods.
   * @param helpMenu reference: Allows access to JMenu methods.
   * @param menus reference: Allows access to JMenuBar methods.
   */
  public DatabaseApp ()
  {
    super ("Address Book");
    add (a);
    JMenuItem quitItem = new JMenuItem ("Quit");
    JMenuItem saveItem = new JMenuItem ("Save");
    JMenuItem saveAsItem = new JMenuItem ("Save As");
    JMenuItem openItem = new JMenuItem ("Open");
    JMenuItem newItem = new JMenuItem ("New");
    JMenuItem helpItem = new JMenuItem ("Help Me");
    JMenuItem aboutItem = new JMenuItem ("About");
    
    JMenu fileMenu = new JMenu ("File");
    JMenu helpMenu = new JMenu ("Help");
    
    fileMenu.add (newItem);
    fileMenu.add (openItem);
    fileMenu.add (saveItem);
    fileMenu.add (saveAsItem);
    fileMenu.add (quitItem);
    helpMenu.add (helpItem);
    helpMenu.add (aboutItem);
    
    JMenuBar menus = new JMenuBar ();
    menus.add (fileMenu);
    menus.add (helpMenu);
    
    quitItem.addActionListener (this);
    saveItem.addActionListener (this);
    saveAsItem.addActionListener (this);
    openItem.addActionListener (this);
    newItem.addActionListener (this);
    helpItem.addActionListener (this);
    aboutItem.addActionListener (this);
    
    setJMenuBar (menus);
    setSize (370,400);
    setVisible (true);
    setResizable (false);
    setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
  }
  
  /**
   * Creates a new address book by clearing all existing entries using a for loop to set 
   * everything to blank. Textfields are also set to blank and the current/total counter is reset.
   * The user may only choose this option if they have saved all changes made or pressed no when the save dialog appears.
   * 
   * @param x int: number used as a counter in the for loop.
   */
  public void newFile ()
  {
    askSave ();
    
    if (changesSaved == true ||  yesNoCancel == JOptionPane.NO_OPTION) 
    {
      a.person.clear ();
      
      a.currentRecord = 0;
      PersonRecord.totalRecords=0;
      a.firstNameField.setText ("");
      a.lastNameField.setText ("");
      a.emailField.setText ("");
      a.phoneField.setText ("");  
      a.updateCurrentTotal ();
    }
    saveAsCheck = false;
  }
  
  /**
   * Opens an existing file created by this program after checking if user has saved current address book,
   * or if the current address book is blank or if they select no when prompted by the save dialog.
   * In order to open a file, the file name cannot be equal to null and the file name must exist
   * or else a found not found dialog will appear to warn the user. Uses JFileChooser with a filter 
   * and checks the file header in order to display only .xyz files created by this program.
   * Blank/null file names are not accepted by this program to prevent crashes or errors.
   * Displays an error message if correct header is not found in the file. If everything is successful, 
   * data from file is loaded into the current address book using a for loop until a null is reached (end of file).
   * Current/total counter is updated afterwards.
   * 
   * @param headerCheck string: stores the file header to check if it was created by this program.
   * @param nullCheck string: checks to see if the line being read is null in order to stop.
   * @param input reference: Allows access BufferedReader methods.
   * @param e reference: Allows access to IOException methods.
   * @param x int: number used as a counter in the for loop.
   * @throws IOException Thrown to indicate an error when reading lines from a file.
   */
  public void openFile ()
  {
    String headerCheck;
    String nullCheck;
    
    chooser.setFileSelectionMode (JFileChooser.FILES_ONLY);
    chooser.setFileFilter (filter);
    
    askSave ();
    
    if (changesSaved == true || PersonRecord.totalRecords == 0 ||  yesNoCancel == JOptionPane.NO_OPTION) 
    {
      result = chooser.showOpenDialog (this);
      fileName = chooser.getSelectedFile ();
      
      if (fileName !=null && result == JFileChooser.APPROVE_OPTION)
      {
        if (fileName.exists () == true)
        {
          try
          {
            BufferedReader input = new BufferedReader (new FileReader (fileName));
            headerCheck = input.readLine ();
            if (headerCheck.equals ("XYZ address book file created by Richard Dang."))
            {
              a.currentRecord = 0;
              PersonRecord.totalRecords=0;
              for (int x= 0 ; x < 50; x++)
              {
                nullCheck = input.readLine ();
                if (nullCheck == null)
                  break;
                else
                {
                  a.person.add (new PersonRecord ("","","",""));
                  a.person.get (x).setFirst (nullCheck); 
                  a.person.get (x).setLast (input.readLine()); 
                  a.person.get (x).setEmail (input.readLine()); 
                  a.person.get (x).setPhone (input.readLine()); 
                  PersonRecord.totalRecords ++;
                }         
              }
              a.firstNameField.setText (a.person.get(0). getFirstName());
              a.lastNameField.setText (a.person.get(0). getLastName());
              a.emailField.setText (a.person.get(0). getEmail());
              a.phoneField.setText (a.person.get(0). getPhone());  
              a.updateCurrentTotal ();
            }
            else
              JOptionPane.showMessageDialog (this, "File not created by this program.", "Invalid File", JOptionPane.ERROR_MESSAGE);  
          }
          catch (IOException e)
          {
          }
        }
        else
        {
          JOptionPane.showMessageDialog (this, "File does not exist.", "File Not Found", JOptionPane.ERROR_MESSAGE);            
          openFile ();
        }
      }
    }
    changesSaved = true;
  }
  
  /**
   * Checks to see if the user has already saved the current file and if not, the JFileChooser save dialog appears in order to allow them
   * to save a new file. Otherwise, saves the file by overwriting the current file using a for loop in order to retrieve 
   * information from each record in the address book. Displays a confirmation message if file is saved successfully.
   * 
   * @param output reference: Allows access to PrintWriter methods.
   * @param e reference: Allows access to IOException methods.
   * @param x int: number used as a counter in the for loop.
   * @throws IOException Thrown to indicate an error when saving lines from a file.
   */
  public void saveFile ()
  {
    if (saveAsCheck == false)
      saveAs ();
    else 
    {
      try 
      {
        output = new PrintWriter (new FileWriter (fileStr));
        output.println ("XYZ address book file created by Richard Dang.");
        for (int x= 0 ; x <  PersonRecord.totalRecords; x++)
        {
          output.println (a.person.get (x).getFirstName ());
          output.println (a.person.get (x).getLastName ());
          output.println (a.person.get (x).getEmail ());
          output.println (a.person.get (x).getPhone ());
        }
        output.close();
      }
      catch (IOException e)
      {
      }
      JOptionPane.showMessageDialog (this, "File has been saved successfully.", "File Saved", JOptionPane.INFORMATION_MESSAGE);
      changesSaved = true;
    }
  }
  
  /**
   * Displays the JFileChooser save dialog that allows the user to choose what they want to name their
   * address book file as long as it is not blank. In order to save, the file name must not equal to null
   * and be have a character length of under 255 or a invalid file name dialog will appear.
   * If the user chooses to name the file a file name that already exists, 
   * they are asked if they would like to overwrite the file or not. The file is overwritten if yes, the save dialog
   * reappears if no, or the user is returned to the main program if they choose to cancel.
   * If the file saves sucessfully, an .xyz extension is added to the file if the user hasn't already done so. 
   * Blank/null file names will not be accepted when saving. If the address book is blank, an error message 
   * will display warning the user they cannot save.
   * 
   * @param output reference: Allows access to PrintWriter methods.
   * @param e reference: Allows access to IOException methods.
   * @param x int: Number used as a counter in the for loop.
   * @throws IOException Thrown to indicate an error when saving lines from a file.
   */
  public void saveAs ()
  {    
    chooser.setFileSelectionMode (JFileChooser.FILES_ONLY);
    chooser.setFileFilter (filter);
    
    if (PersonRecord.totalRecords != 0)
    {
      result = chooser.showSaveDialog (this);
      fileName = chooser.getSelectedFile ();
      
      if (fileName != null)
      {
        if (fileName.getName ().length () < 255) 
        {
          if (fileName.exists () == true && result == JFileChooser.APPROVE_OPTION)
          {
            yesNoCancel = JOptionPane.showConfirmDialog (this, "File already exists. Would you like to overwrite?", "Existing File", JOptionPane.YES_NO_CANCEL_OPTION);
            if (yesNoCancel == JOptionPane.NO_OPTION)
              saveAs ();
            if (yesNoCancel == JOptionPane.CANCEL_OPTION)
              return;
          }
          
          fileStr = fileName.getName();
          
          if (fileStr.contains (".xyz") == false)
            fileStr = fileStr + ".xyz";     
          
          if (result == JFileChooser.APPROVE_OPTION ) 
          {
            try 
            {
              output = new PrintWriter (new FileWriter (fileStr));
              output.println ("XYZ address book file created by Richard Dang.");
              for (int x= 0 ; x < PersonRecord.totalRecords; x++)
              {
                output.println (a.person.get (x).getFirstName ());
                output.println (a.person.get (x).getLastName ());
                output.println (a.person.get (x).getEmail ());
                output.println (a.person.get (x).getPhone ());
              }
              output.close();
            }
            catch (IOException e)
            {
            }
          }
          changesSaved = true;
          saveAsCheck = true;
        }
        else 
        {
          JOptionPane.showMessageDialog (this, "File name cannot exceed 255 characters.", "Invalid File Name", JOptionPane.ERROR_MESSAGE);
          saveAs ();
        }
      }
    }
    else 
      JOptionPane.showMessageDialog (this, "There is nothing to save.", "Blank File", JOptionPane.ERROR_MESSAGE);
  }
  
  /**
   * Asks the user if they would like to save when changes are detected, the address book is not blank or if they have not yet saved.
   * Saves if they click 'yes', continues without saving if they click 'no' and returns if they click 'cancel'.
   */
  public void askSave ()
  {
    if (changesSaved == false && PersonRecord.totalRecords != 0 && saveAsCheck == false)
    {
      yesNoCancel = JOptionPane.showConfirmDialog (this, "File not saved. Would you like to save?", "Unsaved File", JOptionPane.YES_NO_CANCEL_OPTION);
      if ( yesNoCancel == JOptionPane.YES_OPTION)
        saveAs ();
    }
  }
  
  /**
   * Performs certain actions depending on which menu item the user clicks on. 
   * If they choose 'New', a new file will be created (refer to newFile description).
   * If they choose 'Open', they may open an existing file (refer to openFile description).
   * If they choose 'Save', the current file is overwritten (refer to saveFile description).
   * If they choose 'Save As', a new file is saved (refer to saveAs description).
   * If they choose 'Help Me', a help dialog will display with instructions.
   * If they choose ' About', an about dialog will display with programmer information.
   * If they choose 'Quit' and do not choose cancel when prompt to save, the frame closes.
   * 
   * @param ae reference: Allows access to ActionEvent methods.
   */
  public void actionPerformed (ActionEvent ae)
  {
    if (ae.getActionCommand ().equals ("New"))
      newFile ();
    else if (ae.getActionCommand ().equals ("Open"))
      openFile ();
    else if (ae.getActionCommand ().equals ("Save"))
      saveFile ();
    else if (ae.getActionCommand ().equals ("Save As"))
      saveAs ();
    else if (ae.getActionCommand ().equals ("Help Me"))
      JOptionPane.showMessageDialog (this, "1) A maximum of 50 records can be created per address book. \n2) A record must be added before it can be updated. \n3) Emails must contain at least one '@' and have a character before and after. \n4) Phone numbers must contain ten digits, with or without seperators.", "Help Me", JOptionPane.INFORMATION_MESSAGE);
    else if (ae.getActionCommand ().equals ("About"))
      JOptionPane.showMessageDialog (this, "Address book program created by Richard Dang.", "About Me", JOptionPane.INFORMATION_MESSAGE);
    else
    {
      askSave ();
      if (yesNoCancel != JOptionPane.CANCEL_OPTION)
        System.exit (0);
    } 
  }
  
  /**
   * Main method that calls DatabaseApp which creates the frame and menus
   * of the address book program that will allow users to create, open and save records. 
   * 
   * @param args string: array stores command line prompt.
   */
  public static void main (String [] args)
  {
    new DatabaseApp ();
  }
}