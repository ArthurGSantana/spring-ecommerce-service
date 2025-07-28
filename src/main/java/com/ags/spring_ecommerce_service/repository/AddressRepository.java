package com.ags.spring_ecommerce_service.repository;

import com.ags.spring_ecommerce_service.entity.Address;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {}
