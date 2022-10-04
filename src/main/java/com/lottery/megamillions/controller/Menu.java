package com.lottery.megamillions.controller;

import com.lottery.database.LotteryTicket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

  public static final int EXIT_CODE = 0;
  public static final int MAX_MENU_VALUE = 7;
  public static final int CART_MIN_VALUE = 1;
  public static final int CART_MAX_VALUE = 2;
  public static final int MENU_CHOICE = 99;

  private Cart userCart = new Cart();
  private List<LotteryTicket> userTickets = new ArrayList<>();

  // Startup message to the program.
  public Menu() {
    System.out.println("----------Welcome to Win Millions!----------\n");
  }

  // Returns the userInput of a menu choice 0 to 7.
  public int runMenu() {
    Scanner input = new Scanner(System.in);
    int userInput = MENU_CHOICE;
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

  // there is no exit currently, compensate for empty selection or cart?
  // problem with exit 0 choice
  public int createCart(){
    System.out.println("Created cart.");
    System.out.println("Current cart: " + userCart);
    System.out.println("Selected drawings: " + userTickets);

    System.out.println("1. Add selected drawings to cart.");
    System.out.println("2. Return to Menu.");

    String invalidMessage = String.format("Please enter a number %d to %d.", CART_MIN_VALUE, CART_MAX_VALUE);
    Scanner input = new Scanner(System.in);
    int userInput = MENU_CHOICE;
    while(!isValidCartChoice(userInput)){
      //cartMenu();
      while (!input.hasNextInt()){
        System.out.println(invalidMessage);
        input.next();
      }
      userInput = input.nextInt();
      if(!isValidCartChoice(userInput)){
        System.out.println(invalidMessage);
        input.next();
      }
    }

    if(userInput == 1){
      for (LotteryTicket ticket: userTickets) {
        userCart.addTicket(ticket.getLotteryNumbers(),ticket.getMegaBallNumber());
      }
      System.out.println("Tickets added.");
      userInput = MENU_CHOICE;
    }
    return userInput;
  }

  public boolean isValidCartChoice(int choice){
    return choice == CART_MIN_VALUE || choice == CART_MAX_VALUE;
  }
}
