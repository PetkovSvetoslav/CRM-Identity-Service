package com.crm.repository;

import com.crm.domain.entity.Team;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class TeamRepository implements PanacheRepository<Team> {

    public Optional<Team> findByIdOptional(UUID id) {
        return find("id", id).firstResultOptional();
    }

    public List<Team> findByOrganizationId(UUID organizationId) {
        return list("organization.id = ?1 order by name asc", organizationId);
    }

    public Optional<Team> findByOrganizationAndName(UUID organizationId, String name) {
        return find("organization.id = ?1 and lower(name) = ?2", organizationId, name.toLowerCase())
                .firstResultOptional();
    }
}