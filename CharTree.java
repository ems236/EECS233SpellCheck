import java.util.ArrayList;
public class CharTree implements Dict
{
  private CharTreeNode root = new CharTreeNode(true);
  
  public CharTree()
  {
    //don't need to initialize anything?
  }
  
  public CharTreeNode search(String word, boolean deleting)
  {
    CharTreeNode cursor = root;
    for(int i = 0; i < word.length(); i++)
    {
      if(deleting)
      {
        cursor.changeCount(false);
      }
      //if not deleting, searching. if no children can return null.
      else if(cursor.getCount() < 1)
      {
        return null;
      }
      
      if(!cursor.hasChild(word.charAt(i)))
      {
        return null;
      }
      
      cursor = cursor.getChild(word.charAt(i));
    }
    return cursor;
  }
  
  public boolean add(String word)
  {
    CharTreeNode cursor = root;
    
    for(int i = 0; i < word.length(); i++)
    {
      cursor.changeCount(true);
      
      if(!cursor.hasChild(word.charAt(i)))
      {
        if(!cursor.addChild(word.charAt(i)))
        {
          //invalid character
          return false;
        }
      }
      
      cursor = cursor.getChild(word.charAt(i)); 
    }
    
    //if it's already a word, delete all the extra substring counts that just got added
    if(cursor.isWord())
    {
      delete(word);
    }
    cursor.setData(true);
    return true;
  }
  
  public boolean isWord(String word)
  {
    CharTreeNode node = search(word, false);
 
    if(node == null)
    {
      return false;
    }
    else
    {
      return node.isWord();
    }
  }
  
  public boolean delete(String word)
  {
    if(isWord(word))
    {
      CharTreeNode node = search(word, true);
 
      if(node == null)
      {
        return false;
      }
    
      else
      {
        node.setData(false);
        return true;
      }
    }
    
    else
    {
      return false;
    }
  }
  
  public String autoComplete(String word)
  {
    CharTreeNode node = search(word, false);
 
    if(node == null)
    {
      return null;
    }
    
    else
    {
      //find next word
      
      boolean done = false;
      ArrayList<Character> chars = new ArrayList<Character>();
      
      while(!done)
      {
        int i = 0;
        boolean found = false;
        while(!found && i < 26)
        {
          if(node.hasChild((char)(i + 97)))
          {
            node = node.getChild((char)(i + 97));
            found = true;
          }
          i++;
        }
        
        if(i == 26)
        {
          return null;
        }
        
        else if(node.isWord())
        {
          done = true;
          chars.add(Character.valueOf((char)(i + 96)));
        }
        
        else
        {
          if(node.getCount() < 1)
          {
            return null;
          }
          chars.add(Character.valueOf((char)(i + 96)));
        }
      }
      
      if(chars.isEmpty())
      {
        return null;
      }
      
      else
      {
        String output = word;
        for(int i = 0; i < chars.size(); i++)
        {
          output = output + chars.get(i);
        }
        
        return output;
      }
    }
  }
}