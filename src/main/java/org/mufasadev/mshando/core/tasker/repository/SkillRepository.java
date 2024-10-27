package org.mufasadev.mshando.core.tasker.repository;

import org.mufasadev.mshando.core.tasker.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Integer> {

    Optional<Skill> findByName(String name);
}