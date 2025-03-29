package com.example.library_project;

import com.example.library_project.service.QRCodeService;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.net.http.HttpRequest;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class QRCodeGeneratorControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    QRCodeService qrCodeService;

    @Test
    void generateQRCodeGetRequestSuccessfullyGenerateQRCode_test() throws Exception {
        mockMvc.perform(get("/generate-QR-code?url=http://localhost:8080/test"))
                .andExpect(status().isOk());
        verify(qrCodeService, times(1)).generateQRCode(any(), any(), any());
    }

    @Test
    void generateQRCodeGetRequestUnsuccessfulAttemptToGenerateQRCode_test() throws Exception {
        mockMvc.perform(get("/generate-QR-code?url=http://testdklwaljdkladjfllkasjd:8080/test"))
                .andExpect(status().isOk());
        verify(qrCodeService, times(0)).generateQRCode(any(), any(), any());
    }
}
