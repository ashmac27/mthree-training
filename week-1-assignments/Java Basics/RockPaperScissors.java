import java.util.*;

public class RockPaperScissors {

    public static void main(String[] args){

        //Declaring and/or initializing variables
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        int numGames = 0;
        int rock = 1, paper = 2, scissors = 3;
        int computerChoice, userChoice;
        String result = "";
        int userWins = 0, ties = 0, computerWins = 0;
        char decision = 'y';

        //loop runs until user no longer wants to play
        while(decision == 'y') {

            System.out.println("How many times would you like to play Rock, Paper, Scissors?");

            //Try-catch incase user doesn't enter a whole number
            try {
                numGames = scan.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Not a whole number, try again");
                System.exit(0);
            }


            if (numGames >= 1 && numGames <= 10) {
                System.out.println("We're playing " + numGames + " games.");
            } else {
                System.out.println("You can only play up 1 to 10 games");
                System.exit(0);
            }

            // Loop runs until no more games left
            while (numGames > 0) {
                System.out.println("1 = Rock, 2 = Paper, 3 = Scissors");
                System.out.println("Select your choice: ");

                userChoice = scan.nextInt();
                while(userChoice > scissors || userChoice < rock){
                    System.out.println("You can only choose 1, 2, or 3.\nEnter input again:");
                    userChoice = scan.nextInt();
                }

                computerChoice = rand.nextInt(3) + 1;

                //Calling result method to find the results of the game
                result = result(userChoice, computerChoice);
                if(result == "user" ){
                    userWins++;
                }else if(result == "computer"){
                    computerWins++;
                }else if(result == "tie"){
                    ties++;
                }

                --numGames;
            }

            // Printing results and asking player if they want to play again
            System.out.println("\nTies = " + ties + "\nUser Wins = " + userWins + "\nComputer Wins = " + computerWins);

            if(userWins > computerWins){
                System.out.println("You're winner! Congrats!");
            } else if(computerWins > userWins){
                System.out.println("The computer is the winner! Sorry!");
            } else{
                System.out.println("It's a tie!");
            }

            System.out.println("Do you want to play again? Enter y for yes and n for no.: ");
            decision = scan.next().charAt(0);

            if(decision == 'n') {
                System.out.println("Thank you for playing!");
                System.exit(0);
            }
        }
    }

    public static String result(int userChoice, int compChoice){
        int result = userChoice - compChoice;

        /* If result is -2 or 1, the user wins
           If the result is -1 or 2, comp wins*/
        switch (result){
            case -2:
            case 1:
                System.out.println("User wins this round!\n");
                return "user";
            case -1:
            case 2:
                System.out.println("Computer wins this round!\n");
                return "computer";
            default:
                System.out.println("It's a tie!\n");
                return "tie";
        }
    }
}