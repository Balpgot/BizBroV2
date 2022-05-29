package org.bizbro.amocrm.api;

import org.bizbro.amocrm.model.Company;
import org.bizbro.amocrm.repository.CompanyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    public CompanyRepository companyRepo;

    public CompanyController(CompanyRepository companyRepo) {
        this.companyRepo = companyRepo;
    }

    @ResponseBody
    @GetMapping(value = "/get/{id}")
    public Company getCompany (@PathVariable Long id){
        "id".equals(id);
        return companyRepo.getById(id);
    }

    @ResponseBody
    @GetMapping(value = "/get")
    public List<Company> getAllCompanies (){
        return companyRepo.findAll();
    }
}
