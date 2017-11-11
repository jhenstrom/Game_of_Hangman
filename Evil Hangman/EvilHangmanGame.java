//package hangman;

import java.io.IOException;

import java.io.File;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import java.util.HashSet;

import java.util.Scanner;

import java.io.FileNotFoundException;

public class EvilHangmanGame implements IEvilHangmanGame{

  public Set<Character> guesses = new HashSet<Character>();
  public StringBuilder key = new StringBuilder();
  public char currGuess;
  public Set<String> currDict = new HashSet<String>();
  public int winState;
  public int numAppeared;

	public void startGame(File dictionary, int wordLength)
  {
		Scanner in = null;
		try
		{
			in = new Scanner(dictionary);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		while(in.hasNextLine())
		{
      String s = in.nextLine();
      if (s.length() == wordLength)
			   currDict.add(s);
		}

    for (int i = 0; i < wordLength; i++)
    {
      key.append('-');
    }
		return;
  }

	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException
  {
    numAppeared = 0;
    if (guesses.contains(guess))
    {
      GuessAlreadyMadeException e = new GuessAlreadyMadeException();
      throw(e);
    }
    else
      guesses.add(guess);
    Map<String,Set<String>> myMap = new HashMap<String,Set<String>>();
    for (String s : currDict)
    {
      StringBuilder p = new StringBuilder();
      p = patternString(s, guess);
    //  System.out.println(p.toString());
      if (!myMap.containsKey(p.toString()))
        myMap.put(p.toString(), new HashSet<String>());
      myMap.get(p.toString()).add(s);
    }
    StringBuilder bestKey = new StringBuilder();
    for (String s : myMap.keySet())
    {
      //System.out.println(myMap.get(s).toString() + " " + myMap.get(s).size());
      StringBuilder s2 = new StringBuilder(s);
      //System.out.println(myMap.get());
      if (!myMap.containsKey(bestKey.toString()))
      {
      //  System.out.println("bestKey not in map");
        bestKey = s2;
      }
      else if (myMap.get(s).size() > myMap.get(bestKey.toString()).size())
      {
      //  System.out.println("bestKey < s2");
        bestKey = s2;
      }
      else if (myMap.get(s).size() == myMap.get(bestKey.toString()).size())
      {
        if (!greaterThan(bestKey, s2))
        {
        //  System.out.println("bestKey < s2 for letter placement");
          bestKey = s2;
        }
        //else
          //System.out.println("bestKey > s2 for letter placement");
      }
    }
    numAppeared = diff(bestKey, key);
    key = combinePatterns(bestKey, key);

    int numNum = 0;
    for (int i = 0; i < key.length(); i ++)
    {
      if (key.charAt(i) != '-') numNum++;
    }
    if (numNum == key.length())
    {
      winState = 1;
    }
    return myMap.get(bestKey.toString());
  }

  StringBuilder patternString(String s, char c)
  {
    StringBuilder temp = new StringBuilder(s);
    StringBuilder retrn = new StringBuilder();
    for (int i = 0; i < temp.length(); i++)
    {
      if (temp.charAt(i) == c)
        retrn.append(c);
      else
        retrn.append('-');
    }
    return retrn;
  }

  public boolean greaterThan(StringBuilder l, StringBuilder p)
  {
    if (p == null || l == null) System.out.println("null stringbuilder");
    int count1 = 0;
    int count2 = 0;
    for(int i = 0; i < l.length(); i++)
      if (l.charAt(i) != '-') count1++;
    for (int i = 0; i < p.length(); i++)
      if (p.charAt(i) != '-') count2++;
    if (count1 > count2) return false;
    else if (count1 < count2) return true;
    else
    {
      for (int i = p.length() - 1; i >= 0; i--)
      {
        if (p.charAt(i) != '-' && l.charAt(i) == '-') return false;
        if (l.charAt(i) != '-' && p.charAt(i) == '-') return true;
      }
      System.out.println("ERROR:: count3.pattern == count4.pattern");
      return false;
    }
  }

  public StringBuilder combinePatterns(StringBuilder b, StringBuilder p)
  {
    StringBuilder temp = new StringBuilder();
    for (int i = 0; i < b.length(); i++)
    {
      if (b.charAt(i) != '-')
        temp.append(b.charAt(i));
      else if (p.charAt(i) != '-')
        temp.append(p.charAt(i));
      else
        temp.append('-');
    }
    return temp;
  }

  public int diff(StringBuilder b, StringBuilder p)
  {
    int temp = 0;
    for (int i = 0; i < b.length(); i++)
    {
      if (b.charAt(i) != '-' && p.charAt(i) == '-') temp++;
    }
    return temp;
  }
}
