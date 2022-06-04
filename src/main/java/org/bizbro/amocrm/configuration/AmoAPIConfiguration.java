package org.bizbro.amocrm.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:api.properties")
public class AmoAPIConfiguration {

    private final Environment env;
    private final String AMO_PREFIX = "amo.";

    @Autowired
    public AmoAPIConfiguration(Environment env) {
        this.env = env;
    }

    public String getProperty(String name) {
        return env.getProperty(AMO_PREFIX + name);
    }
}
