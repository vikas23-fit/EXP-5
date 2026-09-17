package com.example.restapi.service.impl;

import com.example.restapi.dto.ScheduleRequest;
import com.example.restapi.dto.ScheduleResponse;
import com.example.restapi.entity.Schedule;
import com.example.restapi.exception.ResourceNotFoundException;
import com.example.restapi.repository.ScheduleRepository;
import com.example.restapi.service.ScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponse createSchedule(ScheduleRequest request) {
        Schedule schedule = Schedule.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .scheduledDate(request.getScheduledDate())
                .status(request.getStatus())
                .build();

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return mapToResponse(savedSchedule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleResponse> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleResponse getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + id));
        return mapToResponse(schedule);
    }

    @Override
    public ScheduleResponse updateSchedule(Long id, ScheduleRequest request) {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + id));

        existingSchedule.setTitle(request.getTitle());
        existingSchedule.setDescription(request.getDescription());
        existingSchedule.setScheduledDate(request.getScheduledDate());
        existingSchedule.setStatus(request.getStatus());

        Schedule updatedSchedule = scheduleRepository.save(existingSchedule);
        return mapToResponse(updatedSchedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + id));
        scheduleRepository.delete(existingSchedule);
    }

    private ScheduleResponse mapToResponse(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .title(schedule.getTitle())
                .description(schedule.getDescription())
                .scheduledDate(schedule.getScheduledDate())
                .status(schedule.getStatus())
                .createdAt(schedule.getCreatedAt())
                .build();
    }
}
