package org.wlfek.push.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wlfek.push.domain.GcmSend;

public interface GcmSendRepository extends JpaRepository<GcmSend, Integer> {

}
