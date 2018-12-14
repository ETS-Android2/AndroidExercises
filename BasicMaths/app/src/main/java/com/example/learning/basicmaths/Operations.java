package com.example.learning.basicmaths;

//import java.time.

import java.lang.Math;
import java.util.Random;

public class Operations {

    int numbers[];

    public int random_number(int ad) {
        Random rand = new Random();
        return Math.abs(rand.nextInt() % ad);
    }

    /**
     * m = option
     * r = result
     * n = numbers
     * a = amount
     */
    public int getting_last_result(int m, int r, int n, int a) {
        switch (m) {
            case 1:
                r = r + n;
                break;
            case 2:
                r -= n;
                break;
            case 3:
                r *= n;
                break;
            case 4:
                while (n == 0 || n > numbers[numbers.length-1]) n = numbers[numbers.length-1] = random_number(a);
                r /= n;
                break;
            default:
                System.out.println("error");
        }
        return r;
    }

    /**
     * p = pos
     * m = option
     * r = result
     * n = number
     * a = amount
     */
    public int getting_result(int p, int m, int r, int n, int a) {
        switch (m) {
            case 1: // +
                r += n;
                break;
            case 2: // -
                if (p == 0) r = n - r;
                else r -= n;
                break;
            case 3: // *
                r *= n;
                break;
            case 4: // /
                if (p == 0) {
                    if (n == 0) { //this is to not to start with number zero
                        while (n == 0) n = numbers[p] = random_number(a);
                    }
                    r = n / r; //first number
                }
                else {
                    while (n == 0 || n > numbers[p-1]) n = numbers[p] = random_number(a);//Check 0
                    //this can be changed
                    r /= n;
                }
                break;
            default:
                //ALERT, DO I HAVE TO SHOW A TOAST?
        }
        return r;
    }

    public int[] get_numbers() {
        return this.numbers;
    }

    /**
     * an = amount of numbers to get the result
     * ad = amount of digits for each number
     * m = option (add,sub,mul,div)
     **/
    public int set_number(int an, int ad, int m) {
        this.numbers = new int[an];
        ad = (int) Math.pow(10, ad);
        int result;
        if (m != 3 && m != 4) result = 0; //Add and subtract
        else result = 1; //Multiply and division
        for (int i = 0; i < an; i++) {
            numbers[i] = random_number(ad); //get a number
            //if (i < (an - 1))
                result = getting_result(i, m, result, numbers[i], ad); //get the result depending on operation
            //else
                //result = getting_last_result(m, result, numbers[i], ad);
        }
        return result;
    }

}
