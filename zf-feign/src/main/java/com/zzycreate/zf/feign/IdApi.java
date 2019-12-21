package com.zzycreate.zf.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zzycreate
 * @date 2019/12/19
 */
@FeignClient(value = "leaf", url = "${zf.feign.leaf}")
public interface IdApi {

    /**
     * 获取ID
     *
     * @param key 键值
     * @return ID
     */
    @RequestMapping(value = "/api/segment/get/{key}")
    String getId(@PathVariable("key") String key);

}
