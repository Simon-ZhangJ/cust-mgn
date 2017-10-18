package com.simon.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
    @RequestMapping("/**")
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Error unmappedRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return new Error("There is no resource for path " + uri);
    }
}
