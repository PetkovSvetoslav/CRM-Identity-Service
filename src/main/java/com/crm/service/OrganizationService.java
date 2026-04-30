package com.crm.service;

import com.crm.api.mapper.IdentityApiMapper;
import com.crm.api.request.CreateOrganizationRequest;
import com.crm.api.response.OrganizationResponse;
import com.crm.domain.entity.Organization;
import com.crm.exception.DuplicateResourceException;
import com.crm.exception.NotFoundException;
import com.crm.repository.OrganizationRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OrganizationService {

    @Inject
    OrganizationRepository organizationRepository;

    @Transactional
    public OrganizationResponse create(CreateOrganizationRequest request) {
        organizationRepository.findByName(request.name.trim())
                .ifPresent(existing -> {
                    throw new DuplicateResourceException("Organization with this name already exists");
                });

        Organization organization = new Organization();
        organization.id = UUID.randomUUID();
        organization.name = request.name.trim();
        organization.legalName = request.legalName;
        organization.taxNumber = request.taxNumber;
        organization.country = request.country.trim();
        organization.city = request.city;
        organization.active = request.active;

        organizationRepository.persist(organization);

        return IdentityApiMapper.toOrganizationResponse(organization);
    }

    public List<OrganizationResponse> list() {
        return organizationRepository.findAllOrdered()
                .stream()
                .map(IdentityApiMapper::toOrganizationResponse)
                .toList();
    }

    public Organization getEntityById(UUID id) {
        return organizationRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Organization not found"));
    }
}