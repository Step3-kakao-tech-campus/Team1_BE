package com.example.team1_be.domain.Schedule;

import com.example.team1_be.domain.Schedule.DTO.RecruitSchedule;
import com.example.team1_be.utils.ApiUtils;
import com.example.team1_be.utils.security.auth.UserDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
}
