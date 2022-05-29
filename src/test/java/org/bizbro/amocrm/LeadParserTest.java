package org.bizbro.amocrm;

import com.alibaba.fastjson.JSON;
import org.bizbro.amocrm.model.Lead;
import org.bizbro.amocrm.model.Tag;
import org.bizbro.amocrm.parser.LeadParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class LeadParserTest {

    @Autowired
    LeadParser leadParser;
    String leadResponse = "{\"id\":19030707," +
            "\"name\":\"\\\"ПОЖСТРОЙКОМПЛЕКТ\\\"\"," +
            "\"price\":830000," +
            "\"responsible_user_id\":6570292," +
            "\"group_id\":0," +
            "\"status_id\":37851406," +
            "\"pipeline_id\":3804802," +
            "\"loss_reason_id\":null," +
            "\"created_by\":6570292," +
            "\"updated_by\":6570292," +
            "\"created_at\":1650889284," +
            "\"updated_at\":1650889306," +
            "\"closed_at\":null," +
            "\"closest_task_at\":null," +
            "\"is_deleted\":false," +
            "\"custom_fields_values\":null," +
            "\"score\":null," +
            "\"account_id\":29178583," +
            "\"_links\":{\"self\":{\"href\":\"https://avinichenko.amocrm.ru/api/v4/leads/19030707\"}}," +
            "\"_embedded\":{\"tags\":[{\"id\":12763,\"name\":\"рассылка\"},{\"id\":83057,\"name\":\"Добавлен\"}]," +
            "\"companies\":[{\"id\":20662517,\"_links\":{\"self\":{\"href\":\"https://avinichenko.amocrm.ru/api/v4/companies/20662517\"}}}]," +
            "\"contacts\":[{\"id\":20662519,\"is_main\":true,\"_links\":{\"self\":{\"href\":\"https://avinichenko.amocrm.ru/api/v4/contacts/20662519\"}}}]}}";

    @Test
    void parse_lead_test(){
        Set<Tag> tagSet = Set.of(new Tag(12763L,"рассылка"),new Tag(83057L,"Добавлен"));
        Lead leadExample = Lead.builder()
                .ID(Long.parseLong(("19030707")))
                .NAME("\"ПОЖСТРОЙКОМПЛЕКТ\"")
                .BUDGET(Integer.parseInt("830000"))
                .CONTACT_ID(Long.parseLong("20662519"))
                .TAGS(tagSet)
                .VORONKA(Integer.parseInt("37851406"))
                .build();
        Lead lead = leadParser.parseLead(JSON.parseObject(leadResponse));
        Assertions.assertEquals(leadExample,lead);
    }
}
