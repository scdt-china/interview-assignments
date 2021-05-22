package com.example.shorturl.service;

/**
 * ID生成器
 * @author hcq
 */
public interface IdService {
	/**
	 * 根据分片生成唯一ID
	 * @param sharedId 分片ID
	 * @return 返回长整型ID
	 */
	long genId(int sharedId);
}
