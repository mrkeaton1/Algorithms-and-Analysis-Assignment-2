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

		// initialize the chosen player, needed for answering questions
		this.chosenCharacterName = chosenName;
		this.chosenCharacterMap = leftCharactersMap.get(chosenName);

		// testing:
		// printGameAttributes();
		// printGameCharacters();
		}


	public Guess guess()
		{
		float targetCount = leftCharactersMap.size()/2;								// we care about odd/even number of characters later
		int currentBestCount = 0;
		String currentBestAttribute = "";
		String currentBestValue = "";

		for (Map.Entry<String,HashMap<String,Integer>> attribute : leftValuesCount.entrySet())
			{
			for (Map.Entry<String,Integer> pair : attribute.getValue().entrySet())
				{
				if (pair.getValue()==Math.floor(targetCount) || pair.getValue()==Math.ceil(targetCount))	// we found a halfing attribute; in case of odd number of characters, we want to round up and down for comparison
					{
					return new Guess(Guess.GuessType.Attribute, attribute.getKey(), pair.getKey());
					}
				else if (Math.abs(pair.getValue()-currentBestCount) < Math.abs(targetCount-currentBestCount))
					{
					currentBestCount = pair.getValue();
					currentBestAttribute = attribute.getKey();
					currentBestValue = pair.getKey();
					}
				else continue;
				}
			}
		return new Guess(Guess.GuessType.Attribute, currentBestAttribute, currentBestValue);
		}

	public boolean answer(Guess currGuess) {

		// placeholder, replace
		return false;
	} // end of answer()


	public boolean receiveAnswer(Guess currGuess, boolean answer) {

		// placeholder, replace
		return true;
	} // end of receiveAnswer()


} // end of class BinaryGuessPlayer
