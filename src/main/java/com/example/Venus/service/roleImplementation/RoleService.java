package com.example.Venus.service.roleImplementation;

import com.example.Venus.contants.DATABASE_CONSTANTS;
import com.example.Venus.dto.request.RolesRequest;
import com.example.Venus.exception.ResourceNotFoundException;
import com.example.Venus.utils.LoggedInUserUtil;
import com.example.Venus.utils.ProcedureCallUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final ProcedureCallUtil procedureCallUtil;
    private final LoggedInUserUtil loggedInUserUtil;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public List<Object> getAllRoles() throws JsonProcessingException {
        Map<String, Object> response = procedureCallUtil.callProc(DATABASE_CONSTANTS.GET_ALL_ROLES, jdbcTemplate);
        if (response == null || !response.containsKey("data") || response.get("data") == null) {
            throw new ResourceNotFoundException("resource.not.found");
        }
//        procedureCallUtil.validateProcResponse(response);
        return procedureCallUtil.transformDbResponseToJsonList(response.get("data"));
    }

    public  Object getRoleById(Long roleId) throws JsonProcessingException {
        Map<String, Object> response = procedureCallUtil.callProc(
                DATABASE_CONSTANTS.GET_ROLE_BY_ID,
                jdbcTemplate,
                new Object[]{roleId}
        );
        if (response == null || !response.containsKey("data") || response.get("data") == null) {
            throw new ResourceNotFoundException("resource.not.found");
        }
        procedureCallUtil.validateProcResponse(response);
        return procedureCallUtil.transformDbResponseToJsonList(response.get("data"));
    }

    public void addRole(RolesRequest roleRequest) throws JsonProcessingException {
        // Fetch the logged-in user's ID (Admin)
        Long userId = loggedInUserUtil.getLoggedInUserId(); // This is Admin with `role_id = 1`

        // Set the "createdBy" field with the Admin's user ID
        roleRequest.setCreateBy(userId);

        // Convert RolesRequest object to JSON string to send to the procedure
        ObjectMapper objectMapper = new ObjectMapper();
        String roleDataJson = objectMapper.writeValueAsString(roleRequest);

        log.info("Calling stored procedure with payload: {}", roleDataJson);

        // Call the procedure to insert the new role
        Map<String, Object> response = procedureCallUtil.callProc(
                DATABASE_CONSTANTS.ADD_ROLE,
                jdbcTemplate,
                roleDataJson
        );

        log.info("Stored Procedure Response: {}", response);

        if (response == null || response.get("role_id") == null) {
            throw new ResourceNotFoundException("resource.not.found");
        }

        log.info("Role added successfully with ID: {}", response.get("role_id"));
    }




    public void updateRole(Long roleId, RolesRequest rolesRequest) {
        // Fetch the existing role by ID
        Map<String, Object> existingRoleResponse = procedureCallUtil.callProc(
                DATABASE_CONSTANTS.GET_ROLE_BY_ID,
                jdbcTemplate,
                new Object[]{roleId}
        );

        procedureCallUtil.validateProcResponse(existingRoleResponse);

        if (existingRoleResponse == null || !existingRoleResponse.containsKey("data") || existingRoleResponse.get("data") == null) {
            throw new ResourceNotFoundException("resource.not.found");
        }

        // Get the ID of the logged-in user who is making the update
        Long updatedBy = loggedInUserUtil.getLoggedInUserId();

        // Prepare data for the role update
        Map<String, Object> updatedRoleData = new HashMap<>();
        updatedRoleData.put("name", rolesRequest.getName());
//        updatedRoleData.put("description", rolesRequest.getDescription());
//        updatedRoleData.put("selected_actions", rolesRequest.getSelectedActions());
        updatedRoleData.put("updated_by", updatedBy);

        // Convert the updated role data to JSON string
        String roleJsonData = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            roleJsonData = objectMapper.writeValueAsString(updatedRoleData);
        } catch (Exception e) {
            log.error("Error while transforming role data to JSON", e);
        }

        // Call the procedure to update the role in the database
        Map<String, Object> response = procedureCallUtil.callProc(
                DATABASE_CONSTANTS.UPDATE_ROLE,
                jdbcTemplate,
                new Object[]{roleId, roleJsonData}
        );

        procedureCallUtil.validateProcResponse(response);
    }

    public Object deleteRole(Long roleId) {
        Map<String, Object> existingRoleResponse = procedureCallUtil.callProc(
                DATABASE_CONSTANTS.GET_ROLE_BY_ID,
                jdbcTemplate,
                new Object[]{roleId}
        );

        procedureCallUtil.validateProcResponse(existingRoleResponse);

        if (existingRoleResponse == null || !existingRoleResponse.containsKey("data") || existingRoleResponse.get("data") == null) {
            throw new ResourceNotFoundException("resource.not.found");
        }

        Map<String, Object> response = procedureCallUtil.callProc(
                DATABASE_CONSTANTS.DELETE_ROLE,
                jdbcTemplate,
                new Object[]{roleId}
        );
        procedureCallUtil.validateProcResponse(response);

        return "Role deleted successfully";
    }
}
