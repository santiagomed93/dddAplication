package com.company.ecommerce.infrastructure.repository.countrytax;

import com.company.ecommerce.domain.countrytax.CountryTax;
import com.company.ecommerce.domain.countrytax.CountryTaxId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HibernateCountryTaxRepository extends JpaRepository<CountryTax, CountryTaxId> {
}
