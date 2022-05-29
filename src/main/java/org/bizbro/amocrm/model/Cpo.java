package org.bizbro.amocrm.model;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bizbro.amocrm.parser.Parser;

import javax.persistence.*;

import java.util.Objects;

import static java.util.Objects.hash;

@Entity
@Data
@Table(name = "cpo")
@AllArgsConstructor
public class Cpo {
    @Id
    private Long id;
    private String value;

    public Cpo(Long id, JSONObject cpoJSON) {
        this.id = id;
        this.value = Parser.getFieldValue(cpoJSON);
    }

    @Override
    public int hashCode() {
        return hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cpo cpo = (Cpo) o;
        return id.equals(cpo.id) &&
                Objects.equals(value, cpo.value);
    }
}
