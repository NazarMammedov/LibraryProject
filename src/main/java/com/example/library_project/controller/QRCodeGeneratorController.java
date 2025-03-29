package com.example.library_project.controller;


import com.example.library_project.service.QRCodeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

@Controller
@RequiredArgsConstructor
public class QRCodeGeneratorController {
    private final QRCodeService qrCodeService;

    @GetMapping("/generate-QR-code")
    public String generateQRCode(@RequestParam("url") String url, Model model) throws Exception {
        String allowed_url = "http://localhost:8080";
        if (!url.startsWith(allowed_url)){
            model.addAttribute("error","Det har oppstått en feil, du har oppgitt en feil URL-adresse til QR-koden");
            return "QRCodeGeneratedPage";
        }
        model.addAttribute("generatedQRCodeImage", "data:image/png;base64,"+qrCodeService.generateQRCode(url, 250, 250));
        return "QRCodeGeneratedPage";
    }
}
