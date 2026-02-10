package com.utilitypro.gumbackend.repository;

import com.utilitypro.gumbackend.domain.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, UUID> {
    
    Optional<EmailTemplate> findByTemplateName(String templateName);
}
