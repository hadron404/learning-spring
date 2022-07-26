package com.example.quickstartfeign.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * .
 *
 * @author zhouqiang
 * @since 2022/7/19
 */
@FeignClient(url = "http://service-two")
public interface TwoService {
}
