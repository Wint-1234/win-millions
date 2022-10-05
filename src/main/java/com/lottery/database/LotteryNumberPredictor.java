package com.lottery.database;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LotteryNumberPredictor {

  public static final int YEAR_LOWER_BOUND = 2017;
  public static final int YEAR_UPPER_BOUND = 2022;
  public static final int YEAR_OFFSET = 1900;
  public static final int MONTH_UPPER_BOUND = 11;
  private final List<LotteryTicket> database;
  private final Map<Integer, Integer> numbersMap;
  private final Map<Integer, Integer> megaBallMap;
  private Map<Integer, Integer> localNumbersMap;
  List<Integer> top10Numbers;
  List<Integer> top5MegaBalls;

  public LotteryNumberPredictor() throws FileNotFoundException {
    this.database = new MegaMillionsDatabase().getLotteryTickets();
    this.numbersMap = new MegaMillionsDatabase().getNumbersMap();
    this.megaBallMap = new MegaMillionsDatabase().getMegaBallMap();
    localNumbersMap = new HashMap<>();
    top10Numbers = getTop10Numbers(numbersMap);
    top5MegaBalls = getTop5MegaBalls(megaBallMap);
  }

  public List<LotteryTicket> findByMonth(int month) throws IllegalArgumentException {
    if (month < 0 || month > MONTH_UPPER_BOUND) {
      throw new IllegalArgumentException();
    }
    return database
        .stream()
        .filter(lotteryTicket -> lotteryTicket.getDate().getMonth() == month)
        .collect(Collectors.toList());
  }

  public List<LotteryTicket> findByYear(int year) throws IllegalArgumentException {
    if (year < YEAR_LOWER_BOUND || year > YEAR_UPPER_BOUND) {
      throw new IllegalArgumentException();
    }
    return database
        .stream()
        .filter(lotteryTicket -> (lotteryTicket.getDate().getYear() + YEAR_OFFSET) == year)
        .collect(Collectors.toList());
  }

  public List<LotteryTicket> findByNumbers(int[] numbers) throws NullPointerException {
    if (numbers == null) {
      throw new NullPointerException();
    }
    if (numbers.length > 5 || numbers.length == 0) {
      throw new IllegalArgumentException();
    }
    return database
        .stream()
        .filter(lotteryTicket -> (containsNumbers(lotteryTicket.getLotteryNumbers(), numbers)))
        .collect(Collectors.toList());
  }

  public List<LotteryTicket> predictForMe() {
    List<LotteryTicket> tickets = new ArrayList<>();
    int[] topNumbers = new int[2];
    for(int i = 0; i < 2; i++) {
      topNumbers[i] = top10Numbers.get(i);
    }
    var listContainingTopNumbers = findByNumbers(topNumbers);
    // Add tickets containingTop2Numbers
    for (LotteryTicket ticket: listContainingTopNumbers) {
      tickets.add(ticket);
    }
    for(int i = 0; i < 2; i++) {
      int k = 0 + 2;
      topNumbers[0] = top10Numbers.get(k);
      k++;
    }
    listContainingTopNumbers = findByNumbers(topNumbers);
    for (LotteryTicket ticket: listContainingTopNumbers) {
      tickets.add(ticket);
    }
    return tickets;
  }

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
