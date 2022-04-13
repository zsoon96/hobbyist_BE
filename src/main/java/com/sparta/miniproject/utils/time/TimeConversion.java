package com.sparta.miniproject.utils.time;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Component
public class TimeConversion {

    // 같은 이름의 변수가 여러번 반복되므로, 스프링에 대해 충분히 공부한 뒤 리팩토링 할 수 있는 방법을 찾도록 합니다.

    // 게시글 전체 목록 조회 시간 커스텀
    public String timeConversion(LocalDateTime modifiedAt) {

        LocalDateTime currentTime = LocalDateTime.now();
        Period period = Period.between(modifiedAt.toLocalDate(), currentTime.toLocalDate());
        String resultConversion;

        if ( period.getDays() < 0 ){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            resultConversion = formatter.format(modifiedAt);
        }
        else{
            Long timeDiff = Duration.between(modifiedAt, currentTime).getSeconds();

            if ((timeDiff / 60 / 60) > 0) { // 시간
                resultConversion = timeDiff / 60 / 60 + "시간 전";
            } else if ((timeDiff / 60) > 0) { // 분
                resultConversion = timeDiff / 60 + "분 전";
            } else {
                resultConversion = timeDiff + "초 전";
            }
        }
        return resultConversion;
    }

    // 게시글 상세 조회 시간 커스텀
    public String detailConversion(LocalDateTime modifiedAt) {
        LocalDateTime currentTime = LocalDateTime.now();
        Period period = Period.between(modifiedAt.toLocalDate(), currentTime.toLocalDate());
        DateTimeFormatter formatter;

        if ( period.getYears() < 0 ){
            formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        } else if ( period.getDays() < 0 ){
            formatter = DateTimeFormatter.ofPattern("MM. dd.");
        } else {
            formatter = DateTimeFormatter.ofPattern("HH : mm");
        }
        return formatter.format(modifiedAt);
    }

    // 댓글 목록 조회 시간 커스텀
    public String commentConversion(LocalDateTime modifiedAt) {
        LocalDateTime currentTime = LocalDateTime.now();
        Period period = Period.between(modifiedAt.toLocalDate(), currentTime.toLocalDate());
        DateTimeFormatter formatter;

        if ( period.getYears() < 0 ){
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        } else if ( period.getDays() < 0 ){
            formatter = DateTimeFormatter.ofPattern("MM. dd.");
        } else {
            formatter = DateTimeFormatter.ofPattern("MM. dd. HH:mm:ss");
        }
        return formatter.format(modifiedAt);
    }
}
