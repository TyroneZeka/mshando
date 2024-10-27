package org.mufasadev.mshando.core.tasker.service;

import org.mufasadev.mshando.core.tasker.models.Skill;
import org.mufasadev.mshando.core.tasker.payload.SkillDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SkillService {
    ResponseEntity<List<Skill>> getSkills();

    ResponseEntity<SkillDTO> addSkill(SkillDTO skill);

    ResponseEntity<SkillDTO> getSkill(String name);

    ResponseEntity<List<Skill>> getUserSkills();
}