package org.bizbro.amocrm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.bizbro.amocrm.model.Company;
import org.bizbro.amocrm.model.Lead;
import org.bizbro.amocrm.parser.CompanyParser;
import org.bizbro.amocrm.parser.LeadParser;
import org.bizbro.amocrm.service.AmoRequestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest
public class AmoRequestServiceTest {

    AmoRequestService requestService;
    CompanyParser companyParser;
    LeadParser leadParser;

    @Autowired
    public AmoRequestServiceTest(AmoRequestService requestService, CompanyParser companyParser, LeadParser leadParser) {
        this.requestService = requestService;
        this.companyParser = companyParser;
        this.leadParser = leadParser;
    }

    @Test
    public void testGetCompanyResult(){
        try {
            JSONObject response = requestService.getCompanyById("20662519");
            Company responseCompany = companyParser.parseCompany(response);
            String responseExample = Files.readString(Path.of("C:\\Users\\cinil\\Documents\\GitHub\\bizbroamocrm\\testdata\\contactAsString"));
            JSONObject responseExampleJSON = JSON.parseObject(responseExample);
            Company exampleCompany = companyParser.parseCompany(responseExampleJSON);
            Assertions.assertEquals(responseCompany,exampleCompany);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void testGetLeadResult(){
        try {
            JSONObject response = requestService.getLeadById("19030707");
            Lead responseLead = leadParser.parseLead(response);
            String responseExample = Files.readString(Path.of("C:\\Users\\cinil\\Documents\\GitHub\\bizbroamocrm\\testdata\\leadAsString"));
            JSONObject responseExampleJSON = JSON.parseObject(responseExample);
            Lead exampleLead = leadParser.parseLead(responseExampleJSON);
            Assertions.assertEquals(responseLead, exampleLead);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void testGetLeadNotes(){
        System.out.println(requestService.getLeadNotes("19030707"));
    }

}
