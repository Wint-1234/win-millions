package com.lottery.database;

public class Number implements Comparable<Number> {
  int number;
  int count;

  public Number(int number, int count) {
    this.number = number;
    this.count = count;
  }

  @Override
  public int compareTo(Number other) {
    int result = 0;
    if (this.count == other.count) {
      result = 0;
    } else if (this.count > other.count) {
      result = 1;
    } else {
      result = -1;
    }
    return result;
  }

  public int getNumber() {
    return number;
  }

  public int getCount() {
    return count;
  }
}
