package com.sg.gc.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sg.gc.dao.entities.Operation;


@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

}
