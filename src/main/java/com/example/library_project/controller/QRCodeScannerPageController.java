package com.example.library_project.controller;

import com.example.library_project.model.User;
import com.example.library_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class QRCodeScannerPageController {
    private final UserService userService;

    @GetMapping("/scan-QR-code")
    public String QRCodeScannerPage(Model model){
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByEmail(currentUserEmail);
        model.addAttribute("currentUser", currentUser);
        return "QRCodeScannerPage";
    }
}
