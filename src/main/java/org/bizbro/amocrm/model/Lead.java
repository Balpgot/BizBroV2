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
    private Long ID;
    private String NAME;
    private Integer BUDGET;
    private Integer VORONKA;
    private Long CONTACT_ID;
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(
            name = "lead_tag",
            joinColumns = {@JoinColumn(name = "lead_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private Set<Tag> TAGS;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lead lead = (Lead) o;
        return ID.equals(lead.ID) &&
                Objects.equals(NAME, lead.NAME) &&
                Objects.equals(BUDGET, lead.BUDGET) &&
                Objects.equals(VORONKA, lead.VORONKA) &&
                Objects.equals(CONTACT_ID, lead.CONTACT_ID) &&
                Objects.equals(TAGS, lead.TAGS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
