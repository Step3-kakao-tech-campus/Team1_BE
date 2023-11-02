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
    KAKAO_CONNECT_FAIL(-10007, Series.COMMUNICATION_ERROR, "kakaoId를 찾아올 수 없습니다.");

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