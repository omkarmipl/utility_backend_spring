package com.utilitypro.gumbackend.service;

import com.utilitypro.gumbackend.repository.*;
import com.utilitypro.gumbackend.security.AuthorizationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Search Service
 * Handles global search across entities
 */
@Service
@RequiredArgsConstructor
public class SearchService {

    private final UtilityBillRepository utilityBillRepository;
    private final UtilityAccountRepository utilityAccountRepository;
    private final PaymentRepository paymentRepository;
    private final LocationRepository locationRepository;
    private final MinistryRepository ministryRepository;
    private final AuthorizationHelper authorizationHelper;

    @Transactional(readOnly = true)
    public Map<String, Object> globalSearch(String query) {
        // TODO: Implement global search
        // Search across bills, accounts, payments, locations, ministries
        // Apply scope filtering
        // Limit results per entity type (top 10 each)
        return new HashMap<>();
    }
}
