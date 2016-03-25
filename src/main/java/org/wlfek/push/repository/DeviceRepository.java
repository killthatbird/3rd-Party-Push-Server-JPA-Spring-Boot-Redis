package org.wlfek.push.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wlfek.push.domain.GcmDevice;

public interface DeviceRepository extends JpaRepository<GcmDevice, Long>{

}
