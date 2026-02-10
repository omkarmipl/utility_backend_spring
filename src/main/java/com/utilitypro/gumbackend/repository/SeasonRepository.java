package com.utilitypro.gumbackend.repository;

import com.utilitypro.gumbackend.domain.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SeasonRepository extends JpaRepository<Season, UUID> {
}
