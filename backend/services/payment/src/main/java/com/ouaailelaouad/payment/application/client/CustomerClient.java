package com.ouaailelaouad.payment.application.client;

import com.ouaailelaouad.payment.application.dto.CustomerValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "customer-service",
        url = "${application.config.customer-url}" // Will be configured in application.yml
)
public interface CustomerClient {

    @GetMapping("/api/v1/customers/exists/{customer-id}")
    Boolean existsById(@PathVariable("customer-id") String customerId);

    @GetMapping("/api/v1/customers/{customer-id}")
    CustomerValidationResponse findById(@PathVariable("customer-id") String customerId);
}