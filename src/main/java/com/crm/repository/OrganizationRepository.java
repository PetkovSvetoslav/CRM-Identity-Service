package com.crm.repository;

import com.crm.domain.entity.Organization;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class OrganizationRepository implements PanacheRepository<Organization> {

    public Optional<Organization> findByIdOptional(UUID id) {
        return find("id", id).firstResultOptional();
    }

    public Optional<Organization> findByName(String name) {
        return find("lower(name) = ?1", name.toLowerCase()).firstResultOptional();
    }

    public List<Organization> findAllOrdered() {
        return list("order by name asc");
    }
}