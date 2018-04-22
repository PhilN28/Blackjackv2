import java.util.ArrayList;

public class Player {
	

	private String name; 
	private int age;
	private int money;
	private int points;
	private int wins;
	private int bet;
	private boolean bust;
	private ArrayList<Card> hand;
	
	/**
	 * @param name
	 * @param age
	 */
	public Player(String name, int age) {
		this.name = name;
		this.age = age;
		money = 100; 
		points = 0;
		wins = 0;
		bet = 0;
		bust = false;
		hand = new ArrayList<Card>();
	}
	
	
	
	public void addCard(Card card)
	{
		hand.add(card);
		points = points + card.getFaceValue();
	}
	
	public void evaluateHand() {
		
		int sum = 0;
		for (Card card : hand) 
		{
			sum = sum + card.getFaceValue();
		}
		
		if(sum < 21)
		{
			setBust(false);
		}
		
		if (sum == 21)
		{
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println("$$$$$$$$$$$$$ BLACKJACK $$$$$$$$$$$$$$$");
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println(name + ", you got blackjack!");
            System.out.println();
		}
		
		if (sum > 21) 
		{
			
            System.out.println("***************************************");
            System.out.println("***************************************");
            System.out.println("************* BUSTED ******************");
            System.out.println("***************************************");
            System.out.println("***************************************");
            System.out.println(name + ", you have busted. Please wait until the next round.");
            System.out.println();
            setBust(true);
		}
		
	}
	
	public void printHand()
	{
		for(Card card : hand)
		{
			System.out.println(card.getFace() + " of " + card.getSuit());
		}
	}
	
	public void printHandAndScore(Player aPlayer) {
		System.out.println(aPlayer.getName() + " has:");
		aPlayer.printHand();
		System.out.println("Score: " + aPlayer.getPoints());
		System.out.println();
	}
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the money
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @param money the money to set
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * @return the hand
	 */
	public ArrayList<Card> getHand() {
		return hand;
	}

	/**
	 * @param hand the hand to set
	 */
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	//this method checks the age of the player 
	public boolean checkAge (int playerAge) {
		if (playerAge >= 21) {
			return true; 
		}
		return false; 
	}

	/**
	 * @return the bust
	 */
	public boolean isBust() {
		return bust;
	}

	/**
	 * @param bust the bust to set
	 */
	public void setBust(boolean bust) {
		this.bust = bust;
	}
	
	public void clearHand()
	{
		hand.clear();
	}
	
	public void natural() {
		if (points == 21)
		{
			System.out.println("You have 21 - you have won this round!");
			return;
		}
	}
	
	public int getWins () {
		return wins;
	}
	
	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getBet () {
		return bet;
	}
	
	public void setBet(int bet) {
		this.bet = bet;
	}
}
