package org.bizbro.amocrm.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "note")
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="lead_id", nullable=false)
    private Lead lead;
    private String value;

    public Note(Lead lead, String value) {
        this.lead = lead;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) &&
                Objects.equals(lead.getId(), note.lead.getId()) &&
                Objects.equals(value, note.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", leadId=" + lead.getId() +
                ", value='" + value + '\'' +
                '}';
    }
}
