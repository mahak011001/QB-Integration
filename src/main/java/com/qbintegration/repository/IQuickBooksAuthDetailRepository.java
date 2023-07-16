package com.qbintegration.repository;

import com.qbintegration.model.QuickBooksAuthDetail;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Mahak Agrawal
 */

@Repository
public interface IQuickBooksAuthDetailRepository extends JpaRepository<QuickBooksAuthDetail, UUID> {

  @Query(value = "FROM QuickBooksAuthDetail WHERE partnerId = :partnerId AND id = (SELECT MAX(id) FROM QuickBooksAuthDetail WHERE partnerId = :partnerId)")
  Optional<QuickBooksAuthDetail> findByPartnerId(String partnerId);

  QuickBooksAuthDetail findByCsrf(String csrf);

}
