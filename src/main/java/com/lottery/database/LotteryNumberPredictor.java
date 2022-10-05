package com.lottery.database;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class LotteryNumberPredictor {

  private final List<LotteryTicket> database;
  private final Map<Integer, Integer> numbersMap;
  private final Map<Integer, Integer> megaBallMap;
  private Map<Integer, Integer> localNumbersMap;

  public LotteryNumberPredictor() throws FileNotFoundException {
    this.database = new MegaMillionsDatabase().getLotteryTickets();
    this.numbersMap = new MegaMillionsDatabase().getNumbersMap();
    this.megaBallMap = new MegaMillionsDatabase().getMegaBallMap();
    localNumbersMap = new HashMap<>();
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
    var top20Numbers= getTop20Numbers(numbersMap);
    var top10MegaBalls = getTop10MegaBalls(megaBallMap);

    // Take top 3 numbers and search for LotteryTickets containing these values
    int[] top3Numbers = new int[3];
    for(int i = 0; i < 3; i++) {
      top3Numbers[i] = top20Numbers.get(i);
    }
    var listContainingTop3Numbers = findByNumbers(top3Numbers);




    return null;
  }

  public List<Integer> getTop20Numbers(Map<Integer, Integer> map) {
    return produceTop20Numbers(map);
  }

  public List<Integer> getTop10MegaBalls(Map<Integer, Integer> map) {
    return produceTop10MegaBalls(map);
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

  private List<Integer> produceTop20Numbers(Map<Integer, Integer> map) {
    List<Integer> result = new ArrayList<>();                              // Top 20 Numbers
    List<Integer> list = new ArrayList<Integer>(map.values());      // Get all values in map
    Collections.sort(list);
    list = list.subList(0, 20);

    // Check each value in sorted list and add all numbers with value in map to result
    for (int value: list) {
      for (Entry<Integer, Integer> entry : map.entrySet()) {
        if (entry.getValue() == value) {
          result.add(entry.getKey());
        }
      }
    }
    return result;
  }

  private List<Integer> produceTop10MegaBalls(Map<Integer, Integer> map) {
    List<Integer> result = new ArrayList<>();                              // Top 10 MegaBalls
    List<Integer> list = new ArrayList<Integer>(map.values());     // Get all values in map
    Collections.sort(list);
    list = list.subList(0, 10);

    // Check each value in sorted list and add all numbers with value in map to result
    for (int value: list) {
      for (Entry<Integer, Integer> entry : map.entrySet()) {
        if (entry.getValue() == value) {
          result.add(entry.getKey());
        }
      }
    }
    return result;
  }

  private void countNumberAppearance(int[] numberArray) {
    for (int value : numberArray) {
      if (localNumbersMap.containsKey(value)) {
        localNumbersMap.put(value, localNumbersMap.get(value) + 1);
      } else {
        localNumbersMap.put(value, 1);
      }
    }
  }

}
