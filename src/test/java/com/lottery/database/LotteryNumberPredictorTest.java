package com.lottery.database;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
  void test_predictForMe() throws FileNotFoundException {
    MegaMillionsDatabase database = new MegaMillionsDatabase();
    System.out.println(database.getNumbersMap());
    System.out.println(database.getMegaBallMap());

    LotteryNumberPredictor lotteryNumberPredictor = new LotteryNumberPredictor();
    System.out.println(lotteryNumberPredictor.getTop10Numbers());
    System.out.println(lotteryNumberPredictor.getTop5MegaBalls());
  }

}