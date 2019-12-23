package com.zzycreate.zf.config;

import feign.Client;
import feign.Logger;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * feign ssl
 *
 * @author zzycreate
 * @date 15:29 2019/9/25
 **/
@Configuration
public class BaseFeignConfig {

    private final ObjectFactory<HttpMessageConverters> messageConverters;

    @Autowired
    public BaseFeignConfig(ObjectFactory<HttpMessageConverters> messageConverters) {
        this.messageConverters = messageConverters;
    }

    @Bean
    @Scope("prototype")
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(this.messageConverters));
    }

    /**
     * feign 日志记录级别
     * NONE：无日志记录（默认）
     * BASIC:基本，只记录请求方法和 url 以及响应状态代码和执行时间。
     * HEADERS: 头，记录基本信息以及请求和响应头。
     * FULL: 完整，记录请求和响应的头、正文和元数据。
     *
     * @return Logger.Level
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * SSL 配置
     *
     * @return Client
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws KeyManagementException   KeyManagementException
     */
    @Bean
    @ConditionalOnMissingBean
    public Client feignClient() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext ctx = SSLContext.getInstance("SSL");
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        ctx.init(null, new TrustManager[]{tm}, null);
        return new Client.Default(ctx.getSocketFactory(),
                (hostname, session) -> true);
    }

    @Bean
    public HttpMessageConverter<?> zfMessageConverter() {
        return new ZfMessageConverter();
    }

    /**
     * 自定义 MessageConverter
     */
    static class ZfMessageConverter extends MappingJackson2HttpMessageConverter {

        public ZfMessageConverter() {
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_PLAIN);
            mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
            mediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
            setSupportedMediaTypes(mediaTypes);
        }
    }

}