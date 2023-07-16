package com.qbintegration.service;

import com.intuit.ipp.data.Item;
import com.intuit.ipp.exception.FMSException;
import com.qbintegration.model.QuickBooksAuthDetail;
import java.util.List;

public interface IQuickBooksService {
  List<Item> getItems(String partnerId) throws FMSException;
  void initializeDataService(QuickBooksAuthDetail quickBooksAuthDetails) throws FMSException;
  }
