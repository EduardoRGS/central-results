package com.results.central_results.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    private String email;
    private String message;
    private String type;
    private LocalDateTime timestamp = LocalDateTime.now();
} 