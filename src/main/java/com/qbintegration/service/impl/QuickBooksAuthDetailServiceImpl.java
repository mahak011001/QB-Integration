package com.qbintegration.service.impl;

import com.qbintegration.model.QuickBooksAuthDetail;
import com.qbintegration.repository.IQuickBooksAuthDetailRepository;
import com.qbintegration.service.IQuickBooksAuthDetailService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mahak Agrawal
 */

@Service
public class QuickBooksAuthDetailServiceImpl implements
    IQuickBooksAuthDetailService {
  @Autowired
  private IQuickBooksAuthDetailRepository quickBooksAuthDetailRepository;
  public Optional<QuickBooksAuthDetail> findByPartnerId(String partnerId){
    return quickBooksAuthDetailRepository.findByPartnerId(partnerId);
  }

  public QuickBooksAuthDetail save(QuickBooksAuthDetail quickBooksAuthDetail){
    return quickBooksAuthDetailRepository.save(quickBooksAuthDetail);
  }

  public QuickBooksAuthDetail findByCsrf(String csrf){
    return quickBooksAuthDetailRepository.findByCsrf(csrf);
  }

}
