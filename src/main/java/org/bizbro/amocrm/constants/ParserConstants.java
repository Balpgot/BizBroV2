package org.bizbro.amocrm.constants;

public interface ParserConstants {

    interface StatusVoronka {
        String STATUS_PLATINA = "37851406";
        String STATUS_IN_PROGRESS = "43692277";
        String STATUS_GOLD = "36691654";
        String STATUS_SILVER = "37851409";
        String STATUS_BRONZE = "37851127";
    }

    interface Tag {
        String ID = "id";
        String NAME = "name";
        String ADDED = "83057";
    }

    interface JSON {
        String EMBEDDED_DATA = "_embedded";
        String CUSTOM_FIELDS_DATA = "custom_fields_values";
        String FIELD_ID = "field_id";
        String CUSTOM_FIELD_VALUES = "values";
        String VALUE = "value";
        String ENUM_ID = "enum_id";
    }

    interface Lead {
        String ID = "id";
        String VORONKA = "status_id";
        String PRICE = "price";
        String NAME = "name";
        String CONTACTS = "contacts";
        String TAGS = "tags";

        interface Contact {
            String ID = "id";
            String IS_MAIN = "is_main";
        }
    }
}
