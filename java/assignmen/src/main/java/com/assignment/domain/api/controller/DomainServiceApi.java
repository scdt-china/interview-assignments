package com.assignment.domain.api.controller;

import com.assignment.domain.api.model.FailureResult;
import com.assignment.domain.api.model.OkResult;
import com.assignment.domain.api.model.RespResult;
import com.assignment.domain.api.service.DomainService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api(tags = "域名管理")
@RestController
@RequestMapping("/domain/v1")
public class DomainServiceApi {
    private DomainService domainService;

    public DomainServiceApi(DomainService domainService) {
        this.domainService = domainService;
    }

    @ApiOperation("读取短域名对应的长域名")
    @PostMapping("/long")
    public RespResult readShortDomain(@RequestBody String shortDomain) {
        Optional<String> longDomainOptional = domainService.read(shortDomain);
        return longDomainOptional.isPresent() ? new OkResult(longDomainOptional.get()) :
                new FailureResult("Not found");
    }

    @ApiOperation("短域名生成存储")
    @PutMapping
    public RespResult saveAndReply(@RequestBody String longDomain) {
        String replyShortDomain = domainService.saveAndReply(longDomain);
        return new OkResult(replyShortDomain);
    }
}
