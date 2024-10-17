package org.mufasadev.mshando.core.tasker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mufasadev.mshando.core.tasker.models.Tasker;
import org.mufasadev.mshando.core.tasker.payload.TaskerDTO;
import org.mufasadev.mshando.core.tasker.repository.TaskerRepository;
import org.mufasadev.mshando.core.user.models.User;
import org.mufasadev.mshando.core.user.repository.UserRepository;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskerServiceImpl implements TaskerService {
    private final TaskerRepository taskerRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<TaskerDTO> getTasker(int taskerId) {
        Tasker tasker = taskerRepository.findById(taskerId).orElseThrow(()->new RuntimeException("Tasker not found"));
        TaskerDTO taskerDTO = modelMapper.map(tasker, TaskerDTO.class);
        return new ResponseEntity<TaskerDTO>(taskerDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deactivateTaskerAccount(int taskerId, Authentication connectedUser) {
        User user = (User) connectedUser;
        user.setAccountLocked(true);
        user.setEnabled(false);
        userRepository.save(user);
        return new ResponseEntity<String>("Account Deleted Successfully", HttpStatus.OK);
    }
}