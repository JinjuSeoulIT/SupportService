package com.app.medical_support.nursingtreatment.exception.handler;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.nursingtreatment.exception.MedicationRecordNotFoundException;
import com.app.medical_support.nursingtreatment.exception.RecordNotFoundException;
import com.app.medical_support.nursingtreatment.exception.TreatmentResultNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RecordExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleRecordNotFound(RecordNotFoundException ex) {
        log.warn("RecordNotFoundException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(MedicationRecordNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleMedicationRecordNotFound(MedicationRecordNotFoundException ex) {
        log.warn("MedicationRecordNotFoundException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }

    @ExceptionHandler(TreatmentResultNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleTreatmentResultNotFound(TreatmentResultNotFoundException ex) {
        log.warn("TreatmentResultNotFoundException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }
}
