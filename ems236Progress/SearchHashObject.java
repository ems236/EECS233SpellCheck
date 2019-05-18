public class SearchHashObject
{
  private String data;
  private int children = 0;
  private boolean isWord = false;
  
  public SearchHashObject(String data, boolean isWord)
  {
    this.data = data;
    this.isWord = isWord;
    
    if(!isWord)
    {
      children++;
    }
  }
  
  public void changeCount(boolean upDown)
  {
    if(upDown)
    {
      children++;
    }
    
    else
    {
      children--;
    }
  }
  
    
  public int getCount()
  {
    return children;
  }
  
  public String getData()
  {
    return data;
  }
  
  public void setWord(boolean status)
  {
    isWord = status;
  }
  
  public boolean isWord()
  {
    return isWord;
  }
}