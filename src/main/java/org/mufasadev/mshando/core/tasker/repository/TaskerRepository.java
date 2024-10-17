package org.mufasadev.mshando.core.tasker.repository;

import org.mufasadev.mshando.core.tasker.models.Tasker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskerRepository extends JpaRepository<Tasker, Integer> {
}