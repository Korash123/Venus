package com.example.Venus.controller;

import com.example.Venus.contants.URL_CONSTANTS;
import com.example.Venus.base.BaseController;
import com.example.Venus.dto.global.GlobalApiRequest;
import com.example.Venus.dto.global.GlobalApiResponse;
import com.example.Venus.dto.request.RolesRequest;
import com.example.Venus.service.roleImplementation.RoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(URL_CONSTANTS.Role.USER_BASE_URL)
public class RoleController extends BaseController {

    private final RoleService roleService;

    @PostMapping(URL_CONSTANTS.COMMON.SAVE)
    public GlobalApiResponse<?> createRole(@Valid @RequestBody GlobalApiRequest<RolesRequest> request) throws JsonProcessingException {
        roleService.addRole(request.getData());
        return getSuccessResponse("role.create.success", HttpStatus.CREATED);
    }

    // Get All Roles
    @PostMapping(URL_CONSTANTS.COMMON.GET_ALL)
    public GlobalApiResponse<Object> getAllRoles() throws JsonProcessingException {
        List<Object> roles = roleService.getAllRoles();
        return getSuccessResponse("role.list.success", roles, HttpStatus.OK);
    }

    // Get Role by ID
    @GetMapping(URL_CONSTANTS.COMMON.GETBYID)
    public GlobalApiResponse<Object> getRoleById(@PathVariable Long roleId) throws JsonProcessingException {
        Object role = roleService.getRoleById(roleId);
        return getSuccessResponse("role.get.success", role, HttpStatus.OK);
    }

    // Update Role
    @PutMapping(URL_CONSTANTS.COMMON.UPDATE)
    public GlobalApiResponse<Object> updateRole(
            @PathVariable Long roleId, @Valid @RequestBody GlobalApiRequest<RolesRequest> request) {
        roleService.updateRole(roleId, request.getData());
        return (GlobalApiResponse<Object>) getSuccessResponse("role.update.success", HttpStatus.OK);
    }

    @DeleteMapping(URL_CONSTANTS.COMMON.DELETE_BY_ID)
    public GlobalApiResponse<?> deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return getSuccessResponse("role.delete.success", HttpStatus.NO_CONTENT);
    }
}
