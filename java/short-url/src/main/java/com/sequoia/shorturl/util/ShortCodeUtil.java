package com.sequoia.shorturl.util;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * @Title  ShortCodeUtil.java 生成8位短码工具类
 *
 *
 * 本算法利用62个可打印字符，通过随机生成32位UUID，由于UUID都为十六进制，所以将UUID分成8组，每4个为一组，
 * UUID不重复，然后通过模62操作，结果作为索引取出字符，
 * 这样重复率大大降低，性能可以做出批量生成方式
 *
 * @Author xj
 *
 * @Date 2021/06/27
 *
 * @version v1.0.0
 *
 */
public class ShortCodeUtil {

    public static List<String> shortCodelist=new ArrayList<>(); //批量生成短域名码列表

	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	/**
	 * 单个8位短码生成方法
	 * 根据UUID 生成效率高，不重复
	 * 通过随机生成32位UUID，由于UUID都为十六进制，所以将UUID分成8组，每4个为一组，
	 * UUID不重复，然后通过模62操作，结果作为索引取出字符，
     * @return
	 */
    public static String generateShortCode() {

	    StringBuffer shortBuffer = new StringBuffer();
	    String uuid = UUID.randomUUID().toString().replace("-", "");

	    for (int i = 0; i < 8; i++) {

		   String str = uuid.substring(i * 4, i * 4 + 4); //将UUID分成8组，每4个为一组，截取UUID段，
		   int x = Integer.parseInt(str, 16); //转为为整数
		   shortBuffer.append(chars[x % 0x3E]); //整数转为62进制

	    }

	    return shortBuffer.toString();
    }

	/**
	 * 批量8位短码生成方法
	 * 每次一次生成1000个供使用，用完再重新生成1000个
	 * 避免每次都生成
	 * @return
	 */
	public static synchronized List<String> getBatchShortCodeList(){

    	   if(shortCodelist==null||shortCodelist.size()==0){

    	   	  shortCodelist=new ArrayList<>();
    	   	  for(int i=0;i<1000;i++){
				 shortCodelist.add(generateShortCode());
			  }

		   }

    	   return shortCodelist;
	}

}
