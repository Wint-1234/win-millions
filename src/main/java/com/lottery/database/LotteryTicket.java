package com.lottery.database;

<<<<<<< HEAD
public class LotteryTicket {




=======
import java.sql.Date;

public class LotteryTicket implements Comparable<LotteryTicket> {
  private final Date date;
  private final int[] lotteryNumbers;
  private final int megaBallNumber;

  public LotteryTicket(Date date, int[] lotteryNumbers, int megaBallNumber){
    this.date = date;
    this.lotteryNumbers = lotteryNumbers;
    this.megaBallNumber = megaBallNumber;
  }

  @Override
  public int compareTo(LotteryTicket other) {
    return this.date.compareTo(other.date);
  }

  public int[] getLotteryNumbers() {
    return lotteryNumbers;
  }

  public int getMegaBallNumber() {
    return megaBallNumber;
  }
>>>>>>> main
}
