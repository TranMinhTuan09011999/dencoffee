package com.manage.repository;

import com.manage.model.IpAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IpAddressRepository extends JpaRepository<IpAddress, Long> {

  @Query(value = "SELECT i.* FROM ip_address i"
          + " ORDER BY i.ip_address_id ASC",
          nativeQuery = true)
  List<IpAddress> findAllByStatus();

}