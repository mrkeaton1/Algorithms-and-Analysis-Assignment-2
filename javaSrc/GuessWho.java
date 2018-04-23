import java.io.*;
import java.util.*;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import java.lang.String;

/**
 * Main class for GuessWho framework.
 *
 * @author Jeffrey Chan, 2016
 */
public class GuessWho
{
    /** Name of class, used in error messages. */
	protected static final String progName = "GuessWho";

    /**
	 * Print help/usage message.
	 */
	public static void usage() {
		System.err.println(progName + ": [-l <filename to write game logs to>] [game configuration filename] [chosen persons filename] [type of player1] [type of player2]");
		System.err.println("<type of player> = <random | binary>");
		System.exit(1);
	} // end of usage


    /**
     * Main method.
     */
    public static void main(String[] args) {
        //
        // parse command line options
        //
		OptionParser parser = new OptionParser("l:");
		OptionSet options = parser.parse(args);

        boolean bLog = false;
        String logFilename = null;
		// -l <inputFilename> specifies the file to write game log information to.
		if (options.has("l")) {
            bLog = true;
            if (options.hasArgument("l")) {
                logFilename = (String) options.valueOf("l");
            }
        }

		// non option arguments
		List<?> tempArgs = options.nonOptionArguments();
		List<String> remainArgs = new ArrayList<String>();
		for (Object object : tempArgs) {
			remainArgs.add((String) object);
		}

		// check number of arguments
		if (remainArgs.size() != 4) {
			usage();
		}

		String gameFilename = remainArgs.get(0);
        String assignedFilename = remainArgs.get(1);
        String player1Type = remainArgs.get(2);
        String player2Type = remainArgs.get(3);


        //
        // load chosen person file
        //
		try {
            // load chosen persons for both players
            BufferedReader assignedReader = new BufferedReader(new FileReader(assignedFilename));

            String line;
            String player1AssignedName = null;
            String player2AssignedName = null;
            if ((line = assignedReader.readLine()) != null) {
                String[] fields = line.split(" ");
			  	if (fields.length != 2) {
				  	throw new IOException(assignedFilename + ": Misformed field line: " + line);
			  	}
                player1AssignedName = fields[0];
                player2AssignedName = fields[1];
            }

            //
            // Construct each player.
            // Each player will load the game configuration and store it according to their own design.
            //

            // Load player 1
            Player player1 = null;
            // select the type of players to play against each other
            switch (player1Type) {
                // Task B (random guessing player)
                case "random":
                    player1 = new RandomGuessPlayer(gameFilename, player1AssignedName);
                    break;
                // Task C (binary search based guessing player)
                case "binary":
                    player1 = new BinaryGuessPlayer(gameFilename, player1AssignedName);
                    break;
                default:
                    System.err.println("Uknown player 1 type option: " + player1Type);
                    usage();
            }

            // Load player 2
            Player player2 = null;
            switch (player2Type) {
                // Task B (random guessing player)
                case "random":
                    player2 = new RandomGuessPlayer(gameFilename, player2AssignedName);
                    break;
                // Task C (binary search based guessing player)
                case "binary":
                    player2 = new BinaryGuessPlayer(gameFilename, player2AssignedName);
                    break;
                default:
                    System.err.println("Uknown player 2 type option: " + player2Type);
                    usage();
            }


            //
            // Game logger
            //
            MoveLog log = null;
            if (bLog) {
                log = new MoveLog(logFilename);
            }
            else {
                log = new MoveLog();
            }

            //
            // Game loop
            //
            boolean player1Finished = false;
            boolean player2Finished = false;

            // Note if you do not have any implementation, receiveAnswer() will always
            // return true (to prevent infinite loop), so game ends after 1 round
            // as tie.
            int round = 1;
            while (!player1Finished && !player2Finished) {
                log.add("Round " + round);
                // player 1 makes a guess
                Guess currGuess = player1.guess();
                log.add("Player 1 guessing " + currGuess);
                // player 2 responds to guess
                boolean currAnswer = player2.answer(currGuess);
                log.add("Player 2 answering " + currAnswer);
                // player 1 receives response and updates own status
                // If player 1 made a person guess and it was correct, player1Finished should be true;
                // otherwise be false.
                player1Finished = player1.receiveAnswer(currGuess, currAnswer);

                // player 2's turn
                currGuess = player2.guess();
                log.add("Player 2 guessing " + currGuess);
                // player 1 responds to guess
                currAnswer = player1.answer(currGuess);
                log.add("Player 1 answering " + currAnswer);
                // player 2 receives response and updates own status
                // If player 2 made a person guess and it was correct, player2Finished should be true;
                // otherwise be false
                player2Finished = player2.receiveAnswer(currGuess, currAnswer);

                round++;
            }

            // determine outcome of game
            if (player1Finished && player2Finished) {
                log.add("It's a tie!");
            }
            else if (player1Finished) {
                log.add("Player 1 won!");
            }
            else {
                log.add("Player 2 won!");
            }
		}
		catch(FileNotFoundException ex) {
			System.err.println("Missing file " + ex.getMessage() + ".");
			usage();
		}
		catch(IOException ex) {
			System.err.println(ex.getMessage());
			usage();
		}

    } // end of main()

} // end of class GuessWho


/**
 * Game log class.
 */
class MoveLog
{
    /** Output writer to write log to. */
    private PrintWriter mWriter;

    /** Whether to output to file (mWriter) as well as System.out. */
    private boolean mFileoutput;

    /**
     * Constructor, that sets output to be written to a log file as well as System.out.
     *
     * @param outFilename The name of the file to write game log output to.
     * @throws IOException When there are IO issues from openning file.
     */
    public MoveLog(String outFilename) throws IOException {
        // automatically flush
        mWriter = new PrintWriter(new FileWriter(outFilename), true);
        mFileoutput = true;
    } // end of MoveLog()


    /**
     * Constructor, output just written to System.out.
     */
    public MoveLog()  {
        // no writer
        mWriter = null;
        mFileoutput = false;
    } // end of MoveLog()


    /**
     * Add input line to System.out and to mWriter if output to file is active.
     *
     * @param line Input line to write.
     */
    public void add(String line) {
        // output to file
        if (mFileoutput) {
            mWriter.println(line);
            mWriter.flush();
        }

        // output to stdout
        System.out.println(line);
    } // end of add()

} // end of inner class MoveLog
