package org.bizbro.amocrm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lead")
public class Lead {
    @Id
    private Long id;
    private String name;
    private Integer budget;
    private Integer voronka;
    private Long contactId;
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "lead_tag",
            joinColumns = {@JoinColumn(name = "lead_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Tag> tags;
    @OneToMany(mappedBy = "id")
    private Set<Note> notes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lead lead = (Lead) o;
        return id.equals(lead.id) &&
                Objects.equals(name, lead.name) &&
                Objects.equals(budget, lead.budget) &&
                Objects.equals(voronka, lead.voronka) &&
                Objects.equals(contactId, lead.contactId) &&
                Objects.equals(tags, lead.tags) &&
                Objects.equals(notes, lead.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
