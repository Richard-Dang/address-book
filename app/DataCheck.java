/**
 * Checks the email and phone number entered by the user to see if they are valid before
 * storing them into the record. 
 * 
 * @author Richard Dang 
 * @version 1.0, Feb. 25, 2014
 */

public class DataCheck
{
  
  /**
   * Checks if the phone number entered the user if valid. Accepts either ten digits,
   * with or without seperators (ex. 123 123 1234 or 123-123-1234). If seperators are
   * entered, they are removed in order to check if the string only contains numbers by parsing.
   * 
   * @param newPhone String: stores the phone number entered by the user.
   * @param temp long: temporarily stores the parsed phone number to check if there are only numbers.
   * @param e reference: Allows access to NumberFormatException methods.
   * @return boolean: true if phone number was parsed successfully, otherwise false.
   * @throws NumberFormatException Thrown to indicate an error when parsing the phone number.
   */
  public static boolean checkPhone (String newPhone)
  {
    if (newPhone.length () == 10 ||newPhone.length () == 12 )
    {
      if (newPhone.length ()==12)
      {
        if ((newPhone.charAt (3) == 45 && newPhone.charAt (7) == 45) || 
            (newPhone.charAt (3) == 32 && newPhone.charAt (7) == 32) )
          newPhone = newPhone.substring (0,3)+ newPhone.substring(4,7) +newPhone.substring (8);
      }
      try
      {
        long temp = Long.parseLong (newPhone);
        return true;
      }
      catch (NumberFormatException e)
      {
      }
    }
    return false;
  }
  
  /**
   * Checks if the email entered by the user is valid. Accepts emails with only one '@'
   * by using a for loop to run through the string to check and there must be a character before and after it. 
   * 
   * @param newEmail String: stores the email entered by the user.
   * @param counter int: stores the number of '@'s found in the email.
   * @param x int: number used as a counter in the for loop.
   * @return boolean: true if only one '@' is found in the email and there is a character before and after it, otherwise false.
   */
  public static boolean checkEmail (String newEmail)
  {
    int counter = 0; 
    
    for (int x= 0; x < newEmail.length (); x++)
    {
      if (newEmail.charAt(x) == 64)
        counter ++;
    }
    
    if (counter !=1 || newEmail.charAt (0) == 64 ||newEmail.charAt (newEmail.length ()-1) ==64 )
      return false;
    else 
      return true;
  }
}