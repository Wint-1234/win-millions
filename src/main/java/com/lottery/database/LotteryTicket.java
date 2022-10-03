package com.lottery.database;

import java.sql.Date;

public class LotteryTicket implements Comparable<LotteryTicket> {
  private Date date;
  private int[] lotteryNumbers;
  private int megaBallNumber;

  public LotteryTicket(Date date, int[] lotteryNumbers, int megaBallNumber){
    this.date = date;
    this.lotteryNumbers = lotteryNumbers;
    this.megaBallNumber = megaBallNumber;
  }

  @Override
  public int compareTo(LotteryTicket other) {
    return this.date.compareTo(other.date);
  }
}
