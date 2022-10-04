package com.lottery.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class MegaMillionsDatabase {
  private List<LotteryTicket> lotteryTickets;

//  public static void main(String[] args) throws FileNotFoundException {
//    readFile();
//  }


  public static void readFile() throws FileNotFoundException {
    // File instance to read file and scanner for processing
    File file = new File("src/main/java/com/lottery/database/MegaMillions_Data.txt");
    Scanner scanner  = new Scanner(file);

    // Read each line in the file
    int lineNumber = 1;
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();

      // Get date information and clean it up to use for Date obj
      int firstSemiColon = line.indexOf(";");
      String cleanDate = line.substring(0, firstSemiColon).replaceAll("/", "-");
      String year = cleanDate.substring(cleanDate.length() - 4);
      int dashIndex = cleanDate.indexOf("-");
      int secondDashIndex = cleanDate.indexOf("-", dashIndex + 1);
      String month = cleanDate.substring(0, dashIndex);
      String day = cleanDate.substring(dashIndex + 1, secondDashIndex);
      String finalDate = year + "-" + month + "-" + day;

      Date date = Date.valueOf(finalDate);

      // Get numbers from line and parse into int in order to create Array
      int secondSemiColon = line.indexOf("M", firstSemiColon);
      String numbers = line.substring(firstSemiColon + 2, secondSemiColon - 2);
      int commaIndex = numbers.indexOf(",");
      int number1 = Integer.parseInt(numbers.substring(0, commaIndex));
      int commaIndex2 = numbers.indexOf(",", commaIndex + 1);
      int number2 = Integer.parseInt(numbers.substring(commaIndex + 1, commaIndex2));
      int commaIndex3 = numbers.indexOf(",", commaIndex2 + 1);
      int number3 = Integer.parseInt(numbers.substring(commaIndex2 + 1, commaIndex3));
      int commaIndex4 = numbers.indexOf(",", commaIndex3 + 1);
      int number4 = Integer.parseInt(numbers.substring(commaIndex3 + 1, commaIndex4));
      int number5 = Integer.parseInt(numbers.substring(commaIndex4 + 1));
      int[] numberArray = {number1, number2, number3, number4, number5};

      // Get Mega/Money Ball from line and parse into int
      int colon = line.indexOf(":");
      System.out.println(line.length());
      int ball = Integer.parseInt(line.substring(colon + 1).trim());

      LotteryTicket lotteryTicket = new LotteryTicket(date, numberArray, ball);

      System.out.println("line " + lineNumber + " :" + lotteryTicket);
      lineNumber++;
    }

    scanner.close();
  }


















}
