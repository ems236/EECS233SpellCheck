import java.math.BigInteger;

public class BigIntHashObject
{
  private BigInteger data;
  private int containedCount = 0;
  private boolean used = false;
  private boolean word = false;
  
  public BigIntHashObject(BigInteger data, boolean isWord)
  {
    this.data = data;
    used = true;
    if(isWord)
    {
      word = true;
    }
    else
    {
      containedCount++;
    }
  }
  
  public void setData(BigInteger data)
  {
    this.data = data;
  }
  
  public void delete()
  {
    data = null;
  }
  
  public BigInteger getData()
  {
    return data;
  }
  
  public void changeCount(boolean upDown)
  {
    if(upDown)
    {
      containedCount++;
    }
    
    else
    {
      containedCount--;
    }
  }
  
  public int getCount()
  {
    return containedCount;
  }
  
  public boolean used()
  {
    return used;
  }
  
  public void setWord(boolean status)
  {
    word = status;
  }
  
  public boolean isWord()
  {
    return word;
  }
}