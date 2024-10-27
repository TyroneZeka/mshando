package org.mufasadev.mshando.core.tasker.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mufasadev.mshando.core.handler.ResourceNotFoundException;
import org.mufasadev.mshando.core.tasker.models.Skill;
import org.mufasadev.mshando.core.tasker.payload.SkillDTO;
import org.mufasadev.mshando.core.tasker.payload.UserSkillsResponse;
import org.mufasadev.mshando.core.tasker.repository.SkillRepository;
import org.mufasadev.mshando.core.user.models.User;
import org.mufasadev.mshando.core.user.repository.UserRepository;
import org.mufasadev.mshando.core.utils.AuthUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final ModelMapper modelMapper;
    private final AuthUtils authUtils;

    @Override
    public ResponseEntity<List<Skill>> getSkills() {
        List<Skill> skills = skillRepository.findAll();
        return ResponseEntity.ok(skills);
    }

    @Override
    public ResponseEntity<SkillDTO> addSkill(SkillDTO skill) {
        Skill skillToDb = modelMapper.map(skill, Skill.class);
        skillRepository.save(skillToDb);
        return ResponseEntity.ok(modelMapper.map(skillToDb, SkillDTO.class));
    }

    @Override
    public ResponseEntity<SkillDTO> getSkill(String name) {
        Skill skill = skillRepository.findByName(name).orElseThrow(()->new ResourceNotFoundException("Skill", "name", name));
        return ResponseEntity.ok(modelMapper.map(skill, SkillDTO.class));
    }

    @Override
    public ResponseEntity<List<Skill>> getUserSkills() {
        User user = authUtils.loggedInUser();
        List<Skill> userSkills = user.getTasker().getSkills();

        return ResponseEntity.ok();
    }
}