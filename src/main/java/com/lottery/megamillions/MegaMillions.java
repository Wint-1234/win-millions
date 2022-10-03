package com.lottery.megamillions;

import com.lottery.megamillions.controller.Menu;
import java.io.Console;
import java.util.Scanner;

public class MegaMillions {
  public static final int EXIT_CODE = 0;

  public static void main(String[] args) {
    Scanner inputLine = new Scanner(System.in);
    int userInput = 9;
    Menu menu = new Menu();

    while (userInput != EXIT_CODE){
      menu.startingMenu();
      userInput = inputLine.nextInt();
    }
  }


}
