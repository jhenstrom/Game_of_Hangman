//package hangman;

import java.util.Scanner;
import java.io.File;

public class Main
{

  public static void main(String[] args) {

    EvilHangmanGame game = new EvilHangmanGame();

    File dictionary = new File(args[0]);
    int wordLength = Integer.parseInt(args[1]);
    int guessCount  = Integer.parseInt(args[2]);

    game.startGame(dictionary, wordLength);

    while(guessCount > 0)
    {
      //System.out.println(game.currDict.toString());
      System.out.println("You have " + Integer.toString(guessCount) + " guesses left");
      System.out.println("Used Letters: " + game.guesses.toString());
      System.out.println("Word: " + game.key.toString());
      System.out.print("Make Guess: ");
      Scanner scanner = new Scanner(System.in);
      char currGuess = scanner.next().charAt(0);
      currGuess = Character.toLowerCase(currGuess);
      if (!(currGuess >= 'a' && currGuess <= 'z'))
      {
        System.out.println("Not a valid character");
        continue;
      }
      try
      {
        game.currDict = game.makeGuess(currGuess);
      }
      catch(Exception e)
      {
        System.out.println("Repeat Character");
        continue;
        //e.printStackTrace();
      }
      if(game.numAppeared == 0)
      {
        System.out.println(currGuess + " appeared 0 times");
        guessCount--;
      }
      else if (game.numAppeared > 0)
      {
        System.out.println(currGuess + " appeared " + game.numAppeared + " times");
      }
      if (game.winState == 1)
        {
          for (String s : game.currDict)
          {
            System.out.println("You Win! Word was: " + s);
            break;
          }
          break;
        }
    }
    if (game.winState != 1)
    {
      for (String s : game.currDict)
      {
        System.out.println("You lose... Word was: " + s);
        break;
      }
    }
  }
}
