package com.qbintegration.controller;

import com.qbintegration.model.QuickBooksAuthDetail;
import com.qbintegration.service.IAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mahak Agrawal
 */

@RestController
@RequestMapping("/api/auth")
public class OAuthController {

  private static Logger logger = LoggerFactory.getLogger(OAuthController.class);

  @Autowired
  IAuthService authService;

  @GetMapping("/connect")
  public ResponseEntity<String> connect(@RequestParam String partnerId) {
    try {
      String url = authService.prepareUrlToConnect(partnerId);
      return ResponseEntity.status(HttpStatus.OK).body(url);
    } catch (Exception e) {
      logger.error("Exception in method :{} at line : {} while generating quickbooks connection url for Message: {}, ", e.getStackTrace()[0].getMethodName(), e.getStackTrace()[0].getLineNumber(), e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("QuickBooks login Url not prepared");
    }
  }

  @GetMapping("/oauth2redirect")
  public ResponseEntity<String> callBackFromOAuth(@RequestParam(value = "code") String authCode,
      @RequestParam(value = "state") String state,
      @RequestParam(value = "realmId") String realmId) {
    try {
      QuickBooksAuthDetail quickBooksAuthDetails = authService.addQuickBooksDetails(authCode, state,
          realmId);
      if (quickBooksAuthDetails != null) {
        logger.info("Credentials successfully stored in database for partner id {}", quickBooksAuthDetails.getPartnerId());
        return ResponseEntity.status(HttpStatus.OK).body("Connection created successfully");
      }else{
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Connection not created successfully");
      }
    } catch (Exception e) {
      logger.error("Exception in method :{} at line : {} while saving credential Message: {}, ", e.getStackTrace()[0].getMethodName(), e.getStackTrace()[0].getLineNumber(), e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
