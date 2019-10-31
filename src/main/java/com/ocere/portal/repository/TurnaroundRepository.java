package com.ocere.portal.repository;

import com.ocere.portal.model.Turnaround;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnaroundRepository extends JpaRepository<Turnaround, Integer> {

}
