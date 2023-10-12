package com.example.team1_be.domain.Group.controller;

import com.example.team1_be.domain.Group.GroupCreateRequest;
import com.example.team1_be.domain.Group.GroupCreateResponse;
import com.example.team1_be.domain.Group.GroupMemberListResponse;
import com.example.team1_be.domain.Group.Service.GroupService;
import com.example.team1_be.utils.ApiUtils;
import com.example.team1_be.utils.security.auth.UserDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupCreateResponse> create(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody @Valid GroupCreateRequest groupCreateRequest) {
        GroupCreateResponse groupCreateResponse = groupService.create(userDetails.getUser(), groupCreateRequest);
        return ResponseEntity.ok(groupCreateResponse);
    }

    @GetMapping
    public ResponseEntity<ApiUtils.ApiResult> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        GroupMemberListResponse groupMemberListResponse = groupService.findAll(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(groupMemberListResponse));
    }

    @DeleteMapping("/member/id/{memberId}")
    public ResponseEntity<?> deleteMember(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("memberId") Long memberId) {
        groupService.deleteMember(userDetails.getUser(), memberId);
        return ResponseEntity.ok(null);
    }
}
