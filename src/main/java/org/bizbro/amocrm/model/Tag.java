package org.bizbro.amocrm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Objects;

import static java.util.Objects.hash;

@Entity
@Data
@Table(name = "tag")
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    private Long id;
    private String name;

    @Override
    public int hashCode() {
        return hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return id.equals(tag.id) &&
                Objects.equals(name, tag.name);
    }
}
