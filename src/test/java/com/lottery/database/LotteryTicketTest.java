package com.lottery.database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LotteryTicketTest {

  /* Test case will pass due to correct amount of numbers input. */

  @Test
  void testSetLotteryNumbers() {
    int[] lottoNumbers = new int[]{1, 2, 3, 4, 5};
    int megaBall = 6;
    LotteryTicket ticket = new LotteryTicket(lottoNumbers, megaBall);
    ticket.setLotteryNumbers(lottoNumbers);
    assertEquals(5, lottoNumbers.length);
  }

  /* Test case will fail due to lottery numbers outside of expected range 1-70. */

  @Test
  void testSetLotteryNumbers_range_fails() {
    int[] lottoNumbers = new int[]{-1, 2, 3, 4, 5};
    int megaBall = 6;
    assertThrows(IllegalArgumentException.class, () -> {
      LotteryTicket ticket = new LotteryTicket(lottoNumbers, megaBall);
      ticket.setLotteryNumbers(lottoNumbers);
    });
  }

  /* Test case will fail due to only 4 lottery numbers selected. */

  @Test
  void testSetLotteryNumbers_fail() {
    int[] lottoNumbers = new int[]{1, 2, 3, 4};
    int megaBall = 6;
    assertThrows(IllegalArgumentException.class, () -> {
      LotteryTicket ticket = new LotteryTicket(lottoNumbers, megaBall);
      ticket.setLotteryNumbers(lottoNumbers);
    });
  }

  /* Test case will pass due to MegaBall number within expected range. */

  @Test
  void setMegaBallNumber_pass() {
    int[] lottoNumbers = new int[]{1, 2, 3, 4, 5};
    int megaBall = 6;
    LotteryTicket ticket = new LotteryTicket(lottoNumbers, megaBall);
    ticket.setMegaBallNumber(megaBall);
  }

  /* Test Case should fail due to megaBall number out of expected range. */

  @Test
  void setMegaBallNumber_fail() {
    int[] lottoNumbers = new int[]{1, 2, 3, 4, 5}
    int megaBall = 26;
    assertThrows(IllegalArgumentException.class, () -> {
      LotteryTicket ticket = new LotteryTicket(lottoNumbers, megaBall);
      ticket.setMegaBallNumber(megaBall);
    });

    /* Test case will assert lottoNumbers being passed equals value for getLotteryNumbers(). */

    @Test
    void getLotteryNumbers() {
      int[] lottNumbers = new int[]{1, 2, 3, 4, 5};
      int megaBall = 6;
      LotteryTicket ticket = new LotteryTicket(lottoNumbers, megaBall);
      assertArrayEquals(lottNumbers, ticket.getLotteryNumbers());
    }

    /* Test case will assert megaBall being passed equals value for getMegaBallNumber(). */

    @Test
    void getMegaBallNumber() {
      int[] lottNumbers = new int[]{1, 2, 3, 4, 5};
      int megaBall = 6;
      LotteryTicket ticket = new LotteryTicket(lottoNumbers, megaBall);
      assertEquals(megaBall, ticket.getMegaBallNumber());
    }
  }


}