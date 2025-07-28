package com.ags.spring_ecommerce_service.controller;

import com.ags.spring_ecommerce_service.dto.AddressDto;
import com.ags.spring_ecommerce_service.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
@Tag(name = "Address", description = "Address management operations")
public class AddressController {
  private final AddressService addressService;

  @PostMapping
  @Operation(summary = "Create a new address", description = "Register a new address in the system")
  public ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto addressDto) {
    var createdAddress = addressService.createAddress(addressDto);
    return ResponseEntity.ok(createdAddress);
  }

  @PutMapping("/{id}")
  @Operation(
      summary = "Update an existing address",
      description = "Update the details of an existing address")
  public ResponseEntity<AddressDto> updateAddress(
      @PathVariable("id") UUID id, @RequestBody AddressDto addressDto) {
    var updatedAddress = addressService.updateAddress(id, addressDto);
    return ResponseEntity.ok(updatedAddress);
  }

  @DeleteMapping
  @Operation(summary = "Delete an address", description = "Remove an address from the system")
  public ResponseEntity<Void> deleteAddress(@RequestParam("id") UUID id) {
    addressService.deleteAddress(id);
    return ResponseEntity.noContent().build();
  }
}
