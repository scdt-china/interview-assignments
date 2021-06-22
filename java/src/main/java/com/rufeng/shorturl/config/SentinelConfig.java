package com.rufeng.shorturl.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.rufeng.shorturl.constant.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qujingguo
 * @version 1.0.0
 * @date 2021/5/28 11:42 上午
 * @description
 */
@Configuration
public class SentinelConfig {
    /**
     * AOP使sentinel注解生效
     *
     * @return SentinelResourceAspect
     */
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }


    @PostConstruct
    public void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<FlowRule>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource(Constants.LONG_TO_SHORT_KEY);
        // set limit qps to 20
        rule1.setCount(20);
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        rule1.setLimitApp("short-url");
        rules.add(rule1);

        FlowRule rule2 = new FlowRule();
        rule2.setResource(Constants.LONG_TO_SHORT_KEY);
        // set limit qps to 20
        rule2.setCount(20);
        rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        rule2.setLimitApp("short-url");
        rules.add(rule2);

        FlowRuleManager.loadRules(rules);
    }
}
