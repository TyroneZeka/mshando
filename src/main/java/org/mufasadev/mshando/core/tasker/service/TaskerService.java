package org.mufasadev.mshando.core.tasker.service;

import org.mufasadev.mshando.core.tasker.payload.TaskerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface TaskerService {
    ResponseEntity<TaskerDTO> getTasker(int taskerId);

    ResponseEntity<String> deactivateTaskerAccount(int taskerId, Authentication connectedUser);
}