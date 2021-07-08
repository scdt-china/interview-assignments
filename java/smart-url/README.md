Change short URL and Long URL Java Code Coverage Library
=================================

原理:
 * 1,url以post方法传递到了http://dwz.cn/create.php的url参数内;
 * 2,dwz.cn先检查是否符合转化的要求
 * 3,dwz.cn将url的参数提取为5位字符作为摘要,内部建立了长url和http://dwz.cn/摘要 的映射
 * 4,访问的是http://dwz.cn/摘要,网站获取对应的地址,然后重新跳转到输入地址
 * 5,生成短连接:
 *  {"tinyurl":"http:\/\/dwz.cn\/摘要","status":0,"longurl:"http://..."}---成功 . \/\/是//的js逃逸机制,以防变成正则表达式
 *  {"status":-1,"err_msg":"网址不能为空","longurl":""}---空
 *  {"status":-1,"err_msg":"您输入的网址不存在，请重新输入","longurl":"http://大赛分为"}---大赛分为
 *  {"status":-1,"err_msg":"您输入的网址可能有安全隐患，请重新输入","longurl":"http://大赛分为"}---http://大赛分为
 * 6,查询长连接:
 *  {status:0,longurl:http://weibo.com/lucd1990/profile}
 *  {status:-2,err_msg:您输入的短网址不存在, 请重新输入!,longurl:}
 * 7,自定义短连接:
 *  {"tinyurl":"http:\/\/dwz.cn\/java_comp","status":0,"longurl":"http://www.blogjava.net/jjshcc/archive/2011/04/12/348132.html","err_msg":""}
 *  {"status":-1,"err_msg":"对不起，自定义字符只能包含字母、数字和破折号。请重新输入","longurl":"http://www.blogjava.net/jjshcc/archive/2011/04/12/348132.html"}
 *  {"status":-1,"err_msg":"短网址过长，最长不要超过20个字符","longurl":""}
 *  {"err_msg":"对不起, 该网址已经存在","status":-1,"longurl":""}
 *
 燃果—马瑞——高级java开发
-------------------------------------------------------------------------
