package com.crm.api;

import com.crm.api.request.CreateOrganizationRequest;
import com.crm.api.response.ApiResponse;
import com.crm.api.response.MetaResponse;
import com.crm.api.response.OrganizationResponse;
import com.crm.service.OrganizationService;
import com.crm.util.TimeUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/identity/organizations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrganizationResource {

    @Inject
    OrganizationService organizationService;

    @GET
    @RolesAllowed({"ADMIN", "SALES_MANAGER", "SALES_REP", "SUPPORT_AGENT"})
    public Response list() {
        List<OrganizationResponse> data = organizationService.list();
        return Response.ok(new ApiResponse<>(data, new MetaResponse(TimeUtil.nowIso()))).build();
    }

    @POST
//    @RolesAllowed({"ADMIN", "SALES_MANAGER"})
    @RolesAllowed({"ADMIN", "SALES_MANAGER", "SALES_REP"})
    public Response create(@NotNull @Valid CreateOrganizationRequest request) {
        if (request == null) {
            throw new BadRequestException("Request body is required");
        }
        OrganizationResponse data = organizationService.create(request);
        return Response.status(Response.Status.CREATED)
                .entity(new ApiResponse<>(data, new MetaResponse(TimeUtil.nowIso())))
                .build();
    }
}