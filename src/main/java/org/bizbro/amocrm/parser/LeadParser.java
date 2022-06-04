package org.bizbro.amocrm.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.NoArgsConstructor;
import org.bizbro.amocrm.constants.ParserConstants;
import org.bizbro.amocrm.model.Lead;
import org.bizbro.amocrm.model.Note;
import org.bizbro.amocrm.model.Tag;
import org.bizbro.amocrm.repository.TagRepository;
import org.bizbro.amocrm.service.AmoRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@NoArgsConstructor
public class LeadParser {

    private TagRepository tagRepository;
    private AmoRequestService requestService;

    @Autowired
    public LeadParser(TagRepository tagRepository, AmoRequestService amoRequestService) {
        this.tagRepository = tagRepository;
        this.requestService = amoRequestService;
    }

    public Lead parseLead(JSONObject leadJSON) {
        JSONObject embeddedData = leadJSON.getJSONObject(ParserConstants.JSON.EMBEDDED_DATA);
        JSONArray contacts = embeddedData.getJSONArray(ParserConstants.Lead.CONTACTS);
        JSONArray tags = embeddedData.getJSONArray(ParserConstants.Lead.TAGS);
        Long mainContact = parseMainContact(contacts);
        Set<Tag> tagSet = parseTags(tags);
        Lead lead = Lead.builder()
                .id(leadJSON.getLong(ParserConstants.Lead.ID))
                .budget(leadJSON.getInteger(ParserConstants.Lead.PRICE))
                .name(leadJSON.getString(ParserConstants.Lead.NAME))
                .contactId(mainContact)
                .tags(tagSet)
                .voronka(leadJSON.getInteger(ParserConstants.Lead.VORONKA))
                .build();
        lead.setNotes(getLeadNotes(lead));
        return lead;
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

    private Set<Note> getLeadNotes(Lead lead){
        JSONObject notesJSONObject =
                requestService.getLeadNotes(String.valueOf(lead.getId()));
        JSONArray notesJSONArray = notesJSONObject
                .getJSONObject(ParserConstants.JSON.EMBEDDED_DATA)
                .getJSONArray(ParserConstants.Lead.NOTES);
        Set<Note> notes = new HashSet<>();
        for (Object noteObj:notesJSONArray) {
            JSONObject noteJSON = (JSONObject) noteObj;
            String noteText = noteJSON
                    .getJSONObject(ParserConstants.Note.PARAMS)
                    .getString(ParserConstants.Note.TEXT);
            notes.add(new Note(lead, noteText));
        }
        return notes;
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
