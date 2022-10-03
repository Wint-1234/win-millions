package com.lottery.database;

import java.sql.Date;
import java.util.Arrays;

public class LotteryTicket implements Comparable<LotteryTicket> {

  // Constants
  public static final String CHOOSE_5_ERROR = "You must choose 5 numbers.";
  public static final String AVAILABLE_ERROR = "Please select numbers from 1-70(inclusive).";
  public static final int LOTTERY_NUMBERS_LOWER_BOUND = 1;
  public static final int LOTTERY_NUMBERS_UPPER_BOUND = 70;
  public static final String MEGABALL_ERROR = "Invalid Mega Ball number.";

  private final Date date;
  private int[] lotteryNumbers;
  private int megaBallNumber;

  public LotteryTicket(int[] lotteryNumbers, int megaBallNumber) {
    this.date = new Date(System.currentTimeMillis());
    setLotteryNumbers(lotteryNumbers);
    setMegaBallNumber(megaBallNumber);
  }

  public LotteryTicket(Date date, int[] lotteryNumbers, int megaBallNumber) {
    this.date = date;
    setLotteryNumbers(lotteryNumbers);
    setMegaBallNumber(megaBallNumber);
  }

  private void setLotteryNumbers(int[] lotteryNumbers) {
    if (lotteryNumbers.length != 5) {
      throw new IllegalArgumentException(CHOOSE_5_ERROR);
    }
    Arrays.sort(lotteryNumbers);
    for (int number : lotteryNumbers) {
      if (number < LOTTERY_NUMBERS_LOWER_BOUND || number > LOTTERY_NUMBERS_UPPER_BOUND) {
        throw new IllegalArgumentException(AVAILABLE_ERROR);
      }
    }
    this.lotteryNumbers = lotteryNumbers;
  }

  public void setMegaBallNumber(int megaBallNumber) {
    if (megaBallNumber < 1 || megaBallNumber > 25) {
      throw new IllegalArgumentException(MEGABALL_ERROR);
    }
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

}
