package com.crm.service;

import com.crm.api.mapper.IdentityApiMapper;
import com.crm.api.request.CreateDepartmentRequest;
import com.crm.api.response.DepartmentResponse;
import com.crm.domain.entity.Department;
import com.crm.domain.entity.Organization;
import com.crm.exception.DuplicateResourceException;
import com.crm.repository.DepartmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DepartmentService {

    @Inject
    DepartmentRepository departmentRepository;

    @Inject
    OrganizationService organizationService;

    @Transactional
    public DepartmentResponse create(CreateDepartmentRequest request) {
        Organization organization = organizationService.getEntityById(request.organizationId);

        departmentRepository.findByOrganizationAndName(request.organizationId, request.name.trim())
                .ifPresent(existing -> {
                    throw new DuplicateResourceException("Department with this name already exists in the organization");
                });

        Department department = new Department();
        department.id = UUID.randomUUID();
        department.organization = organization;
        department.name = request.name.trim();
        department.managerUserId = request.managerUserId;
        department.active = request.active;

        departmentRepository.persist(department);

        return IdentityApiMapper.toDepartmentResponse(department);
    }

    public List<DepartmentResponse> list(UUID organizationId) {
        return departmentRepository.findByOrganizationId(organizationId)
                .stream()
                .map(IdentityApiMapper::toDepartmentResponse)
                .toList();
    }

    public Department getEntityById(UUID id) {
        return departmentRepository.findByIdOptional(id)
                .orElseThrow(() -> new com.crm.exception.NotFoundException("Department not found"));
    }
}