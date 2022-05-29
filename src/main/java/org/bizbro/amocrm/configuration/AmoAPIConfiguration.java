package org.bizbro.amocrm.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ConfigurationProperties(prefix = "amo")
@PropertySource("classpath:api.properties")
public class AmoAPIConfiguration {

    private final String ACCESS_TOKEN_KEY = "access_token";
    private final String REFRESH_TOKEN_KEY = "refresh_token";

    private final Environment env;
    private final AmoTokensRepository tokensRepository;

    @Autowired
    public AmoAPIConfiguration(Environment env, AmoTokensRepository tokensRepository) {
        this.env = env;
        this.tokensRepository = tokensRepository;
    }

    public String getProperty(String name) {
        return env.getProperty(name);
    }

    public String getAccessToken() {
        return tokensRepository.getById(ACCESS_TOKEN_KEY).getToken();
    }

    public String getRefreshToken() {
        return tokensRepository.getById(REFRESH_TOKEN_KEY).getToken();
    }

    public void updateTokens(String accessToken, String refreshToken) {
        tokensRepository.save(new AmoToken(ACCESS_TOKEN_KEY, accessToken));
        tokensRepository.save(new AmoToken(REFRESH_TOKEN_KEY, refreshToken));
    }
}
