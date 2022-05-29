package org.bizbro.amocrm.entity;

import lombok.Data;
import org.bizbro.amocrm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class EntityManager {

    private GoszakazRepository goszakazRepository;
    private BankRepository bankRepository;
    private CompanyRepository companyRepository;
    private CpoRepository cpoRepository;
    private LeadRepository leadRepository;
    private LicenseRepository licenseRepository;
    private OborotRepository oborotRepository;
    private OkvedRepository okvedRepository;
    private TagRepository tagRepository;

    @Autowired
    public EntityManager(GoszakazRepository goszakazRepository,
                         BankRepository bankRepository,
                         CompanyRepository companyRepository,
                         CpoRepository cpoRepository,
                         LeadRepository leadRepository,
                         LicenseRepository licenseRepository,
                         OborotRepository oborotRepository,
                         OkvedRepository okvedRepository,
                         TagRepository tagRepository) {
        this.goszakazRepository = goszakazRepository;
        this.bankRepository = bankRepository;
        this.companyRepository = companyRepository;
        this.cpoRepository = cpoRepository;
        this.leadRepository = leadRepository;
        this.licenseRepository = licenseRepository;
        this.oborotRepository = oborotRepository;
        this.okvedRepository = okvedRepository;
        this.tagRepository = tagRepository;
    }
}
