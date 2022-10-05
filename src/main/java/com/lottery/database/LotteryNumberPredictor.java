package com.lottery.database;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
  List<Integer> top20Numbers;
  List<Integer>  top10MegaBalls;

  public LotteryNumberPredictor() throws FileNotFoundException {
    this.database = new MegaMillionsDatabase().getLotteryTickets();
    this.numbersMap = new MegaMillionsDatabase().getNumbersMap();
    this.megaBallMap = new MegaMillionsDatabase().getMegaBallMap();
    localNumbersMap = new HashMap<>();
    top20Numbers = getTop20Numbers(numbersMap);
    top10MegaBalls = getTop10MegaBalls(megaBallMap);
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
      top3Numbers[i] = top20Numbers.get(i);
    }
    var listContainingTop3Numbers = findByNumbers(top3Numbers);

    // Count appearance of each number and store in LocalNumbersMap, get newTop20Numbers
    for (LotteryTicket ticket : listContainingTop3Numbers) {
      countNumberAppearance(ticket.getLotteryNumbers());
    }
    var newTop20Numbers = getTop20Numbers(localNumbersMap);

    // Create array for which to append values, find new values, and update.
    int[] lotteryNumbersArray = Arrays.copyOf(top3Numbers, 5);
    int number4 = -1;
    int number5 = -1;
    for (int value : newTop20Numbers) {
      if (top20Numbers.contains(value) && (number4 == -1)) {
        number4 = value;
        break;
      }
    }
    for (int value : newTop20Numbers) {
      if (top20Numbers.contains(value) && (number5 == -1)) {
        number5 = value;
        break;
      }
    }
    lotteryNumbersArray[3] = ((number4 == -1) ? top20Numbers.get(0) : number4);
    lotteryNumbersArray[4] = ((number5 == -1) ? top20Numbers.get(1) : number5);

    // Create 2 more arrays to create Lottery Tickets based on top values on new/old Top20
//    int[] lotteryNumbersArray2 = {newTop20Numbers.get(0), newTop20Numbers.get(1),
//        newTop20Numbers.get(2), newTop20Numbers.get(3), newTop20Numbers.get(4)};
    int[] lotteryNumbersArray3 = {top20Numbers.get(0), top20Numbers.get(1),
        top20Numbers.get(2), top20Numbers.get(3), top20Numbers.get(4)};

    // Create LotteryTickets
    LotteryTicket ticket = new LotteryTicket(lotteryNumbersArray, top10MegaBalls.get(0));
    LotteryTicket ticket2 = new LotteryTicket(lotteryNumbersArray, top10MegaBalls.get(1));
//    LotteryTicket ticket3 = new LotteryTicket(lotteryNumbersArray2, top10MegaBalls.get(0));
//    LotteryTicket ticket4 = new LotteryTicket(lotteryNumbersArray2, top10MegaBalls.get(1));
    LotteryTicket ticket5 = new LotteryTicket(lotteryNumbersArray3, top10MegaBalls.get(0));
    LotteryTicket ticket6 = new LotteryTicket(lotteryNumbersArray3, top10MegaBalls.get(1));

    // Add Tickets to List
    tickets.add(ticket);
    tickets.add(ticket2);
//    tickets.add(ticket3);
//    tickets.add(ticket4);
    tickets.add(ticket5);
    tickets.add(ticket6);

    return tickets;
  }

  public List<Integer> getTop20Numbers(Map<Integer, Integer> map) {
    return produceTop20Numbers(map);
  }

  public List<Integer> getTop10MegaBalls(Map<Integer, Integer> map) {
    return produceTop10MegaBalls(map);
  }

  private boolean containsNumbers(int[] numberArray, int[] numbersToCheck) {
    for (int value: numbersToCheck) {
      if (Arrays.binarySearch(numberArray, value) < 0) {
        return false;
      }
    }
    return true;
  }

  // TODO: 10/5/22 Check map keys in order of highest values, save them, and return them as List
  private List<Integer> produceTop20Numbers(Map<Integer, Integer> map) {
//    List<Integer> result = new ArrayList<>();                              // Top 20 Numbers
//    List<Integer> list = new ArrayList<>(map.values());      // Get all values in map
//    Collections.sort(list);
//    list = list.subList(0, list.size());
//
//    // Check each value in sorted list and add all numbers with value in map to result
//    for (int value: list) {
//      for (Entry<Integer, Integer> entry : map.entrySet()) {
//        if (entry.getValue() == value) {
//          result.add(entry.getKey());
//        }
//      }
//    }
    return null;
  }

  // TODO: 10/5/22 Check map keys in order of highest values, save them, and return them as List
  private List<Integer> produceTop10MegaBalls(Map<Integer, Integer> map) {
//    List<Integer> result = new ArrayList<>();                              // Top 10 MegaBalls
//    List<Integer> list = new ArrayList<>(map.values());                    // Get all values in map
//    Collections.sort(list);
//    list = list.subList(0, list.size());
//
//    // Check each value in sorted list and add all numbers with value in map to result
//    for (int value: list) {
//      for (Entry<Integer, Integer> entry : map.entrySet()) {
//        if (entry.getValue() == value) {
//          result.add(entry.getKey());
//        }
//      }
//    }
    return null;
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

  public List<Integer> getTop20Numbers() {
    return top20Numbers;
  }

  public List<Integer> getTop10MegaBalls() {
    return top10MegaBalls;
  }
}
