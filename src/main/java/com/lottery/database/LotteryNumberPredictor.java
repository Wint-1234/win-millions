package com.lottery.database;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LotteryNumberPredictor {

  private final List<LotteryTicket> database;

  public LotteryNumberPredictor() throws FileNotFoundException {
    this.database = new MegaMillionsDatabase().getLotteryTickets();
  }

  public List<LotteryTicket> findByMonth(int month) {
    return database
        .stream()
        .filter(lotteryTicket -> lotteryTicket.getDate().getMonth() == month)
        .collect(Collectors.toList());
  }

  public List<LotteryTicket> findByYear(int year) {
    return database
        .stream()
        .filter(lotteryTicket -> lotteryTicket.getDate().getYear() == year)
        .collect(Collectors.toList());
  }

  public List<LotteryTicket> findByNumbers(int[] numbers) {
    return database
        .stream()
        .filter(lotteryTicket -> containsNumbers(lotteryTicket.getLotteryNumbers(), numbers))
        .collect(Collectors.toList());
  }

  // TODO: 10/4/22 Complete after Lunch
  public List<LotteryTicket> getTop20Numbers() {
    return null;
  }

  // TODO: 10/4/22 Complete after Lunch
  public List<LotteryTicket> getTop10MegaBalls() {
    return null;
  }

  private boolean containsNumbers(int[] numberArray, int[] numbersToCheck) {
    var listArray = List.of(numberArray);
    for (int value: numbersToCheck) {
      if (!listArray.contains(value)) {
        return false;
      }
    }
    return true;
  }
}
