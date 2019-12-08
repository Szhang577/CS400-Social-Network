package application;

@SuppressWarnings("serial")
public class DuplicatePersonException extends Exception{
  public DuplicatePersonException(String string) {
    super (string);
  }
}
