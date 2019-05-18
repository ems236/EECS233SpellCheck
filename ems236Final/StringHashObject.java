public class StringHashObject
{
  private String data;
  
  public StringHashObject(String data)
  {
    this.data = data;
  }
  
  public void setData(String data)
  {
    this.data = data;
  }
  
  public void delete()
  {
    data = null;
  }
  
  public String getData()
  {
    return data;
  }
}