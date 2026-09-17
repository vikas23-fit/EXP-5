package com.example.restapi.service;

import com.example.restapi.dto.ScheduleRequest;
import com.example.restapi.dto.ScheduleResponse;

import java.util.List;

public interface ScheduleService {

    ScheduleResponse createSchedule(ScheduleRequest request);

    List<ScheduleResponse> getAllSchedules();

    ScheduleResponse getScheduleById(Long id);

    ScheduleResponse updateSchedule(Long id, ScheduleRequest request);

    void deleteSchedule(Long id);
}
