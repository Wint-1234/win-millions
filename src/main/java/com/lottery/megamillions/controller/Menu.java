package com.lottery.megamillions.controller;

import com.lottery.database.LotteryNumberPredictor;
import com.lottery.database.LotteryTicket;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

  // Top level menu selection that is in main.
  public static final int EXIT_CODE = 0;
  public static final int MENU_CHOICE = 99;
  public static final int CART_CHOICE = 1;
  public static final int PREDICT_CHOICE = 2;
  public static final int REPEATED_CHOICE = 3;
  public static final int DAY_CHOICE = 4;
  public static final int TOP_CHOICE = 5;
  public static final int WAYS_TO_WIN_CHOICE = 6;
  public static final int HISTORY_CHOICE = 7;

  // Top bounds values that is for invalidMessage local values, which is passed in boundsCheck()
  public static final int MENU_MAX_VALUE = 7;
  public static final int CART_MAX_VALUE = 2;
  public static final int PREDICT_MAX_VALUE = 4;
  public static final int EARLIEST_YEAR = 2017;
  public static final int LATEST_YEAR = 2022;
  public static final int EARLIEST_MONTH = 0;
  public static final int LATEST_MONTH = 0;

  // this is exclusive towards the predictNumbers() method to be passsed into the boundsCheck() method.
  public static final int YEAR_ID = 8;


  private Cart userCart = new Cart();
  private List<LotteryTicket> userTickets = new ArrayList<>();
  private final LotteryNumberPredictor database = new LotteryNumberPredictor();
  private final String standardInputMessage = "Please enter a number %d to %d:%n";
  private final String standardInputError = "Error, enter a number %d to %d.";

  // Startup message to the program.
  public Menu() throws FileNotFoundException {
    System.out.println("----------Welcome to Win Millions!----------\n");
  }

  // Returns the userInput of a menu choice 0 to 7.
  public int runMenu() {
    Scanner input = new Scanner(System.in);
    String invalidMessage = String.format(standardInputError, EXIT_CODE,
        MENU_MAX_VALUE);
    int userInput = MENU_CHOICE;
    startingMenu();

    userInput = boundsCheck(MENU_CHOICE, invalidMessage, input);

    return userInput;
  }

  /*--------------   Methods below are in order of menu selection untill next comment seperator         ----------- */

  // maybe remove and add contents to runMenu().
  public void startingMenu() {
    System.out.println("1. Create lottery numbers cart.");
    System.out.println("2. Predict numbers for me.");
    System.out.println("3. Display Repeated Mega Ball Winning Numbers.");
    System.out.println("4. Display Winning Numbers on a Particular Day.");
    System.out.println("5. Display Top 10 Winning Numbers and Top 5 Mega Ball Numbers");
    System.out.println("6. Display Ways to Win in Mega Millions");
    System.out.println("7. Display Mega Millions History");
    System.out.println("0. Exit.");
    System.out.printf(standardInputMessage, EXIT_CODE, MENU_MAX_VALUE);
  }

  // change to just create message/display cart?
  public int createCart(){
    System.out.println("Created cart.");
    System.out.println("Current cart: " + userCart);
    System.out.println("Selected drawings: " + userTickets);

    System.out.println("1. Add selected drawings to cart.");
    System.out.println("2. Return to Menu.");
    System.out.println("0. Close Program.");

    System.out.printf(standardInputMessage ,EXIT_CODE,CART_MAX_VALUE);

    String invalidMessage = String.format(standardInputError, EXIT_CODE, CART_MAX_VALUE);
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
    return MENU_CHOICE;
  }

  //currently has issues withgetting year prediction and adding it to userCart
  public int predictNumbers(){
    System.out.println("Predicting Numbers.");
    System.out.println("1. Find by year.");
    System.out.println("2. Find by month.");
    System.out.println("3. Find by day.");
    System.out.println("4. Enter custom values.");
    System.out.println("0. Close Program.");
    System.out.printf(standardInputMessage , EXIT_CODE, PREDICT_MAX_VALUE);

    String invalidMessage = String.format(standardInputError, EXIT_CODE, PREDICT_MAX_VALUE);
    Scanner input = new Scanner(System.in);
    int userInput = MENU_CHOICE;

    userInput = boundsCheck(PREDICT_CHOICE, invalidMessage, input);

    // TODO: 10/4/2022 requires adding of intricate IDs for boundsCheck() params and isValidChoice()
    switch (userInput){
      case 1: //this is year
        System.out.println("Enter a year from 2017 to 2022.");
        String invalidMessageYear = String.format(standardInputError,EARLIEST_YEAR,LATEST_YEAR);
        int yearInput = boundsCheck(YEAR_ID, invalidMessageYear, input);
        userTickets = database.findByYear(yearInput);
        userCart.addTicketList(userTickets);
        System.out.println(userCart);
        break;
      case 2: //this is month
        break;
      case 3: // this is day
        break;
      case 4: // this is custom values
        break;
      case 0:
        break;
      default:

    }

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
    // for the method getTopXXXX() should return a list of the respective top numbers
    // need to iterate over and pull the value from the list. with .getNumber
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

  private void addToCart(){

  }

  /*     -----------          helper methods for bounds                                                    -------------        */
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
      case CART_CHOICE:
        result = choice >= EXIT_CODE && choice <= CART_MAX_VALUE;
        break;
      case PREDICT_CHOICE:
        result = choice >= EXIT_CODE && choice <= PREDICT_MAX_VALUE;
        break;
      case REPEATED_CHOICE:
        result = false;
        break;
      case DAY_CHOICE:
        result = false;
        break;
      case TOP_CHOICE:
        result = false;
        break;
      case WAYS_TO_WIN_CHOICE:
        result = false;
        break;
      case HISTORY_CHOICE:
        result = false;
        break;
      case YEAR_ID:
        result = choice >= EARLIEST_YEAR && choice <= LATEST_YEAR;
        break;
      default:
        result = false;
        break;
    }
    return  result;
  }
}
