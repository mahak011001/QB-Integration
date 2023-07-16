package com.qbintegration.service.impl;

import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.config.OAuth2Config;
import com.intuit.oauth2.config.Scope;
import com.intuit.oauth2.data.BearerTokenResponse;
import com.qbintegration.config.OAuth2PlatformClientFactory;
import com.qbintegration.model.QuickBooksAuthDetail;
import com.qbintegration.service.IAuthService;
import com.qbintegration.service.IQuickBooksAuthDetailService;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author Mahak Agrawal
 */

@Service
public class AuthServiceImpl implements IAuthService {

  private static Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

  @Autowired
  private IQuickBooksAuthDetailService quickBooksAuthDetailService;

  @Autowired
  private OAuth2PlatformClientFactory factory;

  @Value("${quickbooks.oauth2.app.redirect.uri}")
  private String redirectUri;

  public String prepareUrlToConnect(String partnerId) throws Exception {
    OAuth2Config oauth2Config = factory.getOAuth2Config();
    String csrf = oauth2Config.generateCSRFToken();
    List<Scope> scopes = List.of(Scope.Accounting);
    String url = oauth2Config.prepareUrl(scopes, redirectUri, csrf);
    QuickBooksAuthDetail quickBooksAuthDetails = new QuickBooksAuthDetail(partnerId, csrf);
    quickBooksAuthDetailService.save(quickBooksAuthDetails);
    return url;
  }

  public QuickBooksAuthDetail addQuickBooksDetails(String authCode, String state, String realmId)
      throws Exception {
    if (StringUtils.isEmpty(state)) {
      throw new Exception("CSRF Token not found");
    }
    QuickBooksAuthDetail quickBooksAuthDetails = quickBooksAuthDetailService.findByCsrf(state);
    if (ObjectUtils.isEmpty(quickBooksAuthDetails)) {
      throw new Exception("Could not match CSRF token");
    }
    OAuth2PlatformClient client = factory.getOAuth2PlatformClient();
    BearerTokenResponse bearerTokenResponse = client.retrieveBearerTokens(authCode, redirectUri);
    quickBooksAuthDetails.setRealmId(realmId);
    quickBooksAuthDetails.setAuthCode(authCode);
    quickBooksAuthDetails.setCsrf(state);
    quickBooksAuthDetails.setAccessToken(bearerTokenResponse.getAccessToken());
    quickBooksAuthDetails.setRefreshToken(bearerTokenResponse.getRefreshToken());
    logger.info("Credential added successfully");
    quickBooksAuthDetails = quickBooksAuthDetailService.save(quickBooksAuthDetails);
    return quickBooksAuthDetails;
  }
}
