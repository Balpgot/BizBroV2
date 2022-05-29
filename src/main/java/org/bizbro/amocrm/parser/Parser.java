package org.bizbro.amocrm.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.bizbro.amocrm.constants.ParserConstants;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static String getFieldValue(JSONObject fieldObject) {
        String fieldValue = fieldObject.getString(ParserConstants.JSON.VALUE);
        return fieldValue;
    }

    public static String getOkvedGroup(String okvedId) {
        String okvedGroupRegex = "\\d{2}";
        Pattern okvedGroupPattern = Pattern.compile(okvedGroupRegex);
        Matcher okvedGroupMatcher = okvedGroupPattern.matcher(okvedId);
        if(okvedGroupMatcher.find()) {
            return okvedGroupMatcher.group();
        }
        else return "00";
    }

    public static String getOkvedId(String okved) {
        String okvedIdRegex = "(\\d{2}.\\d{2})";
        Pattern okvedIdPattern = Pattern.compile(okvedIdRegex);
        Matcher okvedIdMatcher = okvedIdPattern.matcher(okved);
        if(okvedIdMatcher.find()) {
            return okvedIdMatcher.group();
        }
        else return "00.00";
    }

    public static String parseResponse(CloseableHttpResponse response) {
        try {
            byte [] content = response.getEntity().getContent().readAllBytes();
            return new String(content);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static JSONObject getJSONObjectFromResponse(CloseableHttpResponse response) {
        String responseBody = Parser.parseResponse(response);
        return JSONObject.parseObject(responseBody);

    }

}
