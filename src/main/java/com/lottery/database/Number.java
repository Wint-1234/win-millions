package com.lottery.database;

/**
 * Custom data obj to hold different numbers that could
 * potentially hold the same count value.
 *
 * @version 1.0
 */
public class Number implements Comparable<Number> {
  private int number;
  private int count;

  /**
   * Initializes the field of this obj.
   *
   * @param number The number of this obj.
   * @param count The frequency count of this number.
   */
  public Number(int number, int count) {
    this.number = number;
    this.count = count;
  }

  /**
   * Compares two Number objects based on their count.
   *
   * @param other The object to be compared.
   * @return int representation of comparing two Numbers based on count.
   */
  @Override
  public int compareTo(Number other) {
    return Integer.compare(this.count, other.count);
  }

  /**
   * Gets the number of this obj.
   *
   * @return int of this obj.
   */
  public int getNumber() {
    return number;
  }

  /**
   * Gets the count of this obj.
   *
   * @return int count of this number obj.
   */
  public int getCount() {
    return count;
  }
}
