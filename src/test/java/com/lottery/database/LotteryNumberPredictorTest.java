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
    System.out.println(predictor.findByMonth(0));
  }

  @Test
  void test_findByNumbers() {
    System.out.println(predictor.findByNumbers(new int[]{9, 15}));
  }

  @Test
  void test_findByYear() {
    System.out.println(predictor.findByYear(2018));
  }

  @Test
  void test_predictForMe() {
  }

}