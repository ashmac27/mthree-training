import java.util.*;

public class DogGenetics {
    public static void main(String [] args){

        Random rand = new Random();
        Scanner scan = new Scanner(System.in);
        String dogName = "";
        int[] dogGenes = new int[5];
        int total = 100;

        // Getting Dog input from user
        System.out.print("What's your dog's name?: ");
        dogName = scan.nextLine();
        System.out.println("Well then, I have this highly reliable report on " + dogName +"'s prestigious background right here.");

        System.out.println(dogName +" is:\n");

        // Looping through dogGenes until next to last value
        for(int i = 0; i < dogGenes.length-1; i++){
            dogGenes[i] = rand.nextInt(total);
            total -= dogGenes[i];
        }

        // Remaining total assigned to last value in array
        dogGenes[4] = total;

        System.out.println(dogGenes[0]+"% St. Bernard");
        System.out.println(dogGenes[1]+"% Chihuahua");
        System.out.println(dogGenes[2]+"% Dramatic RedNosed Asian Pug");
        System.out.println(dogGenes[3]+"% Common Cur");
        System.out.println(dogGenes[4]+"% King Doberman");
        System.out.println("\nWow, that's QUITE the dog!");
    }
}
