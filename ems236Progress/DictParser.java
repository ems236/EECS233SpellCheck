import java.io.*;
import java.util.Scanner;

public class DictParser
{ 
  public static void main(String[] args) throws IOException
  {
    long startTime;
    
    //dict.txt contains all words on their own line in alphabetical order.
    
    Scanner dictText;
    
    //made an interface so this can be done in a loop
    Dict[] dicts = {new DictList(), new CharTree(), new StringHash(), new SearchableHash()};
    
    //build dictionaries
    for(int i = 0; i < dicts.length; i++)
    { 
      dictText = new Scanner(new File("dict.txt"));
      startTime = System.currentTimeMillis(); 
      //370099 words total
      while(dictText.hasNextLine())
      {
        String word = dictText.nextLine().toLowerCase();
        dicts[i].add(word);
      }
      dictText.close();
      System.out.println(System.currentTimeMillis() - startTime);
    }
    
    //run time of isWord 
    //use randomized list to get a good average.
    //generated some random words and some random strings to search.  Should be reasonable spread out lenghts and alphabetic positions.
    String[] testWords = 
    {
       "decade"
      , "cooperate"
      , "magnitude"
      , "contract"
      , "switch"
      , "throat"
      , "sausage"
      , "climate"
      , "berry"
      , "program"
      , "cotton"
      , "achievement"
      , "double"
      , "encourage"
      , "reform"
      , "ministry"
      , "talkative"
      , "unfortunate"
      , "transition"
      , "iemxyngygu"
      , "pcsxvqgwvh"
      , "wfhsrvfwlg"
      , "rxsuzockdk"
      , "qdsyy"
      , "gvabn"
      , "njzzt"
      , "yskoj" 
    };
    /*
    //5 linearly spaced dictionary sizes
    for(int i = 1; i < 6; i++)
    {
      Dict[] testDicts = buildNSize(new Dict[] {new DictList(), new CharTree(), new StringHash(), new SearchableHash()}, i, 5);
      
      //perform actual test
      for(int j = 0; j < testDicts.length; j++)
      {
        //search all test words a bunch of times, array is so much slower than the others so adjust
        int[] constants = {500, 10000000, 10000000, 10000000};
        
        long startTime2 = System.currentTimeMillis();
        for(int k = 0; k < constants[j]; k++)
        {
          for(int m = 0; m < testWords.length; m++)
          {
            testDicts[j].isWord(testWords[m]);
          }
        }
        long runTime = System.currentTimeMillis() - startTime2;
        System.out.println("Dict " + j + " N: " + i + " SearchTime: " + runTime);
      }
    }
    */
    testWords = new String[] {
       "a"
      , "z"
      , "ox"
      , "wz"
      , "map"
      , "nnn"
      , "four"
      , "ffff"
      , "berry"
      , "wrrrd"
      , "cotton"
      , "xxxxxx"
      , "weasels"
      , "qqqqqqq"
      , "ministry"
      , "hhhhhhhh" 
    };
    
    //test dependence on word size using dicts[]
    for(int i = 0; i < testWords.length; i += 2)
    {
      for(int j = 0; j < dicts.length; j++)
      {
        int[] constants = {500, 100000000, 100000000, 100000000};
        
        long startTime3 = System.currentTimeMillis();
        for(int k = 0; k < constants[j]; k++)
        {
          dicts[j].isWord(testWords[i]);
          dicts[j].isWord(testWords[i+1]);
        }
        long runTime = System.currentTimeMillis() - startTime3;
        int length = i/2 + 1;
        System.out.println("Dict " + j + " N: " + length + " SearchTime: " + runTime);
      }
    }
    
    //test of all methods, no runtime
    for(int i = 0; i < dicts.length; i++)
    {
      System.out.println("\r\n" + dicts[i].isWord("banana"));
      System.out.println(dicts[i].isWord("bananananansdwf"));
      System.out.println(dicts[i].isWord("abacax"));
      dicts[i].delete("banana");
      System.out.println(dicts[i].isWord("banana") + "\r\n");
      System.out.println(dicts[i].autoComplete("aal"));
      System.out.println(dicts[i].autoComplete("aals") + "\r\n");  
    }
  }
  
  //builds a dictionary of some size N.  
  public static Dict[] buildNSize(Dict[] dicts, int iteration, int fraction) throws IOException
  {
    Scanner dictText;
    for(int i = 0; i < dicts.length; i++)
    { 
      dictText = new Scanner(new File("dict.txt"));
      //370099 words total
      //build this way so that no bias in alphabetical position of words while testing
      int counter = 0;
      while(dictText.hasNextLine())
      {
        String word = dictText.nextLine().toLowerCase();
        if(counter < iteration)
        {
          dicts[i].add(word);
        }
        
        counter++;
        counter %= fraction;
      }
      dictText.close();
    }
    
    return dicts;
  }
}