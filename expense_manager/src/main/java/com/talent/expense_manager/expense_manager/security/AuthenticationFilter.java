package com.talent.expense_manager.expense_manager.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.talent.expense_manager.expense_manager.response.BaseResponse;
import com.talent.expense_manager.expense_manager.response.ResponseUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationFilter extends GenericFilter {


    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestUri = httpRequest.getRequestURI();
        String[] segments = requestUri.split("/");
        String apiId =  segments[segments.length - 1];

        try {
            Authentication authentication = authenticationService.doAuthentication(httpRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = httpResponse.getWriter();

            writer.print(convertObjectToJson(ResponseUtil.success(
                    HttpStatus.BAD_REQUEST,
                    requestUri,
                    apiId,
                    "overview",
                    e.getMessage())));
            writer.flush();
            writer.close();
            return;
        }
        chain.doFilter(request, response);

    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(object);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
