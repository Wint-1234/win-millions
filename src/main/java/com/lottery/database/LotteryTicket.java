package com.lottery.database;

import java.sql.Date;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents a new LotteryTicket based on date, lottery numbers, and a mega ball number
 *
 * @version 1.0
 */
public class LotteryTicket implements Comparable<LotteryTicket> {

  // Constants
  public static final String CHOOSE_5_ERROR = "You must choose 5 numbers.";
  public static final String AVAILABLE_ERROR = "Please select numbers from 1-70(inclusive).";
  public static final int LOTTERY_NUMBERS_LOWER_BOUND = 1;
  public static final int LOTTERY_NUMBERS_UPPER_BOUND = 75;
  public static final String MEGA_BALL_ERROR = "Invalid Mega Ball number.";
  public static final String DUPLICATES_ERROR = "Cannot create a lottery ticket with a"
      + " duplicate number: ";
  public static final int ARRAY_LENGTH_RESTRICTION = 5;

  private final Date date;
  private int[] lotteryNumbers;
  private int megaBallNumber;

  /**
   * Creates a new LotteryTicket. Sets the default date of this ticket to
   * current date.
   *
   * @param lotteryNumbers Int array of five numbers from 1-70 (inclusive).
   * @param megaBallNumber Int for mega ball number from 1-25 (inclusive).
   */
  public LotteryTicket(int[] lotteryNumbers, int megaBallNumber) {
    this.date = new Date(System.currentTimeMillis());
    setLotteryNumbers(lotteryNumbers);
    setMegaBallNumber(megaBallNumber);
  }

  /**
   * Creates a new LotteryTicket. Sets the date of this ticket to the date
   * passed in.
   *
   * @param date The date this ticket was drawn.
   * @param lotteryNumbers Int array of five numbers from 1-70 (inclusive).
   * @param megaBallNumber Int for mega ball number from 1-25 (inclusive).
   */
  public LotteryTicket(Date date, int[] lotteryNumbers, int megaBallNumber) {
    this.date = date;
    setLotteryNumbers(lotteryNumbers);
    setMegaBallNumber(megaBallNumber);
  }

  /**
   * Checks constraints in the lottery numbers array of this ticket.
   * If the array is null, does not have exactly five numbers from 1-70 (inclusive),
   * or has duplicate numbers, an exception is thrown.
   * Otherwise, it sets the lottery numbers of this ticket.
   *
   * @param lotteryNumbers
   * @throws IllegalArgumentException
   */
  public void setLotteryNumbers(int[] lotteryNumbers) {
    if (lotteryNumbers == null || lotteryNumbers.length != ARRAY_LENGTH_RESTRICTION) {
      throw new IllegalArgumentException(CHOOSE_5_ERROR);
    }
    checkForDuplicates(lotteryNumbers);        // Ensure lottery numbers does not contain duplicates
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
      throw new IllegalArgumentException(MEGA_BALL_ERROR);
    }
    this.megaBallNumber = megaBallNumber;
  }

  public Date getDate() {
    return date;
  }

  public int[] getLotteryNumbers() {
    return lotteryNumbers;
  }

  public int getMegaBallNumber() {
    return megaBallNumber;
  }

  @Override
  public int compareTo(LotteryTicket other) {
    return this.date.compareTo(other.date);
  }

  @Override
  public String toString(){
    return Arrays.toString(getLotteryNumbers()) + " (" + getMegaBallNumber() + ")\n";
  }

  private void checkForDuplicates(int[] lotteryNumbers) {
    Set<Integer> set = new TreeSet<>();
    for (int value: lotteryNumbers) {
      if ((set.add(value) == false)) {
        throw new IllegalArgumentException(DUPLICATES_ERROR + value);
      }
    }
  }
}
