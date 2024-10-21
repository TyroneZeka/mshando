package org.mufasadev.mshando.core.tasker.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mufasadev.mshando.core.tasker.models.Tasker;
import org.mufasadev.mshando.core.tasker.payload.TaskerDTO;
import org.mufasadev.mshando.core.tasker.payload.TaskerRequest;
import org.mufasadev.mshando.core.tasker.payload.TaskerResponse;
import org.mufasadev.mshando.core.tasker.repository.TaskerRepository;
import org.mufasadev.mshando.core.user.models.User;
import org.mufasadev.mshando.core.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskerServiceImpl implements TaskerService {
    private final TaskerRepository taskerRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<TaskerDTO> getTasker(Authentication activeUser) {
        User user = ((User) activeUser.getPrincipal());
        if(!user.isAccountLocked() && user.isEnabled()){
            Tasker tasker = taskerRepository.findById(user.getId()).orElseThrow(()->new RuntimeException("Tasker not found"));
            TaskerDTO taskerDTO = modelMapper.map(tasker, TaskerDTO.class);
            taskerDTO.setFullname(user.fullName());
            taskerDTO.setEmail(user.getEmail());
            taskerDTO.setPhone(user.getPhone());
            return new ResponseEntity<TaskerDTO>(taskerDTO, HttpStatus.OK);
        }
        throw new RuntimeException("User is locked");
    }

    @Override
    public ResponseEntity<String> deactivateTaskerAccount(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        user.setAccountLocked(true);
        user.setEnabled(false);
        userRepository.save(user);
        return new ResponseEntity<String>("Account Locked.", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> reactivateTaskerAccount(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        user.setAccountLocked(false);
        user.setEnabled(true);
        userRepository.save(user);
        return new ResponseEntity<>("Account UnLocked Successfully", HttpStatus.OK);
    }

    @Override
    public TaskerResponse getAllTaskers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Tasker> taskersPage = taskerRepository.findAll(pageDetails);
        List<Tasker> taskers = taskersPage.getContent();
        if(taskers.isEmpty()) throw new RuntimeException("Tasker list is empty");

        List<TaskerDTO> taskerDTOS = taskers.stream().map(tasker->modelMapper.map(tasker,TaskerDTO.class)).toList();
        TaskerResponse taskerResponse = new TaskerResponse();
        taskerResponse.setContent(taskerDTOS);
        taskerResponse.setPageNumber(taskersPage.getNumber());
        taskerResponse.setPageSize(taskersPage.getSize());
        taskerResponse.setTotalElements(taskersPage.getNumberOfElements());
        taskerResponse.setTotalPages(taskersPage.getTotalPages());
        taskerResponse.setIsLastPage(taskersPage.isLast());
        return taskerResponse;
    }

    @Override
    public ResponseEntity<TaskerDTO> updateTasker(Authentication activeUser, Integer taskerId, TaskerRequest taskerRequest) {
        User user = (User) activeUser.getPrincipal();
        Tasker tasker = taskerRepository.findById(taskerId).orElseThrow(()->new RuntimeException("Tasker not found"));
        if(user.getTasker() != tasker){
            throw new RuntimeException("Tasker not found");
        }
        tasker.setBio(taskerRequest.getBio());
        tasker.setLocation(taskerRequest.getLocation());
        tasker.setHourlyRate(taskerRequest.getHourlyRate());
        tasker.setRating(taskerRequest.getRating());
        Tasker savedTasker = taskerRepository.save(tasker);
        TaskerDTO taskerDTO = modelMapper.map(savedTasker, TaskerDTO.class);
        taskerDTO.setFullname(user.fullName());
        taskerDTO.setEmail(user.getEmail());
        taskerDTO.setPhone(user.getPhone());
        return ResponseEntity.ok(taskerDTO);
    }

}