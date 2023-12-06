package com.newSummary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newSummary.domain.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
	

	
}

