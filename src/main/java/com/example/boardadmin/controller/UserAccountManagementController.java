package com.example.boardadmin.controller;

import com.example.boardadmin.dto.response.ArticleResponse;
import com.example.boardadmin.dto.response.UserAccountResponse;
import com.example.boardadmin.service.UserAccountManagementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/management/user-accounts")
@Controller
public class UserAccountManagementController {

    private final UserAccountManagementService userAccountManagementService;

    @GetMapping
    public String userAccounts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ){
        session.setAttribute("sessionData", "Hello Session");
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", request.getServletContext());
        model.addAttribute("userAccounts", userAccountManagementService.getUserAccounts().stream().map(UserAccountResponse::from).collect(Collectors.toList()));
        return "management/user-accounts";
    }

    @ResponseBody
    @GetMapping("/{userId}")
    public UserAccountResponse userAccount(@PathVariable(name = "userId") String userId) {
        return UserAccountResponse.from(userAccountManagementService.getUserAccount(userId));
    }

    @PostMapping("/{userId}")
    public String deleteArticle(@PathVariable(name = "userId") String userId,
                                HttpServletRequest request,
                                HttpServletResponse response,
                                HttpSession session,
                                Model model) {
        userAccountManagementService.deleteUserAccount(userId);
        session.setAttribute("sessionData", "Hello Session");
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", request.getServletContext());
        return "redirect:/management/user-accounts";
    }


}
