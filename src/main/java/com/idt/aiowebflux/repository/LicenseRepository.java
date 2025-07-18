package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {

    @Query("SELECT l FROM License l WHERE l.licenseId = 1")
    License findOne();

}
