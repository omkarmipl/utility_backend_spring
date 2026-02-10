package com.utilitypro.gumbackend.service;

import com.utilitypro.gumbackend.domain.entity.AuditLog;
import com.utilitypro.gumbackend.domain.entity.UtilityAccount;
import com.utilitypro.gumbackend.repository.AuditLogRepository;
import com.utilitypro.gumbackend.repository.UtilityAccountRepository;
import com.utilitypro.gumbackend.security.AuthorizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Utility Account Service
 * Handles utility account CRUD and bulk import
 */
@Service
@RequiredArgsConstructor
public class UtilityAccountService {

    private final UtilityAccountRepository utilityAccountRepository;
    private final AuditLogRepository auditLogRepository;
    private final AuthorizationHelper authorizationHelper;

    @Transactional(readOnly = true)
    public List<UtilityAccount> listUtilityAccounts(UUID locationId, Boolean includeInactive) {
        if (locationId != null) {
            return utilityAccountRepository.findByLocationId(locationId);
        }
        if (Boolean.TRUE.equals(includeInactive)) {
            return utilityAccountRepository.findAll();
        }
        return utilityAccountRepository.findByIsActiveTrue();
    }

    @Transactional
    public UtilityAccount createUtilityAccount(UtilityAccount account) {
        authorizationHelper.requireRole("system_admin", "mof_admin", "ministry_admin");

        // Validate unique provider account number
        if (utilityAccountRepository.findByProviderAccountNumber(account.getProviderAccountNumber()).isPresent()) {
            throw new IllegalArgumentException("Provider account number already exists");
        }

        account.setIsActive(true);
        UtilityAccount saved = utilityAccountRepository.save(account);
        createAuditLog("UtilityAccount", saved.getId(), "CREATE");
        return saved;
    }

    @Transactional
    public UtilityAccount updateUtilityAccount(UUID id, UtilityAccount updatedAccount) {
        authorizationHelper.requireRole("system_admin", "mof_admin", "ministry_admin");

        UtilityAccount existing = utilityAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utility account not found"));

        existing.setProviderAccountNumber(updatedAccount.getProviderAccountNumber());
        existing.setAccountName(updatedAccount.getAccountName());
        existing.setIsActive(updatedAccount.getIsActive());

        UtilityAccount saved = utilityAccountRepository.save(existing);
        createAuditLog("UtilityAccount", saved.getId(), "UPDATE");
        return saved;
    }

    @Transactional
    public void deleteUtilityAccount(UUID id) {
        authorizationHelper.requireRole("system_admin", "mof_admin");

        UtilityAccount account = utilityAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utility account not found"));

        account.setIsActive(false);
        utilityAccountRepository.save(account);
        createAuditLog("UtilityAccount", id, "DELETE");
    }

    @Transactional
    public List<UtilityAccount> bulkImportUtilityAccounts(List<UtilityAccount> accounts) {
        authorizationHelper.requireRole("system_admin", "mof_admin", "ministry_admin");

        // Validate all accounts before inserting
        for (UtilityAccount account : accounts) {
            if (utilityAccountRepository.findByProviderAccountNumber(account.getProviderAccountNumber()).isPresent()) {
                throw new IllegalArgumentException("Duplicate provider account number: " + account.getProviderAccountNumber());
            }
            account.setIsActive(true);
        }

        List<UtilityAccount> saved = utilityAccountRepository.saveAll(accounts);
        
        // Audit log for bulk import
        createAuditLog("UtilityAccount", null, "BULK_IMPORT");
        
        return saved;
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
