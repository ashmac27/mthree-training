public class SummativeSums {
    public static void main(String[] args){

        int [] arr1 = { 1, 90, -33, -55, 67, -16, 28, -55, 15 };
        int [] arr2 = { 999, -60, -77, 14, 160, 301 };
        int [] arr3 = { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200, -99 };

        // Calling method and printing results
        System.out.print("#1 Array Sum: ");
        System.out.println(addInts(arr1));
        System.out.print("#2 Array Sum: ");
        System.out.println(addInts(arr2));
        System.out.print("#3 Array Sum: ");
        System.out.println(addInts(arr3));
    }

    // Method to add the ints within the array.
    static int addInts(int[] arr){

        int total = 0;
        for(int i = 0; i < arr.length; i++){
            total += arr[i];
        }
        return total;
    }

}
