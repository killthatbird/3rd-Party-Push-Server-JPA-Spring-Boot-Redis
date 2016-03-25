package org.wlfek.push.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wlfek.push.domain.GcmApp;

public interface AppRepository extends JpaRepository<GcmApp, String>  {

}
