package com.lottery.megamillions;

import com.lottery.megamillions.controller.Menu;
import java.io.Console;

public class MegaMillions {
  public static final int EXIT_CODE = 0;

  public static void main(String[] args) {
    Console console = System.console();
    int userInput = 9;
    Menu menu = new Menu();

    while (userInput != EXIT_CODE){
      menu.startingMenu();
      String line = console.readLine();
      userInput = Integer.parseInt(line);
    }
  }


}
