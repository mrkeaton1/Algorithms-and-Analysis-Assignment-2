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

	// HashMap that maps names of LEFTOVER game characters
	// to a HashMap of their Attribute-Value pairs
	// => HashMap of Key:Character-Name, Values:(HashMap of Key:Character-Attribute, Value:Attribute-Value)
	protected HashMap<String,HashMap<String,String>> leftCharactersMap;
	protected int numLeftCharacters = 0;

	// HashMap that maps all possible attributes
	// to a HashMap of their possible/leftover values and the count of players who have that value
	// => HashMap of Key:Attribute, Values:(HashMap of Key:Attribute-Value, Value:Count)
	protected HashMap<String,HashMap<String,Integer>> leftValuesCount;

	// the following should be set by constructor of Player-classes who inherit from us
	protected String chosenCharacterName = "";
	protected HashMap<String,String> chosenCharacterMap;

	protected ParentPlayer(String filename)
		{
		leftCharactersMap = new HashMap<String,HashMap<String,String>>();
		leftValuesCount = new HashMap<String,HashMap<String,Integer>>();
		loadGame(filename);															// this fills leftValuesCount and leftCharactersMap
		chosenCharacterMap = new HashMap<String,String>(leftValuesCount.size());	// all characters should have all possible attributes
		numLeftCharacters = leftCharactersMap.size();								// in the beginning, all of the characters are considered
		}

	private void loadGame(String filename)
		{
		try ( BufferedReader reader = new BufferedReader(new FileReader(filename)) )
			{
			if (DEBUG) long startTime = System.nanoTime();
			String line;
			String delimiter = " ";
			String[] tokens;
			String[] attrValues;
			String currentCharacterName = "";

			while ((line = reader.readLine()) != null)
				{
				tokens = line.split(delimiter,2);									// the pattern is applied only once, thus the returned array will be at most 2 long
																					// (containing all values of an attribute in the second element for the case of game description)
				if (tokens[0].equals(""))											// we are at the end of any description
					{
					currentCharacterName = "";
					continue;
					}
				else {
					if (!currentCharacterName.equals(""))							// we are in the middle of character description
						{
						leftCharactersMap.get(currentCharacterName).put(tokens[0],tokens[1]);
						continue;
						}
					else if (1==tokens.length)										// we are at the start of a character description block, whenever there is only one word on a line (and its not an empty string "")
						{
						currentCharacterName = tokens[0];
						leftCharactersMap.put(currentCharacterName,new HashMap<String,String>());
						continue;
						}
					else {															// we are at a game description block
						currentCharacterName = "";
						attrValues = tokens[1].split(delimiter);
						leftValuesCount.put(tokens[0],new HashMap<String,Integer>(attrValues.length));
						for (String val : attrValues)
							{
							leftValuesCount.get(tokens[0]).put(val,0);				// 0 is the initial count of values
							}
						continue;
						}
					}
				}

			if(DEBUG)
				{
				long endTime = System.nanoTime();
				System.out.println("Time to load game config file: " + (double)(endTime-startTime)*Math.pow(10,-9) + " s");
				}
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
		String innerKey, outerKey;
		Set outerKeys = leftValuesCount.keySet();
		Set innerKeys;

		System.out.println("\nGame attribute set:");
		for (Iterator it1 = outerKeys.iterator(); it1.hasNext(); )
			{
			innerKey = (String) it1.next();
			System.out.print(innerKey + " - ");
			innerKeys = leftValuesCount.get(innerKey).keySet();
			for (Iterator it2 = innerKeys.iterator(); it2.hasNext(); )
				{
				outerKey = it2.next();
				System.out.print(" " + outerKey + ":" + String.valueOf(leftValuesCount.get(innerKey).get(outerKey)));
				}
			System.out.print("\n");
			}
		return;
		}

	protected void printGameCharacters()
		{
		String innerKey, outerKey;
		Set outerKeys = leftValuesCount.keySet();
		Set innerKeys;

		System.out.println("\nGame character set:");
		for (Iterator it1 = outerKeys.iterator(); it1.hasNext(); )
			{
			innerKey = (String) it1.next();
			System.out.println(innerKey);
			innerKeys = leftValuesCount.get(innerKey).keySet();
			for (Iterator it2 = innerKeys.iterator(); it2.hasNext(); )
				{
				outerKey = it2.next();
				System.out.println(outerKey + " " + leftValuesCount.get(innerKey).get(outerKey));
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
