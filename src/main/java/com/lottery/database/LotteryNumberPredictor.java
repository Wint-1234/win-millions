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
    top5MegaBalls = getTop10MegaBalls(megaBallMap);
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

    // Take top 3 numbers and search for LotteryTickets containing these values
    int[] top3Numbers = new int[3];
    for(int i = 0; i < 3; i++) {
      top3Numbers[i] = top10Numbers.get(i);
    }
    var listContainingTop3Numbers = findByNumbers(top3Numbers);

    // Count appearance of each number and store in LocalNumbersMap, get newTop20Numbers
    for (LotteryTicket ticket : listContainingTop3Numbers) {
      countNumberAppearance(ticket.getLotteryNumbers());
    }
    var newTop20Numbers = getTop10Numbers(localNumbersMap);

    // Create array for which to append values, find new values, and update.
    int[] lotteryNumbersArray = Arrays.copyOf(top3Numbers, 5);
    int number4 = -1;
    int number5 = -1;
    for (int value : newTop20Numbers) {
      if (top10Numbers.contains(value) && (number4 == -1)) {
        number4 = value;
        break;
      }
    }
    for (int value : newTop20Numbers) {
      if (top10Numbers.contains(value) && (number5 == -1)) {
        number5 = value;
        break;
      }
    }
    lotteryNumbersArray[3] = ((number4 == -1) ? top10Numbers.get(0) : number4);
    lotteryNumbersArray[4] = ((number5 == -1) ? top10Numbers.get(1) : number5);

    // Create 2 more arrays to create Lottery Tickets based on top values on new/old Top20
//    int[] lotteryNumbersArray2 = {newTop20Numbers.get(0), newTop20Numbers.get(1),
//        newTop20Numbers.get(2), newTop20Numbers.get(3), newTop20Numbers.get(4)};
    int[] lotteryNumbersArray3 = {top10Numbers.get(0), top10Numbers.get(1),
        top10Numbers.get(2), top10Numbers.get(3), top10Numbers.get(4)};

    // Create LotteryTickets
    LotteryTicket ticket = new LotteryTicket(lotteryNumbersArray, top5MegaBalls.get(0));
    LotteryTicket ticket2 = new LotteryTicket(lotteryNumbersArray, top5MegaBalls.get(1));
//    LotteryTicket ticket3 = new LotteryTicket(lotteryNumbersArray2, top10MegaBalls.get(0));
//    LotteryTicket ticket4 = new LotteryTicket(lotteryNumbersArray2, top10MegaBalls.get(1));
    LotteryTicket ticket5 = new LotteryTicket(lotteryNumbersArray3, top5MegaBalls.get(0));
    LotteryTicket ticket6 = new LotteryTicket(lotteryNumbersArray3, top5MegaBalls.get(1));

    // Add Tickets to List
    tickets.add(ticket);
    tickets.add(ticket2);
//    tickets.add(ticket3);
//    tickets.add(ticket4);
    tickets.add(ticket5);
    tickets.add(ticket6);

    return tickets;
  }

  public List<Integer> getTop10Numbers(Map<Integer, Integer> map) {
    return produceTop10Numbers(map);
  }

  public List<Integer> getTop10MegaBalls(Map<Integer, Integer> map) {
    return produceTop5MegaBalls(map);
  }

  private boolean containsNumbers(int[] numberArray, int[] numbersToCheck) {
    for (int value: numbersToCheck) {
      if (Arrays.binarySearch(numberArray, value) < 0) {
        return false;
      }
    }
    return true;
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
    for (int i = numberArray.length - 1; i > 60; i--) {
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
    for (int i = numberArray.length - 1; i > 20; i--) {
      result.add(numberArray[i].getNumber());
    }
    return result;
  }

  private void countNumberAppearance(int[] numberArray) {
    for (int value : numberArray) {
      if (localNumbersMap.containsKey(value)) {
        localNumbersMap.replace(value, localNumbersMap.get(value) + 1);
      } else {
        localNumbersMap.put(value, 1);
      }
    }
  }

  public List<Integer> getTop10Numbers() {
    return top10Numbers;
  }

  public List<Integer> getTop5MegaBalls() {
    return top5MegaBalls;
  }
}
