package com.qbintegration.service;

import com.qbintegration.model.QuickBooksAuthDetail;
import java.util.Optional;

public interface IQuickBooksAuthDetailService {
  Optional<QuickBooksAuthDetail> findByPartnerId(String partnerId);
  QuickBooksAuthDetail save(QuickBooksAuthDetail quickBooksAuthDetail);

  QuickBooksAuthDetail findByCsrf(String csrf);
}
