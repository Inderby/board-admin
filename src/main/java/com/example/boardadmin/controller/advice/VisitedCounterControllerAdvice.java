package com.example.boardadmin.controller.advice;

import com.example.boardadmin.service.VisitCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@ControllerAdvice
public class VisitedCounterControllerAdvice {
    private final VisitCounterService visitCounterService;
    @ModelAttribute("visitCount")
    public Long visitCount(){
        return visitCounterService.visitCount();
    }
}
