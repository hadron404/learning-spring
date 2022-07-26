package com.example.quickstartfeign.service;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * .
 *
 * @author zhouqiang
 * @since 2022/7/19
 */
@FeignClient(name = "service-three")
@Headers({})
public interface ThreeService {
}
