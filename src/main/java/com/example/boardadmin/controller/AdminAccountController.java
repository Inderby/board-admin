package com.example.boardadmin.controller;

import com.example.boardadmin.dto.response.AdminAccountResponse;
import com.example.boardadmin.service.AdminAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class AdminAccountController {
    private final AdminAccountService adminAccountService;
    @GetMapping("/admin/members")
    public String members(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ){
        session.setAttribute("sessionData", "Hello Session");
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", request.getServletContext());
        return "admin/members";
    }

    @ResponseBody
    @GetMapping("/api/admin/members")
    public List<AdminAccountResponse> getMembers(){
        return adminAccountService.users().stream().map(AdminAccountResponse::from).collect(Collectors.toList());
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    @DeleteMapping("/api/admin/members/{userId}")
    public void delete(@PathVariable(name = "userId") String userId){
        adminAccountService.deleteUser(userId);
    }
}
