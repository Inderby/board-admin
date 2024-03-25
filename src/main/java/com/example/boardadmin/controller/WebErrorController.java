package com.example.boardadmin.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebErrorController implements ErrorController {
    @RequestMapping(value = "/error")
    public ModelAndView handleNoHandlerFoundException(HttpServletResponse response, HttpServletRequest request, Model model) {
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        // int status = response.getStatus();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("response", response);
        modelAndView.addObject("request", request);
        if (statusCode != null) {
            Integer status = Integer.valueOf(statusCode.toString());
            if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                modelAndView.addObject("errorCode", status);
                modelAndView.setViewName("/error/5xx");
            } else {
                modelAndView.addObject("errorCode", status);
                modelAndView.setViewName("/error/4xx");
            }
        }
        return modelAndView;
    }
}
