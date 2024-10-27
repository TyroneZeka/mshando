package org.mufasadev.mshando.core.tasker.controller;

import lombok.RequiredArgsConstructor;
import org.mufasadev.mshando.core.tasker.models.Skill;
import org.mufasadev.mshando.core.tasker.payload.SkillDTO;
import org.mufasadev.mshando.core.tasker.payload.UserSkillsResponse;
import org.mufasadev.mshando.core.tasker.service.SkillService;
import org.mufasadev.mshando.core.utils.AuthUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @PostMapping("/add")
    public ResponseEntity<SkillDTO> addSkill(@RequestBody SkillDTO skill) {
        return skillService.addSkill(skill);
    }

    @GetMapping()
    public ResponseEntity<List<Skill>> getSkills() {
        return skillService.getSkills();
    }

    @GetMapping()
    public ResponseEntity<SkillDTO> getSkill(@RequestParam(required = false) String name) {
        return skillService.getSkill(name);
    }

    @GetMapping("/user/skills")
    public ResponseEntity<List<Skill>> getUserSkills() {
        return skillService.getUserSkills();

    }
}