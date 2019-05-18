import java.math.BigInteger;

public class StringHash implements Dict
{
  //hash values of strings are unique and nonzero (hash("") = 0, but that's not a word anyway).
   
  //about 370000 words in dict, make hash table bigger so it's sparse
  //2000003 is prime so double hashing is slightly more reliable.
  
  //use object so more information is stored about if has been deleted or not.  
  //If the object is initialized, it's been used.
  private StringHashObject[] hashData = new StringHashObject[2000003];
  
  public StringHash()
  {
    //don't need to initialize anything?
  }
  
  public boolean add(String word)
  {
    int hash = Math.abs(word.hashCode()) % 2000003;
    int hash2 = Math.abs(word.hashCode()) % 2000001;
    if(hash2 == 0)
    {   
      hash2 = 1;
    }
    
    //add to table, double hash for collisions
    while(hashData[hash] != null && hashData[hash].getData() != null)
    {
      hash = (hash + hash2) % 2000003;
    }
    
    if(hashData[hash] == null)
    {
      hashData[hash] = new StringHashObject(word);
    }
    else
    {
      hashData[hash].setData(word); 
    }
    return true;
  }
  
  public boolean isWord(String word)
  {
    int hash = Math.abs(word.hashCode()) % 2000003;
    int hash2 = Math.abs(word.hashCode()) % 2000001;
    if(hash2 == 0)
    {   
      hash2 = 1;
    }
    
    //search hashtable, false if a null
    while(hashData[hash] == null || (hashData[hash].getData() == null || !hashData[hash].getData().equals(word)))
    {
      if(hashData[hash] == null)
      {
        return false;
      }
      hash = (hash + hash2) % 2000003;
    }
    return true;
  }
  
  public boolean delete(String word)
  {
    int hash = Math.abs(word.hashCode()) % 2000003;
    int hash2 = Math.abs(word.hashCode()) % 2000001;
    if(hash2 == 0)
    {   
      hash2 = 1;
    }
    
    while(hashData[hash] == null || (hashData[hash].getData() == null || !hashData[hash].getData().equals(word)))
    {
      if(hashData[hash] == null)
      {
        return false;
      }
      hash = (hash + hash2) % 2000003;
    }
    
    //set data to null
    hashData[hash].delete();
    return true;
  }
  
  public String autoComplete(String word)
  {
   //this one adds and deletes fast but autoCompletes pretty slowly
   
   //can't really search so linear search entire array
   for(int i = 0; i < hashData.length; i++)
   {
     //and should be smart and check these left to right, not check the rest if any are false
     //first 3 conditions just prevent null pointer exceptions
     //then checks if data contains the word, then checks if the data is the word.
     if(hashData[i] != null && hashData[i].getData() != null && hashData[i].getData().length() > word.length() && word.equals(hashData[i].getData().substring(0, word.length())) && !word.equals(hashData[i].getData()))
     {
       return hashData[i].getData();
     }
   }
   
   return null;
  }
   /* the worst search possible just for fun, takes around 14 hours to run
   BigInteger i = new BigInteger("0");
   System.out.println(BigInteger.valueOf(26).pow(longestWord - word.length()));
   while(i.compareTo(BigInteger.valueOf(26).pow(longestWord - word.length())) < 0)
   {
    String next = numToWord(i);
    if(isWord(word + next))
    {
   return word + next;  
    }
    i = i.add(BigInteger.valueOf(1));
   }
   
   return null;
  }
  public String numToWord(BigInteger num)
  {
   String output = "";
   boolean next = false;
   boolean done = false;
   while(!done)
   {
    output = (char)(num.mod(BigInteger.valueOf(26)).intValue() + 97) + output; 
    num = num.divide(BigInteger.valueOf(26));
    
    if(num.equals(BigInteger.valueOf(0)))
    {
     next = true;
    }
    
    if(next)
    {
     done = true;
    }
   }
   
   return output;
  }
  */
}
  