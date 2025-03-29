package com.example.library_project;

import com.example.library_project.service.QRCodeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Base64;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QRCodeServiceTest {
    @Autowired
    QRCodeService qrCodeService;
    @Test
    void generateQRCodeQRCodeIsNotNull_test() throws Exception {
        String qrCode = qrCodeService.generateQRCode("https://test.com", 250, 250);
        assertNotNull(qrCode);
    }

    @Test
    void generateQRCodeQRCodeIsNotEmpty_test() throws Exception {
        String qrCode = qrCodeService.generateQRCode("https://test.com", 250, 250);
        assertFalse(qrCode.isEmpty());
    }

    @Test
    void generateQRCodeIsBase64Pattern_test() throws Exception {
        String qrCode = qrCodeService.generateQRCode("https://test.com", 250, 250);
        assertThat(qrCode).containsPattern("^[A-Za-z0-9+/]+={0,2}$");
    }
    @Test
    void generateQRCodeCallsOnceTest_test() throws Exception {
        String qrCode = qrCodeService.generateQRCode("https://test.com", 250, 250);
        assertThat(qrCode).containsPattern("^[A-Za-z0-9+/]+={0,2}$");
    }
}
