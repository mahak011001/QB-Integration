package com.qbintegration.service;


import com.intuit.oauth2.exception.InvalidRequestException;
import com.qbintegration.model.QuickBooksAuthDetail;

public interface IAuthService {
  String prepareUrlToConnect(String partnerId) throws Exception;
  QuickBooksAuthDetail addQuickBooksDetails(String authCode,String state,String realmId)
      throws Exception;
}
