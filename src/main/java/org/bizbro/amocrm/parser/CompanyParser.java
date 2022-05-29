package org.bizbro.amocrm.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.bizbro.amocrm.constants.ContactConstants;
import org.bizbro.amocrm.constants.ParserConstants;
import org.bizbro.amocrm.entity.EntityManager;
import org.bizbro.amocrm.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompanyParser {

    private EntityManager entityManager;
    private Map<String, JSONArray> customFieldsMap;
    private int startYear = 2018;
    private int endYear = 2022;
    private Map<Integer, String> yearToIdMap = Map.ofEntries(
            Map.entry(2018,"830629"),
            Map.entry(2019,"830631"),
            Map.entry(2020,"830633"),
            Map.entry(2021,"830635"),
            Map.entry(2022,"830637")
    );

    @Autowired
    public CompanyParser(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.customFieldsMap = new HashMap<>();
    }

    public Company parseCompany(JSONObject companyJSON) {
        fillCompanyParameters(companyJSON);
        Company company = Company.builder()
                .id(companyJSON.getLong(ContactConstants.ID))
                .lead(null)
                .mobile(getStringValue(ContactConstants.PHONE_ID))
                .email(getStringValue(ContactConstants.EMAIL_ID))
                .form(getStringValue(ContactConstants.FORM_ID))
                .companyName(getStringValue(ContactConstants.NAME_ID))
                .inn(getStringValue(ContactConstants.INN_ID))
                .city(getStringValue(ContactConstants.CITY_ID))
                .sno(getStringValue(ContactConstants.SNO_ID))
                .registrationDate(getLongValue(ContactConstants.REGISTRATION_DATE_ID))
                .hasOborot(getStringToBoolean(ContactConstants.OBOROT_ID))
                .address(getStringValue(ContactConstants.ADDRESS_ID))
                .oformlenie(getStringValue(ContactConstants.OFORMLENIE_ID))
                .keepAddress(getStringToBoolean(ContactConstants.KEEP_ADDRESS_ID))
                .addressNote(getStringValue(ContactConstants.ADDRESS_NOTE_ID))
                .nalog(getStringValue(ContactConstants.NALOG_ID))
                .report(getStringValue(ContactConstants.OTCHET_ID))
                .ecp(getStringValue(ContactConstants.ECP_ID))
                .founders(getIntegerValue(ContactConstants.FOUNDERS_COUNT_ID))
                .workersCount(getIntegerValue(ContactConstants.WORKERS_COUNT_ID))
                .elimination(getStringToBoolean(ContactConstants.ELIMINATION_ID))
                .price(getIntegerValue(ContactConstants.PRICE_ID))
                .debt(getStringToBoolean(ContactConstants.DEBT_ID))
                .marriage(getStringToBoolean(ContactConstants.MARRIAGE_ID))
                .owner(getStringValue(ContactConstants.IS_OWNER_ID))
                .aim(getStringValue(ContactConstants.AIM_ID))
                .comment(getStringValue(ContactConstants.COMMENT_ID))
                .post(getStringValue(ContactConstants.POST_ID))
                .odinC(getBooleanValue(ContactConstants.ODIN_C_ID))
                .dopScheta(getBooleanValue(ContactConstants.DOP_SCHETA_ID))
                .bank(parseBankSet())
                .cpo(parseCpo())
                .license(parseLicense())
                .okved(parseOkved())
                .goszakaz(parseGoszakaz())
                .build();
        company.setOboroty(parseOboroty(company));
        return company;
    }

    private void fillCompanyParameters(JSONObject companyJSON) {
        JSONArray customFields = companyJSON.getJSONArray(ParserConstants.JSON.CUSTOM_FIELDS_DATA);
        for (Object customFieldObj : customFields) {
            JSONObject customField = (JSONObject) customFieldObj;
            customFieldsMap.put(
                customField.getString(ParserConstants.JSON.FIELD_ID),
                customField.getJSONArray(ParserConstants.JSON.CUSTOM_FIELD_VALUES)
            );
        }
    }

    private Set<Bank> parseBankSet() {
        JSONArray values = customFieldsMap
                .get(ContactConstants.BANK_ACCOUNTS_ID);
        Set<Bank> bankSet = new HashSet<>();
        for (Object bankObj:values) {
            JSONObject bankJSON = (JSONObject) bankObj;
            Long bankId = bankJSON.getLong(ParserConstants.JSON.ENUM_ID);
            Optional<Bank> bankIfExists =
                    entityManager
                            .getBankRepository()
                            .findById(bankId);
            if(bankIfExists.isPresent()){
                bankSet.add(bankIfExists.get());
            }
            else {
                Bank bank = new Bank(bankId, bankJSON);
                bankSet.add(bank);
            }
        }
        return bankSet;
    }

    private Set<Cpo> parseCpo() {
        JSONArray values = customFieldsMap
                .get(ContactConstants.CPO_ID);
        Set<Cpo> cpoSet = new HashSet<>();
        for (Object cpoObj:values) {
            JSONObject cpoJSON = (JSONObject) cpoObj;
            Long cpoId = cpoJSON.getLong(ParserConstants.JSON.ENUM_ID);
            Optional<Cpo> cpoIfExists =
                    entityManager
                            .getCpoRepository()
                            .findById(cpoId);
            if(cpoIfExists.isPresent()){
                cpoSet.add(cpoIfExists.get());
            }
            else {
                Cpo cpo = new Cpo(cpoId, cpoJSON);
                cpoSet.add(cpo);
            }
        }
        return cpoSet;
    }

    private Set<License> parseLicense() {
        JSONArray values = customFieldsMap
                .get(ContactConstants.LICENCE_ID);
        Set<License> licenseSet = new HashSet<>();
        for (Object licenseObj:values) {
            JSONObject licenseJSON = (JSONObject) licenseObj;
            Long licenseId = licenseJSON.getLong(ParserConstants.JSON.ENUM_ID);
            Optional<License> licenseIfExists =
                    entityManager
                            .getLicenseRepository()
                            .findById(licenseId);
            if(licenseIfExists.isPresent()){
                licenseSet.add(licenseIfExists.get());
            }
            else {
                License license = new License(licenseId, licenseJSON);
                licenseSet.add(license);
            }
        }
        return licenseSet;
    }

    private Set<Okved> parseOkved() {
        JSONArray values = customFieldsMap
                .get(ContactConstants.OKVED_ID);
        Set<Okved> okvedSet = new HashSet<>();
        for (Object okvedObj:values) {
            JSONObject okvedJSON = (JSONObject) okvedObj;
            Okved okved = new Okved(okvedJSON);
            Optional<Okved> okvedIfExists =
                    entityManager
                    .getOkvedRepository()
                    .findById(okved.getId());
            if(okvedIfExists.isPresent()){
                okvedSet.add(okvedIfExists.get());
            }
            else {
                okvedSet.add(okved);
            }
        }
        return okvedSet;
    }

    private Set<Goszakaz> parseGoszakaz() {
        JSONArray values = customFieldsMap
                .get(ContactConstants.GOSZAKAZ_ID);
        Set<Goszakaz> goszakazSet = new HashSet<>();
        for (Object goszakazObj:values) {
            JSONObject goszakazJSON = (JSONObject) goszakazObj;
            Long goszakazId = goszakazJSON.getLong(ParserConstants.JSON.ENUM_ID);
            Optional<Goszakaz> goszakazIfExists =
                    entityManager
                            .getGoszakazRepository()
                            .findById(goszakazId);
            if(goszakazIfExists.isPresent()){
                goszakazSet.add(goszakazIfExists.get());
            }
            else {
                Goszakaz goszakaz = new Goszakaz(goszakazId, goszakazJSON);
                goszakazSet.add(goszakaz);
            }
        }
        return goszakazSet;
    }

    private Set<Oborot> parseOboroty(Company company) {
        Integer currentYear = startYear;
        Set<Oborot> oboroty = new HashSet<>();
        while (currentYear<=endYear) {
            JSONArray value = customFieldsMap
                    .get(yearToIdMap.get(currentYear));
            if(value.size()>0) {
                String oborotAmountString =
                        value
                                .getJSONObject(0)
                                .getString(ParserConstants.JSON.VALUE)
                                .replaceAll("\\.", "");
                Oborot oborot = new Oborot(
                        company,
                        currentYear,
                        Integer.parseInt(oborotAmountString)
                );
                oboroty.add(oborot);
            }
            currentYear++;
        }
        return oboroty;
    }

    private JSONObject getJSONValue(String id) {
        JSONArray values = customFieldsMap.get(id);
        if(values==null) {
            return null;
        }
        return (JSONObject) values.get(0);
    }

    private String getStringValue(String id) {
        JSONObject jsonObject = getJSONValue(id);
        if(jsonObject==null) {
            return null;
        }
        return jsonObject.getString(ParserConstants.JSON.VALUE);
    }

    private Boolean getBooleanValue(String id) {
        JSONObject jsonObject = getJSONValue(id);
        if(jsonObject==null) {
            return null;
        }
        return jsonObject.getBoolean(ParserConstants.JSON.VALUE);
    }

    private Boolean getStringToBoolean(String id) {
        JSONObject jsonObject = getJSONValue(id);
        if(jsonObject==null) {
            return null;
        }
        String value = jsonObject.getString(ParserConstants.JSON.VALUE);
        Set<String> trueValues = Set.of("Да","В браке","В стадии ликвидации");
        if(trueValues.contains(value)){
            return true;
        }
        else return false;
    }

    private Integer getIntegerValue(String id) {
        JSONObject jsonObject = getJSONValue(id);
        if(jsonObject==null) {
            return null;
        }
        return jsonObject.getInteger(ParserConstants.JSON.VALUE);
    }

    private Long getLongValue(String id) {
        JSONObject jsonObject = getJSONValue(id);
        if(jsonObject==null) {
            return null;
        }
        return jsonObject.getLong(ParserConstants.JSON.VALUE);
    }
}
