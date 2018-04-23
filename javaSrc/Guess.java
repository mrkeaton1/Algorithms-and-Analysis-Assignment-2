
/**
 * Class implementing a player's guess.
 *
 * @author Jeffrey Chan, 2016
 */
public class Guess
{
	/**
	 * Enum of the two types of guess in game.
	 * Attribute ("does your chosen person have attribute eyeColour with value blue?")
	 * Person ("Is your chosen person Ms X?")
	 * When constructing a Guess object, set this to the appropriate type of guess.
	 */
	public static enum GuessType {
		Attribute,
		Person
	};

	/** Guess type. */
	protected GuessType mType;
	/** Attribute (only valid when mType = Attribute) */
	protected String mAttribute;
	/** Value.
	 *  if mType = Attribute, then it is the value of the associated attribute).
	 *  if mType = Person, then this is the guessed person's name.
	 */
	protected String mValue;


	/**
	 * Constructor to create a new guess.
	 *
	 * If asking if the opponent's chosen person has an attribute-value pair (attr,val for example),
	 * then constructor should be called as follows:
	 *    Guess(Guess.GuessType.Attribute, attr, val)
	 * If asking if the opponent's chosen person is a particular person (X for example),
	 * then constructor should be called as follows:
	 *    Guess(Guess.GuessType.Person, "", X)
	 *
	 * @param type Type of guess.
	 * @param attribute Attribute of guess.
	 * @param value Value of guess.
	 */
	public Guess(GuessType type, String attribute, String value) {
		mType = type;
		mAttribute = attribute;
		mValue = value;
	} // end of Guess()


	/**
	 * @return Type of guess.
	 */
	public GuessType getType() {
		return mType;
	} // end of getType()


	/**
	 * @return Attribute of guess.
	 */
	public String getAttribute() {
		return mAttribute;
	} // end of getAttribute()


	/**
	 * @return Value of guess.
	 */
	public String getValue() {
		return mValue;
	}  // end of getValue()


	/**
	 * Converts Guess object to String.
	 *
	 * @return String representation of Guess object.
	 */
	public String toString() {
		return mType + " " + mAttribute + " " + mValue;
	} // end of toString()
} // end of class Guess
