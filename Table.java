/**
 * @author Phillip Nguyen (10am), Aditya Mehta (10am), Phillip Phan (10am), Christopher Maltusch (9am)
 *
 * 
 */

import java.util.*;
import java.lang.Thread;

public class Table {
	
	private Scanner scan = new Scanner(System.in);
	private Random rand = new Random();
	private String namePlayer1; 
	private String namePlayer2; 
	private Player player1; 
	private Player player2;
	private Player dealer;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Integer> highScores = new ArrayList<Integer>();
	private boolean pass;
	private Deck aDeck = new Deck();
	
	public Table() {
		
		opening();
		createDealer();
		addPlayers();
		game();
		
	}

	//Welcoming statement
	public void opening() {
		System.out.println("Welcome to the Big Baller Casino!");
		System.out.println("You must be 21 and over to play.");
		System.out.println();
	}
	
	//Creates player objects
	public void addPlayers() {
		
		while (!pass)
		{
			System.out.println("Player 1 please enter your name >");
			namePlayer1 = scan.next();
			System.out.println("Player 1 please enter your age >");
			int agePlayer1 = scan.nextInt();
			Player player1 = new Player(namePlayer1, agePlayer1);
			players.add(player1);
			
			System.out.println("Player 2 please enter your name >");
			namePlayer2 = scan.next();
			System.out.println("Player 2 please enter your age >");
			int agePlayer2 = scan.nextInt();
			Player player2 = new Player(namePlayer2, agePlayer2);
			players.add(player2);
			
				if (!ageCheck())
				{
					players.clear();
				}
		}

	}
	
	//Creates the dealer, which is the CPU player
	public void createDealer() {
		String cpuName = "Dealer";
		dealer = new Player(cpuName, 99);
		dealer.setMoney(1000);
		players.add(dealer);
	}
	
	//Checks age of the player, must be 21+
	public boolean ageCheck() {
		for (Player aPlayer : players)
		{
			if (!aPlayer.checkAge(aPlayer.getAge()))
			{
				System.out.println(aPlayer.getName() + " is not of age - please return when you and your guests are 21.");
				System.out.println("");
				pass = false;
				return pass;
			}
		}
		pass = true; 
		return pass;
	}

	//Creates a card
	public Card createCard() {
		Card card = aDeck.gimmeACard();	
		return card;
	}
	
	//Add card to player hand, checks if ace
	public void deal(Player thisPlayer)
	{
		Card thisCard = createCard();
		//checkAce(thisCard);
		
		if (thisCard.getFace().equals("Ace"))
		{
			if (!thisPlayer.getName().equals("Dealer"))
			{
				System.out.println(thisPlayer.getName() + ", you have been dealt an ace!");
				System.out.println("Do you want the ace to equal 1 or 11? (1/11)");
				int aceRsp = scan.nextInt();
				
				if (aceRsp == 1)
				{
					thisCard.setFaceValue(1);
				}
				
				if (aceRsp == 11)
				{
					thisCard.setFaceValue(11);
				}
			}
			if(thisPlayer.getName().equals("Dealer"))
			{
				thisCard.setFaceValue(1);
			}
		}
		
		thisPlayer.addCard(thisCard);
		thisCard.printInfo();
		System.out.println(" (" + thisCard.getFaceValue() + ")" + " has been added to " +  thisPlayer.getName()+ "'s hand.");
		System.out.println();
	}
	
	
	//this method will take player bets and add the sum to get the potential winning 
//	public void bet(Player thisPlayer)
//	{
//		int winnings = 0;
//		for (Player aPlayer : players)
//		{
//			if (!(aPlayer.getName().equals("Dealer")))
//			{
//				System.out.println(aPlayer.getName() + " how much would you like to bet?");
//				int bet = scan.nextInt();
//				aPlayer.setMoney(aPlayer.getMoney() - bet);
//				aPlayer.setBet(bet);
//				winnings = winnings + bet;
//				System.out.println();
//			}
//		}
//	}
	
	//this method will set the bets for the players 
	public void setBet()
	{
		int winnings = 0;
		for (Player aPlayer : players)
		{
			if (!(aPlayer.getName().equals("Dealer")))
			{
				System.out.println(aPlayer.getName() + " how much would you like to bet?");
				int bet = scan.nextInt();
				aPlayer.setMoney(aPlayer.getMoney() - bet);
				aPlayer.setBet(bet);
				winnings = winnings + bet;
			}
		}
	}
	
	
	//calculates the sum of the bets
	public int winnings() {
		int sum = 0;
		for (Player aPlayer : players)
		{
			sum = sum + aPlayer.getBet();
		}
		return sum;
	}
	
	public int draw(Player thisPlayer) {
		int bet = 0;
		for (Player aPlayer : players)
		{
			if (thisPlayer.getName().equals(aPlayer.getName()))
			{
				bet = aPlayer.getBet();
			}
		}
		
		return bet; 
	}
	
	//User hits or stays 
	public void hitOrStay() {
		String rsp = null;
		boolean finish = false;
		
		for (Player aPlayer : players)
		{
			if (!aPlayer.getName().equals("Dealer"))
			{
				System.out.println(aPlayer.getName() + ", your score is " + aPlayer.getPoints() + ". Would you like to hit or stay? (h/s)");
				rsp = scan.next();
					while (rsp.equals("h") && !finish)
					{											
						if (aPlayer.getPoints() < 21)
						{
							deal(aPlayer);
							aPlayer.printHandAndScore(aPlayer);
							aPlayer.evaluateHand();
							aPlayer.setBust(false);
							
							if (aPlayer.getPoints() > 21)
							{
								aPlayer.setBust(true);
								break;
							}
							
							System.out.println("Hit or stay? (h/s)");
							rsp = scan.next();
							System.out.println();
						}						
					}
					
					if (rsp.equals("s"))
					{
						continue;
					}
			}
		}
	}
	
	//Dealer plays until he busts/wins
	public void dealer() {
		if (checkBust())
		{
			return;
		}
		Player thisPlayer = null;
		//finding the score the dealer has to beat
		int maxScore = 0;
		boolean playerTie = false;
		
		for (Player aPlayer : players)
		{
			if (aPlayer.getPoints() > maxScore && !aPlayer.isBust())
			{
				thisPlayer = aPlayer;
				maxScore = aPlayer.getPoints();
				
				if (players.get(1).getPoints() == maxScore && players.get(2).getPoints() == maxScore)
				{
					playerTie = true; 
				}
				
			}
		}
		
		System.out.println();
		if (playerTie)
		{
			System.out.println("The dealer must beat " + players.get(1).getName() + " and " + players.get(2).getName() + "'s score " + "(" + maxScore + ")" + " without busting in order to win!");
		}
		if (!playerTie)
		{
			System.out.println("The dealer must beat " + thisPlayer.getName() + "'s score " + "(" + thisPlayer.getPoints() + ")" + " without busting in order to win!");
		}
		System.out.println();
		
		while(dealer.getPoints() <= 21 || dealer.getPoints() <= maxScore)
		{
				System.out.println("Card is being dealt. Please wait.");
				
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					deal(dealer);
					
					System.out.println("Dealer's score: " + dealer.getPoints());
					System.out.println();
					if (dealer.getPoints() > 21)
					{
						//dealer loses 
						if (playerTie)
							{
								System.out.println("The dealer has busted! " + players.get(1).getName() + " and " + players.get(2).getName() + " have beat the dealer!");
								players.get(1).setMoney(players.get(1).getMoney() + ((draw(players.get(1))*2)));
								players.get(1).setWins(players.get(1).getWins()+1);
								players.get(2).setMoney(players.get(2).getMoney() + ((draw(players.get(2))*2)));
								players.get(2).setWins(players.get(2).getWins()+1);
								dealer.setMoney(dealer.getMoney() - winnings());
								break;

							}

						else if (dealer.getPoints() > maxScore)
							{
								System.out.println("The dealer has busted - " + thisPlayer.getName() + " has beat the dealer!");
								thisPlayer.setWins(thisPlayer.getWins()+1);
								thisPlayer.setMoney(thisPlayer.getMoney() + (draw(thisPlayer)*2));
								dealer.setMoney(dealer.getMoney() - draw(thisPlayer));
								break;
							}
					}
					
					//dealer wins/ties
					if (dealer.getPoints() <= 21)
					{
						if (dealer.getPoints() == maxScore)
						{
							if (!playerTie)
							{
								System.out.println("The dealer has tied with " + thisPlayer.getName() + "!");
								thisPlayer.setMoney(thisPlayer.getMoney() + draw(thisPlayer));
								break;
							}
							if (playerTie)
							{
								System.out.println("The dealer has tied with " + players.get(1).getName() + " and " + players.get(2).getName() + " !");
								players.get(1).setMoney(players.get(1).getMoney() + draw(players.get(1)));
								players.get(2).setMoney(players.get(2).getMoney() + draw(players.get(2)));
								break;
							}
						}
						
						if (dealer.getPoints() > maxScore)
							{
								if (!playerTie)
								{
									System.out.println("The dealer has beat " + thisPlayer.getName() + "!");
									thisPlayer.setMoney(thisPlayer.getMoney() - draw(thisPlayer));
									dealer.setWins(dealer.getWins()+1);
									dealer.setMoney(dealer.getMoney() + winnings());
									thisPlayer.setMoney(thisPlayer.getMoney() - draw(thisPlayer));
									break;
								}
								else if (playerTie)
								{
									System.out.println("The dealer has beat " + players.get(1).getName() + " and " + players.get(2).getName() + " !");
									players.get(1).setMoney(players.get(1).getMoney() - ((draw(players.get(1)))));
									players.get(2).setMoney(players.get(2).getMoney() - ((draw(players.get(2)))));
									dealer.setWins(dealer.getWins()+1);
									dealer.setMoney(dealer.getMoney() + winnings());
									break;
								}
							}
					}
			System.out.println();
		}
	}
		
	//Contains methods that make blackjack work
	public void game()
	{
		String gameRsp = "n";
		boolean gameFinish = false;
		
		
		while (!gameFinish && gameRsp.equals("n"))
		{
			setBet();
			
			System.out.println("Card is being dealt. Please wait.");
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			deal(dealer); 
			
			for (Player aPlayer : players)
			{
				if (!(aPlayer.getName().equals("Dealer")))
				{
					System.out.println("Card is being dealt. Please wait.");
					
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					deal(aPlayer);
					
					System.out.println("Card is being dealt. Please wait.");
					
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					deal(aPlayer);
				}
				
				System.out.println();
				aPlayer.printHandAndScore(aPlayer);
				aPlayer.evaluateHand();
			}
			
			hitOrStay();
			
			dealer();
			
			System.out.println("Are you done playing? (y/n)");
			gameRsp = scan.next();
			
				if (gameRsp.equals("y"))
				{
					gameFinish = true;
					System.out.println("We hope you enjoyed your vi$it!");
					results();
				}
				
				if (gameRsp.equals("n"))
				{
					System.out.println("Get ready for the next round.");
					reset();
					System.out.println();
				}
		}
	}
	
	//checks if players have busted 
	public boolean checkBust()
	{
		for (int i = 0; i < players.size(); i++)
		{
			if(players.get(1).getPoints() > 21 && players.get(2).getPoints() > 21)
			{
				System.out.println("Both " + players.get(1).getName() + " and " + players.get(2).getName() + " have busted! The dealer wins!");
				System.out.println();
				return true; 
			}
		}
		return false;
	}
	
	//If the user prompts to play on to the next round, this method clears their hands and sets bets to 0
	public void reset() {
		for (Player aPlayer : players)
		{
			aPlayer.setPoints(0);
			aPlayer.clearHand();
			
			if (!aPlayer.getName().equals("Dealer"))
			{
				aPlayer.setBet(0);
			}
		}
	}
	
	
	//Prints the results after the player ends the game 
	public void results() {
		
		System.out.println("*-*-*-*-*-*-* Results *-*-*-*-*-*-*-*-*");
		System.out.println("Players     Rounds Won      Money");
		
		for (Player aPlayer : players)
		{
			System.out.println(aPlayer.getName() + "            " + aPlayer.getWins() + "           " + aPlayer.getMoney());
		}
		
		System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Table blackjack = new Table();
	}

}
