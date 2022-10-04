package com.lottery.database;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class LotteryNumberPredictor {

  private final List<LotteryTicket> database;

  public LotteryNumberPredictor() throws FileNotFoundException {
    this.database = new MegaMillionsDatabase().getLotteryTickets();
  }

  public List<LotteryTicket> findByMonth(Enum month) {
    return null;
  }

  public List<LotteryTicket> findByYear(int year) {
    return null;
  }

  public List<LotteryTicket> findByNumbers(int[] numbers) {
    return null;
  }

  public List<LotteryTicket> getTop20Numbers() {
    return null;
  }

  public List<LotteryTicket> getTop10MegaBalls() {
    return null;
  }

}
