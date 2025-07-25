package com.idt.aiowebflux.repository;


import com.idt.aiowebflux.entity.AclEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AclEntryRepository extends JpaRepository<AclEntry, Long> {

}
