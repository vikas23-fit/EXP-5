package com.example.restapi.controller;

import com.example.restapi.dto.ScheduleRequest;
import com.example.restapi.dto.ScheduleResponse;
import com.example.restapi.response.ApiResponse;
import com.example.restapi.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ScheduleResponse>> createSchedule(@Valid @RequestBody ScheduleRequest request) {
        ScheduleResponse response = scheduleService.createSchedule(request);
        return new ResponseEntity<>(
                ApiResponse.success("Schedule created successfully", response),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ScheduleResponse>>> getAllSchedules() {
        List<ScheduleResponse> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(
                ApiResponse.success("Schedules retrieved successfully", schedules)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ScheduleResponse>> getScheduleById(@PathVariable Long id) {
        ScheduleResponse schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(
                ApiResponse.success("Schedule retrieved successfully", schedule)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ScheduleResponse>> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleRequest request) {
        ScheduleResponse updatedSchedule = scheduleService.updateSchedule(id, request);
        return ResponseEntity.ok(
                ApiResponse.success("Schedule updated successfully", updatedSchedule)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok(
                ApiResponse.success("Schedule deleted successfully", null)
        );
    }
}
