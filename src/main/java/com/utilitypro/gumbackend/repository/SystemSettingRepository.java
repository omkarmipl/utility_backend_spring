package com.utilitypro.gumbackend.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utilitypro.gumbackend.domain.entity.SystemSetting;

@Repository
public interface SystemSettingRepository extends JpaRepository<SystemSetting, UUID> {
    
    /**
     * Find system setting by key
     * @param settingKey Setting key
     * @return Optional system setting
     */
    Optional<SystemSetting> findBySettingKey(String settingKey);
    
    /**
     * Find all settings by category
     * @param category Category name
     * @return List of system settings
     */
    java.util.List<SystemSetting> findByCategory(String category);
    
    /**
     * Find all settings by category with pagination
     * @param category Category name
     * @param pageable Pagination parameters
     * @return Page of system settings
     */
    Page<SystemSetting> findByCategory(String category, Pageable pageable);
    
    /**
     * Delete setting by key
     * @param settingKey Setting key
     */
    void deleteBySettingKey(String settingKey);
}
