package com.test.sync13.generate2;

import com.lmax.disruptor.EventHandler;
import com.test.sync13.generate1.Trade;

public class Handler3 implements EventHandler<Trade> {
    @Override  
    public void onEvent(Trade event, long sequence,  boolean endOfBatch) throws Exception {  
    	System.out.println("handler3: name: " + event.getName() + " , price: " + event.getPrice() + ";  instance: " + event.toString()  + "  " + Thread.currentThread().getName());
    	Thread.sleep(300);
    }  
}
