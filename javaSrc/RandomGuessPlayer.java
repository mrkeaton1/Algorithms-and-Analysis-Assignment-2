import java.io.*;
import java.util.*;

/**
 * Random guessing player.
 * This player is for task B.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class RandomGuessPlayer extends ParentPlayer implements Player
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
	public RandomGuessPlayer(String gameFilename, String chosenName)
		{
		super(gameFilename);
		this.chosenCharacter = chosenName;
		} // end of RandomGuessPlayer()

	// The player randomly selects an attribute and value from the possible attributes and values
	// of the remaining characters, and then creates a guess from these options.
	// When there is one character remaining to make guesses from, the player will select that character.
	public Guess guess() {
		if(numLeftCharacters == 1){ // Make a guess on the character
			return new Guess(Guess.GuessType.Player, "", "P1"); // ***Change the 3rd parameter to align with leftCharactersList implementation update
		}
		else{ // Randomly guess a valid attribute-value pair
			String[] attributes = (String[])leftValuesCount.keySet().toArray(); //***String array casting may be unnecessary
			String randAtt = attributes[(int)(Math.random()*attributes.length)];
			String[] randAttVals = (String[])leftValuesCount.get(randAtt).keySet().toArray(); //***String array casting may be unnecessary
			String randVal = randAttVals[(int)(Math.random()*randAttVals.length)];
			return new Guess(Guess.GuessType.Attribute, randAtt, randVal);
		}
	} // end of guess()

	public boolean receiveAnswer(Guess currGuess, boolean answer) {
		if(currGuess.getType().equals(Guess.GuessType.Player) && answer) return true;
		else if(currGuess.getType().equals(Guess.GuessType.Player) && !answer){
			return false; //this situation will never actually happen, as players will only guess characters when there is only one remaining candidate.
		}
		else{
			String guessAtt = currGuess.getAttribute();
			String guessVal = currGuess.getValue();
			if(answer){ // The attribute guess was correct
				for(Map.Entry<String,HashMap<String,String>> leftCharacter : leftCharactersMap.entrySet()){ // Remove all characters that don't contain the guessed attribute
					HashMap<String,String> currCharacter = leftCharacter.getValue();
					if(!currCharacter.get(guessAtt).equals(guessVal)){
						//***replace following two lines with RemoveCharacter
						leftCharactersMap.remove(currCharacter);
						numLeftCharacters--;
					}
				}
			}
			else{ // The attribute guess was incorrect
				for(Map.Entry<String,HashMap<String,String>> leftCharacter : leftCharactersMap.entrySet()){ // Remove all characters that don't contain the guessed attribute
					HashMap<String,String> currCharacter = leftCharacter.getValue();
					if(currCharacter.get(guessAtt).equals(guessVal)){
						//***replace following two lines with RemoveCharacter
						leftCharactersMap.remove(currCharacter);
						numLeftCharacters--;
					}
				}
			}
			return false;
		}
	} // end of receiveAnswer()

} // end of class RandomGuessPlayer
