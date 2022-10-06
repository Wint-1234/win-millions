package com.lottery.database;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Predictor that can be used to query various values and predict lottery number drawings.
 *
 * @version 1.0
 */
public class LotteryNumberPredictor {
  // Constants
  public static final int YEAR_LOWER_BOUND = 2017;
  public static final int YEAR_UPPER_BOUND = 2022;
  public static final int YEAR_OFFSET = 1900;
  public static final int MONTH_UPPER_BOUND = 11;
  public static final String MONTH_ERROR = "Invalid month value: ";
  public static final String INVALID_YEAR_ERROR = "Invalid year: ";
  public static final String PROVIDE_VALID_ARRAY_ERROR = "Please provide an array of values - "
      + "must not be empty or have more than five values";

  private final List<LotteryTicket> database;
  private final Map<Integer, Integer> numbersMap;
  private final Map<Integer, Integer> megaBallMap;

  private Map<Integer, Integer> localNumbersMap;
  private List<Integer> top10Numbers;
  private List<Integer> top5MegaBalls;

  /**
   * Creates a predictor object. It calls on the MegaMillionsDatabase class
   * to initialize the database, numbersMap, and the megaBallMap.
   * It also initializes the top10Numbers and top5MegaBalls.
   *
   * @throws FileNotFoundException If the MegaMillionsDatabase could not find a file.
   */
  public LotteryNumberPredictor() throws FileNotFoundException {
    this.database = new MegaMillionsDatabase().getLotteryTickets();
    this.numbersMap = new MegaMillionsDatabase().getNumbersMap();
    this.megaBallMap = new MegaMillionsDatabase().getMegaBallMap();
    localNumbersMap = new HashMap<>();
    top10Numbers = getTop10Numbers(numbersMap);
    top5MegaBalls = getTop5MegaBalls(megaBallMap);
  }

  /**
   * Returns a list of LotteryTickets with the specified month.
   *
   * @param month int representation of month to search for from 0-11 (Inclusive).
   * @return List of LotteryTickets with the specified month.
   * @throws IllegalArgumentException If month value does not adhere to specifications.
   */
  public List<LotteryTicket> findByMonth(int month) throws IllegalArgumentException {
    if (month < 0 || month > MONTH_UPPER_BOUND) {
      throw new IllegalArgumentException(MONTH_ERROR + month);
    }
    return database
        .stream()
        .filter(lotteryTicket -> lotteryTicket.getDate().getMonth() == month)
        .collect(Collectors.toList());
  }

  /**
   * Returns a list of LotteryTickets with the specified year.
   *
   * @param year int representation of year to search for from 2017-2022 (Inclusive).
   * @return List of LotteryTickets with the specified year.
   * @throws IllegalArgumentException If year value does not adhere to specifications.
   */
  public List<LotteryTicket> findByYear(int year) throws IllegalArgumentException {
    if (year < YEAR_LOWER_BOUND || year > YEAR_UPPER_BOUND) {
      throw new IllegalArgumentException(INVALID_YEAR_ERROR + year);
    }
    return database
        .stream()
        .filter(lotteryTicket -> (lotteryTicket.getDate().getYear() + YEAR_OFFSET) == year)
        .collect(Collectors.toList());
  }

  /**
   * Returns a list of LotteryTickets with the specified numbers.
   *
   * @param numbers int[] of numbers to search for across all tickets.
   * @return List of LotteryTickets containing the provided ints within the array.
   * @throws NullPointerException If array provided is null.
   * @throws IllegalArgumentException If the array length is greater than five or empty.
   */
  public List<LotteryTicket> findByNumbers(int[] numbers) throws NullPointerException {
    if (numbers == null) {
      throw new NullPointerException();
    }
    if (numbers.length > 5 || numbers.length == 0) {
      throw new IllegalArgumentException(PROVIDE_VALID_ARRAY_ERROR);
    }
    return database
        .stream()
        .filter(lotteryTicket -> (containsNumbers(lotteryTicket.getLotteryNumbers(), numbers)))
        .collect(Collectors.toList());
  }

  /**
   * Provides a custom list of lottery tickets that have the numbers with the highest frequencies
   * in the database.
   *
   * @return List of predicted lottery tickets.
   */
  public List<LotteryTicket> predictForMe() {
    List<LotteryTicket> tickets = new ArrayList<>();
    int[] topNumbers = new int[2];
    for(int i = 0; i < 2; i++) {
      topNumbers[i] = top10Numbers.get(i);
    }
    var listContainingTopNumbers = findByNumbers(topNumbers);
    // Add tickets containingTop2Numbers
    tickets.addAll(listContainingTopNumbers);
    for(int i = 0; i < 2; i++) {
      int k = i + 2;
      topNumbers[0] = top10Numbers.get(k);
    }
    listContainingTopNumbers = findByNumbers(topNumbers);
    tickets.addAll(listContainingTopNumbers);
    return tickets;
  }

  /**
   *
   *
   * @param numberArray
   * @param numbersToCheck
   * @return
   */
  private boolean containsNumbers(int[] numberArray, int[] numbersToCheck) {
    for (int value: numbersToCheck) {
      if (Arrays.binarySearch(numberArray, value) < 0) {
        return false;
      }
    }
    return true;
  }

  public List<Integer> getTop10Numbers(Map<Integer, Integer> map) {
    return produceTop10Numbers(map);
  }

  public List<Integer> getTop5MegaBalls(Map<Integer, Integer> map) {
    return produceTop5MegaBalls(map);
  }

  public List<Integer> getTop10Numbers() {
    return top10Numbers;
  }

  public List<Integer> getTop5MegaBalls() {
    return top5MegaBalls;
  }

  private List<Integer> produceTop10Numbers(Map<Integer, Integer> map) {
    var numbers = numbersMap.keySet().toArray();
    var frequency = numbersMap.values().toArray();
    List<Integer> result = new ArrayList<>();
    Number[] numberArray = new Number[numbers.length];

    for (int i = 0; i < numbers.length; i++) {
      Number number = new Number((int) numbers[i], (int) frequency[i]);
      numberArray[i] = number;
    }
    Arrays.sort(numberArray);
    for (int i = numberArray.length - 1; i >= 60; i--) {
      result.add(numberArray[i].getNumber());
    }
    return result;
  }

  private List<Integer> produceTop5MegaBalls(Map<Integer, Integer> map) {
    var numbers = megaBallMap.keySet().toArray();
    var frequency = megaBallMap.values().toArray();
    List<Integer> result = new ArrayList<>();
    Number[] numberArray = new Number[numbers.length];

    for (int i = 0; i < numberArray.length; i++) {
      Number number = new Number((int) numbers[i], (int) frequency[i]);
      numberArray[i] = number;
    }
    Arrays.sort(numberArray);
    for (int i = numberArray.length - 1; i >= 20; i--) {
      result.add(numberArray[i].getNumber());
    }
    return result;
  }
}
