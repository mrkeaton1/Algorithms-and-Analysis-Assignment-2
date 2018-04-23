import java.io.*;


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


	/**
	 * Ask player for an answer/response to a guess.
	 *
	 * @param currGuess Opponent's guess.
	 * @return True (yes) or False (no) to opponent's guess.
	 */
	abstract public boolean answer(Guess currGuess);


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
