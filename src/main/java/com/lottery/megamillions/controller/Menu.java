package com.lottery.megamillions.controller;

import java.util.Scanner;

public class Menu {

  public static final int EXIT_CODE = 0;

  private int userInput = 99;

  // Startup message to the program.
  public Menu() {
    System.out.println("----------Welcome to Win Millions!----------");
    System.out.println("");
  }

  // only exits on 0
  // Potentionally change the return type to return to the menu to so the Menu object can call other methods.
  public void run() {
    Scanner input = new Scanner(System.in);

    while (userInput != EXIT_CODE) {
      startingMenu();

      while(!input.hasNextInt()){
        System.out.println("Please enter a number 0 to 7.");
        input.next();
      }
      userInput = input.nextInt();
      if(userInput < 0 || userInput > 7){
        System.out.println("Please enter a number 0 to 7.");
        input.next();
      }
      // TODO: 10/3/2022 add a method for userInput to call a necessary function?


    }

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
