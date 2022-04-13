package com.sparta.miniproject.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.miniproject.utils.userHandler.UserResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

// 리팩토링이 필요합니다. 겹치는 부분은 utils 폴더 안에 합쳐두도록 합시다.
@Component
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    UserResponseHandler userResponseHandler;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse httpServletResponse,
                                        AuthenticationException exception) throws IOException, ServletException{

        httpServletResponse = userResponseHandler.setResponse(httpServletResponse);
        Map<String,Object> response = userResponseHandler.setMessage("아이디와 비밀번호를 확인해 주세요.", HttpStatus.UNAUTHORIZED);

        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(out, response);
        out.flush();
    };
}
