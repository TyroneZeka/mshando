package org.mufasadev.mshando.core.tasker.service;

import org.mufasadev.mshando.core.tasker.payload.TaskerDTO;
import org.mufasadev.mshando.core.tasker.payload.TaskerRequest;
import org.mufasadev.mshando.core.tasker.payload.TaskerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TaskerService {
    ResponseEntity<TaskerDTO> getTasker(Authentication taskerId);

    ResponseEntity<String> deactivateTaskerAccount(Authentication connectedUser);

    ResponseEntity<String> reactivateTaskerAccount(Authentication connectedUser);

    TaskerResponse getAllTaskers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ResponseEntity<TaskerDTO> updateTasker(Authentication activeUser, Integer taskerId, TaskerRequest taskerRequest);
}