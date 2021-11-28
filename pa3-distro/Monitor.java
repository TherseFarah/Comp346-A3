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
	boolean myTurnToTalk;

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		numPhilo = piNumberOfPhilosophers;
		chopsticks = new boolean[piNumberOfPhilosophers];
		myTurnToTalk = true;

		for(int i = 0; i< chopsticks.length; i++){
			chopsticks[i] = true;
	}

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

	//chopsticks false -> picked up/in use
	public synchronized void pickUp(final int piTID)
	{
		System.out.printf("Philosopher %d is hungry\n", numPhilo);
		while (!(chopsticks[(piTID-1)%(numPhilo)] && chopsticks[(piTID)%(numPhilo)])){
			try {
				System.out.printf("Philosopher %d is waiting\n", piTID);
				this.wait();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		chopsticks[(piTID-1)%(numPhilo)]=false;
		chopsticks[piTID%numPhilo]=false;
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		chopsticks[(piTID-1)%(numPhilo)]=true;
		chopsticks[(piTID)%(numPhilo)]=true;
		this.notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		while(!myTurnToTalk){
			try{
				this.wait();
			}
			catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		myTurnToTalk = true;
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		myTurnToTalk = true;
		this.notifyAll();
	}
}

// EOF
