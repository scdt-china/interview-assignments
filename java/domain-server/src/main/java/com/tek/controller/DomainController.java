package com.tek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tek.entity.Domain;
import com.tek.req.DomainReq;
import com.tek.rsp.JsonRsp;
import com.tek.service.DomainService;


/**
 * 长短域名转换服务
 * @author Administrator
 *
 */
@RestController
public class DomainController {
	
	@Autowired
	private DomainService domainService;
	
	/**
	 * 长域名转为短域名
	 * @param domainReq
	 * @return
	 */
	@PostMapping("/domain/getShortDomain")
	public JsonRsp getShortDomain(@RequestBody DomainReq domainReq) {
		JsonRsp jsonRsp = JsonRsp.success();
		Domain domain = domainService.getShortDomain(domainReq.getDomain());
		jsonRsp.setData(domain);
		return jsonRsp;
	}
	
	/**
	 * 根据短域名获取长域名
	 * @param domainReq
	 * @return
	 */
	@PostMapping("/domain/getLongDomain")
	public JsonRsp getLongDomain(@RequestBody DomainReq domainReq) {
		JsonRsp jsonRsp = JsonRsp.success();
		Domain domain = domainService.getLongDomain(domainReq.getDomain());
		jsonRsp.setData(domain);
		return jsonRsp;
	}
}
