package com.lottery.megamillions;

import com.lottery.database.MegaMillionsDatabase;
import com.lottery.megamillions.controller.Menu;
import java.io.FileNotFoundException;

public class MegaMillions {
  public static final int EXIT_CODE = 0;
  public static final int NEUTRAL_CHOICE_VALUE = 99;

  public static void main(String[] args) {
    int choice = NEUTRAL_CHOICE_VALUE;
    try {
      MegaMillionsDatabase.readFile();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    Menu menu = new Menu();
    while (choice != EXIT_CODE){
      choice = menu.runMenu(); // runs the main menu and asks the user for input. change this?
    }



  }


}
