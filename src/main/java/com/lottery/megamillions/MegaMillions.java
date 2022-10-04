package com.lottery.megamillions;

import com.lottery.database.MegaMillionsDatabase;
import com.lottery.megamillions.controller.Menu;
import java.io.FileNotFoundException;

public class MegaMillions {
  public static final int EXIT_CODE = 0;
  public static final int MENU_CHOICE = 99;

  public static void main(String[] args) throws FileNotFoundException {
    int choice = MENU_CHOICE;
    var database = new MegaMillionsDatabase().getLotteryTickets();

    Menu menu = new Menu();
    while (choice != EXIT_CODE){
      switch (choice){
        case 1:
          choice = menu.createCart();
          break;
        default:
          choice = menu.runMenu();
      }
      choice = menu.runMenu(); // runs the main menu and asks the user for input. change this?
    }



  }


}
