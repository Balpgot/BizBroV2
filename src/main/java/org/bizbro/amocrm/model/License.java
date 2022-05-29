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
@Table(name = "license")
@NoArgsConstructor
@AllArgsConstructor
public class License {
    @Id
    private Long id;
    private String value;

    public License(Long id, JSONObject licenseJSON) {
        this.id = id;
        this.value = Parser.getFieldValue(licenseJSON);
    }

    @Override
    public int hashCode() {
        return hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        License license = (License) o;
        return id.equals(license.id) &&
                Objects.equals(value, license.value);
    }
}
