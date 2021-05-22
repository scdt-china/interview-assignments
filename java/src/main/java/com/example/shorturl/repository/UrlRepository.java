package com.example.shorturl.repository;

import com.example.shorturl.utils.CommonUtils;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Repository;

/**
 * 数据仓库，这里假定用Map存储数据
 * @author hcq
 */
@Repository
public final class UrlRepository {
	/**
	 * 按分片数定义的数组
	 * key->longUrl, value->shortUrl
	 */
	private BiMap<String, String>[] urlMapArray = new BiMap[CommonUtils.URL_DB_SHARED_NUM];

	/**
	 * 初始化每个分片的MAP,这里是模拟数据库，不考虑内存溢出问题
	 */
	private UrlRepository() {
		for (int i = 0; i < urlMapArray.length; i++) {
			urlMapArray[i] = Maps.synchronizedBiMap(HashBiMap.create(CommonUtils.DB_MAP_INIT_CAPACITY));
		}
	}

	/**
	 * 根据短域名获取长域名地址
	 * @param shortUrl 短域名地址，用来获取长域名地址
	 * @return URL数据分片的ID
	 */
	public String getLongUrl(String shortUrl) {
		int shareId = CommonUtils.getShareIdByShortUrl(shortUrl);
		if (shareId < 0 || shareId >= urlMapArray.length) {
			return null;
		}
		return urlMapArray[shareId].inverse().get(shortUrl);
	}

	/**
	 * 保存ID和长域名的对应关系,相当于入库
	 * @param shareId 分片Id
	 * @param shortUrl 短域名URL
	 * @param longUrl 长域名URL
	 * @return  返回shortUrl
	 */
	public String putUrl(int shareId, String shortUrl, String longUrl) {
		String result = urlMapArray[shareId].putIfAbsent(longUrl, shortUrl);
		if (result != null) {
			return result;
		}
		return urlMapArray[shareId].get(longUrl);
	}

	/**
	 * 根据URL获取ID,用来在创建短域名链接时验证是否重复
	 * 这里应该在数据库里通过索引查询，在这里模拟用遍历查找
	 * @param longUrl
	 * @return shortUrl
	 */
	public String getShortUrl(String longUrl) {
		int shareId = CommonUtils.getShareIdByLongUrl(longUrl);
		BiMap<String, String> map = urlMapArray[shareId];
		return map.get(longUrl);
	}
}
