package WordGame;

import java.lang.Math;

public class Numbers {
	private int randomNum;
	
	public int getRandomNum() {
		return randomNum;
	}
	
	public void setRandomNum(int randomNumber) {
		randomNum = randomNumber;
	}
	
	public void generateNumber() {
		randomNum = (int) (Math.random() * 101);
	}

	public boolean compareNumber(int guess) {
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
