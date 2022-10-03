package com.lottery.megamillions.controller;

import java.util.Scanner;

public class Menu {
  public static final int EXIT_CODE = 0;

  private int userInput = 99;

  public Menu(){
    System.out.println("Welcome to Win Millions!");
    System.out.println("");
  }

  public void run(){
    Scanner input = new Scanner(System.in);

    while(userInput != EXIT_CODE){
      startingMenu();

      if(input.hasNextInt()){
        userInput = input.nextInt();
      } else if (userInput < 0 || userInput > 7){
        System.out.println("Enter a number between 0 and 7");
      } else {
        System.out.println("Enter a number between 0 and 7");
      }
    }

  }

  public void startingMenu(){
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
