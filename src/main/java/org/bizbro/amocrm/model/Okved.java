package org.bizbro.amocrm.model;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bizbro.amocrm.parser.Parser;

import javax.persistence.*;

import java.util.Objects;

import static java.util.Objects.hash;

@Entity
@Data
@Table(name = "okved")
@NoArgsConstructor
@AllArgsConstructor
public class Okved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String value;
    private String okvedGroup;

    public Okved(JSONObject okvedJSON) {
        this.value = Parser.getFieldValue(okvedJSON).trim();
        this.id = Parser.getOkvedId(value);
        this.okvedGroup = Parser.getOkvedGroup(id);
    }

    @Override
    public int hashCode() {
        return hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Okved okved = (Okved) o;
        return Objects.equals(id, okved.id) &&
                Objects.equals(value, okved.value) &&
                Objects.equals(okvedGroup, okved.okvedGroup);
    }
}
