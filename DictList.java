public class DictList implements Dict
{
  //dict has like 300000 words, but I'll make it generalizable.
  private String[] data = new String[32];
  private int capacity = 32;
  private int size = 0;
  
  public DictList()
  {
    //don't actually need to do anything in constructor.
  }
  
  private void increaseCapacity()
  {
    String[] temp = new String[capacity * 2];
    for(int i = 0; i < size; i++)
    {
      temp[i] = data[i];
    }
    data = temp;
    capacity *= 2;
  }
  
  public boolean add(String word)
  { 
    size++;
    if(size == capacity)
    {
      increaseCapacity();
    }
    
    data[size - 1] = word;
    return true;
  }
  
  public boolean isWord(String word)
  {
    for(int i = 0; i < size; i++)
    {
      if(data[i].equals(word))
      {
        return true;
      }
    }
    
    return false;
  }
  
  public boolean delete(String word)
  {
    boolean found = false;
    int index = 0;
    for(int i = 0; !found && i < size; i++)
    {
      if(data[i].equals(word))
      {
        found = true;
        index = i;
      }
    }
    
    if(!found)
    {
      return false;
    }
    
    else
    {
      for(int i = index; i < size - 1; i++)
      {
        //shift left
        data[i] = data[i+1];
      }
      size--;
      return true;
    }
  }
  
  public String autoComplete(String word)
  {
    for(int i = 0; i < size; i++)
    {
      if(data[i].length() > word.length() && word.equals(data[i].substring(0, word.length())) && !word.equals(data[i]))
      {
        return data[i];
      }
    }
    return null;
  }
}