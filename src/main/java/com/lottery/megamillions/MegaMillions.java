package com.lottery.megamillions;

import com.lottery.megamillions.controller.Menu;

/**
 * Main class that operates the Menu class that reads/displays user input.
 *
 * @version 1.0
 */
public class MegaMillions {

  /**
   * Entry point to start the program by creating a menu and is displayed to the user. User can input
   * desired choice from menu options.
   * @param args not currently used.
   */
  public static void main(String[] args) {
    Menu menu = new Menu();
    int choice = Menu.MENU_CHOICE;

    while (choice != Menu.EXIT_CODE){
      switch (choice){
        case Menu.CART_CHOICE:
          choice = menu.viewCart();
          break;
        case Menu.PREDICT_CHOICE:
          choice = menu.predictNumbers();
          break;
        case Menu.MONTH_WINNER_CHOICE:
          choice = menu.winnersByMonth();
          break;
        case Menu.YEAR_WINNER_CHOICE:
          choice = menu.winnersByYear();
          break;
        case Menu.TOP_CHOICE:
          choice = menu.topWinningNumbers();
          break;
        case Menu.WAYS_TO_WIN_CHOICE:
          choice = menu.howToPlay();
          break;
        case Menu.HISTORY_CHOICE:
          choice = menu.displayHistory();
          break;
        default:
          choice = menu.runMenu();
      }
    }
    System.out.println("Thank you for using our predictor.");
  }
}
