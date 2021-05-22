package com.example.shorturl.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLongArray;

/**
 * 基于雪花算法的ID生成器
 * 长整形从低位到高位：
 *    1-12位  表示每个时段的序列号
 *    13-22位 表示分片ID
 *    22-63位 表示时间，2 ^ 41 - 1大概69年
 * @author hcq
 */
@Slf4j
public class SnowFlake {
	/**
	 * 最大的分片ID，1024个
	 */
	public static final long MAX_SHARED_ID = 1023L;
	/**
	 * AtomicLongArray 环的大小，可保存256毫秒内，每个毫秒数上一次的MessageId，时间回退的时候依赖与此
	 */
	private static final int ID_CYCLE_CAPACITY = 256;
	/**
	 * 时间戳在messageId中左移的位数
	 */
	private static final int TIMESTAMP_SHIFT_COUNT = 22;
	/**
	 * 分片ID在messageId中左移的位数
	 */
	public static final int SHARED_ID_SHIFT_COUNT = 12;
	/**
	 * 序列号的掩码 2^12 4096
	 */
	private static final long SEQUENCE_MASK = 4095L;
	/**
	 * Id ，开始的时间戳，start the world，世界初始之日 2021-05-20 00:00:00
	 */
	private static final long START_THE_WORLD_MILLIS = 1621440000000L;
	/**
	 * 分片ID
	 */
	private long sharedId;
	/**
	 * Id环，解决时间回退的关键，亦可在多线程情况下减少毫秒数切换的竞争
	 */
	private AtomicLongArray idCycle = new AtomicLongArray(ID_CYCLE_CAPACITY);

	/**
	 * @param sharedId 分片ID
	 * @throws Exception
	 */
	public SnowFlake(long sharedId) throws Exception {
		if (sharedId < 0L || sharedId > MAX_SHARED_ID) {
			throw new Exception("the shared id is out of range,it must between 1 and 1023");
		}
		this.sharedId = sharedId;
	}

	/**
	 * 生成ID
	 * @return 返回生成的ID
	 */
	public long genId() {
		do {
			/*
			 * 获取当前时间戳，此时间戳是当前时间减去start the world的毫秒数
			 */
			long timestamp = System.currentTimeMillis() - START_THE_WORLD_MILLIS;
			/*
			 * 获取当前时间在messageIdCycle 中的下标，用于获取环中上一个MessageId
			 */
			int index = (int) timestamp & (ID_CYCLE_CAPACITY - 1);
			long messageIdInCycle = idCycle.get(index);
			/*
			 * 通过在messageIdCycle 获取到的messageIdInCycle，计算上一个MessageId的时间戳
			 */
			long timestampInCycle = messageIdInCycle >> TIMESTAMP_SHIFT_COUNT;
			/*
			 * 如果timestampInCycle 并没有设置时间戳，或时间戳小于当前时间，认为需要设置新的时间戳
			 */
			if (messageIdInCycle == 0 || timestampInCycle < timestamp) {
				long messageId = timestamp << TIMESTAMP_SHIFT_COUNT | sharedId << SHARED_ID_SHIFT_COUNT;
				/*
				 * 使用CAS的方式保证在该条件下，messageId 不被重复
				 */
				if (idCycle.compareAndSet(index, messageIdInCycle, messageId)) {
					return messageId;
				}
				log.debug("messageId cycle CAS1 failed");
			}
			/*
			 * 如果当前时间戳与messageIdCycle的时间戳相等，使用环中的序列号+1的方式，生成新的序列号
			 * 如果发生了时间回退的情况，（即timestampInCycle > timestamp的情况）那么不能也更新messageIdCycle 的时间戳，使用Cycle中MessageId+1
			 */
			if (timestampInCycle >= timestamp) {
				long sequence = messageIdInCycle & SEQUENCE_MASK;
				if (sequence >= SEQUENCE_MASK) {
					log.debug("over sequence mask :{}", sequence);
					continue;
				}
				long messageId = messageIdInCycle + 1L;
				/*
				 * 使用CAS的方式保证在该条件下，messageId 不被重复
				 */
				if (idCycle.compareAndSet(index, messageIdInCycle, messageId)) {
					return messageId;
				}
				log.debug("messageId cycle CAS2 failed");
			}
			/*
			 * 整个生成过程中，采用的spinLock
			 */
		} while (true);
	}
}
