package org.bizbro.amocrm.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "company")
@Builder(toBuilder = true)
public class Company {

    @Id
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lead_id",referencedColumnName = "id")
    private Lead lead;
    private String mobile;
    private String email;
    private String form;
    private String companyName;
    private String inn;
    private String city;
    private String sno;
    private Long registrationDate;
    private Boolean hasOborot;
    @OneToMany(mappedBy = "id")
    private Set<Oborot> oboroty;
    private String address;
    private String oformlenie;
    private Boolean keepAddress;
    private String addressNote;
    private String nalog;
    private String report;
    private String ecp;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "company_cpo",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "cpo_id")}
    )
    private Set<Cpo> cpo;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "company_license",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "license_id")}
    )
    private Set<License> license;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "company_okved",
            joinColumns = {@JoinColumn(name = "company_id")},
            inverseJoinColumns = {@JoinColumn(name = "okved_id")}
    )
    private Set<Okved> okved;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "company_bank",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "bank_id"))
    private Set<Bank> bank;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "company_goszakaz",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "goszakaz_id"))
    private Set<Goszakaz> goszakaz;
    private Integer founders;
    private Integer workersCount;
    private Boolean elimination;
    private Integer price;
    private Boolean debt;
    private Boolean marriage;
    private String owner;
    private String aim;
    private String comment;
    private String post;
    private Boolean odinC;
    private Boolean dopScheta;
    private Boolean isDeleted;
    private Boolean isPosted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id.equals(company.id) &&
                Objects.equals(lead, company.lead) &&
                Objects.equals(mobile, company.mobile) &&
                Objects.equals(email, company.email) &&
                Objects.equals(form, company.form) &&
                Objects.equals(companyName, company.companyName) &&
                Objects.equals(inn, company.inn) &&
                Objects.equals(city, company.city) &&
                Objects.equals(sno, company.sno) &&
                Objects.equals(registrationDate, company.registrationDate) &&
                Objects.equals(hasOborot, company.hasOborot) &&
                Objects.equals(oboroty, company.oboroty) &&
                Objects.equals(address, company.address) &&
                Objects.equals(oformlenie, company.oformlenie) &&
                Objects.equals(keepAddress, company.keepAddress) &&
                Objects.equals(addressNote, company.addressNote) &&
                Objects.equals(nalog, company.nalog) &&
                Objects.equals(report, company.report) &&
                Objects.equals(ecp, company.ecp) &&
                Objects.equals(cpo, company.cpo) &&
                Objects.equals(license, company.license) &&
                Objects.equals(okved, company.okved) &&
                Objects.equals(bank, company.bank) &&
                Objects.equals(goszakaz, company.goszakaz) &&
                Objects.equals(founders, company.founders) &&
                Objects.equals(workersCount, company.workersCount) &&
                Objects.equals(elimination, company.elimination) &&
                Objects.equals(price, company.price) &&
                Objects.equals(debt, company.debt) &&
                Objects.equals(marriage, company.marriage) &&
                Objects.equals(owner, company.owner) &&
                Objects.equals(aim, company.aim) &&
                Objects.equals(comment, company.comment) &&
                Objects.equals(post, company.post) &&
                Objects.equals(odinC, company.odinC) &&
                Objects.equals(dopScheta, company.dopScheta) &&
                Objects.equals(isDeleted, company.isDeleted) &&
                Objects.equals(isPosted, company.isPosted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
