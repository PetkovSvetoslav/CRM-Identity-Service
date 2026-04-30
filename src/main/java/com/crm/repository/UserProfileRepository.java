package com.crm.repository;

import com.crm.domain.entity.UserProfile;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserProfileRepository implements PanacheRepository<UserProfile> {

    public Optional<UserProfile> findByIdOptional(UUID id) {
        return find("id", id).firstResultOptional();
    }

    public Optional<UserProfile> findByAuthUserId(UUID authUserId) {
        return find("authUserId", authUserId).firstResultOptional();
    }

    public boolean existsByAuthUserId(UUID authUserId) {
        return count("authUserId", authUserId) > 0;
    }

    public PanacheQuery<UserProfile> search(
            String q,
            UUID organizationId,
            UUID departmentId,
            UUID teamId,
            Boolean active
    ) {
        StringBuilder jpql = new StringBuilder("1=1");
        Map<String, Object> params = new HashMap<>();

        if (q != null && !q.isBlank()) {
            jpql.append(" and (lower(firstName) like :q or lower(lastName) like :q or lower(email) like :q)");
            params.put("q", "%" + q.toLowerCase() + "%");
        }

        if (organizationId != null) {
            jpql.append(" and organization.id = :organizationId");
            params.put("organizationId", organizationId);
        }

        if (departmentId != null) {
            jpql.append(" and department.id = :departmentId");
            params.put("departmentId", departmentId);
        }

        if (teamId != null) {
            jpql.append(" and team.id = :teamId");
            params.put("teamId", teamId);
        }

        if (active != null) {
            jpql.append(" and status = :status");
            params.put("status", active ? "ACTIVE" : "INACTIVE");
        }

        jpql.append(" order by firstName asc, lastName asc");

        return find(jpql.toString(), params);
    }
}