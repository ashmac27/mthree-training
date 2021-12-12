import java.util.Scanner;

public class HealthyHearts {
    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);
        int age;
        double maxHeartRate;
        double minTarget, maxTarget;

        // Getting input
        System.out.print("What is your age? ");
        age = scan.nextInt();

        // Calculating heart rates
        maxHeartRate = 220 - age;
        minTarget = .5 *maxHeartRate;
        maxTarget = Math.ceil(.85 * maxHeartRate);

        // Printing heart rates
        System.out.println("Your maximum heart rate should be " + (int)maxHeartRate + " beats per minute");
        System.out.println("Your target HR Zone is " + (int)minTarget + " - " + (int)maxTarget + " beats per minute");
    }
}
