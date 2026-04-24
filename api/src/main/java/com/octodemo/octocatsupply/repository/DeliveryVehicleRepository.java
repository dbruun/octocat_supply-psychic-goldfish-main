package com.octodemo.octocatsupply.repository;

import com.octodemo.octocatsupply.model.DeliveryVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryVehicleRepository extends JpaRepository<DeliveryVehicle, Long> {
}
