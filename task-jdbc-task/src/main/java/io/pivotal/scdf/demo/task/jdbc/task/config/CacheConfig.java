package io.pivotal.scdf.demo.task.jdbc.task.config;

import io.pivotal.scdf.demo.task.jdbc.task.common.JdbcGemfireTaskProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;
import com.gemstone.gemfire.cache.client.Pool;
import com.gemstone.gemfire.pdx.ReflectionBasedAutoSerializer;


@Configuration
@EnableConfigurationProperties({JdbcGemfireTaskProperties.class})
public class CacheConfig {


    private static final Logger LOG = LoggerFactory.getLogger(CacheConfig.class);

    @Bean
    public ClientCache createCache(JdbcGemfireTaskProperties props) {
        LOG.info("Initializing ClientCache..");
        String pdxSerializedClasses = "io.pivotal.scdf.demo.task.common.model.*";
        String locator = props.locators;
        Boolean readSerializedFlag = false;

        ClientCacheFactory ccf = new ClientCacheFactory();

        String[] sa1 = locator.split(",");
        for (String st : sa1) {
            String[] sat = st.split(":");
            String host = sat[0];
            int port = sat.length > 1 ? Integer.parseInt(sat[1]) : 10334;
            LOG.info("Adding Locator to pool : host={}, port={}", host, port);

            ccf.addPoolLocator(host, port);

        }

        ccf.setPdxReadSerialized(readSerializedFlag);
        ccf.setPdxSerializer(new ReflectionBasedAutoSerializer(pdxSerializedClasses));
        LOG.info("Created ClientCache with properties[Locators:{}, PdxSerializer:{}, ReadSerialized:{}]", locator, pdxSerializedClasses, readSerializedFlag);
        return ccf.create();
    }

    @Bean
    public Pool createPool(ClientCache cache) {
        LOG.info("creating pool");
        return cache.getDefaultPool();
    }
}