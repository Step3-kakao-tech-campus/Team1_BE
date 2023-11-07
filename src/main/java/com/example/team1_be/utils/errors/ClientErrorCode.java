package com.example.team1_be.utils.errors;

import lombok.Getter;

@Getter
public enum ClientErrorCode {

    // 10xxx
    TIMEOUT(-10000, Series.COMMUNICATION_ERROR, "Time Out 되었습니다."),
    UNKNOWN_ERROR(-10001, Series.COMMUNICATION_ERROR, "알 수 없는 오류로 실패했습니다."),
    INVALID_URI(-10002, Series.COMMUNICATION_ERROR, "요청 주소가 잘못되었습니다."),
    INVALID_PARAMETER(-10003, Series.COMMUNICATION_ERROR, "Get 시 Parameter 형식이 잘못되었습니다."),
    INVALID_REQUEST_BODY(-10004, Series.COMMUNICATION_ERROR, "Request Body의 형식이 잘못되었습니다."),
    INVALID_FORM_INPUT(-10005, Series.COMMUNICATION_ERROR, "Request Body의 폼 입력값이 잘못되었습니다."),
    NOT_USER(-10006, Series.COMMUNICATION_ERROR, "회원이 아닙니다."),
    KAKAO_CONNECT_FAIL(-10007, Series.COMMUNICATION_ERROR, "kakaoId를 찾아올 수 없습니다."),

    // 11xxx
    RECRUITMENT_NOT_STARTED(-11000, Series.BUSINESS_LOGIC_ISSUE, "아직 startWeekDate가 모집을 시작하지 않아, 스케줄 객체가 없습니다."),
    NO_CONFIRMED_SCHEDULE(-11001, Series.BUSINESS_LOGIC_ISSUE, "selectedDate에 확정된 스케줄이 없습니다."),

    // 20xxx
    DUPLICATE_KAKAO_ID(-20000, Series.DUPLICATE, "이미 가입된 kakaoId 입니다."),
    DUPLICATE_GRUOP(-20001, Series.DUPLICATE, "이미 그룹이 존재합니다."),
    RECRUITMENT_ON_GOING(-20002, Series.DUPLICATE, "이미 startWeekDate가 모집 중으로, 스케줄 객체가 있습니다."),
    RECRUITMENT_CLOSED(-20003, Series.DUPLICATE, "이미 startWeekDate가 모집 마감 되었습니다."),
    GROUP_NOT_FOUND(-20004, Series.DUPLICATE, "초대키로 그룹을 찾을 수 없습니다."),

    // 21xxx
    INVALID_TOKEN(-21000, Series.INACCESSIBLE, "토큰이 유효하지 않습니다."),
    NO_GROUP(-21001, Series.INACCESSIBLE, "소속 그룹이 없습니다."),
    ONLY_MEMBER(-21002, Series.INACCESSIBLE, "그룹에 멤버가 본인 뿐입니다."),
    USER_ID_NOT_FOUND(-21003, Series.INACCESSIBLE, "해당 userId를 찾을 수 없습니다."),
    MANAGER_API_REQUEST_ERROR(-21004, Series.INACCESSIBLE, "매니저의 API를 알바생이 요청할 수 없습니다."),
    MEMBER_API_REQUEST_ERROR(-21005, Series.INACCESSIBLE, "알바생의 API를 매니저가 요청할 수 없습니다."),
    MANAGER_USER_ID_ERROR(-21006, Series.INACCESSIBLE, "매니저가 userId를 전송하지 않았습니다."),
    MEMBER_USER_ID_ERROR(-21007, Series.INACCESSIBLE, "알바생은 userId를 전송할 수 없습니다."),
    UNFINISHED_USER_NOT_FOUND(-21008, Series.INACCESSIBLE, "해당 code로 UnfinishedUser의 kakaoId를 찾을 수 없습니다.");

    private final int value;
    private final Series series;
    private final String message;

    ClientErrorCode(int value, Series series, String message) {
        this.value = value;
        this.series = series;
        this.message = message;
    }

    public enum Series {
        COMMUNICATION_ERROR(10),
        BUSINESS_LOGIC_ISSUE(11),
        DUPLICATE(20),
        INACCESSIBLE(21);

        private final int value;

        Series(int value) {
            this.value = value;
        }
        }
}