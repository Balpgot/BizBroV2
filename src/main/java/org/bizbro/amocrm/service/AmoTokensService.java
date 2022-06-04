package org.bizbro.amocrm.service;

import org.bizbro.amocrm.model.AmoToken;
import org.bizbro.amocrm.repository.AmoTokensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmoTokensService {

    private final AmoTokensRepository tokensRepository;
    private final String ACCESS_TOKEN_KEY = "access_token";
    private final String REFRESH_TOKEN_KEY = "refresh_token";

    @Autowired
    public AmoTokensService(AmoTokensRepository tokensRepository) {
        this.tokensRepository = tokensRepository;
    }

    public String getAccessToken() {
        for (AmoToken token:tokensRepository.findAll()) {
            if (ACCESS_TOKEN_KEY.equals(token.getId())){
                return token.getToken();
            }
        }
        return "";
    }

    public String getRefreshToken() {
        for (AmoToken token:tokensRepository.findAll()) {
            if (REFRESH_TOKEN_KEY.equals(token.getId())){
                return token.getToken();
            }
        }
        return "";
    }

    public void updateTokens(String accessToken, String refreshToken) {
        tokensRepository.save(new AmoToken(ACCESS_TOKEN_KEY, accessToken));
        tokensRepository.save(new AmoToken(REFRESH_TOKEN_KEY, refreshToken));
    }


}
