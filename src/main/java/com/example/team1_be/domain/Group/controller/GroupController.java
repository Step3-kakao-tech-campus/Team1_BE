package com.example.team1_be.domain.Group.controller;

import com.example.team1_be.domain.Group.GroupCreateRequest;
import com.example.team1_be.domain.Group.Service.GroupService;
import com.example.team1_be.utils.security.auth.UserDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid GroupCreateRequest groupCreateRequest) {
        groupService.create(userDetails.getUser(), groupCreateRequest);

        return ResponseEntity.ok(null);
    }
}