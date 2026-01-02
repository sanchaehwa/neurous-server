package com.example.server.domain.user.metadata.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server.domain.user.metadata.entity.CharacterData;

@Repository
public interface CharacterDataRepository extends JpaRepository<CharacterData, Integer> {

	List<CharacterData> findAllByOrderByLevelAsc();

	Optional<CharacterData> findByLevel(Integer level);
}
