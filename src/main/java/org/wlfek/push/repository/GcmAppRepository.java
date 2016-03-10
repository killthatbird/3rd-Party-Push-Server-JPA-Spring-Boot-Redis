package org.wlfek.push.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wlfek.push.domain.GcmApp;

public interface GcmAppRepository extends JpaRepository<GcmApp, String>  {

}
