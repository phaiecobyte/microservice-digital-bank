package com.phai.customerservice.controllers;

import com.phai.customerservice.exception.AppException;
import com.phai.customerservice.models.Customer;
import com.phai.customerservice.models.IdentityCard;
import com.phai.customerservice.util.ResponseBuilder;
import com.phai.customerservice.services.impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/customer")
public class CustomerController {
    private final CustomerServiceImpl service;

    @GetMapping("/{customerId}")
    public ResponseEntity<Object> getCustomerById(@PathVariable String customerId){
        try {
            log.info("Processing get customer by ID request for customerId: {}", customerId);
            var data = service.getCustomerById(customerId);
            return ResponseBuilder.success(data);
        }catch (AppException e){
            log.error("Application error occurred {}",e.getMessage());
            return ResponseBuilder.error(e);
        }catch (Exception e){
            log.error("Error occurred during get customer {}", customerId);
            return ResponseBuilder.error(e);
        }finally {
            log.info("Process get customer by customer id completed");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody Customer req) {
        log.info("Processing customer registration request");
        try {
            var data = service.register(req);
            log.info("Customer registered successfully with ID: {}", data.getId());
            return ResponseBuilder.created(data);
        }
        catch (AppException e){
            log.error("Application error occurred", e);
            return ResponseBuilder.error(e);
        }
        catch (Exception e) {
            log.error("Error occurred during customer registration: {}", e.getMessage(), e);
            return ResponseBuilder.error(e);
        } finally {
            log.info("Customer registration process completed");
        }
    }
    
    @PutMapping("/{customerId}/identity-card")
    public ResponseEntity<Object> updateIdentityCard(@PathVariable String customerId, @Valid @RequestBody IdentityCard identityCard) {
        log.info("Processing identity card update request for customer:{}",customerId);
        try {
            Customer customer = service.updateIdentityCard(customerId,identityCard);
            log.info("Identity card updated successfully for customer {}",customerId);
            return ResponseBuilder.updated(customer);
        } catch (AppException e) {
            log.error("Application error while update customer {}", e.getMessage());
            return ResponseBuilder.error(e);
        } catch (Exception e) {
            log.error("Error updating identity card: {}", e.getMessage(), e);
            return ResponseBuilder.error(e);
        } finally {
            log.info("Process update identity card for customer {} completed",customerId);
        }
    }
    
    @GetMapping("/{customerId}/identity-cards")
    public ResponseEntity<Object> getIdentityCardHistory(@PathVariable String customerId) {
        log.info("Retrieving identity card history for customer: {}", customerId);
        try {
            var data = service.getIdentityCardByCustomer(customerId);
            return ResponseBuilder.success(data);
        } catch (AppException e){
            log.error("Application error while identity card history {}" ,e.getMessage());
            return ResponseBuilder.error(e);
        } catch (Exception e) {
            log.error("Error retrieving identity card history: {}", e.getMessage(), e);
            return ResponseBuilder.error(e);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getAllCustomer(Pageable pageable) {
        try {
            log.info("Processing get all customer request");
            var data = service.getAllCustomer(pageable);
            return ResponseBuilder.paged(data);
        } catch (AppException e) {
            log.error("Application error occurred: {}", e.getMessage());
            return ResponseBuilder.error(e);
        } catch (Exception e) {
            log.error("Error occurred while retrieving all customers: {}", e.getMessage(), e);
            return ResponseBuilder.error(e);
        } finally {
            log.info("Get all customer process completed");
        }
    }

    @PostMapping(value = "/{customerId}/profile-photo", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    @io.swagger.v3.oas.annotations.Operation(summary = "Update customer profile photo", description = "Uploads and assigns a new profile photo to the customer")
    public ResponseEntity<Object> updateProfilePhoto(@PathVariable String customerId, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Profile photo file to upload", required = true) @RequestParam("photo") MultipartFile photo) {
        log.info("Processing profile photo update request for customer: {}", customerId);
        try {
            Customer customer = service.updateProfilePhoto(customerId, photo);
            log.info("Profile photo updated successfully for customer: {}", customerId);
            return ResponseBuilder.updated(customer);
        } catch (AppException e) {
            log.error("Application error while updating profile photo: {}", e.getMessage());
            return ResponseBuilder.error(e);
        } catch (Exception e) {
            log.error("Error updating profile photo: {}", e.getMessage(), e);
            return ResponseBuilder.error(e);
        } finally {
            log.info("Process update profile photo for customer {} completed", customerId);
        }
    }
}
