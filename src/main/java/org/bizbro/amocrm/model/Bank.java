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
@Table(name = "bank")
@NoArgsConstructor
@AllArgsConstructor
public class Bank {
    @Id
    private Long id;
    private String value;

    public Bank (Long id, JSONObject fieldObject) {
        this.id = id;
        this.value = Parser.getFieldValue(fieldObject);
    }

    @Override
    public int hashCode() {
        return hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return id.equals(bank.id) &&
                Objects.equals(value, bank.value);
    }
}