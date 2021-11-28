import java.util.Scanner;

/**
 * Class DiningPhilosophers
 * The main starter.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class DiningPhilosophers
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */

	/**
	 * This default may be overridden from the command line
	 */
	public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;

	/**
	 * Dining "iterations" per philosopher thread
	 * while they are socializing there
	 */
	public static final int DINING_STEPS = 10;

	/**
	 * Our shared monitor for the philosphers to consult
	 */
	public static Monitor soMonitor = null;

	/*
	 * -------
	 * Methods
	 * -------
	 */

	/**
	 * Main system starts up right here
	 */
	public static void main(String[] argv)
	{
		try
		{
			/*
			 * TODO:
			 * Should be settable from the command line
			 * or the default if no arguments supplied.
			 */
			String input = null;
			boolean safe = false;
			int iPhilosophers;
			Scanner read = new Scanner(System.in);
			do {
				try{

					System.out.print("Enter the number of philosophers: ");
					input = read.next();
					iPhilosophers = Integer.parseInt(input);
					if (iPhilosophers<1)
						throw new NumberFormatException();
					safe = true;
				}
				catch (NumberFormatException e){
					System.out.println("\"" + input +"\" is not a positive decimal integer\n\nUsage: java DiningPhilosophers[NUMBER_OF_PHILOSOPHERS]");
					iPhilosophers = 0;
				}

			} while (!safe);


			soMonitor = new Monitor(iPhilosophers);

			// Make the monitor aware of how many philosophers there are


			// Space for all the philosophers
			Philosopher aoPhilosophers[] = new Philosopher[iPhilosophers];


			System.out.println
					(
							iPhilosophers +
									" philosopher(s) came in for a dinner."
					);

			// Let 'em sit down
			for(int j = 0; j < iPhilosophers; j++)
			{
				aoPhilosophers[j] = new Philosopher();
				aoPhilosophers[j].start();
			}



			// Main waits for all its children to die...
			// I mean, philosophers to finish their dinner.
			for(int j = 0; j < iPhilosophers; j++)
				aoPhilosophers[j].join();

			System.out.println("All philosophers have left. System terminates normally.");
		}
		catch(InterruptedException e)
		{
			System.err.println("main():");
			reportException(e);
			System.exit(1);
		}
	} // main()

	/**
	 * Outputs exception information to STDERR
	 * @param poException Exception object to dump to STDERR
	 */
	public static void reportException(Exception poException)
	{
		System.err.println("Caught exception : " + poException.getClass().getName());
		System.err.println("Message          : " + poException.getMessage());
		System.err.println("Stack Trace      : ");
		poException.printStackTrace(System.err);
	}
}

// EOF
