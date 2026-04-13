package com.octodemo.octocatsupply.repository;

import com.octodemo.octocatsupply.model.Headquarters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadquartersRepository extends JpaRepository<Headquarters, Long> {
}
