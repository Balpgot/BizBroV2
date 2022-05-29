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
@Table(name = "goszakaz")
@NoArgsConstructor
@AllArgsConstructor
public class Goszakaz {
    @Id
    private Long id;
    private String value;

    public Goszakaz(Long id, JSONObject goszakazJSON) {
        this.id = id;
        this.value = Parser.getFieldValue(goszakazJSON);
    }

    @Override
    public int hashCode() {
        return hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goszakaz goszakaz = (Goszakaz) o;
        return id.equals(goszakaz.id) &&
                Objects.equals(value, goszakaz.value);
    }
}
