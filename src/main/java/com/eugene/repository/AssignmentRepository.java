package com.eugene.repository;

import com.eugene.domain.Assignment;
import com.eugene.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Eugene on 12/23/2016.
 */
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
  List<Assignment> findAllByUnit(Unit unit);
}
