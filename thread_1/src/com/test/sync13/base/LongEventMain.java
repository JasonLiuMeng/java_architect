package com.test.sync13.base;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class LongEventMain {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//创建缓冲池
		ExecutorService executor = Executors.newCachedThreadPool();
		//创建数据生成工厂
		LongEventFactory lFactory = new LongEventFactory();
		
		int ringBufferSize = 1024 * 1024;
		
		/**
		//BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现
		WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
		//SleepingWaitStrategy 的性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景
		WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
		//YieldingWaitStrategy 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于CPU逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性
		WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
		*/
		
		//创建Disruptor
		//1 第一个参数为工厂类对象，用于创建一个个的LongEvent， LongEvent是实际的消费数据，初始化启动Disruptor的时候，Disruptor会调用该工厂方法创建一个个的消费数据实例存放到RingBuffer缓冲区里面去，创建的对象个数为ringBufferSize指定的
		//2 第二个参数为RingBuffer缓冲区大小
		//3 线程池，进行Disruptor内部的数据接收处理调用
		//4 第四个参数ProducerType.SINGLE和ProducerType.MULTI，用来指定数据生成者有一个还是多个
		//5 第五个参数是一种策略：WaitStrategy
		Disruptor<LongEvent> disruptor = new Disruptor<>(lFactory, ringBufferSize, executor,ProducerType.MULTI,new YieldingWaitStrategy());
		
		//连接消费事件方法
		disruptor.handleEventsWith(new LongEventHandler());
		
		//启动
		disruptor.start();
		
		//往disruptor中推送数据
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
		
//		LongEventProducer producer = new LongEventProducer(ringBuffer);
		LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(8);
		for(long i = 0; i < 100; i++){
			byteBuffer.putLong(0, i);
			producer.onData(byteBuffer);
		}
		  
		disruptor.shutdown();
		executor.shutdown();
	}
	
}
