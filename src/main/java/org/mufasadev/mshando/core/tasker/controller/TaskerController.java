package org.mufasadev.mshando.core.tasker.controller;

import lombok.RequiredArgsConstructor;
import org.mufasadev.mshando.core.tasker.payload.TaskerDTO;
import org.mufasadev.mshando.core.tasker.service.TaskerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasker")
public class TaskerController {
    private final TaskerService taskerService;

    @GetMapping("/{taskerId}")
    public ResponseEntity<TaskerDTO> getTasker(@PathVariable("taskerId") Integer taskerId) {
        return taskerService.getTasker(taskerId);
    }

    @PutMapping("/{taskerId}")
    public ResponseEntity<TaskerDTO> updateTasker(@PathVariable("taskerId") Integer taskerId){
        return null;
    }

    @DeleteMapping("/{taskerId}")
    public ResponseEntity<String> deleteTasker(@PathVariable("taskerId") Integer taskerId, Authentication connectedUser) {
        return taskerService.deactivateTaskerAccount(taskerId,connectedUser);
    }
}