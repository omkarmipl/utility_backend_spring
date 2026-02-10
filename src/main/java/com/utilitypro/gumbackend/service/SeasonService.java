package com.utilitypro.gumbackend.service;

import com.utilitypro.gumbackend.domain.entity.AuditLog;
import com.utilitypro.gumbackend.domain.entity.Season;
import com.utilitypro.gumbackend.dto.common.PageResponse;
import com.utilitypro.gumbackend.dto.system.SeasonRequest;
import com.utilitypro.gumbackend.dto.system.SeasonResponse;
import com.utilitypro.gumbackend.mapper.SeasonMapper;
import com.utilitypro.gumbackend.repository.AuditLogRepository;
import com.utilitypro.gumbackend.repository.SeasonRepository;
import com.utilitypro.gumbackend.security.AuthorizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Season Service
 * Handles season/billing period management
 */
@Service
@RequiredArgsConstructor
public class SeasonService {

    private final SeasonRepository seasonRepository;
    private final SeasonMapper seasonMapper;
    private final AuditLogRepository auditLogRepository;
    private final AuthorizationHelper authorizationHelper;

    @Transactional(readOnly = true)
    public PageResponse<SeasonResponse> listSeasons(Pageable pageable) {
        Page<Season> seasons = seasonRepository.findAll(pageable);
        return PageResponse.of(seasons, seasonMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public SeasonResponse getSeason(UUID id) {
        Season season = seasonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Season not found"));
        return seasonMapper.toResponse(season);
    }

    @Transactional
    public SeasonResponse createSeason(SeasonRequest request) {
        authorizationHelper.requireRole("system_admin", "mof_admin");
        
        Season season = seasonMapper.toEntity(request);
        Season saved = seasonRepository.save(season);
        
        createAuditLog("Season", saved.getId(), "CREATE");
        return seasonMapper.toResponse(saved);
    }

    @Transactional
    public SeasonResponse updateSeason(UUID id, SeasonRequest request) {
        authorizationHelper.requireRole("system_admin", "mof_admin");

        Season existing = seasonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Season not found"));

        seasonMapper.updateEntityFromRequest(existing, request);
        Season saved = seasonRepository.save(existing);
        
        createAuditLog("Season", saved.getId(), "UPDATE");
        return seasonMapper.toResponse(saved);
    }

    @Transactional
    public void deleteSeason(UUID id) {
        authorizationHelper.requireRole("system_admin");
        seasonRepository.deleteById(id);
        createAuditLog("Season", id, "DELETE");
    }

    private void createAuditLog(String entityType, UUID entityId, String action) {
        AuditLog auditLog = AuditLog.builder()
                .entityType(entityType)
                .entityId(entityId)
                .action(action)
                .changedBy(authorizationHelper.getCurrentUserId())
                .changedAt(OffsetDateTime.now())
                .build();
        auditLogRepository.save(auditLog);
    }
}
