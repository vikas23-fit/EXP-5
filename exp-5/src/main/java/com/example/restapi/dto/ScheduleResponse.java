package com.example.restapi.dto;

import com.example.restapi.entity.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime scheduledDate;
    private ScheduleStatus status;
    private LocalDateTime createdAt;
}
