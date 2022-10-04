package com.lottery.megamillions.controller;

import com.lottery.database.LotteryTicket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

  public static final int EXIT_CODE = 0;
  public static final int MENU_MAX_VALUE = 7;
  public static final int CART_MIN_VALUE = 1;
  public static final int CART_MAX_VALUE = 2;
  public static final int MENU_CHOICE = 99;
  public static final int CART_CHOICE = 1;

  private Cart userCart = new Cart();
  private List<LotteryTicket> userTickets = new ArrayList<>();

  // Startup message to the program.
  public Menu() {
    System.out.println("----------Welcome to Win Millions!----------\n");
  }

  // Returns the userInput of a menu choice 0 to 7.
  public int runMenu() {
    Scanner input = new Scanner(System.in);
    String invalidMessage = String.format("Error, enter a number %d to %d.", EXIT_CODE,
        MENU_MAX_VALUE);
    int userInput = MENU_CHOICE;
    startingMenu();

    userInput = boundsCheck(MENU_CHOICE, invalidMessage, input);

    return userInput;
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
    System.out.println("Please enter a value 0 - 7:");
  }

  // there is no exit currently, compensate for empty selection or cart?
  // problem with exit 0 choice
  public int createCart(){
    System.out.println("Created cart.");
    System.out.println("Current cart: " + userCart);
    System.out.println("Selected drawings: " + userTickets);

    System.out.println("1. Add selected drawings to cart.");
    System.out.println("2. Return to Menu.");
    System.out.printf("Please enter a number %d to %d:%n",CART_MIN_VALUE,CART_MAX_VALUE);

    String invalidMessage = String.format("Error, enter a number %d to %d.", CART_MIN_VALUE, CART_MAX_VALUE);
    Scanner input = new Scanner(System.in);
    int userInput = MENU_CHOICE;

    userInput = boundsCheck(CART_CHOICE,invalidMessage, input);

    if(userInput == 1){
      for (LotteryTicket ticket: userTickets) {
        userCart.addTicket(ticket.getLotteryNumbers(),ticket.getMegaBallNumber());
      }
      System.out.println("Tickets added.");
      userInput = MENU_CHOICE;
    }
    return userInput;
  }

  public int predictNumbers(){
    System.out.println("Predicting Numbers.");
    // TODO: 10/4/2022 need implementation, this is place holder.

    return MENU_CHOICE;
  }

  public int repeatedNumbers(){
    System.out.println("Repeated Mega Ball Winning Numbers.");
    // TODO: 10/4/2022 need imp
    return MENU_CHOICE;
  }

  public int winningDay(){
    System.out.println("Winning numbers on a particular day.");
    // TODO: 10/4/2022 need imp

    return MENU_CHOICE;
  }

  public int topWinningNumbers(){
    System.out.println("Top winning numbers, from choice of 10 or 5.");
    // TODO: 10/4/2022 need imp
    return  MENU_CHOICE;
  }

  public int waysToWin(){
    System.out.println("Ways to win the Mega Ball.");
    // TODO: 10/4/2022 need imp
    return MENU_CHOICE;
  }

  public int displayHistory(){
    System.out.println("History of the Mega Millions.");
    // TODO: 10/4/2022 need imp
    return MENU_CHOICE;
  }

  // checks bound, id is the overall menu selection. invalidMessage is custom error. input is the scanner.
  private int boundsCheck(int id, String invalidMessage, Scanner input){
    int userInput = MENU_CHOICE;
    while(!isValidChoice(userInput, id)){
      while (!input.hasNextInt()){
        System.out.println("Non Number " + invalidMessage);
        input.next();
      }
      userInput = input.nextInt();
      if(!isValidChoice(userInput, id)){
        System.out.println("Bounds " + invalidMessage);
      }
    }
    return  userInput;
  }

  // checks for valid choice. choice is what entered. id is corrasponds to overall menu selection.(enum?)
  private boolean isValidChoice(int choice, int id){
    boolean result = false;
    switch (id){
      case MENU_CHOICE:
        result = choice >= EXIT_CODE && choice <= MENU_MAX_VALUE;
        break;
      case 1:
        result = choice == CART_MIN_VALUE || choice == CART_MAX_VALUE;
        break;
      case 2:
        result = false;
        break;
      case 3:
        result = false;
        break;
      case 4:
        result = false;
        break;
      case 5:
        result = false;
        break;
      case 6:
        result = false;
        break;
      case 7:
        result = false;
        break;
      default:
        result = false;
        break;
    }
    return  result;
  }
}
