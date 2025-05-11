package id.co.bca.camsbatch.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.bca.camsbatch.service.BatchService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/batch")
@RequiredArgsConstructor
public class BatchController {
    private final BatchService batchService;

    @PostMapping("/run")
    public ResponseEntity<String> runBatch() {
        try {
            batchService.runBatchJob();
            return ResponseEntity.ok("Batch Job Has Been initiated");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error");
        }
       
    }
    
}
