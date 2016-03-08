package org.wlfek.push.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wlfek.push.domain.GcmAppInfo;

public interface GcmInfoRepository extends JpaRepository<GcmAppInfo, String>  {

}
