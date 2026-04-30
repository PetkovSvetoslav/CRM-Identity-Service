package com.crm.repository;

import com.crm.domain.entity.Department;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class DepartmentRepository implements PanacheRepository<Department> {

    public Optional<Department> findByIdOptional(UUID id) {
        return find("id", id).firstResultOptional();
    }

    public List<Department> findByOrganizationId(UUID organizationId) {
        return list("organization.id = ?1 order by name asc", organizationId);
    }

    public Optional<Department> findByOrganizationAndName(UUID organizationId, String name) {
        return find("organization.id = ?1 and lower(name) = ?2", organizationId, name.toLowerCase())
                .firstResultOptional();
    }
}