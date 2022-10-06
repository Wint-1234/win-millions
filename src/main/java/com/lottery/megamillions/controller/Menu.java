package com.lottery.megamillions.controller;

import com.lottery.database.LotteryNumberPredictor;
import com.lottery.database.LotteryTicket;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Menu class that handles the user's input and performs operations given through menu and submenu
 * options.
 *
 * @version 1.0
 */
public class Menu {
  // Constants
  public static final int EXIT_CODE = 0;
  public static final int MENU_CHOICE = 99;
  public static final int CART_CHOICE = 1;
  public static final int PREDICT_CHOICE = 2;
  public static final int MONTH_WINNER_CHOICE = 3;
  public static final int YEAR_WINNER_CHOICE = 4;
  public static final int TOP_CHOICE = 5;
  public static final int WAYS_TO_WIN_CHOICE = 6;
  public static final int HISTORY_CHOICE = 7;
  public static final int MENU_MAX_VALUE = 7;
  public static final int CART_MAX_VALUE = 4;
  public static final int PREDICT_MAX_VALUE = 2;
  public static final int BY_MONTH_MAX_VALUE = 2;
  public static final int BY_YEAR_MAX_VALUE = 2;
  public static final int TOP_WINNING_CHOICE_MAX_VALUE = 3;
  public static final int EARLIEST_MONTH = 1;
  public static final int LATEST_MONTH = 12;
  public static final int EARLIEST_YEAR = 2017;
  public static final int LATEST_YEAR = 2022;
  public static final int MONTH_SUBMENU_SIGNATURE = 9;
  public static final int YEAR_SUBMENU_SIGNATURE = 8;
  public static final int TWO_OPTIONS_SIGNATURE = 98;
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_RED = "\u001B[31m";


  // Private fields
  private Cart userCart = new Cart();
  private List<LotteryTicket> selectedTickets = new ArrayList<>();
  private final LotteryNumberPredictor database;
  public final String closeProgramMessage = "0. Close Program.";
  private final String standardInputMessage = "Please enter a number %d to %d:%n";
  private final String standardInputError = "Error, enter a number %d to %d.";
  private final String addTicketsMessage = "Would you like to add the tickets to cart?\n1. Yes\n2. No";
  private final String winMillionsLogo = ANSI_GREEN + "\n"
          + "\t\t\t\t\t\t       _.a$$$$$a._\n"
          + "\t\t\t\t\t\t     ,$$$$MEGA$$$$$.\n"
          + "\t\t\t\t\t\t   ,$$$$MILLIONS$$$$$.\n"
          + "\t\t\t\t\t\t  d$$$$$$$$$$$$$$$$$$$b\n"
          + "\t\t\t\t\t\t d$$$$$$$$~'\"`~$$$$$$$$b\n"
          + "\t\t\t\t\t\t($$$$$$$p   _   q$$$$$$$)\n"
          + "\t\t\t\t\t\t$$$$$$$$    $    $$$$$$$$\n"
          + "\t\t\t\t\t\t$$$$$$$$    $    $$$$$$$$\n"
          + "\t\t\t\t\t\t($$$$$$$b       d$$$$$$$)\n"
          + "\t\t\t\t\t\t q$$$$$$$$a._.a$$$$$$$$p\n"
          + "\t\t\t\t\t\t  q$$$$$$$MEGA$$$$$$$$p\n"
          + "\t\t\t\t\t\t   `$$$$$MILLIONS$$$$'\n"
          + "\t\t\t\t\t\t     `$$$$$$$$$$$$$'\n"
          + "\t\t\t\t\t\t       `~$$$$$$$~'\n\n"
          + ANSI_RESET
          + "\t\t----------Welcome to Win Millions!----------\n";

  /**
   * Initialize the database.
   */
  public Menu() {
    try {
      database = new LotteryNumberPredictor();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Displays the overall main menu to the user and asks for input for next operation.
   *
   * @return int that is used in MegaMillions class for the user's choice of submenu/action.
   */
  public int runMenu() {
    System.out.println(winMillionsLogo);
    System.out.println("1. View lottery numbers cart.");
    System.out.println("2. Predict numbers for me.");
    System.out.println("3. Display winning numbers by selected month.");
    System.out.println("4. Display winning numbers by selected year.");
    System.out.println("5. Display Top-10 winning numbers & Top-5 Mega Ball numbers.");
    System.out.println("6. Display Ways to Win in Mega Millions");
    System.out.println("7. Display Mega Millions History");
    System.out.println(closeProgramMessage);
    System.out.printf(standardInputMessage, EXIT_CODE, MENU_MAX_VALUE);

    String invalidMessage = String.format(standardInputError, EXIT_CODE,
        MENU_MAX_VALUE);

    return boundsCheck(MENU_CHOICE, invalidMessage);
  }

  /**
   * Displays to the user the current cart, selected drawings, and options for those objects.
   * Those actions range from clearing to adding to the cart.
   *
   * @return int that is used in MegaMillions class for the user's choice of submenu/action.
   */
  public int viewCart() {
    System.out.println("Current cart: " + userCart);
    System.out.println("Selected drawings: " + selectedTickets);

    System.out.println("1. Add selected drawings to cart.");
    System.out.println("2. Clear selected tickets.");
    System.out.println("3. Clear current cart.");
    System.out.println("4. Return to Menu.");
    System.out.println(closeProgramMessage);

    System.out.printf(standardInputMessage, EXIT_CODE, CART_MAX_VALUE);

    String invalidMessage = String.format(standardInputError, EXIT_CODE, CART_MAX_VALUE);
    int userInput = boundsCheck(CART_CHOICE, invalidMessage);
    switch (userInput) {
      case 1:
        addToCart();
        userInput = returnToMenu();
        break;
      case 2:
        selectedTickets = new ArrayList<>();
        System.out.println("Selected tickets cleared.");
        userInput = returnToMenu();
        break;
      case 3:
        userCart = new Cart();
        System.out.println("Cart is cleared.");
        userInput = returnToMenu();
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

  /**
   * Display to the user the option to generate predicted Mega Ball tickets, and with the option to
   * add the tickets to the cart.
   *
   * @return int that is used in MegaMillions class for the user's choice of submenu/action.
   */
  public int predictNumbers() {
    System.out.println("\nPredicting Numbers.");
    System.out.println("1. Predict Mega Ball Tickets.");
    System.out.println("2. Return to main menu.");
    System.out.println(closeProgramMessage);
    System.out.printf(standardInputMessage, EXIT_CODE, PREDICT_MAX_VALUE);

    String invalidMessage = String.format(standardInputError, EXIT_CODE, PREDICT_MAX_VALUE);
    int userInput = boundsCheck(TWO_OPTIONS_SIGNATURE, invalidMessage);

    if (userInput == 2) {
      userInput = MENU_CHOICE;
    }
    if (userInput == 1) {
      System.out.println("Predicting tickets.");
      selectedTickets.addAll(database.predictForMe());

      System.out.println("The predicted tickets: \n" + selectedTickets);
      System.out.println(addTicketsMessage);

      invalidMessage = String.format(standardInputError, EXIT_CODE, TWO_OPTIONS_SIGNATURE);
      userInput = boundsCheck(TWO_OPTIONS_SIGNATURE, invalidMessage);

      if (userInput == 1) {
        addToCart();
      }
      if (userInput == EXIT_CODE){
        userInput = EXIT_CODE;
      }else {
        userInput = returnToMenu();
      }
    }
    if (userInput < 0) {
      userInput = PREDICT_CHOICE;
    }
    return userInput;
  }

  /**
   * Display to the user the option to retrieve winning numbers by desired month, and with the
   * option to add the tickets to the cart.
   *
   * @return int that is used in MegaMillions class for the user's choice of submenu/action.
   */
  public int winnersByMonth() {
    System.out.println("Winning numbers on particular month.");
    System.out.println("1. Display winning numbers by desired month.");
    System.out.println("2. Return to main menu.");
    System.out.println(closeProgramMessage);
    System.out.printf(standardInputMessage, EXIT_CODE, BY_MONTH_MAX_VALUE);

    String invalidMessage = String.format(standardInputError, EXIT_CODE, BY_MONTH_MAX_VALUE);
    int userInput = boundsCheck(TWO_OPTIONS_SIGNATURE, invalidMessage);

    // menu breakout first
    if (userInput == 2) {
      userInput = MENU_CHOICE;
    }
    if (userInput == 1) {
      System.out.println("Enter the month 1 (January) to 12 (December)");
      String invalidMessageMonth = String.format(standardInputError, EARLIEST_MONTH, LATEST_MONTH);
      int monthInput = boundsCheck(MONTH_SUBMENU_SIGNATURE, invalidMessageMonth);
      selectedTickets.addAll(database.findByMonth(monthInput - 1));

      System.out.println("Winning tickets for the Month " + monthInput + ": ");
      System.out.println(selectedTickets);
      System.out.println(addTicketsMessage);

      invalidMessage = String.format(standardInputError, EXIT_CODE, TWO_OPTIONS_SIGNATURE);
      userInput = boundsCheck(TWO_OPTIONS_SIGNATURE, invalidMessage);

      if (userInput == 1) {
        addToCart();
      }
      if (userInput == EXIT_CODE){
        userInput = EXIT_CODE;
      }else {
        userInput = returnToMenu();
      }
    }
    if (userInput < 0) {
      userInput = MONTH_WINNER_CHOICE;
    }
    return userInput;
  }

  /**
   * Display to the user the option to retrieve winning numbers by desired year, and with the
   * option to add the tickets to the cart.
   *
   * @return int that is used in MegaMillions class for the user's choice of submenu/action.
   */
  public int winnersByYear() {
    System.out.println("Winning numbers on a particular year.");
    System.out.println("1. Display winning numbers by desired year.");
    System.out.println("2. Return to main menu.");
    System.out.println(closeProgramMessage);
    System.out.printf(standardInputMessage, EXIT_CODE, BY_YEAR_MAX_VALUE);

    String invalidMessage = String.format(standardInputError, EXIT_CODE, BY_YEAR_MAX_VALUE);
    int userInput = boundsCheck(YEAR_WINNER_CHOICE, invalidMessage);

    //menu breakout first
    if (userInput == 2) {
      userInput = MENU_CHOICE;
    }
    if (userInput == 1) {
      System.out.println("Enter a year from 2017 to 2022.");

      String invalidMessageYear = String.format(standardInputError, EARLIEST_YEAR, LATEST_YEAR);
      int yearInput = boundsCheck(YEAR_SUBMENU_SIGNATURE, invalidMessageYear);
      selectedTickets.addAll(database.findByYear(yearInput));

      System.out.println("Winning tickets for the Year " + yearInput + ": ");
      System.out.println(selectedTickets);
      System.out.println(addTicketsMessage);

      invalidMessage = String.format(standardInputError, EXIT_CODE, TWO_OPTIONS_SIGNATURE);
      userInput = boundsCheck(TWO_OPTIONS_SIGNATURE, invalidMessage);
      if (userInput == 1) {
        addToCart();
      }
      if (userInput == EXIT_CODE){
        userInput = EXIT_CODE;
      }else {
        userInput = returnToMenu();
      }
    }
    if (userInput < 0) {
      userInput = YEAR_WINNER_CHOICE;
    }
    return userInput;
  }

  /**
   * Display to the user the option to retrieve the top-10 winning numbers and top-5 Mega Ball
   * numbers.
   *
   * @return int that is used in MegaMillions class for the user's choice of submenu/action.
   */
  public int topWinningNumbers() {
    System.out.println("Top winning numbers, from choice of 10 or 5.");
    System.out.println("1. Display Top-10 winning numbers & Top-5 Mega Ball numbers.");
    System.out.println("2. Return to main menu.");
    System.out.println(closeProgramMessage);
    System.out.printf(standardInputMessage, EXIT_CODE, TOP_WINNING_CHOICE_MAX_VALUE);

    String invalidMessage = String.format(standardInputError, EXIT_CODE, TOP_WINNING_CHOICE_MAX_VALUE);
    int userInput = boundsCheck(TOP_CHOICE, invalidMessage);

    // menu breakout first
    if (userInput == 2) {
      userInput = MENU_CHOICE;
    }
    if (userInput == 1) {
      System.out.println("Displaying Top-10 winning numbers: \n" + database.getTop10Numbers());
      System.out.println("Displaying Top-5 Mega Ball numbers: \n" + database.getTop5MegaBalls());
      userInput = returnToMenu();
    }
    if (userInput < 0) {
      userInput = TOP_CHOICE;
    }
    return userInput;
  }

  /**
   * Display to the user the how to play the Mega Millions game.
   *
   * @return int that is used in MegaMillions class for the user's choice of submenu/action.
   */
  public int howToPlay() {
    System.out.println("\nMega Millions tickets cost $2.00 per play.\n"
        + "Players may pick six numbers from two separate pools of numbers:\n"
        + "- Five different numbers from 1 to 70 (the white balls).\n"
        + "And one number from 1 to 25 (the gold Mega Ball).\n"
        + "- Or select Easy Pick/Quick Pick.\n"
        + ANSI_GREEN
        + "You win the jackpot by matching all six winning numbers in a drawing.\n"
        + ANSI_RESET);
    int userInput = returnToMenu();
    if(userInput < 0){
      userInput = WAYS_TO_WIN_CHOICE;
    }
    return userInput;

  }

  /**
   * Display to the user the brief history of the Mega Millions game.
   *
   * @return int that is used in MegaMillions class for the user's choice of submenu/action.
   */
  public int displayHistory() {
    System.out.println("\n"
        + "There is over $100 billion spent on lotteries each year.\n"
        + "Lotteries are run by 48 jurisdictions: 45 states plus the\n"
        + "District of Columbia, Puerto Rico, and the U.S. Virgin Islands.\n"
        + "Lotteries are subject to the laws of and operated independently\n"
        + "by each jurisdiction.\n\n"
        + "Mega Millions began in 1996 as the Big Game. The current cost is $2\n"
        + "to play and the largest jackpot ever won was $1.537 billion in 2018.\n"
        + ANSI_RED
        + "NATIONAL PROBLEM GAMBLING HELPLINE 1-800-522-4700."
        + ANSI_RESET
        + "\n");
    int userInput = returnToMenu();
    if(userInput < 0){
      userInput = HISTORY_CHOICE;
    }
    return userInput;
  }

  /**
   * Helper method to add selected tickets to the user's cart.
   */
  private void addToCart() {
    userCart.addTicketList(selectedTickets);
    selectedTickets = new ArrayList<>();
    System.out.println("Tickets added to cart: \n" + userCart);
  }

  /**
   * Checks the input the user has entered is a valid int value within a specified range.
   *
   * @param menuSelectionChoice int signature of the menu of originating method call for proper check.
   * @param invalidMessage String message that is holds an error message unique to the menu.
   * @return int that is a valid menu option based on the menuSelectionChoice that is passed in.
   */
  private int boundsCheck(int menuSelectionChoice, String invalidMessage) {
    Scanner scanner  = new Scanner(System.in);
    int userInput = MENU_CHOICE;
    while (!isValidChoice(userInput, menuSelectionChoice)) {
      while (!scanner.hasNextInt()) {
        System.out.println("Non Number " + invalidMessage);
        scanner.next();
      }
      userInput = scanner.nextInt();
      if (!isValidChoice(userInput, menuSelectionChoice)) {
        System.out.println("Bounds " + invalidMessage);
      }
    }
    return userInput;
  }

  /**
   * Method is called from boundsCheck to verify the proper value range for input of respective
   * menu choice.
   * @param userInput int the users input that is being checked against the menu's valid input range.
   * @param menuSelectionChoice int signature of the menu of originating method call for proper check.
   * @return boolean that is verifies that the userInput is in bounds or not.
   */
  private boolean isValidChoice(int userInput, int menuSelectionChoice) {
    boolean result = false;
    switch (menuSelectionChoice) {
      case MENU_CHOICE:
        result = userInput >= EXIT_CODE && userInput <= MENU_MAX_VALUE;
        break;
      case CART_CHOICE:
        result = userInput >= EXIT_CODE && userInput <= CART_MAX_VALUE;
        break;
      case PREDICT_CHOICE:
        result = userInput >= EXIT_CODE && userInput <= PREDICT_MAX_VALUE;
        break;
      case MONTH_WINNER_CHOICE:
        result = userInput >= EXIT_CODE && userInput <= BY_MONTH_MAX_VALUE;
        break;
      case YEAR_WINNER_CHOICE:
        result = userInput >= EXIT_CODE && userInput <= BY_YEAR_MAX_VALUE;
        break;
      case TOP_CHOICE:
        result = userInput >= EXIT_CODE && userInput <= TOP_WINNING_CHOICE_MAX_VALUE;
        break;
      case MONTH_SUBMENU_SIGNATURE:
        result = userInput >= EARLIEST_MONTH && userInput <= LATEST_MONTH;
        break;
      case YEAR_SUBMENU_SIGNATURE:
        result = userInput >= EARLIEST_YEAR && userInput <= LATEST_YEAR;
        break;
      case TWO_OPTIONS_SIGNATURE:
        result = userInput >= EXIT_CODE && userInput <= 2;
        break;
    }
    return result;
  }

  /**
   * Helper method that asks the user if they would like to return to the main menu. This method makes
   * calls to boundsCheck() for valid input.
   * @return MENU_CHOICE to return to the main menu or -2 to signal to remain in current menu.
   */
  private int returnToMenu() {
    System.out.println("Return to Main Menu?\n1. Yes.\n2. No.");
    String invalidMessage = String.format(standardInputError, EXIT_CODE, 2);
    int userInput = boundsCheck(TWO_OPTIONS_SIGNATURE, invalidMessage);
    if (userInput == 1) {
      return MENU_CHOICE;
    }

    return userInput * -1;
  }
}
