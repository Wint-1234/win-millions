package com.lottery.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Reads in a file and creates the database of all Mega Millions winning
 * numbers dating back to 2017.
 *
 * @version 1.0
 */
public class MegaMillionsDatabase {
  // Constants
  public static final String DATABASE_FILE = "src/main/java/com/lottery/"
      + "database/MegaMillions_Data.txt";

  private List<LotteryTicket> lotteryTickets = new ArrayList<>();
  private Map<Integer, Integer> numbersMap;
  private Map<Integer, Integer> megaBallMap;

  /**
   * Reads in a file and initializes the database.
   *
   * @throws FileNotFoundException If the file path provided cannot be found.
   */
  public MegaMillionsDatabase() throws FileNotFoundException {
    numbersMap = new HashMap<>();
    megaBallMap = new HashMap<>();
    readFile();
  }

  /**
   * Gets a list of all LotteryTickets in the database.
   *
   * @return List of all LotteryTickets in the database.
   */
  public List<LotteryTicket> getLotteryTickets() {
    return lotteryTickets;
  }

  /**
   * Gets the database of all numbers and their frequency of appearance.
   *
   * @return Map of all numbers and their frequency of appearance.
   */
  public Map<Integer, Integer> getNumbersMap() {
    return numbersMap;
  }

  /**
   * Gets the database of all Mega Ball numbers and their frequency of appearance.
   *
   * @return Map of all Mega Ball numbers and their frequency of appearance.
   */
  public Map<Integer, Integer> getMegaBallMap() {
    return megaBallMap;
  }

  /**
   * Helper method that reads in a file and process each line to create new LotteryTickets.
   * The lotteryTickets are then added to the database.
   *
   * @throws FileNotFoundException If the file path provided cannot be found.
   */
  private void readFile() throws FileNotFoundException {
    // File instance to read file and scanner for processing
    File file = new File(DATABASE_FILE);
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
      countNumberAppearance(numberArray);         // Increment frequency count of each number

      // Get Mega/Money Ball from line and parse into int
      int colon = line.indexOf(":");
      int ball = Integer.parseInt(line.substring(colon + 1).trim());
      countMegaBallAppearance(ball);

      // Create lottery ticket and add it to the list.
      LotteryTicket lotteryTicket = new LotteryTicket(date, numberArray, ball);
      addToList(lotteryTicket);
    }
  }

  /**
   * Counts the number appearance of each lottery number in the database.
   *
   * @param numberArray The int[] of numbers to tally for frequency.
   */
  private void countNumberAppearance(int[] numberArray) {
    for (int value : numberArray) {
      if (numbersMap.containsKey(value)) {
        numbersMap.replace(value, numbersMap.get(value) + 1);
      } else {
        numbersMap.put(value, 1);
      }
    }
  }

  /**
   * Counts the Mega Ball number appearance within in the database.
   *
   * @param value The Mega Ball number to tally for frequency.
   */
  private void countMegaBallAppearance(int value) {
    if (megaBallMap.containsKey(value)) {
      megaBallMap.replace(value, megaBallMap.get(value) + 1);
    } else {
      megaBallMap.put(value, 1);
    }
  }

  /**
   * Adds the lotteryTicket to running list of LotteryTickets.
   *
   * @param lotteryTicket The ticket to add to the list.
   * @throws NullPointerException If the ticket is null.
   */
  private void addToList(LotteryTicket lotteryTicket) throws NullPointerException {
    if (lotteryTicket == null) {
      throw new NullPointerException();
    }
    lotteryTickets.add(lotteryTicket);
  }
}