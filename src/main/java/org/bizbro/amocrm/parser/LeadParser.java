package org.bizbro.amocrm.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.NoArgsConstructor;
import org.bizbro.amocrm.constants.ParserConstants;
import org.bizbro.amocrm.model.Lead;
import org.bizbro.amocrm.model.Tag;
import org.bizbro.amocrm.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@NoArgsConstructor
public class LeadParser {

    private TagRepository tagRepository;

    @Autowired
    public LeadParser(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Lead parseLead(JSONObject leadJSON) {
        JSONObject embeddedData = leadJSON.getJSONObject(ParserConstants.JSON.EMBEDDED_DATA);
        JSONArray contacts = embeddedData.getJSONArray(ParserConstants.Lead.CONTACTS);
        JSONArray tags = embeddedData.getJSONArray(ParserConstants.Lead.TAGS);
        Long mainContact = parseMainContact(contacts);
        Set<Tag> tagSet = parseTags(tags);
        return Lead.builder()
                .ID(leadJSON.getLong(ParserConstants.Lead.ID))
                .BUDGET(leadJSON.getInteger(ParserConstants.Lead.PRICE))
                .NAME(leadJSON.getString(ParserConstants.Lead.NAME))
                .CONTACT_ID(mainContact)
                .TAGS(tagSet)
                .VORONKA(leadJSON.getInteger(ParserConstants.Lead.VORONKA))
                .build();
    }

    private Set<Tag> parseTags (JSONArray tags) {
        Set<Tag> tagSet = new HashSet<>();
        for (Object tagObj:tags) {
            JSONObject tagJSON = (JSONObject) tagObj;
            Tag tag = new Tag(
                tagJSON.getLong(ParserConstants.Tag.ID),
                tagJSON.getString(ParserConstants.Tag.NAME)
            );
            addTagToDBIfNeeded(tag);
            tagSet.add(tag);
        }
        return tagSet;
    }

    private void addTagToDBIfNeeded (Tag tag){
        if(!tagRepository.existsById(tag.getId())){
            tagRepository.save(tag);
        }
    }

    private Long parseMainContact (JSONArray contacts) {
        for (Object contactObj:contacts){
            JSONObject contact = (JSONObject) contactObj;
            if(contact.getBooleanValue(ParserConstants.Lead.Contact.IS_MAIN)) {
                return contact.getLong(ParserConstants.Lead.Contact.ID);
            }
        }
        return null;
    }
}
