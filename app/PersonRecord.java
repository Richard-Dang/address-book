/**
 * Stores information entered by the user into seperate records and formats valid
 * names and phone numbers. Allows users to view and modify existing records as well.
 * Names are formatted to capitalize the first letter and phone numbers are formatted
 * to insert dashes to seperate the numbers into groups of 3,3 and 4.
 * 
 * @author J.Dyke modified by Richard Dang
 * @version 1.0, Feb. 25, 2014
 */

public class PersonRecord 
{
  /**
   * Stores the first name entered by the user in each record.
   */
  private String first = "";
  /**
   * Stores the last name entered by the user in each record.
   */
  private String last = "";
  /**
   * Stores the email entered by the user in each record.
   */
  private String email = "";
  /**
   * Stores the phone number entered by the user in each record.
   */
  private String phone = "";
  /**
   * Stores total numbers of records that the user has saved.
   */
  static int totalRecords =0;
  
  /**
   * Blank class constructor.
   */
  public PersonRecord ( )
  {
  }
  
  /**
   * Class constructor with a first parameter that stores the first name entered by the user.
   * 
   * @param first String: stores the first name entered by the user.
   */
  public PersonRecord (String first)
  {
    this.first = first;
  }
  
  /**
   * Class constructor with a first and last parameter that stores the 
   * first and last name entered by the user.
   * 
   * @param first String: stores the first name entered by the user.
   * @param last String: stores the last name entered by the user.
   */
  public PersonRecord (String first, String last)
  {
    this.first = first;
    this.email = email;
  }
  
  /**
   * Class constructor with a first, last and email parameter that stores the 
   * first name, last name and email  entered by the user.
   * 
   * @param first String: stores the first name entered by the user.
   * @param last String: stores the last name entered by the user.
   * @param email String: stores the email entered by the user. 
   */
  public PersonRecord (String first, String last, String email )
  {
    this.first = first;
    this.email = email;
    this.last = last;
  }
  
  /**
   * Class constructor with a first, last, email and phone parameter that stores the 
   * first name, last name, email and phone number entered by the user.
   * 
   * @param first String: stores the first name entered by the user.
   * @param last String: stores the last name entered by the user.
   * @param email String: stores the email entered by the user. 
   * @param phone String: stores the phone number entered by the user. 
   */
  public PersonRecord (String first, String last, String email, String phone )
  {
    this.first = first;
    this.email = email;
    this.last = last;
    this.phone = phone; 
  }
  
  /**
   * Accessor method that returns the first name stored in the current record being accessed.
   * 
   * @return String: returns the first name stored.
   */
  public String getFirstName ()
  {
    return first;
  }
  
  /**
   * Accessor method that returns the lastst name stored in the current record being accessed.
   * 
   * @return String: returns the last name stored.
   */
  public String getLastName ()
  {
    return last;
  }
  
  /**
   * Accessor method that returns the email stored in the current record being accessed.
   * 
   * @return String: returns the email name stored.
   */
  public String getEmail ()
  {
    return email;
  }
  
  /**
   * Accessor method that returns the phone number stored in the current record being accessed.
   * 
   * @return String: returns the phone number name stored.
   */
  public String getPhone ()
  {
    return phone;
  }
  
  /**
   * Mutator method that stores the formatted first name obtained from the user.
   * 
   * @param newFirst String: temporarily stores the first name entered by the user.
   */
  public void setFirst (String newFirst)
  {
    first = formatName (newFirst);
  }
  
  /**
   * Mutator method that stores the formatted last name obtained from the user.
   * 
   * @param newLast String: temporarily stores the last name entered by the user before being formatted.
   */
  public void setLast (String newLast)
  {
    last = formatName (newLast);
  }
  
  /**
   * Mutator method that stores the email obtained from the user.
   * 
   * @param newLast String: temporarily stores the email entered by the user.
   */
  public void setEmail (String newEmail)
  {
    email = newEmail;
  }
  
  /**
   * Mutator method that stores the formatted phone number obtained from the user.
   * 
   * @param newPhone String: temporarily stores the phone number entered by the user before being formatted.
   */
  public void setPhone (String newPhone)
  {
    phone = formatPhone (newPhone);
  }
  
  /**
   * Formats the first or last name in order to capitalize only the first letter of the name if they are not blank.
   * 
   * @param newName String: stores the first or last name entered by the user.
   * @return String: returns the formatted first or last name entered by the user. 
   */
  private String formatName (String newName)
  {
    if (!newName.equals (""))
    {
      newName =  newName.toLowerCase ();
      newName = newName.substring (0,1).toUpperCase () + newName.substring (1);
    }
    return newName;
  }
  
  /**
   * Formats the phone number in order to add dashes in between in groups of 3,3 and 4 if it is not blank.
   * If space seperators are found, they are replaced by dashes.
   * 
   * @param newPhone String: stores the phone number entered by the user.
   * @return String: returns the formatted phone number entered by the user.
   */
  private String formatPhone (String newPhone)
  {
    if (!newPhone.equals (""))
    {
      if (newPhone.length ()==12)
        newPhone = newPhone.substring (0,3)+ newPhone.substring(4,7) +newPhone.substring (8);
      newPhone = newPhone.substring (0,3)+ "-"+newPhone.substring(3,6)+"-"+newPhone.substring (6);
    }
    return newPhone;
  }
}