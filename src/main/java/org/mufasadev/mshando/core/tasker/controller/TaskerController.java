package org.mufasadev.mshando.core.tasker.controller;

import lombok.RequiredArgsConstructor;
import org.mufasadev.mshando.core.config.AppConstants;
import org.mufasadev.mshando.core.tasker.payload.TaskerDTO;
import org.mufasadev.mshando.core.tasker.payload.TaskerRequest;
import org.mufasadev.mshando.core.tasker.payload.TaskerResponse;
import org.mufasadev.mshando.core.tasker.service.TaskerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/taskers")
public class TaskerController {
    private final TaskerService taskerService;

    @GetMapping("/all")
    public ResponseEntity<TaskerResponse> getAllTaskers(
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_TASKERS_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_ORDER,required = false) String sortOrder

    ) {
        //Todo admin endpoint
        TaskerResponse taskerResponse = taskerService.getAllTaskers(pageNumber,pageSize,sortBy,sortOrder);
        return ResponseEntity.ok(taskerResponse);
    }

    @GetMapping("/tasker")
    public ResponseEntity<TaskerDTO> getTasker(Authentication activeUser) {
        return taskerService.getTasker(activeUser);
    }

    @PutMapping("/tasker/{taskerId}")
    public ResponseEntity<TaskerDTO> updateTasker(Authentication activeUser,@PathVariable Integer taskerId,@RequestBody TaskerRequest taskerRequest) {
        return taskerService.updateTasker(activeUser,taskerId,taskerRequest);
    }

    @DeleteMapping("/tasker/deactivate-account")
    public ResponseEntity<String> deactivateTaskerAccount(Authentication connectedUser) {
        return taskerService.deactivateTaskerAccount(connectedUser);
    }

    @PutMapping("/tasker/reactivate-account")
    public ResponseEntity<String> reactivateTaskerAccount(Authentication connectedUser) {
        return taskerService.reactivateTaskerAccount(connectedUser);
    }


}