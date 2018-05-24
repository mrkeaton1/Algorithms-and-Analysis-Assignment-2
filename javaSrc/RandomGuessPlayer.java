import java.io.*;

/**
 * Random guessing player.
 * This player is for task B.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class RandomGuessPlayer extends ParentPlayer implements Player
	{
	private String chosenCharacter = "";

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
	public RandomGuessPlayer(String gameFilename, String chosenName)
		{
		super(gameFilename);
		this.chosenCharacter = chosenName;
		} // end of RandomGuessPlayer()

	// The player randomly selects an attribute and value from the possible attributes and values
	// of the remaining characters, and then creates a guess from these options.
	// When there is one character remaining to make guesses from, the player will select that character.
	public Guess guess() {
		if(numLeftCharacters = 1){ // Make a guess on the character
			return Guess(Player, "", "P1"); // ***Change the 3rd parameter to align with leftCharactersList implementation update
		}
		else{ // Randomly guess a valid attribute-value pair
			String[] attributes = (String[])possibleAttr.keySet().toArray();
			String randAtt = attributes[Math.random()*attributes.length];
			String[] randAttVals = possibleAttr.get(randAtt);
			String randVal = randAttVals[Math.random()*randAttVals.length];
			return Guess(Attribute, randAtt, randVal);
		}
	} // end of guess()

	public boolean receiveAnswer(Guess currGuess, boolean answer) {

		// placeholder, replace
		return true;
	} // end of receiveAnswer()

} // end of class RandomGuessPlayer
