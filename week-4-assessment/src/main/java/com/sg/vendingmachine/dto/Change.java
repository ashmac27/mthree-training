package com.sg.vendingmachine.dto;

import java.util.HashMap;
import java.util.Map;

// Enum for different types of US currency
enum Coin{
    QUARTERS(25), DIMES(10), NICKELS(5);
    private int value;

    Coin(int value){
        this.value = value;
    }

    public int getVal(){
        return value;
    }
}

public class Change {
    private Map<String, Integer> change;
    private int totalChange;
    private int numQuarters = 0;
    private int numDimes = 0;
    private int numNickels = 0;
    private int numPennies = 0;

    public Change(int totalChange) {
        this.totalChange = totalChange;
        change = getChange(totalChange);
    }

    public Map<String, Integer> getChange() {
        return change;
    }

    public int getTotalChange() {
        return totalChange;
    }

    public int getNumQuarters() {
        return numQuarters;
    }

    public int getNumDimes() {
        return numDimes;
    }

    public int getNumNickels() {
        return numNickels;
    }

    public int getNumPennies() {
        return numPennies;
    }

    public Map<String, Integer> getChange(int changeDue){

        Coin coin = null;

        // Calculating how much change should be given to user
        int total = changeDue;
        Map<String, Integer> changeToReturn = new HashMap<>();
        while(total >= coin.QUARTERS.getVal()){
            this.numQuarters+=1;
            total -=25;
        }
        while(total >= coin.DIMES.getVal()){
            this.numDimes+=1;
            total -=10;
        }
        while(total >= coin.NICKELS.getVal()){
            this.numNickels+=1;
            total -=5;
        }
        this.numPennies = total;

        changeToReturn.put("Quarters", numQuarters);
        changeToReturn.put("Dimes", numDimes);
        changeToReturn.put("Nickels", numNickels);
        changeToReturn.put("Pennies", numPennies);
        changeToReturn.put("Total", changeDue);
        return changeToReturn;
    }


}
