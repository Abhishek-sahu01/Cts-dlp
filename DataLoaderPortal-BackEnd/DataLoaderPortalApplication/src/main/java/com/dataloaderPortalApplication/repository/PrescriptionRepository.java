package com.dataloaderPortalApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dataloaderPortalApplication.model.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer>{

}
