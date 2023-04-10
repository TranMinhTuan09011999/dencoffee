package com.manage.repository;

import com.manage.model.IpAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpAddressRepository extends JpaRepository<IpAddress, Long> {
}