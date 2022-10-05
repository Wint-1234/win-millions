package com.lottery.database;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

class LotteryNumberPredictorTest {

  private static final LotteryNumberPredictor predictor;

  static {
    try {
      predictor = new LotteryNumberPredictor();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void test_findByMonth() {
  }

  @Test
  void test_findByNumbers() {
  }

  @Test
  void test_findByYear() {
  }

  @Test
  void test_predictForMe() {
  }

}