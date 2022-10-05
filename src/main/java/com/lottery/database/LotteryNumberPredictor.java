package com.lottery.database;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class LotteryNumberPredictor {

  private final List<LotteryTicket> database;
  private final Map<Integer, Integer> numbersMap;
  private final Map<Integer, Integer> megaBallMap;

  public LotteryNumberPredictor() throws FileNotFoundException {
    this.database = new MegaMillionsDatabase().getLotteryTickets();
    this.numbersMap = new MegaMillionsDatabase().getNumbersMap();
    this.megaBallMap = new MegaMillionsDatabase().getMegaBallMap();
  }

  public List<LotteryTicket> findByMonth(int month) throws IllegalArgumentException {
    if (month < 0 || month > 11) {
      throw new IllegalArgumentException();
    }
    return database
        .stream()
        .filter(lotteryTicket -> lotteryTicket.getDate().getMonth() == month)
        .collect(Collectors.toList());
  }

  public List<LotteryTicket> findByYear(int year) throws IllegalArgumentException {
    if (year < 2017 || year > 2022) {
      throw new IllegalArgumentException();
    }
    return database
        .stream()
        .filter(lotteryTicket -> lotteryTicket.getDate().getYear() == year)
        .collect(Collectors.toList());
  }

  public List<LotteryTicket> findByNumbers(int[] numbers) throws NullPointerException {
    if (numbers == null) {
      throw new NullPointerException();
    }
    return database
        .stream()
        .filter(lotteryTicket -> containsNumbers(lotteryTicket.getLotteryNumbers(), numbers))
        .collect(Collectors.toList());
  }

  public List<LotteryTicket> predictForMe() {
    var top20Numbers= getTop20Numbers();
    var top10MegaBalls = getTop10MegaBalls();


    return null;
  }

  public List<Integer> getTop20Numbers() {
    return produceTop20Numbers();
  }

  public List<Integer> getTop10MegaBalls() {
    return produceTop10MegaBalls();
  }

  private boolean containsNumbers(int[] numberArray, int[] numbersToCheck)
      throws NullPointerException {
    if (numberArray == null || numbersToCheck == null) {
      throw new NullPointerException();
    }
    var listArray = List.of(numberArray);
    for (int value: numbersToCheck) {
      if (!listArray.contains(value)) {
        return false;
      }
    }
    return true;
  }

  private List<Integer> produceTop20Numbers() {
    List<Integer> result = new ArrayList<>();                              // Top 20 Numbers
    List<Integer> list = new ArrayList<Integer>(numbersMap.values());      // Get all values in map
    Collections.sort(list);
    list = list.subList(0, 20);

    // Check each value in sorted list and add all numbers with value in map to result
    for (int value: list) {
      for (Entry<Integer, Integer> entry : numbersMap.entrySet()) {
        if (entry.getValue() == value) {
          result.add(entry.getKey());
        }
      }
    }
    return result;
  }

  private List<Integer> produceTop10MegaBalls() {
    List<Integer> result = new ArrayList<>();                              // Top 10 MegaBalls
    List<Integer> list = new ArrayList<Integer>(megaBallMap.values());     // Get all values in map
    Collections.sort(list);
    list = list.subList(0, 10);

    // Check each value in sorted list and add all numbers with value in map to result
    for (int value: list) {
      for (Entry<Integer, Integer> entry : megaBallMap.entrySet()) {
        if (entry.getValue() == value) {
          result.add(entry.getKey());
        }
      }
    }
    return result;
  }

}
