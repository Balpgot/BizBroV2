package org.bizbro.amocrm.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.bizbro.amocrm.configuration.AmoAPIConfiguration;
import org.bizbro.amocrm.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Set;

@Service
public class AmoRequestService {

    private final CloseableHttpClient httpClient;
    private final AmoTokensService amoTokensService;
    private final String BASE_URL;
    private final String CONTACT_URI;
    private final String TOKENS_URI;
    private final String LEAD_URI;
    private final String AUTH_PREFIX;
    private final String AUTH_TOKEN;
    private final String REFRESH_TOKEN;
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private final String GRANT_TYPE;
    private final String REDIRECT_URL;

    @Autowired
    public AmoRequestService(AmoAPIConfiguration apiConfiguration, AmoTokensService amoTokensService) {
        this.httpClient = HttpClients.createDefault();
        this.amoTokensService = amoTokensService;
        this.BASE_URL = apiConfiguration.getProperty("baseUrl");
        this.CONTACT_URI = apiConfiguration.getProperty("contactUri");
        this.TOKENS_URI = apiConfiguration.getProperty("tokensUri");
        this.LEAD_URI = apiConfiguration.getProperty("leadUri");
        this.AUTH_PREFIX = apiConfiguration.getProperty("authPrefix");
        this.CLIENT_ID = apiConfiguration.getProperty("clientId");
        this.CLIENT_SECRET = apiConfiguration.getProperty("clientSecret");
        this.GRANT_TYPE = apiConfiguration.getProperty("grantType");
        this.REDIRECT_URL = apiConfiguration.getProperty("redirectUrl");
        this.AUTH_TOKEN = amoTokensService.getAccessToken();
        this.REFRESH_TOKEN = amoTokensService.getRefreshToken();
    }

    public JSONObject getCompanyById(String id) {
        String url = BASE_URL + CONTACT_URI + id;
        HttpUriRequest request = createGetRequest(url);
        CloseableHttpResponse response = executeRequest(request);
        return Parser.getJSONObjectFromResponse(response);
    }

    public JSONObject getLeadNotes(String id) {
        String url = BASE_URL + LEAD_URI + id + "/notes";
        HttpUriRequest request = createGetRequest(url);
        CloseableHttpResponse response = executeRequest(request);
        return Parser.getJSONObjectFromResponse(response);
    }

    public JSONObject getLeadById(String id) {
        String url = BASE_URL + LEAD_URI + id;
        HttpUriRequest request =
                createGetRequestWithParameters(url,
                Set.of(new BasicNameValuePair(
                     "with",
                     "contacts"
                )));
        CloseableHttpResponse response = executeRequest(request);
        return Parser.getJSONObjectFromResponse(response);
    }

    private HttpUriRequest createGetRequest(String url) {
        RequestBuilder requestBuilder = RequestBuilder
                .get()
                .setUri(url)
                .addHeader(HttpHeaders.AUTHORIZATION, AUTH_PREFIX + " " + AUTH_TOKEN);
        return requestBuilder.build();
    }

    private HttpUriRequest createPostRequest(String url, String body, boolean authEnabled) {
        try {
            RequestBuilder requestBuilder = RequestBuilder
                    .post()
                    .setUri(url);
            if(authEnabled) {
                requestBuilder.addHeader(HttpHeaders.AUTHORIZATION, AUTH_PREFIX + " " + AUTH_TOKEN);
            }
            requestBuilder.setEntity(new StringEntity(body));
            return requestBuilder.build();
        }
        catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private String createBodyForTokensRefresh() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("client_id", CLIENT_ID);
        requestBody.put("client_secret", CLIENT_SECRET);
        requestBody.put("grant_type", GRANT_TYPE);
        requestBody.put("refresh_token", REFRESH_TOKEN);
        requestBody.put("redirect_uri", REDIRECT_URL);
        return requestBody.toJSONString();
    }

    private HttpUriRequest createGetRequestWithParameters(String url, Set<NameValuePair> parameters) {
        RequestBuilder requestBuilder = RequestBuilder
                .get()
                .setUri(url)
                .addHeader(HttpHeaders.AUTHORIZATION, AUTH_PREFIX + " " + AUTH_TOKEN);
        for (NameValuePair parameter:parameters) {
            requestBuilder.addParameter(parameter);
        }
        return requestBuilder.build();
    }

    private CloseableHttpResponse executeRequest(HttpUriRequest request) {
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            if(isResponseStatusOK(response)) {
                return response;
            }
        }
        catch (AuthenticationException authEx) {
            executeRequest(request);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

    private boolean isResponseStatusOK(CloseableHttpResponse response) throws AuthenticationException {
        StatusLine statusLine = response.getStatusLine();
        if(statusLine.getStatusCode()==HttpStatus.UNAUTHORIZED.value()) {
            refreshAuthenticationTokens();
            throw new AuthenticationException("Tokens refresh required");
        }
        else {
            return true;
        }
    }

    private void refreshAuthenticationTokens() {
        String tokenRefreshUrl = BASE_URL + TOKENS_URI;
        HttpUriRequest request = createPostRequest(
                tokenRefreshUrl,
                createBodyForTokensRefresh(),
                false);
        //CloseableHttpResponse response = executeRequest(request);
       // JSONObject responseJSON = Parser.getJSONObjectFromResponse(response);
       // String accessToken = responseJSON.getString(ParserConstants.Tokens.ACCESS_TOKEN);
       // String refreshToken = responseJSON.getString(ParserConstants.Tokens.REFRESH_TOKEN);
       // this.amoTokensService.updateTokens(accessToken, refreshToken);
    }

}

