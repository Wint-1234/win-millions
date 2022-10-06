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
    assertEquals(44, predictor.findByMonth(0).size());
    assertEquals(40, predictor.findByMonth(1).size());
    assertEquals(45, predictor.findByMonth(2).size());
    assertEquals(43, predictor.findByMonth(3).size());
    assertEquals(44, predictor.findByMonth(4).size());
    assertEquals(43, predictor.findByMonth(5).size());
    assertEquals(45, predictor.findByMonth(6).size());
    assertEquals(44, predictor.findByMonth(7).size());
    assertEquals(41, predictor.findByMonth(8).size());
    assertEquals(39, predictor.findByMonth(9).size());
    assertEquals(43, predictor.findByMonth(10).size());
    assertEquals(44, predictor.findByMonth(11).size());
  }

  @Test
  void test_findByNumbers() {
    assertEquals(1, predictor.findByNumbers(new int[]{9, 15}).size());
    assertEquals(33, predictor.findByNumbers(new int[]{9}).size());
    assertEquals(0, predictor.findByNumbers(new int[]{99}).size());
  }

  @Test
  void test_findByYear() {
    assertEquals(77, predictor.findByYear(2022).size());
    assertEquals(105, predictor.findByYear(2021).size());
    assertEquals(104, predictor.findByYear(2020).size());
    assertEquals(105, predictor.findByYear(2019).size());
    assertEquals(104, predictor.findByYear(2018).size());
    assertEquals(20, predictor.findByYear(2017).size());
  }

  @Test
  void test_predictForMe() throws FileNotFoundException {
    System.out.println(predictor.getTop10Numbers());
    System.out.println(predictor.getTop5MegaBalls());
    System.out.println("\n");
    System.out.println(predictor.predictForMe());
  }
}