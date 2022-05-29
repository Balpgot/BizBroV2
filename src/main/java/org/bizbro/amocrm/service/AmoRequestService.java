package org.bizbro.amocrm.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.util.json.JSONParser;
import org.bizbro.amocrm.configuration.AmoAPIConfiguration;
import org.bizbro.amocrm.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
public class AmoRequestService {

    private final CloseableHttpClient httpClient;
    private final AmoAPIConfiguration apiConfiguration;
    private final String BASE_URL;
    private final String CONTACT_URI;
    private final String LEAD_URI;
    private final String AUTH_PREFIX;
    private final String AUTH_TOKEN;
    private final String REFRESH_TOKEN;

    @Autowired
    public AmoRequestService(AmoAPIConfiguration apiConfiguration) {
        this.httpClient = HttpClients.createDefault();
        this.apiConfiguration = apiConfiguration;
        this.BASE_URL = apiConfiguration.getProperty("baseUrl");
        this.CONTACT_URI = apiConfiguration.getProperty("contactUri");
        this.LEAD_URI = apiConfiguration.getProperty("leadUri");
        this.AUTH_PREFIX = apiConfiguration.getProperty("authPrefix");
        this.AUTH_TOKEN = apiConfiguration.getAccessToken();
        this.REFRESH_TOKEN = apiConfiguration.getRefreshToken();
    }

    public JSONObject getCompanyById(String id) {
        String url = BASE_URL + CONTACT_URI + id;
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
            throw new AuthenticationException("Tokens refresh required");
        }
        else {
            return true;
        }
    }

    private void refreshAuthenticationTokens() {

    }

}

