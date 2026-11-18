import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        int round = 0;
        int totalWins = 0;
        int totalAttempts = 0;

        System.out.println("==================================");
        System.out.println("       NUMBER GUESSING GAME");
        System.out.println("==================================");

        boolean playAgain = true;

        while (playAgain) {
            round++;

            Difficulty difficulty = chooseDifficulty();
            int secretNumber = random.nextInt(difficulty.maxNumber - difficulty.minNumber + 1)
                    + difficulty.minNumber;

            System.out.println("\nRound " + round + " started!");
            System.out.println("Difficulty: " + difficulty.name);
            System.out.println("Guess a number between " + difficulty.minNumber
                    + " and " + difficulty.maxNumber + ".");
            System.out.println("You have " + difficulty.maxAttempts + " attempts.");

            int attemptsUsed = 0;
            boolean guessedCorrectly = false;

            while (attemptsUsed < difficulty.maxAttempts && !guessedCorrectly) {
                int remaining = difficulty.maxAttempts - attemptsUsed;
                System.out.print("\nEnter your guess (" + remaining + " attempt(s) left): ");

                Integer guess = readInteger();
                if (guess == null) {
                    System.out.println("Please enter a valid whole number.");
                    continue;
                }

                if (guess < difficulty.minNumber || guess > difficulty.maxNumber) {
                    System.out.println("Your guess must be between "
                            + difficulty.minNumber + " and " + difficulty.maxNumber + ".");
                    continue;
                }

                attemptsUsed++;
                totalAttempts++;

                if (guess > secretNumber) {
                    System.out.println("Too High!");
                } else if (guess < secretNumber) {
                    System.out.println("Too Low!");
                } else {
                    System.out.println("Correct! You guessed the number.");
                    guessedCorrectly = true;
                    totalWins++;
                }

                System.out.println("Attempts used: " + attemptsUsed
                        + " / " + difficulty.maxAttempts);
            }

            if (guessedCorrectly) {
                System.out.println("\nRound " + round + " — guessed in "
                        + attemptsUsed + " attempt(s).");
            } else {
                System.out.println("\nYou Lost! The correct number was " + secretNumber + ".");
                System.out.println("Round " + round + " — not guessed within "
                        + difficulty.maxAttempts + " attempts.");
            }

            System.out.println("\n========== SCORE SUMMARY ==========");
            System.out.println("Rounds played: " + round);
            System.out.println("Rounds won: " + totalWins);
            System.out.println("Total valid guesses: " + totalAttempts);
            System.out.println("===================================");

            playAgain = askToPlayAgain();
        }

        System.out.println("\nThanks for playing!");
        System.out.println("Final result: " + totalWins + " win(s) in " + round + " round(s).");
        scanner.close();
    }

    private static Difficulty chooseDifficulty() {
        while (true) {
            System.out.println("\nChoose difficulty:");
            System.out.println("1. Easy   (1-50, 10 attempts)");
            System.out.println("2. Medium (1-100, 7 attempts)");
            System.out.println("3. Hard   (1-200, 5 attempts)");
            System.out.print("Enter choice (1-3): ");

            Integer choice = readInteger();
            if (choice == null) {
                System.out.println("Please enter 1, 2, or 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    return new Difficulty("Easy", 1, 50, 10);
                case 2:
                    return new Difficulty("Medium", 1, 100, 7);
                case 3:
                    return new Difficulty("Hard", 1, 200, 5);
                default:
                    System.out.println("Invalid choice. Select 1, 2, or 3.");
            }
        }
    }

    private static Integer readInteger() {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private static boolean askToPlayAgain() {
        while (true) {
            System.out.print("\nPlay again? (yes/no): ");
            String answer = scanner.nextLine().trim();

            if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                return true;
            } else if (answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.println("Please enter yes or no.");
            }
        }
    }

    private static class Difficulty {
        String name;
        int minNumber;
        int maxNumber;
        int maxAttempts;

        Difficulty(String name, int minNumber, int maxNumber, int maxAttempts) {
            this.name = name;
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.maxAttempts = maxAttempts;
        }
    }
}
