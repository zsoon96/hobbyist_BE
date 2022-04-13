package com.sparta.miniproject.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.miniproject.model.User;
import com.sparta.miniproject.repository.UserRepository;
import com.sparta.miniproject.utils.userHandler.UserResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Component
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserResponseHandler userResponseHandler;
    @Autowired
    UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse httpServletResponse,
                                 Authentication authentication) throws IOException, ServletException{

        httpServletResponse = userResponseHandler.setResponse(httpServletResponse);
        Map<String,Object> response = userResponseHandler.setMessage("로그인이 완료되었습니다.", HttpStatus.OK);
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));
        response.put("nickname", user.getNickname());

        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(out, response);
        out.flush();
    };

}
