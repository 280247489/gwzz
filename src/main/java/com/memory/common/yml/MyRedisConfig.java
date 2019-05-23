package com.memory.common.yml;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author INS6+
 * @date 2019/5/15 17:11
 */

/**
 * spring:
 *   redis:
 *     host: 127.0.0.1
 *     port: 6379
 *     password:
 *     # Redis数据库索引（默认为0）
 *     database: 0
 *     # 连接超时时间（毫秒）
 *     timeout: 5000
 *     jedis:
 *       pool:
 *         # 连接池最大连接数（使用负值表示没有限制）
 *         max-active: 100
 *         # 连接池最大阻塞等待时间（使用负值表示没有限制）
 *         max-wait: 1000
 *         # 连接池中的最大空闲连接
 *         max-idle: 100
 *         # 连接池中的最小空闲连接
 *         min-idle: 10
 *         blockWhenExhausted: true
 *
 */

@Component
@ConfigurationProperties(prefix = "spring.redis")
public class MyRedisConfig {

    private String host;
    private Integer port;
    private String password;
    private Integer database;
    private Integer timeout;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
