package com.crm.service;

import com.crm.api.mapper.IdentityApiMapper;
import com.crm.api.request.CreateUserProfileRequest;
import com.crm.api.request.UpdateMyProfileRequest;
import com.crm.api.request.UpdateUserProfileRequest;
import com.crm.api.request.UpdateUserStatusRequest;
import com.crm.api.response.UserProfileResponse;
import com.crm.domain.entity.Department;
import com.crm.domain.entity.Organization;
import com.crm.domain.entity.Team;
import com.crm.domain.entity.UserProfile;
import com.crm.domain.enums.ProfileStatus;
import com.crm.exception.AccessDeniedException;
import com.crm.exception.DuplicateResourceException;
import com.crm.exception.NotFoundException;
import com.crm.repository.UserProfileRepository;
import com.crm.security.AuthenticatedUserProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class IdentityService {

    @Inject
    UserProfileRepository userProfileRepository;

    @Inject
    OrganizationService organizationService;

    @Inject
    DepartmentService departmentService;

    @Inject
    TeamService teamService;

    @Inject
    AuthenticatedUserProvider authenticatedUserProvider;

    @Transactional
    public UserProfileResponse create(CreateUserProfileRequest request) {
        if (userProfileRepository.existsByAuthUserId(request.authUserId)) {
            throw new DuplicateResourceException("User profile for this auth user already exists");
        }

        UserProfile profile = new UserProfile();
        profile.id = UUID.randomUUID();
        profile.authUserId = request.authUserId;
        profile.email = request.email.trim().toLowerCase();
        profile.role = request.role.trim();
        profile.firstName = request.firstName.trim();
        profile.lastName = request.lastName.trim();
        profile.phone = request.phone;
        profile.avatarUrl = request.avatarUrl;
        profile.jobTitle = request.jobTitle;
        profile.organization = resolveOrganization(request.organizationId);
        profile.department = resolveDepartment(request.departmentId);
        profile.team = resolveTeam(request.teamId);
        profile.status = request.active ? ProfileStatus.ACTIVE : ProfileStatus.INACTIVE;

        userProfileRepository.persist(profile);

        return IdentityApiMapper.toUserProfileResponse(profile);
    }

    public UserProfileResponse me() {
        UUID authUserId = authenticatedUserProvider.getCurrentAuthUserId();

        UserProfile profile = userProfileRepository.findByAuthUserId(authUserId)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        return IdentityApiMapper.toUserProfileResponse(profile);
    }

    @Transactional
    public UserProfileResponse updateMe(UpdateMyProfileRequest request) {
        UUID authUserId = authenticatedUserProvider.getCurrentAuthUserId();

        UserProfile profile = userProfileRepository.findByAuthUserId(authUserId)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        if (request.firstName != null) {
            profile.firstName = request.firstName.trim();
        }

        if (request.lastName != null) {
            profile.lastName = request.lastName.trim();
        }

        if (request.phone != null) {
            profile.phone = request.phone;
        }

        if (request.avatarUrl != null) {
            profile.avatarUrl = request.avatarUrl;
        }

        if (request.jobTitle != null) {
            profile.jobTitle = request.jobTitle;
        }

        return IdentityApiMapper.toUserProfileResponse(profile);
    }

    public List<UserProfileResponse> listUsers(
            String q,
            UUID organizationId,
            UUID departmentId,
            UUID teamId,
            Boolean active,
            int page,
            int size
    ) {
        PanacheQuery<UserProfile> query = userProfileRepository.search(q, organizationId, departmentId, teamId, active)
                .page(page, size);

        return query.list()
                .stream()
                .map(IdentityApiMapper::toUserProfileResponse)
                .toList();
    }

    public UserProfileResponse getById(UUID id) {
        UserProfile profile = userProfileRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        return IdentityApiMapper.toUserProfileResponse(profile);
    }

    @Transactional
    public UserProfileResponse update(UUID id, UpdateUserProfileRequest request) {
        UserProfile profile = userProfileRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        if (request.email != null) {
            profile.email = request.email.trim().toLowerCase();
        }

        if (request.role != null) {
            profile.role = request.role.trim();
        }

        if (request.firstName != null) {
            profile.firstName = request.firstName.trim();
        }

        if (request.lastName != null) {
            profile.lastName = request.lastName.trim();
        }

        if (request.phone != null) {
            profile.phone = request.phone;
        }

        if (request.avatarUrl != null) {
            profile.avatarUrl = request.avatarUrl;
        }

        if (request.jobTitle != null) {
            profile.jobTitle = request.jobTitle;
        }

        if (request.organizationId != null) {
            profile.organization = resolveOrganization(request.organizationId);
        }

        if (request.departmentId != null) {
            profile.department = resolveDepartment(request.departmentId);
        }

        if (request.teamId != null) {
            profile.team = resolveTeam(request.teamId);
        }

        if (request.active != null) {
            profile.status = request.active ? ProfileStatus.ACTIVE : ProfileStatus.INACTIVE;
        }

        return IdentityApiMapper.toUserProfileResponse(profile);
    }

    @Transactional
    public UserProfileResponse updateStatus(UUID id, UpdateUserStatusRequest request) {
        UserProfile profile = userProfileRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        profile.status = request.active ? ProfileStatus.ACTIVE : ProfileStatus.INACTIVE;

        return IdentityApiMapper.toUserProfileResponse(profile);
    }

    private Organization resolveOrganization(UUID id) {
        return id != null ? organizationService.getEntityById(id) : null;
    }

    private Department resolveDepartment(UUID id) {
        return id != null ? departmentService.getEntityById(id) : null;
    }

    private Team resolveTeam(UUID id) {
        return id != null ? teamService.getEntityById(id) : null;
    }
}