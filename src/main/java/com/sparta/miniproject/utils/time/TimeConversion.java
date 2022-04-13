package com.sparta.miniproject.utils.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class TimeConversion {

    public static String timeConversion(LocalDateTime modifiedAt) {

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

    public static String detailConversion(LocalDateTime modifiedAt) {
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

    public static String commentConversion(LocalDateTime modifiedAt) {
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
