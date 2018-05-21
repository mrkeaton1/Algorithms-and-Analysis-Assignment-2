import java.io.*;
import java.util.*;

/**
 * Binary-search based guessing player.
 * This player is for task C.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class BinaryGuessPlayer extends ParentPlayer implements Player
	{
	// this holds the number of characters that have a value of a given attribute
	// => HashMap of Key:Attribute, Values:(HashMap of Key:Attribute-Value, Value:Count)
	private HashMap<String,HashMap<String,Integer>> attributeCount;


	/**
	 * Loads the game configuration from gameFilename, and also store the chosen
	 * person.
	 *
	 * @param gameFilename Filename of game configuration.
	 * @param chosenName Name of the chosen person for this player.
	 * @throws IOException If there are IO issues with loading of gameFilename.
	 *    Note you can handle IOException within the constructor and remove
	 *    the "throws IOException" method specification, but make sure your
	 *    implementation exits gracefully if an IOException is thrown.
	 */
	public BinaryGuessPlayer(String gameFilename, String chosenName)
		{
		super(gameFilename);
		this.chosenCharacter = chosenName;
		attributeCount = new HashMap<String,HashMap<String,Integer>>(possibleAttr.size());

		// initialize attributeCount HashMap
		for (Map.Entry<String,String[]> entry : possibleAttr.entrySet())
			{
			attributeCount.put(entry.getKey(), new HashMap<String,Integer>());
			for(String value : entry.getValue())
				{
				attributeCount.get(entry.getKey()).put(value,0);
				}
			}

		// testing:
		printGameAttributes();
		printGameCharacters();
		} // end of BinaryGuessPlayer()


	public Guess guess()
		{
		// placeholder, replace
		return new Guess(Guess.GuessType.Person, "", "Placeholder");
	} // end of guess()


	public boolean answer(Guess currGuess) {

		// placeholder, replace
		return false;
	} // end of answer()


	public boolean receiveAnswer(Guess currGuess, boolean answer) {

		// placeholder, replace
		return true;
	} // end of receiveAnswer()

	private String determineHalfingAttribute()
		{
		// => HashMap of Key:Attribute, Values:(HashMap of Key:Attribute-Value, Value:Count)
		HashMap<String,Integer> currentValue;

		// // @QUESTION why doesnt this work with a foreach loop?
		// for (HashMap character : characterList)
			// {
			// for (Map.Entry<String,String> entry : character.entrySet())
			
		for (int i=0;i<characterList.size();++i)
			{
			for (Map.Entry<String,String> entry : characterList.get(i).entrySet())
				{
				currentValue = attributeCount.get(entry.getKey());
				currentValue.put(entry.getValue(), currentValue.get(entry.getValue())+1 );
				}
			}
		for (Map.Entry<String,HashMap<String,Integer>> entry : attributeCount.entrySet())
			{

			}
		return "";
		}

} // end of class BinaryGuessPlayer
