import java.io.*;
import java.util.*;

/**
 * Interface of a player in Guess Who game.
 * Your player implementations must directly or indirectly implement this interface.
 */
public interface Player
	{
	/**
	 * Ask player for a guess.  The guess can be either about attribute-value pair,
	 * or asking if opponent's chosen person is a certain person.  See Guess.java
	 * for details about how to create an appropriate Guess object.
	 *
	 * @return This player's guess.
	 */
	abstract public Guess guess();


	// /**
	//  * Ask player for an answer/response to a guess.
	//  *
	//  * @param currGuess Opponent's guess.
	//  * @return True (yes) or False (no) to opponent's guess.
	//  */
	// abstract public boolean answer(Guess currGuess);


	/**
	 * After asking/making a guess, this method passes the initial guess,
	 * along with the answer from their opponent, to the player.
	 *
	 * @param currGuess This player's initial guess.
	 * @param answer Opponent's answer to guess.
	 *
	 * @return True if player made a Person guess and it was true.  In other words,
	 *   this player has guessed opponent's chosen person and this player has
	 *   finished.  Otherwise return false.
	 */
	abstract public boolean receiveAnswer(Guess currGuess, boolean answer);

	} // end of interface Player

abstract class ParentPlayer
	{
	private static final Boolean DEBUG = true;

	protected List<HashMap<String,String>> leftCharactersList;						// list of hashmaps for the LEFTOVER game characters (character number = list index +1), each of which maps their attributes to their values
	protected int numLeftCharacters = 0;
	protected HashMap<String,String[]> possibleAttr;								// maps possible attributes to a list of their possible values

	// this should be set by constructor of Player-classes who inherit from us
	protected String chosenCharacterName = "";
	protected HashMap<String,String> chosenCharacterMap;

	protected ParentPlayer(String filename)
		{
		leftCharactersList = new ArrayList<HashMap<String,String>>();
		possibleAttr = new HashMap<String,String[]>();
		loadGame(filename);
		chosenCharacterMap = new HashMap<String,String>(possibleAttr.size());		// all characters should have all possible attributes
		numLeftCharacters = leftCharactersList.size();								// in the beginning, all of the characters are considered
		}

	private void loadGame(String filename)
		{
		try ( BufferedReader reader = new BufferedReader(new FileReader(filename)) )
			{
			long startTime = System.nanoTime();
			String line;
			String delimiter = " ";
			String[] tokens;
			boolean newCharacter = false;

			while ((line = reader.readLine()) != null)
				{
				tokens = line.split(delimiter,2);									// the pattern is applied only once, thus the returned array will be at most 2 long
																					// (containing all values of an attribute in the second element for the case of game description)
				if (tokens[0].equals(""))											// we are at the end of any description
					{
					newCharacter = false;
					continue;
					}
				else {
					if (newCharacter)												// we are in the middle of character description
						{
						leftCharactersList.get(leftCharactersList.size()-1).put(tokens[0],tokens[1]);
						continue;
						}
					else if (1==tokens.length && 'P' == tokens[0].charAt(0))		// double check ('P' could possibly be the start of a game description line) to make sure we are at the start of a character description block
						{
						newCharacter = true;
						leftCharactersList.add(new HashMap<String,String>());
						continue;
						}
					else {															// we are at a game description block
						newCharacter = false;
						possibleAttr.put(tokens[0],tokens[1].split(delimiter));
						continue;
						}
					}
				}
			long endTime = System.nanoTime();
			if(DEBUG) System.out.println("Time to load game config file: " + (double)(endTime-startTime)*Math.pow(10,-9) + " s");
			}
		catch (FileNotFoundException ex) {
			System.err.println("File " + filename + " not found.");
			System.err.println(ex.getMessage());
			System.err.println(ex.getCause());
			return;
			}
		catch(IOException ex) {
			System.err.println("Cannot open file " + filename);
			System.err.println(ex.getMessage());
			System.err.println(ex.getCause());
			return;
			}
		}

	// Receives the guess from other player and returns a true or false based off the player's character and attribute-value pairs
	public boolean answer(Guess currGuess) {
		if (currGuess.getType().equals(Attribute)){ // The opponent is guessing the attribute-value pair
			String guessAtt = currGuess.getAttribute();
			String guessVal = currGuess.getValue();
			return chosenCharacterMap.get(guessAtt).equals(guessVal); //returns true if the player's character contains the attribute-value pair.
		}
		else{ // The opponent is guessing the character
			return currGuess.getValue().equals(chosenCharacterName); //returns true if the opponent guesses the player's character
		}
	} // end of answer()

	protected void printGameAttributes()
		{
		Set keys = possibleAttr.keySet();
		String key;
		System.out.println("\nGame attribute set:");

		for (Iterator it = keys.iterator(); it.hasNext(); )
			{
			key = (String) it.next();
			System.out.println(key + " - " + Arrays.toString(possibleAttr.get(key)));
			}
		return;
		}

	protected void printGameCharacters()
		{
		int characterNum = 1;
		System.out.println("\nGame character set:");

		for (HashMap hm : leftCharactersList)
			{
			Set keys = hm.keySet();
			String key;
			System.out.println("P" + characterNum++);

			for (Iterator it = keys.iterator(); it.hasNext(); )
				{
				key = (String) it.next();
				System.out.println(key + " - " + Arrays.toString(possibleAttr.get(key)));
				}
			}
		return;
		}

	// public void loadGameJ8(String filename)
	// 	{
	// 	try (Stream<String> lines = Files.lines(Paths.get(filename)))
	// 		{
	// 		long startTime = System.nanoTime();
	// 		lines.forEach(s -> System.out.println(s));
	// 		long endTime = System.nanoTime();
	// 		if(DEBUG) System.out.println("Time to load file: " + (double)(endTime-startTime)*Math.pow(10,-9) + " s");
	// 		}
	// 	catch (FileNotFoundException ex) {
	// 		System.err.println("File " + filename + " not found.");
	// 		System.err.println(ex.getMessage());
	// 		System.err.println(ex.getCause());
	// 		return;
	// 	}
	// 	catch(IOException ex) {
	// 		System.err.println("Cannot open file " + filename);
	// 		System.err.println(ex.getMessage());
	// 		System.err.println(ex.getCause());
	// 		return;
	// 	}
	//
	// 	return;
	// 	}

	}
