package com.crm.service;

import com.crm.api.mapper.IdentityApiMapper;
import com.crm.api.request.CreateTeamRequest;
import com.crm.api.response.TeamResponse;
import com.crm.domain.entity.Organization;
import com.crm.domain.entity.Team;
import com.crm.exception.DuplicateResourceException;
import com.crm.repository.TeamRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TeamService {

    @Inject
    TeamRepository teamRepository;

    @Inject
    OrganizationService organizationService;

    @Transactional
    public TeamResponse create(CreateTeamRequest request) {
        Organization organization = organizationService.getEntityById(request.organizationId);

        teamRepository.findByOrganizationAndName(request.organizationId, request.name.trim())
                .ifPresent(existing -> {
                    throw new DuplicateResourceException("Team with this name already exists in the organization");
                });

        Team team = new Team();
        team.id = UUID.randomUUID();
        team.organization = organization;
        team.name = request.name.trim();
        team.managerUserId = request.managerUserId;
        team.active = request.active;

        teamRepository.persist(team);

        return IdentityApiMapper.toTeamResponse(team);
    }

    public List<TeamResponse> list(UUID organizationId) {
        return teamRepository.findByOrganizationId(organizationId)
                .stream()
                .map(IdentityApiMapper::toTeamResponse)
                .toList();
    }

    public Team getEntityById(UUID id) {
        return teamRepository.findByIdOptional(id)
                .orElseThrow(() -> new com.crm.exception.NotFoundException("Team not found"));
    }
}