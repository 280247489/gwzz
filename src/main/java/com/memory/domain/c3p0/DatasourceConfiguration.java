
package com.memory.domain.c3p0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


/**
 * @Auther: cui.Memory
 * @Date: 2018/11/6 0006 10:12
 * @Description:
 */


@Configuration
public class DatasourceConfiguration {
    private final static Logger logger = LoggerFactory.getLogger(DatasourceConfiguration.class);
    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.c3p0")
    public DataSource dataSource() {
        logger.info("com.mchange.v2.c3p0.ComboPooledDataSource.class");
        return DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();
    }
}