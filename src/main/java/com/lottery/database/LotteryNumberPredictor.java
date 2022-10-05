package com.lottery.database;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

  // TODO: 10/5/22
  public List<Number> getTop20Numbers() {
    return null;
  }

  // TODO: 10/5/22
  public List<Number> getTop10MegaBalls() {
    return null;
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


}
