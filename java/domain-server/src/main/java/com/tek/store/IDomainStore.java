package com.tek.store;

public interface IDomainStore {
	
	public void addDomain(String domain,Long number);
	
	public String getDomainByNumber(Long number);
	
	public Long getNmberByDomain(String domain);
}
