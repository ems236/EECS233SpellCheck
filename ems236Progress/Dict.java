public interface Dict
{
  public boolean add(String word);
  public boolean isWord(String word);
  public boolean delete(String word);
  public String autoComplete(String word);
}