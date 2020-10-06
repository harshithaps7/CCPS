package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO,schedule);
        schedule = scheduleService.saveSchedule(schedule);
        BeanUtils.copyProperties(schedule, scheduleDTO);
        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return getScheduleDTOS(schedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        List<Schedule> petSchedules = scheduleService.getScheduleByPetId(petId);
        return getScheduleDTOS(petSchedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> employeeSchedules = scheduleService.getScheduleByEmployeeId(employeeId);
        return getScheduleDTOS(employeeSchedules);
    }

    private List<ScheduleDTO> getScheduleDTOS(List<Schedule> employeeSchedules) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<ScheduleDTO>();
        for (Schedule schedule : employeeSchedules) {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
            scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
            scheduleDTOS.add(scheduleDTO);
        }
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> customerSchedules = scheduleService.getScheduleByCustomerId(customerId);
        return getScheduleDTOS(customerSchedules);
    }
}
