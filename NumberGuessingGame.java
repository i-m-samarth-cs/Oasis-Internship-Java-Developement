import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain = true;

        System.out.println("🎉 Welcome to the Number Guessing Game! 🎉");

        while (playAgain) {
            int range = 100;
            int randomNumber = random.nextInt(range) + 1;
            int attempts = 0;
            int maxAttempts = 7; // You can change this to adjust the difficulty
            int score = 100; // Initial score
            boolean hasGuessed = false;

            System.out.println("\n🔢 I have chosen a number between 1 and " + range + ".");
            System.out.println("You have " + maxAttempts + " attempts to guess the number. Let's begin!");

            while (attempts < maxAttempts) {
                System.out.print("\nEnter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == randomNumber) {
                    System.out.println("🎉 Correct! You've guessed the number in " + attempts + " attempts.");
                    score += (maxAttempts - attempts) * 10; // Bonus points for fewer attempts
                    hasGuessed = true;
                    break;
                } else if (userGuess < randomNumber) {
                    System.out.println("📉 Too low! Try again.");
                } else {
                    System.out.println("📈 Too high! Try again.");
                }

                System.out.println("Attempts left: " + (maxAttempts - attempts));
                score -= 10; // Deduct points for each incorrect guess
            }

            if (!hasGuessed) {
                System.out.println("\n😞 You've used all your attempts! The correct number was: " + randomNumber);
            }

            System.out.println("🎯 Your score for this round: " + score);

            System.out.print("\nDo you want to play another round? (yes/no): ");
            String response = scanner.next();
            playAgain = response.equalsIgnoreCase("yes");
        }

        System.out.println("\n✨ Thank you for playing the Number Guessing Game! Goodbye! ✨");
        scanner.close();
    }
}
