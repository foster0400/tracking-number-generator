package com.teleport.tracking.controller.api;

import com.teleport.tracking.controller.model.APIBaseResponse;
import com.teleport.tracking.dto.TrackingNumberResponse;
import com.teleport.tracking.service.TrackingNumberService;
import com.teleport.tracking.validation.ValidCountryCode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/next-tracking-number")
public class TrackingNumberController {

    @Autowired
    private TrackingNumberService service;

    @GetMapping
    public APIBaseResponse<TrackingNumberResponse> generateTrackingNumber(
            @RequestParam(name = "") @ValidCountryCode @Valid String originCountryId,
            @RequestParam @ValidCountryCode String destinationCountryId,
            @RequestParam BigDecimal weight,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime createdAt,
            @RequestParam UUID customerId,
            @RequestParam String customerName,
            @RequestParam String customerSlug
    ) {
        weight = weight.setScale(3, RoundingMode.UP);
        return new APIBaseResponse<>(service.generateTrackingNumber(
                originCountryId,
                destinationCountryId,
                weight,
                createdAt,
                customerId,
                customerName,
                customerSlug
        ));
    }
}
