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
  public static final int MONTH_WINNER_CHOICE = 3;
  public static final int YEAR_WINNER_CHOICE = 4;
  public static final int TOP_CHOICE = 5;
  public static final int WAYS_TO_WIN_CHOICE = 6;
  public static final int HISTORY_CHOICE = 7;
  public static final String CLOSE_PROGRAM_MESSAGE = "0. Close Program.";
  // Top bounds values that is for invalidMessage local values, which is passed in boundsCheck()
  public static final int MENU_MAX_VALUE = 7;
  public static final int CART_MAX_VALUE = 4;
  public static final int PREDICT_MAX_VALUE = 2; //change?
  public static final int EARLIEST_YEAR = 2017;
  public static final int LATEST_YEAR = 2022;
  public static final int EARLIEST_MONTH = 1;
  public static final int LATEST_MONTH = 12;

  // this is exclusive towards the predictNumbers() method to be passsed into the boundsCheck() method.
  public static final int YEAR_SIGNATURE = 8;
  public static final int MONTH_SIGNATURE = 9;
  public static final int TWO_OPTIONS_SIGNATURE = 98;

  private Cart userCart = new Cart();
  private List<LotteryTicket> selectedTickets = new ArrayList<>();
  private final LotteryNumberPredictor database;

  private final String standardInputMessage = "Please enter a number %d to %d:%n";
  private final String standardInputError = "Error, enter a number %d to %d.";
  private final String addTicketsMessage = "Would you like to add the tickets to cart?\n1. Yes\n2. No";

  // Startup message to the program.

  private final String menuBorder = "========================";
  String num = "5";

  String ball2 =
      "\t\t\t\t\t\t        _.a$$$$$a._\n"
          + "\t\t\t\t\t\t     ,$$$$MEGA$$$$$.\n"
          + "\t\t\t\t\t\t   ,$$$$MILLIONS$$$$$.\n"
          + "\t\t\t\t\t\t  d$$$$$$$$$$$$$$$$$$$b\n"
          + "\t\t\t\t\t\t d$$$$$$$$~'\"`~$$$$$$$$b\n"
          + "\t\t\t\t\t\t($$$$$$$p   _   q$$$$$$$)\n"
          + "\t\t\t\t\t\t$$$$$$$$    $    $$$$$$$$\n"
          + "\t\t\t\t\t\t$$$$$$$$    $    $$$$$$$$\n"
          + "\t\t\t\t\t\t($$$$$$$b       d$$$$$$$)\n"
          + "\t\t\t\t\t\t q$$$$$$$$a._.a$$$$$$$$p\n"
          + "\t\t\t\t\t\t  q$$$$$$$MEGA$$$$$$p\n"
          + "\t\t\t\t\t\t   `$$$$$MILLIONS$$$$'\n"
          + "\t\t\t\t\t\t     `$$$$$$$$$$$$$'\n"
          + "\t\t\t\t\t\t       `~$$$$$$$~'\n\n"
          + "\t\t----------Welcome to Win Millions!----------\n";

  // Startup message to the program.
  public Menu() {
    System.out.println("\t\t----------Welcome to Win Millions!----------\n");
    try {
      database = new LotteryNumberPredictor();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  // Returns the userInput of a menu choice 0 to 7.
  public int runMenu() {
    Scanner input = new Scanner(System.in);
    String invalidMessage = String.format(standardInputError, EXIT_CODE,
        MENU_MAX_VALUE);
    startingMenu();

    return boundsCheck(MENU_CHOICE, invalidMessage, input);
  }

  /*--------------   Methods below are in order of menu selection untill next comment seperator         ----------- */

  // maybe remove and add contents to runMenu().
  public void startingMenu() {
    System.out.println(ball2);

    System.out.println("1. View lottery numbers cart.");
    System.out.println("2. Predict numbers for me.");
    System.out.println("3. Display winning numbers by selected month.");
    System.out.println("4. Display winning numbers by selected year.");
    System.out.println("5. Display Top-10 winning numbers & Top-5 Mega Ball numbers.");
    System.out.println("6. Display Ways to Win in Mega Millions");
    System.out.println("7. Display Mega Millions History");
    System.out.println(CLOSE_PROGRAM_MESSAGE);
    System.out.printf(standardInputMessage, EXIT_CODE, MENU_MAX_VALUE);
  }

  // change to just create message/display cart?
  public int viewCart() {
    System.out.println("Current cart: " + userCart);
    System.out.println("Selected drawings: " + selectedTickets);

    System.out.println("1. Add selected drawings to cart.");
    System.out.println("2. Clear selected tickets.");
    System.out.println("3. Clear current cart.");
    System.out.println("4. Return to Menu.");
    System.out.println(CLOSE_PROGRAM_MESSAGE);

    System.out.printf(standardInputMessage, EXIT_CODE, CART_MAX_VALUE);

    String invalidMessage = String.format(standardInputError, EXIT_CODE, CART_MAX_VALUE);
    Scanner input = new Scanner(System.in);
    int userInput = boundsCheck(CART_CHOICE, invalidMessage, input);
    // TODO: 10/5/2022 potentional change for method call only that is provided in cart
    switch (userInput) {
      case 1:
        addToCart();
        userInput = returnToMenu(input); //stall
        break;
      case 2:
        selectedTickets = new ArrayList<>();
        System.out.println("Selected tickets cleared.");
        userInput = returnToMenu(input);
        break;
      case 3:
        userCart = new Cart();
        System.out.println("Cart is cleared.");
        userInput = returnToMenu(input);
        break;
      case 4:
        userInput = MENU_CHOICE;
        break;
    }

    if (userInput < 0) {
      userInput = CART_CHOICE;
    }
    return userInput;
  }

  //currently has issues withgetting year prediction and adding it to userCart
  public int predictNumbers() {
    System.out.println("\nPredicting Numbers.");
    System.out.println("1. Predict Mega Ball Tickets.");
    System.out.println("2. Return to main menu.");
    System.out.println(CLOSE_PROGRAM_MESSAGE);
    System.out.printf(standardInputMessage, EXIT_CODE, PREDICT_MAX_VALUE);

    String invalidMessage = String.format(standardInputError, EXIT_CODE, PREDICT_MAX_VALUE);
    Scanner input = new Scanner(System.in);
    int userInput = boundsCheck(PREDICT_CHOICE, invalidMessage, input);

    if (userInput == 2) {
      userInput = MENU_CHOICE;
    }
    if (userInput == 1) {
      System.out.println("Predicting tickets.");
      selectedTickets.addAll(database.predictForMe());

      System.out.println("The predicted tickets: \n" + selectedTickets);
      System.out.println(addTicketsMessage);

      invalidMessage = String.format(standardInputError, EXIT_CODE, 2);
      userInput = boundsCheck(TWO_OPTIONS_SIGNATURE, invalidMessage, input);

      if (userInput == 1) {
        addToCart();
      }
      userInput = returnToMenu(input);
    }
    if (userInput < 0) {
      userInput = PREDICT_CHOICE;
    }
    return userInput;
  }

  public int winnersByMonth() {// TODO: 10/5/2022 going to have to -1 to month input for method call
    System.out.println("Winning numbers on particular month.");
    System.out.println("1. Display winning numbers by desired month.");
    System.out.println("2. Return to main menu.");
    System.out.println(CLOSE_PROGRAM_MESSAGE);
    System.out.printf(standardInputMessage, EXIT_CODE, 2);

    String invalidMessage = String.format(standardInputError, EXIT_CODE, 2);
    Scanner input = new Scanner(System.in);
    int userInput = boundsCheck(MONTH_WINNER_CHOICE, invalidMessage, input);

    if (userInput == 2) {
      userInput = MENU_CHOICE;
    }
    if (userInput == 1) {
      System.out.println("Enter the month 1 (January) to 12 (December)");
      String invalidMessageMonth = String.format(standardInputError, EARLIEST_MONTH, LATEST_MONTH);
      int monthInput = boundsCheck(MONTH_SIGNATURE, invalidMessageMonth, input);
      selectedTickets.addAll(database.findByMonth(monthInput - 1));

      System.out.println("Winning tickets for the Month " + monthInput + ": ");
      System.out.println(selectedTickets);

      System.out.println(addTicketsMessage);

      invalidMessage = String.format(standardInputError, EXIT_CODE, 2);
      userInput = boundsCheck(TWO_OPTIONS_SIGNATURE, invalidMessage, input);
      if (userInput == 1) {
        addToCart();
      }
      userInput = returnToMenu(input);

    }
    if (userInput < 0) { //redundant?
      userInput = MONTH_WINNER_CHOICE;
    }
    return userInput;
  }

  public int winnersByYear() {
    System.out.println("Winning numbers on a particular year.");
    System.out.println("1. Display winning numbers by desired year.");
    System.out.println("2. Return to main menu.");
    System.out.println(CLOSE_PROGRAM_MESSAGE);
    System.out.printf(standardInputMessage, EXIT_CODE, 2);

    String invalidMessage = String.format(standardInputError, EXIT_CODE, 2);
    Scanner input = new Scanner(System.in);
    int userInput = boundsCheck(YEAR_WINNER_CHOICE, invalidMessage, input);

    if (userInput == 2) {
      userInput = MENU_CHOICE;
    }
    if (userInput == 1) {
      System.out.println("Enter a year from 2017 to 2022.");

      String invalidMessageYear = String.format(standardInputError, EARLIEST_YEAR, LATEST_YEAR);
      int yearInput = boundsCheck(YEAR_SIGNATURE, invalidMessageYear, input);
      selectedTickets.addAll(database.findByYear(yearInput));
      System.out.println("Winning tickets for the Year " + yearInput + ": ");
      System.out.println(selectedTickets);

      System.out.println(addTicketsMessage);

      invalidMessage = String.format(standardInputError, EXIT_CODE, 2);
      userInput = boundsCheck(TWO_OPTIONS_SIGNATURE, invalidMessage, input);
      if (userInput == 1) {
        addToCart();
      }
      userInput = returnToMenu(input);
    }
    if (userInput < 0) {
      userInput = YEAR_WINNER_CHOICE;
    }
    return userInput;
  }

  public int topWinningNumbers() {
    System.out.println("Top winning numbers, from choice of 10 or 5.");
    System.out.println("1. Display Top-10 winning numbers & Top-5 Mega Ball numbers.");
    System.out.println("2. Return to main menu.");
    System.out.println(CLOSE_PROGRAM_MESSAGE);
    System.out.printf(standardInputMessage, EXIT_CODE, 3);

    String invalidMessage = String.format(standardInputError, EXIT_CODE, 2);
    Scanner input = new Scanner(System.in);
    int userInput = boundsCheck(TOP_CHOICE, invalidMessage, input);

    // menu breakout first
    if (userInput == 2) {
      userInput = MENU_CHOICE;
    }
    if (userInput == 1) {
      System.out.println("Displaying Top-10 winning numbers: \n" + database.getTop10Numbers());
      System.out.println("Displaying Top-5 Mega Ball numbers: \n" + database.getTop5MegaBalls());
      userInput = returnToMenu(input);
    }
    if (userInput < 0) {
      userInput = TOP_CHOICE;
    }
    return userInput;
  }

  public int waysToWin() {
    System.out.println("Ways to win the Mega Ball.");
    // TODO: 10/4/2022 need imp
    return MENU_CHOICE;
  }

  public int displayHistory() {
    System.out.println("History of the Mega Millions.");
    // TODO: 10/4/2022 need imp
    return MENU_CHOICE;
  }

  private void addToCart() {
    userCart.addTicketList(selectedTickets);
    selectedTickets = new ArrayList<>();
    System.out.println("Tickets added to cart: \n" + userCart);
  }

  /*     -----------          helper methods for bounds                                                    -------------        */
  // checks bound, selectionSignature is the overall menu selection. invalidMessage is custom error. input is the scanner.
  private int boundsCheck(int selectionSignature, String invalidMessage, Scanner input) {
    int userInput = MENU_CHOICE;
    while (!isValidChoice(userInput, selectionSignature)) {
      while (!input.hasNextInt()) {
        System.out.println("Non Number " + invalidMessage);
        input.next();
      }
      userInput = input.nextInt();
      if (!isValidChoice(userInput, selectionSignature)) {
        System.out.println("Bounds " + invalidMessage);
      }
    }
    return userInput;
  }

  // checks for valid choice. choice is what entered. selectionSignature is corrasponds to overall menu selection.(enum?)
  private boolean isValidChoice(int choice, int selectionSignature) {
    boolean result = false;
    switch (selectionSignature) {
      case MENU_CHOICE:
        result = choice >= EXIT_CODE && choice <= MENU_MAX_VALUE;
        break;
      case CART_CHOICE:
        result = choice >= EXIT_CODE && choice <= CART_MAX_VALUE;
        break;
      case PREDICT_CHOICE:
        result = choice >= EXIT_CODE && choice <= PREDICT_MAX_VALUE;
        break;
      case MONTH_WINNER_CHOICE:
      case TWO_OPTIONS_SIGNATURE:
      case YEAR_WINNER_CHOICE:
        result = choice >= EXIT_CODE && choice <= 2;
        break;
      case TOP_CHOICE:
        result = choice >= EXIT_CODE && choice <= 3;
        break;
      case MONTH_SIGNATURE:
        result = choice >= EARLIEST_MONTH && choice <= LATEST_MONTH;
        break;
      case YEAR_SIGNATURE:
        result = choice >= EARLIEST_YEAR && choice <= LATEST_YEAR;
        break;
    }
    return result;
  }

  // a call after each menu selection action, so the user does not see the menu after each action.
  private int returnToMenu(Scanner input) {
    System.out.println("Return to Main Menu?\n1. Yes.\n2. No.");
    String invalidMessage = String.format(standardInputError, EXIT_CODE, 2);
    int userInput = boundsCheck(TWO_OPTIONS_SIGNATURE, invalidMessage, input);
    if (userInput == 1) {
      return MENU_CHOICE;
    }

    return userInput * -1;
  }
}
