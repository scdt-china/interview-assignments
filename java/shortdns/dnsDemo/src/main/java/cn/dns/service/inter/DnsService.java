package cn.dns.service.inter;


public interface DnsService {
    /**
     * 获取短域名
     * @param longDns
     * @return
     */
    public String getShortDns(String longDns);

    /**
     * 获取长域名
     * @param shortDns
     * @return
     */
    public String getLongDns(String shortDns);


}
