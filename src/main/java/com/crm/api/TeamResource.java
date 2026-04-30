package com.crm.api;

import com.crm.api.request.CreateTeamRequest;
import com.crm.api.response.ApiResponse;
import com.crm.api.response.MetaResponse;
import com.crm.api.response.TeamResponse;
import com.crm.service.TeamService;
import com.crm.util.TimeUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/api/identity/teams")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeamResource {

    @Inject
    TeamService teamService;

    @GET
    @RolesAllowed({"ADMIN", "SALES_MANAGER", "SALES_REP", "SUPPORT_AGENT"})
    public Response list(@QueryParam("organizationId") UUID organizationId) {
        if (organizationId == null) {
            throw new BadRequestException("organizationId is required");
        }

        List<TeamResponse> data = teamService.list(organizationId);
        return Response.ok(new ApiResponse<>(data, new MetaResponse(TimeUtil.nowIso()))).build();
    }

    @POST
    @RolesAllowed({"ADMIN", "SALES_MANAGER", "SALES_REP"})
    public Response create(@NotNull @Valid CreateTeamRequest request) {
        if (request == null) {
            throw new BadRequestException("Request body is required");
        }
        TeamResponse data = teamService.create(request);
        return Response.status(Response.Status.CREATED)
                .entity(new ApiResponse<>(data, new MetaResponse(TimeUtil.nowIso())))
                .build();
    }
}