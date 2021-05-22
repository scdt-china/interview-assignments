package com.example.shorturl.service.impl;

import com.example.shorturl.service.IdService;
import com.example.shorturl.utils.CommonUtils;
import com.example.shorturl.utils.SnowFlake;
import org.springframework.stereotype.Service;

/**
 * 基于雪花算法的ID生成器
 * @author hcq
 */
@Service
public class IdServiceImpl implements IdService {
	/**
	 * 根据分片获取SnowFlake的数组
	 */
	private SnowFlake[] snowFlakeArray = new SnowFlake[CommonUtils.URL_DB_SHARED_NUM];

	/**
	 * 初始化SnowFlake数组
	 * @throws Exception
	 */
	public IdServiceImpl() throws Exception {
		for (int i = 0; i < snowFlakeArray.length; i++) {
			snowFlakeArray[i] = new SnowFlake(i);
		}
	}

	/**
	 * 根据分片生成唯一ID
	 * @param sharedId 分片ID
	 * @return
	 */
	@Override
	public long genId(int sharedId) {
		return snowFlakeArray[sharedId].genId();
	}
}
