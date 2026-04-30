package com.crm.api.mapper;

import com.crm.api.response.*;
import com.crm.domain.entity.*;

public final class IdentityApiMapper {

    private IdentityApiMapper() {
    }

    public static OrganizationResponse toOrganizationResponse(Organization organization) {
        OrganizationResponse response = new OrganizationResponse();
        response.id = organization.id.toString();
        response.name = organization.name;
        response.legalName = organization.legalName;
        response.taxNumber = organization.taxNumber;
        response.country = organization.country;
        response.city = organization.city;
        response.active = organization.active;
        return response;
    }

    public static DepartmentResponse toDepartmentResponse(Department department) {
        DepartmentResponse response = new DepartmentResponse();
        response.id = department.id.toString();
        response.organizationId = department.organization != null ? department.organization.id.toString() : null;
        response.name = department.name;
        response.managerUserId = department.managerUserId != null ? department.managerUserId.toString() : null;
        response.active = department.active;
        return response;
    }

    public static TeamResponse toTeamResponse(Team team) {
        TeamResponse response = new TeamResponse();
        response.id = team.id.toString();
        response.organizationId = team.organization != null ? team.organization.id.toString() : null;
        response.name = team.name;
        response.managerUserId = team.managerUserId != null ? team.managerUserId.toString() : null;
        response.active = team.active;
        return response;
    }

    public static UserProfileResponse toUserProfileResponse(UserProfile profile) {
        UserProfileResponse response = new UserProfileResponse();
        response.id = profile.id.toString();
        response.authUserId = profile.authUserId.toString();
        response.email = profile.email;
        response.role = profile.role;
        response.firstName = profile.firstName;
        response.lastName = profile.lastName;
        response.phone = profile.phone;
        response.avatarUrl = profile.avatarUrl;
        response.jobTitle = profile.jobTitle;
        response.organization = toNamedReference(profile.organization);
        response.department = toNamedReference(profile.department);
        response.team = toNamedReference(profile.team);
        response.active = profile.status != null && profile.status.name().equals("ACTIVE");
        response.createdAt = profile.createdAt != null ? profile.createdAt.toString() : null;
        response.updatedAt = profile.updatedAt != null ? profile.updatedAt.toString() : null;
        return response;
    }

    private static NamedReferenceResponse toNamedReference(Organization organization) {
        if (organization == null) {
            return null;
        }
        NamedReferenceResponse response = new NamedReferenceResponse();
        response.id = organization.id.toString();
        response.name = organization.name;
        return response;
    }

    private static NamedReferenceResponse toNamedReference(Department department) {
        if (department == null) {
            return null;
        }
        NamedReferenceResponse response = new NamedReferenceResponse();
        response.id = department.id.toString();
        response.name = department.name;
        return response;
    }

    private static NamedReferenceResponse toNamedReference(Team team) {
        if (team == null) {
            return null;
        }
        NamedReferenceResponse response = new NamedReferenceResponse();
        response.id = team.id.toString();
        response.name = team.name;
        return response;
    }
}