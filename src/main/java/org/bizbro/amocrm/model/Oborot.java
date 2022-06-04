package org.bizbro.amocrm.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

import static java.util.Objects.hash;

@Entity
@Data
@Table(name = "oborot")
public class Oborot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    private Company company;
    private Integer year;
    private Integer value;

    public Oborot(Company company, Integer year, Integer value) {
        this.company = company;
        this.year = year;
        this.value = value;
    }

    @Override
    public int hashCode() {
        return hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Oborot oborot = (Oborot) o;
        if(id==null && oborot.getId()==null) {
            return  Objects.equals(company.getId(), oborot.getCompany().getId()) &&
                    Objects.equals(year, oborot.year) &&
                    Objects.equals(value, oborot.value);
        }
        return Objects.equals(id, oborot.id) &&
                Objects.equals(company.getId(), oborot.getCompany().getId()) &&
                Objects.equals(year, oborot.year) &&
                Objects.equals(value, oborot.value);
    }

    @Override
    public String toString() {
        return "Oborot{" +
                "id=" + id +
                ", companyId=" + company.getId() +
                ", year=" + year +
                ", value=" + value +
                '}';
    }
}
