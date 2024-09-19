/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Legia
 */
package Utility;

import Controller.ProductController;
import Model.Product;

import java.util.Collections;
import java.util.Scanner;

public  class Tools {


    public static String inputString(String welcome){
        boolean checking = true ;
        String input = "";
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println(welcome);
            input = sc.nextLine();
            if (input.isEmpty()) {
                System.out.println("Please input your data ");
            } else{
                checking = false;


            }
        }while (checking);
    return input;
    }
    public static String updateString(String welcome, String old){
        String newString = old ;
        Scanner sc = new Scanner(System.in);
        System.out.println(welcome + " (Press Enter to keep the old one):");
        String tmp = sc.nextLine();
        if (!tmp.isEmpty()) {
            newString = tmp;
        }
        return newString;
    }



  

    public static int inputInt (String welcome , int min , int max ) {
        boolean checking = true;
        int input = 0;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println(welcome);
            try {
                input = Integer.parseInt(sc.nextLine());
                if (input < min || input > max) {
                    System.out.println("Please input number between " + min + " and " + max);
                } else {
                    checking = false;
                }
            } catch (Exception e) {
                System.out.println("Please input number");
            }
        } while (checking);
        return input;
    }

    public static int updateInt(String welcome, int min, int max, int old) {
        Scanner sc = new Scanner(System.in);
        System.out.println(welcome + " (Press Enter to keep the old one):");
        String input = sc.nextLine();
        if (input.isEmpty()) {
            return old;
        }
        try {
            int number = Integer.parseInt(input);
            if (number >= min && number <= max) {
                return number;
            } else {
                System.out.println("Please input a number between " + min + " and " + max);}
        } catch (Exception e) {
            System.out.println("Please input a valid number.");}
        return old;
    }

    public static double inputDouble (String welcome ) {
        boolean checking = true;
        double input = 0;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println(welcome);
            try {
                input = Double.parseDouble(sc.nextLine());
                checking = false;
            } catch (Exception e) {
                System.out.println("Please input number");
            }
        } while (checking);
        return input;
    }

    public static double updateDouble(String welcome, double old) {
        String input = String.valueOf(old);
        Scanner sc = new Scanner(System.in);
        System.out.println(welcome + " (or press Enter to keep the old value: " + old + "):");
        String tmp = sc.nextLine();
        if (!tmp.isEmpty()) {
            try {
                input = tmp;
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please input a valid number.");
            }
        }
        return old;
    }

    public static boolean confirmYesNo(String message) {
        while (true) {
            String input = inputString(message).trim().toUpperCase();
            if ("Y".equals(input)) {
                return true;
            } else if ("N".equals(input)) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
            }
        }
    }



}