package com.lottery.megamillions;

import com.lottery.database.MegaMillionsDatabase;
import com.lottery.megamillions.controller.Menu;
import java.io.FileNotFoundException;

/**
 * Main class that operates the Menu class that reads/displays user input
 */
public class MegaMillions {
  public static final int EXIT_CODE = 0;
  public static final int MENU_CHOICE = 99;
  public static final int CART_CHOICE = 1;
  public static final int PREDICT_CHOICE = 2;
  public static final int MONTH_WINNER_CHOICE = 3;
  public static final int YEAR_WINNER_CHOICE = 4;
  public static final int TOP_CHOICE = 5;
  public static final int WAYS_TO_WIN_CHOICE = 6;
  public static final int HISTORY_CHOICE = 7;

  public static void main(String[] args) throws FileNotFoundException {
    int choice = MENU_CHOICE;
    var database = new MegaMillionsDatabase().getLotteryTickets();

    // TODO: 10/4/2022 within these choices add the option for the user to add their custom ticket,
    //  particularly for topXXX choice
    Menu menu = new Menu();
    while (choice != EXIT_CODE){
      switch (choice){
        case CART_CHOICE: // 1
          choice = menu.createCart();
          break;
        case PREDICT_CHOICE: // 2
          choice = menu.predictNumbers();
          break;
        case MONTH_WINNER_CHOICE: // 3
          choice = menu.winnersByMonth();
          break;
        case YEAR_WINNER_CHOICE: // 4
          choice = menu.winnersByYear();
          break;
        case TOP_CHOICE: // 5
          choice = menu.topWinningNumbers();
          break;
        case WAYS_TO_WIN_CHOICE: // 6
          choice = menu.waysToWin();
          break;
        case HISTORY_CHOICE: // 7
          choice = menu.displayHistory();
          break;
        default:
          choice = menu.runMenu();
      }
    }
    System.out.println("Thank you for using our predictor.");
  }
}
