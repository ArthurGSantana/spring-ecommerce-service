package com.ags.spring_ecommerce_service.service;

import com.ags.spring_ecommerce_service.dto.AddressDto;
import com.ags.spring_ecommerce_service.entity.Address;
import com.ags.spring_ecommerce_service.exception.NotFoundException;
import com.ags.spring_ecommerce_service.repository.AddressRepository;
import com.ags.spring_ecommerce_service.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
  private final AddressRepository addressRepository;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  public AddressDto createAddress(AddressDto addressDto) {
    var user =
        userRepository
            .findById(addressDto.getUserId())
            .orElseThrow(() -> new NotFoundException("User not found"));

    var address = modelMapper.map(addressDto, Address.class);

    address.setUser(user);

    address = addressRepository.save(address);

    return modelMapper.map(address, AddressDto.class);
  }

  public AddressDto updateAddress(UUID id, AddressDto addressDto) {
    var address =
        addressRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Address not found"));

    address.setStreet(addressDto.getStreet());
    address.setNumber(addressDto.getNumber());
    address.setCity(addressDto.getCity());
    address.setState(addressDto.getState());
    address.setZipCode(addressDto.getZipCode());

    address = addressRepository.save(address);

    return modelMapper.map(address, AddressDto.class);
  }

  public void deleteAddress(UUID id) {
    var address =
        addressRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Address not found"));

    addressRepository.delete(address);
  }
}
