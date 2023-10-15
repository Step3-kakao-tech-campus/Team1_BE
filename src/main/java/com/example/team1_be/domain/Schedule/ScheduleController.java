package com.example.team1_be.domain.Schedule;

import com.example.team1_be.domain.Schedule.DTO.WeeklyScheduleCheck;
import com.example.team1_be.domain.Schedule.DTO.RecruitSchedule;
import com.example.team1_be.utils.ApiUtils;
import com.example.team1_be.utils.security.auth.UserDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/worktime")
    public ResponseEntity<?> recruitSchedule(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @RequestBody @Valid RecruitSchedule.Request request) {
        scheduleService.recruitSchedule(userDetails.getUser(), request);
        ApiUtils.ApiResult<String> result = ApiUtils.success(null);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/remain/week/{startWeekDate}")
    public ResponseEntity<?> weeklyScheduleCheck(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 @PathVariable("startWeekDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startWeekDate) {
        WeeklyScheduleCheck.Response responseDTO = scheduleService.weeklyScheduleCheck(userDetails.getUser(), startWeekDate);
        ApiUtils.ApiResult<WeeklyScheduleCheck.Response> response = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(response);
    }
}
