package com.lottery.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MegaMillionsDatabase {

  private List<LotteryTicket> lotteryTickets = new ArrayList<>();

  public MegaMillionsDatabase() throws FileNotFoundException {
    readFile();
  }

  private void readFile() throws FileNotFoundException {
    // File instance to read file and scanner for processing
    File file = new File("src/main/java/com/lottery/database/MegaMillions_Data.txt");
    Scanner scanner = new Scanner(file);

    // Scan through each line in the file
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();

      // Get date information and clean it up to use for Date obj
      int firstSemiColon = line.indexOf(";");
      String cleanDate = line.substring(0, firstSemiColon).replaceAll("/", "-");
      String year = cleanDate.substring(cleanDate.length() - 4);
      int dashIndex = cleanDate.indexOf("-");
      int secondDashIndex = cleanDate.indexOf("-", dashIndex + 1);
      String month = cleanDate.substring(0, dashIndex).trim();
      String day = cleanDate.substring(dashIndex + 1, secondDashIndex).trim();
      String finalDate = (year + "-" + month + "-" + day).trim();

      Date date = Date.valueOf(finalDate);

      // Get numbers from line and parse into int in order to create Array
      int secondSemiColon = line.indexOf("M", firstSemiColon);
      String numbers = line.substring(firstSemiColon + 2, secondSemiColon - 2).trim();
      int commaIndex = numbers.indexOf(",");
      int number1 = Integer.parseInt(numbers.substring(0, commaIndex).trim());
      int commaIndex2 = numbers.indexOf(",", commaIndex + 1);
      int number2 = Integer.parseInt(numbers.substring(commaIndex + 1, commaIndex2).trim());
      int commaIndex3 = numbers.indexOf(",", commaIndex2 + 1);
      int number3 = Integer.parseInt(numbers.substring(commaIndex2 + 1, commaIndex3).trim());
      int commaIndex4 = numbers.indexOf(",", commaIndex3 + 1);
      int number4 = Integer.parseInt(numbers.substring(commaIndex3 + 1, commaIndex4).trim());
      int number5 = Integer.parseInt(numbers.substring(commaIndex4 + 1).trim());
      int[] numberArray = {number1, number2, number3, number4, number5};

      // Get Mega/Money Ball from line and parse into int
      int colon = line.indexOf(":");
      System.out.println(line.length());
      int ball = Integer.parseInt(line.substring(colon + 1).trim());

      // Create lottery ticket and add it to the list.
      LotteryTicket lotteryTicket = new LotteryTicket(date, numberArray, ball);
      addToList(lotteryTicket);
    }
  }

  private void addToList(LotteryTicket lotteryTicket) throws NullPointerException {
    if (lotteryTicket == null) {
      throw new NullPointerException();
    }
    lotteryTickets.add(lotteryTicket);
  }

  public List<LotteryTicket> getLotteryTickets() {
    return lotteryTickets;
  }

}