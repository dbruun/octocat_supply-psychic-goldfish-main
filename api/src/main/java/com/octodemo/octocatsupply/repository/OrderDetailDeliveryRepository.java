package com.octodemo.octocatsupply.repository;

import com.octodemo.octocatsupply.model.OrderDetailDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailDeliveryRepository extends JpaRepository<OrderDetailDelivery, Long> {
}
