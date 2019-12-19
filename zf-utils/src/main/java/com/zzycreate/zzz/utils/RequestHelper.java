package com.zzycreate.zzz.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Request 工具类
 *
 * @author dazzlzy
 * @date 2018/4/1
 */
@Slf4j
public class RequestHelper {

    private static final String UNKNOWN = "unknown";
    private static final String DEFAULT_SEPARATOR = ",";
    private static final int DEFAULT_IP_LENGTH = 15;

    private static final String HEADER_X_FORWARDED_FOR = "x-forwarded-for";
    private static final String HEADER_PROXY_CLIENT_IP = "Proxy-Client-IP";
    private static final String HEADER_WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    private static final String HEADER_CDN_SRC_IP = "Cdn-Src-Ip";
    private static final String HEADER_HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    private static final String HEADER_HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

    private static final String LOCALHOST = "127.0.0.1";
    private static final String ANYHOST = "0.0.0.0";

    /**
     * 从http request中获取真实IP
     *
     * @param request HttpServletRequest
     * @return 真实IP
     */
    public static String parseIp(HttpServletRequest request) {
        String ip = request.getHeader(HEADER_X_FORWARDED_FOR);
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HEADER_PROXY_CLIENT_IP);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            // 代理IP
            ip = request.getHeader(HEADER_WL_PROXY_CLIENT_IP);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            // 网宿cdn的真实ip
            ip = request.getHeader(HEADER_CDN_SRC_IP);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            // 蓝讯cdn的真实ip
            ip = request.getHeader(HEADER_HTTP_CLIENT_IP);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HEADER_HTTP_X_FORWARDED_FOR);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            // 真实IP
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户端ip
        if (ip != null && ip.length() > DEFAULT_IP_LENGTH && ip.contains(DEFAULT_SEPARATOR)) {
            String[] ips = ip.split(DEFAULT_SEPARATOR);
            for (String strIp : ips) {
                if (!UNKNOWN.equalsIgnoreCase(strIp)) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 获取当前服务器的本地IP地址
     *
     * @return 服务器本地IP地址
     */
    public static InetAddress getServerLocalAddress() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Exception e) {
            log.warn("Failed to retrieving ip address, " + e.getMessage(), e);
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        while (addresses.hasMoreElements()) {
                            try {
                                InetAddress address = addresses.nextElement();
                                if (isValidAddress(address)) {
                                    return address;
                                }
                            } catch (Exception e) {
                                log.warn("Failed to retrieving ip address, " + e.getMessage(), e);
                            }
                        }
                    } catch (Exception e) {
                        log.warn("Failed to retrieving ip address, " + e.getMessage(), e);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Failed to retrieving ip address, " + e.getMessage(), e);
        }
        log.error("Could not get local host ip address, will use 127.0.0.1 instead.");
        return localAddress;
    }

    /**
     * 判断 InetAddress 对象是否是正确的IP地址
     *
     * @param address InetAddress
     * @return 是否是正确的IP地址
     */
    private static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress()) {
            return false;
        }
        String name = address.getHostAddress();
        return (name != null
                && !ANYHOST.equals(name)
                && !LOCALHOST.equals(name)
                && ValidateHelper.isIpAddress(name));
    }

}
