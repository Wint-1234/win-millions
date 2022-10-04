package com.lottery.database;

public class Number {
  private int number;
  private int count;

  public Number(int number, int count) {
    this.number = number;
    this.count = count;
  }

  public int getNumber() {
    return number;
  }

  public int getCount() {
    return count;
  }
}
