package org.wlfek.push.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.wlfek.push.domain.GcmSend;

public interface GcmSendRepository extends JpaRepository<GcmSend, Integer> {
	List<GcmSend> findByStatusCode(int statusCode);
	
	@Modifying(clearAutomatically=true)
	@Query("update GcmSend set statusCode = ?1 where statusCode = 1")
	int setStatusSending(int statusCode);
	//	getEntityManager().createQuery(jpql)
	//	.setParameter("statusCode", statusCode)
	//	.executeUpdate();
	
}
