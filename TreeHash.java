import java.math.BigInteger;
import java.util.ArrayList;

public class TreeHash implements Dict
{
  /*
   the goal here was to make an array out of the true using the same trick used for heaps. 
  You run out of memory really quickly because there are so many falses, 
  so solve that by making a hash table of the indeces of the trues in the tree.
  
  For searching single words, this is worse than a normal hash table and probably worse than a normal tree.
  For designing an autocomplete feature, you can still travel down the tree once you find an index in the hash table,
  so it probably works pretty well.
  
  Probably really slow because hashtable is forced to be a table of BigInts
  */
  
  //sets up hash table for this
  //about 370000 words in dict, make hash table bigger so it's sparse
  //2000003 is prime so double hashing is slightly more reliable.
  public BigIntHashObject[] hashTable = new BigIntHashObject[2000003];
  
  //addOne specifies if adding a word too a tree, or only substring information. 
  //true if adding a word.
  private void addHash(BigInteger num, boolean isWord)
  {
    int hash = Math.abs(num.hashCode()) % 2000003;
    int hash2 = Math.abs(num.hashCode()) % 2000001;
    if(hash2 == 0)
    {   
      hash2 = 1;
    }
    
    while(hashTable[hash] != null)
    {
      hash = (hash + hash2) % 2000003;
    }
    
    hashTable[hash] = new BigIntHashObject(num, isWord);
  }
  
  private int findHash(BigInteger num)
  {
    int hash = Math.abs(num.hashCode()) % 2000003;
    int hash2 = Math.abs(num.hashCode()) % 2000001;
    
    if(hash2 == 0)
    {   
      hash2 = 1;
    }
    
    while((hashTable[hash] == null || !hashTable[hash].used()) || !hashTable[hash].getData().equals(num))
    {
      if(hashTable[hash] == null || !hashTable[hash].used())
      {
        return -1;
      }
      
      else
      {
        hash = (hash + hash2) % 2000003;
      }
    }
    
    return hash;
  }
  
  //tree information for max size
  private BigInteger size = new BigInteger("1");
  
  public TreeHash()
  {
    //doesn't need to do anything 
  }
  
  private void increaseCapacity()
  {
    size = size.multiply(new BigInteger("26"));
  }
  
  private BigInteger getChild(BigInteger current, int letter)
  {
    if(letter == -1)
    {
      return new BigInteger("-1");
    }
    
    else
    {
      return current.multiply(new BigInteger("26")).add(BigInteger.valueOf(letter + 1));
    }
  }
  
  public BigInteger search(String word, boolean delete)
  {
    BigInteger current = new BigInteger("0");
    for(int i = 0; i < word.length(); i++)
    {
      //handle autocompletion counters
      if(delete)
      {
        int hash = findHash(current);
        if(hash == -1)
        {
          return null;
        }
        else
        {
          hashTable[hash].changeCount(false);
        }
      }
      
      current = getChild(current, charToInt(word.charAt(i)));
      if(current.equals(new BigInteger("-1")))
      {
        return null;
      }
      
      else if(current.compareTo(size) >= 0)
      {
        return null;
      }
    }
    
    return current;
  }
  
  public boolean add(String word)
  {
    BigInteger current = new BigInteger("0");
    for(int i = 0; i < word.length(); i++)
    {
      //handle autocompletion counters
      int hash = findHash(current);
      if(hash == -1)
      {
        addHash(current, false);
      }
      else
      {
        hashTable[hash].changeCount(true);
      }
      
      current = getChild(current, charToInt(word.charAt(i)));
      if(current.equals(new BigInteger("-1")))
      {
        return false;
      }
      
      else if(current.compareTo(size) >= 0)
      {
        increaseCapacity();
      }
    }
    
    int hash = findHash(current);
    if(hash == -1)
    {
      addHash(current, true);
    }
    else
    {
      hashTable[hash].setWord(true);
    }
    
    return true;
  }
  
  public boolean isWord(String word)
  { 
    BigInteger current = search(word, false);
    if(current == null)
    {
      return false;
    } 
    
    else
    {
      //handle hash here
      int hash = findHash(current);
      if(hash == -1)
      {
        return false;
      }
    
      else
      {
        return hashTable[hash].isWord();
      }
    }
  }
  
  public int charToInt(char letter)
  {
    int intVal = (int)letter - 97;
    if(intVal < 0 || intVal > 25)
    {
      return -1;
    }
    
    return intVal;
  }
  
  public boolean delete(String word)
  {
    if(!isWord(word))
    {
      return false;
    } 
    
    else
    {
      BigInteger current = search(word, true);
      //delete from hash
      int hashIndex = findHash(current);
      if(hashIndex == -1)
      {
        return false;
      }
      else
      {
        hashTable[hashIndex].setWord(false);
        return true;
      }
    }
  }
  
  public String autoComplete(String word)
  {
    BigInteger current = search(word, false);
    if(current == null || hashTable[findHash(current)].getCount() == 0)
    {
      return null;
    }
    
    else
    {
      ArrayList<Character> chars = new ArrayList<Character>();
      boolean done = false;
      while(!done)
      {
        int i = 0;
        BigInteger nextChild = getChild(current, 0);
        int nextHash = findHash(nextChild);
        while(nextHash == -1 || (!hashTable[nextHash].isWord() && hashTable[nextHash].getCount() == 0))
        {
          i++;
          nextChild = getChild(current, i);
          nextHash = findHash(nextChild);
        }
      
        //i should never get to 26 so don't even test for it.
        chars.add(Character.valueOf((char)(i + 97)));
        if(hashTable[nextHash].isWord())
        {
          done = true;
        }
        
        else
        {
          current = nextChild;
        }
      }
      
      for(int i = 0; i < chars.size(); i++)
      {
        word = word + chars.get(i);
      }
      
      return word;
      
    }
  }
}
