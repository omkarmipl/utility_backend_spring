package com.utilitypro.gumbackend.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Job trigger response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobTriggerResponse {
    private String jobName;
    private String status;
    private String message;
    private String jobId;
}
