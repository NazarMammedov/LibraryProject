package com.example.library_project;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
public class QRCodeScannerPageControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void QRCodeScannerPage_test() throws Exception{
        mockMvc.perform(get("/scan-QR-code")).andExpect(status().isOk());
    }
}
