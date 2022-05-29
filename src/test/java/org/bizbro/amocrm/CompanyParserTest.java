package org.bizbro.amocrm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.annotation.Before;
import org.bizbro.amocrm.constants.ContactConstants;
import org.bizbro.amocrm.model.*;
import org.bizbro.amocrm.parser.CompanyParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@SpringBootTest
public class CompanyParserTest {

    @Autowired
    CompanyParser companyParser;

    @Test
    void parseCompanyTest() {
        try {
            String companyResponse = Files.readString(Path.of("C:\\Users\\cinil\\Documents\\GitHub\\bizbroamocrm\\testdata\\contactAsString"));
            JSONObject object = JSON.parseObject(companyResponse);
            Company parsedCompany = companyParser.parseCompany(object);
            Company example = createExampleCompany();
            Assertions.assertEquals(parsedCompany,example);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private Company createExampleCompany(){
        Company company = Company.builder()
                .id(20662519L)
                .lead(null)
                .mobile("89295888813")
                .email("notgogolev@yandex.ru")
                .form("ООО")
                .companyName("\"ПОЖСТРОЙКОМПЛЕКТ\"")
                .inn("9701037020")
                .city("МСК")
                .sno("ОСН")
                .registrationDate(1459890000L)
                .hasOborot(true)
                .address("Офис")
                .oformlenie("С переоформлением")
                .keepAddress(true)
                .addressNote("Адрес действующий")
                .nalog("Инспекция ФНС России № 1 по г.Москве")
                .report("Сдана")
                .ecp("Да")
                .founders(1)
                .workersCount(1)
                .elimination(false)
                .price(630000)
                .debt(false)
                .marriage(false)
                .owner("Собственник")
                .aim(null)
                .comment("3 госконтракта на сумму 2 952 057 руб.\nадрес: 35 тыс 11 мес.")
                .post(null)
                .odinC(true)
                .dopScheta(true)
                .bank(parseBankSet())
                .cpo(parseCpo())
                .license(parseLicense())
                .okved(parseOkved())
                .goszakaz(parseGoszakaz())
                .build();
        company.setOboroty(parseOboroty(company));
        return company;
    }

    private Set<Bank> parseBankSet() {
        return Set.of(new Bank(422847L,"Сбербанк"));
    }

    private Set<Cpo> parseCpo() {
        return Set.of(new Cpo(335957L,"Нет"));
    }

    private Set<License> parseLicense() {
        return Set.of(new License(481785L,"МЧС"));
    }

    private Set<Okved> parseOkved() {
        return Set.of(
                new Okved("43.21","Производство электромонтажных работ (43.21)","43")
        );
    }

    private Set<Goszakaz> parseGoszakaz() {
        return Set.of(new Goszakaz(550685L,"44 фз"));
    }

    private Set<Oborot> parseOboroty(Company company) {
        Oborot oborot2018 = new Oborot(company,2018,552000);
        Oborot oborot2019 = new Oborot(company,2019,3200000);
        Oborot oborot2020 = new Oborot(company,2020,2400000);
        Oborot oborot2021 = new Oborot(company,2021,1200000);
        Oborot oborot2022 = new Oborot(company,2022,500000);
        return Set.of(oborot2018,oborot2022,oborot2020,oborot2021,oborot2019);

    }
}
