package com.lottery.megamillions.controller;

import java.util.Scanner;

public class Menu {

  public static final int EXIT_CODE = 0;
  public static final int MAX_MENU_VALUE = 7;

  public Cart userCart;

  // Startup message to the program.
  public Menu() {
    System.out.println("----------Welcome to Win Millions!----------");
    System.out.println("");
  }

  // Returns the userInput of a menu choice 0 to 7.
  public int runMenu() {
    Scanner input = new Scanner(System.in);
    int userInput = 99;
    while (!isValidMenuChoice(userInput)) {
      startingMenu();

      while(!input.hasNextInt()){
        System.out.println("Please enter a number 0 to 7.");
        input.next();
      }
      userInput = input.nextInt();
      if(!isValidMenuChoice(userInput)){
        System.out.println("Please enter a number 0 to 7.");
        input.next();
      }
      // TODO: 10/3/2022 add a method for userInput to call a necessary function?


    }
    return userInput;
  }

  public boolean isValidMenuChoice(int choice){
    return choice >= EXIT_CODE && choice <= MAX_MENU_VALUE;
  }

  public void startingMenu() {
    System.out.println("1. Create lottery numbers cart.");
    System.out.println("2. Predict numbers for me.");
    System.out.println("3. Display Repeated Mega Ball Winning Numbers.");
    System.out.println("4. Display Winning Numbers on a Particular Day.");
    System.out.println("5. Display Top 10 Winning Numbers and Top 5 Mega Ball Numbers");
    System.out.println("6. Display Ways to Win in Mega Millions");
    System.out.println("7. Display Mega Millions History");
    System.out.println("0. Exit.");
  }


}
