package com.crm.api;

import com.crm.api.request.CreateUserProfileRequest;
import com.crm.api.request.UpdateMyProfileRequest;
import com.crm.api.request.UpdateUserProfileRequest;
import com.crm.api.request.UpdateUserStatusRequest;
import com.crm.api.response.ApiResponse;
import com.crm.api.response.MetaResponse;
import com.crm.api.response.UserProfileResponse;
import com.crm.service.IdentityService;
import com.crm.util.TimeUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/api/identity")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IdentityResource {

    @Inject
    IdentityService identityService;

    @GET
    @Path("/me")
    @RolesAllowed({"ADMIN", "SALES_MANAGER", "SALES_REP", "SUPPORT_AGENT"})
    public Response me() {
        UserProfileResponse data = identityService.me();
        return Response.ok(new ApiResponse<>(data, new MetaResponse(TimeUtil.nowIso()))).build();
    }

    @PATCH 
    @Path("/me")
    @RolesAllowed({"ADMIN", "SALES_MANAGER", "SALES_REP", "SUPPORT_AGENT"})
    public Response updateMe(@Valid UpdateMyProfileRequest request) {
        UserProfileResponse data = identityService.updateMe(request);
        return Response.ok(new ApiResponse<>(data, new MetaResponse(TimeUtil.nowIso()))).build();
    }

    @GET
    @Path("/users")
    @RolesAllowed({"ADMIN", "SALES_MANAGER"})
    public Response listUsers(
            @QueryParam("q") String q,
            @QueryParam("organizationId") UUID organizationId,
            @QueryParam("departmentId") UUID departmentId,
            @QueryParam("teamId") UUID teamId,
            @QueryParam("active") Boolean active,
            @DefaultValue("0") @QueryParam("page") int page,
            @DefaultValue("20") @QueryParam("size") int size
    ) {
        List<UserProfileResponse> data = identityService.listUsers(q, organizationId, departmentId, teamId, active, page, size);
        return Response.ok(new ApiResponse<>(data, new MetaResponse(TimeUtil.nowIso()))).build();
    }

    @GET
    @Path("/users/{id}")
    @RolesAllowed({"ADMIN", "SALES_MANAGER"})
    public Response getById(@PathParam("id") UUID id) {
        UserProfileResponse data = identityService.getById(id);
        return Response.ok(new ApiResponse<>(data, new MetaResponse(TimeUtil.nowIso()))).build();
    }

    @POST
    @Path("/users")
//    @RolesAllowed({"ADMIN", "SALES_MANAGER"})
    @RolesAllowed({"ADMIN", "SALES_MANAGER", "SALES_REP"})
    public Response create(@Valid CreateUserProfileRequest request) {
        UserProfileResponse data = identityService.create(request);
        return Response.status(Response.Status.CREATED)
                .entity(new ApiResponse<>(data, new MetaResponse(TimeUtil.nowIso())))
                .build();
    }

    @PATCH 
    @Path("/users/{id}")
    @RolesAllowed({"ADMIN", "SALES_MANAGER"})
    public Response update(@PathParam("id") UUID id, @Valid UpdateUserProfileRequest request) {
        UserProfileResponse data = identityService.update(id, request);
        return Response.ok(new ApiResponse<>(data, new MetaResponse(TimeUtil.nowIso()))).build();
    }

    @PATCH
    @Path("/users/{id}/status")
    @RolesAllowed({"ADMIN", "SALES_MANAGER"})
    public Response updateStatus(@PathParam("id") UUID id, UpdateUserStatusRequest request) {
        UserProfileResponse data = identityService.updateStatus(id, request);
        return Response.ok(new ApiResponse<>(data, new MetaResponse(TimeUtil.nowIso()))).build();
    }
}