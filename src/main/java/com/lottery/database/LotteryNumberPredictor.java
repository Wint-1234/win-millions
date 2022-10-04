package com.lottery.database;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class LotteryNumberPredictor {

  private final List<LotteryTicket> database;
  private final int[] lotteryNumbersCount;
  private final int[] megaBallCount;
  private LinkedList<Number> numbers = new LinkedList<>();
  private LinkedList<Number> megaBalls = new LinkedList<>();
  private LinkedList<Number> top20Numbers = new LinkedList<>();
  private LinkedList<Number> top10MegaBalls = new LinkedList<>();

  public LotteryNumberPredictor() throws FileNotFoundException {
    this.database = new MegaMillionsDatabase().getLotteryTickets();
    this.lotteryNumbersCount = new MegaMillionsDatabase().getLotteryNumbersCount();
    this.megaBallCount =  new MegaMillionsDatabase().getMegaBallCount();
    convertToLinkedList(lotteryNumbersCount, megaBallCount);
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

  public List<Number> getTop20Numbers() {
    for (int i = 0; i < 20; i++) {
      Number max = findMax(numbers);
      top20Numbers.add(max);
      numbers.remove(max);
    }
    return top20Numbers;
  }

  public List<Number> getTop10MegaBalls() {
    for (int i = 0; i < 10; i++) {
      Number max = findMax(megaBalls);
      top10MegaBalls.add(max);
      megaBalls.remove(max);
    }
    return top10MegaBalls;
  }

  private Number findMax(List<Number> numbersList) {
    Number max = new Number(-1, -1);
    for (Number number : numbersList) {
      if (number.getCount() > max.getCount()) {
        max = number;
      }
    }
    return max;
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

  private void convertToLinkedList(int[] lotteryNumbersCount, int[] megaBallCount) {
    Number lotteryNumber;
    Number megaBall;

    for (int i = 1; i < lotteryNumbersCount.length; i++) {
      lotteryNumber = new Number(i, lotteryNumbersCount[i]);
      numbers.add(lotteryNumber);
    }
    for (int i = 1; i < megaBallCount.length; i++) {
      megaBall = new Number(i, megaBallCount[i]);
      megaBalls.add(megaBall);
    }
  }
}
