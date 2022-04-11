package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.Hobby;
import com.sun.xml.bind.v2.runtime.JaxBeanInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private String comment;
    private Long hobbyId;
}
