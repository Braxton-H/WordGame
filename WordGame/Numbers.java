package WordGame;

import java.lang.Math;

public class Numbers {
	//set up the randomNum int
	private static int randomNum;
	
	//Getter for the randomNum
	public static int getRandomNum() {
		return randomNum;
	}
	
	//Setter for the randomNum
	public static void setRandomNum(int randomNumber) {
		randomNum = randomNumber;
	}
	
	//Generator for randomNum
	public static void generateNumber() {
		randomNum = (int) (Math.random() * 101);
	}

	public static boolean compareNumber(int guess) {
		if(guess == randomNum) {
			System.out.println("Congratulations, you guessed the right number!");
			return true;
		} else if(guess > randomNum) {
			System.out.println("I'm sorry. That guess was too high.");
			return false;
		} else if(guess < randomNum) {
			System.out.println("I'm sorry. That guess was too low.");
			return false;
		}
		return false;
	}
}
