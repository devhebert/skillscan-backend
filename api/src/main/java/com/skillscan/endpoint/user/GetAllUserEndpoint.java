package com.skillscan.endpoint.user;

import com.skillscan.dto.user.UserDTO;
import com.skillscan.endpoint.BaseUserEndpoint;
import com.skillscan.usecase.user.GetAllUser;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllUserEndpoint extends BaseUserEndpoint {
    private final GetAllUser useCase;

    public GetAllUserEndpoint(GetAllUser useCase) {
        this.useCase = useCase;
    }

    private ResponseEntity<?> okResponse(List<UserDTO> userResponseDTOList) {
        return ResponseEntity.ok(userResponseDTOList);
    }

    private ResponseEntity<?> noResultResponse() {
        return ResponseEntity.noContent().build();
    }

    @Operation(operationId = "get-all", summary = "Get all users", description = "This operation retrieves all users from the database")
    @GetMapping(value = "get-all")
    public ResponseEntity<?> execute() {
        GetAllUser.OutputPort result = this.useCase.execute();

        return switch (result) {
            case GetAllUser.OutputPort.Ok ok -> okResponse(ok.list());
            case GetAllUser.OutputPort.NoResults __ ->noResultResponse();
            case GetAllUser.OutputPort.NotAuthorized __ ->ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        };
    }
}
