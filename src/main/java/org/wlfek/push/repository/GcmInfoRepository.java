package org.wlfek.push.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wlfek.push.domain.GcmInfo;

public interface GcmInfoRepository extends JpaRepository<GcmInfo, String>  {

}
