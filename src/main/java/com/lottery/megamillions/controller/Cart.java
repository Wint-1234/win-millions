package com.lottery.megamillions.controller;

import com.lottery.database.LotteryTicket;
import java.util.ArrayList;
import java.util.List;

public class Cart {

  private List<LotteryTicket> wantedTickets = new ArrayList<>();

  public void addTicket(int[] numbers, int megaBall){
    wantedTickets.add(new LotteryTicket(numbers,megaBall));
  }

  public void addTicketList(List<LotteryTicket> userTickets){

    for(LotteryTicket ticket : userTickets){
      addTicket(ticket.getLotteryNumbers(), ticket.getMegaBallNumber());
    }
  }

  @Override
  public String toString(){
    return getClass().getSimpleName() + ": " + wantedTickets;
  }
}
