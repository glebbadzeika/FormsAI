package com.example.spring_boot_backend.controller;

import com.example.spring_boot_backend.repository.FormRepository;
import com.example.spring_boot_backend.service.PdfGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pdf")
public class ReportController {

    @Autowired
    private PdfGenerationService pdfGenerationService;
    @Autowired
    private FormRepository formRepository;

    @GetMapping("/chart/form/{id}")
    public ResponseEntity<byte[]> getPieChartPdf(@PathVariable Long id) {
        if(!formRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        byte[] pdfContent = pdfGenerationService.generatePieChartPdf(id);
        return ResponseEntity
                .ok()
                .header("Content-Type", "application/pdf")
                .body(pdfContent);
    }
}