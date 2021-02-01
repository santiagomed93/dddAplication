package com.company.ecommerce.infrastructure.repository.country;

import com.company.ecommerce.domain.country.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface HibernateCountryRepository extends JpaRepository<Country, Long> {
}
