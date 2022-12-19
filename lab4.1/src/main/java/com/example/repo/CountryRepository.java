package com.example.repo;

import com.example.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface CountryRepository extends JpaRepository<Country,Long> {
     Country save(Country country);
}
