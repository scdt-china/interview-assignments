package com.tek.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tek.entity.Domain;
import com.tek.store.DomainStoreImpl;
import com.tek.util.ConvertUtil;
import com.tek.util.NumberGeneratorUtil;

@Service
public class DomainService {
	
	
	@Autowired
	private DomainStoreImpl domainStore;
	
	
	public Domain getShortDomain(String longDoamin) {
		
		Long number = domainStore.getNmberByDomain(longDoamin);
		if(Objects.isNull(number)) {
			number = NumberGeneratorUtil.generatorNumber();
		}
		String shortDomain = ConvertUtil.encode(number);
		domainStore.addDomain(longDoamin, number);
		return new Domain(shortDomain);
	}
	
	public Domain getLongDomain(String shortDomain) {
		
		Long number = ConvertUtil.decode(shortDomain);
		String longDomain = domainStore.getDomainByNumber(number);
		return new Domain(longDomain);
	}
}
