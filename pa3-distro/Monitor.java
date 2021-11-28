/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	int numPhilo;
	boolean chopsticks[];


	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		numPhilo = piNumberOfPhilosophers;
		chopsticks = new boolean[numPhilo];

	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		System.out.printf("Philosopher %d is hungry\n", numPhilo);
		while (chopsticks[piTID-1]==true && chopsticks[piTID%numPhilo]==true){
			try {
				System.out.printf("Philosopher %d is waiting\n", piTID);
				this.wait();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		chopsticks[piTID-1]=false;
		chopsticks[piTID%numPhilo]=false;
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		chopsticks[piTID-1]=true;
		chopsticks[piTID%numPhilo]=true;
		this.notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		// ...
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		// ...
	}
}

// EOF
