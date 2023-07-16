package com.qbintegration.config;

import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.config.Environment;
import com.intuit.oauth2.config.OAuth2Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Mahak Agrawal
 */
@Service
public class OAuth2PlatformClientFactory {

  OAuth2PlatformClient client;

  OAuth2Config oauth2Config;

  @Value("${quickbooks.oauth2.app.client.id}")
  private String quickBookClientId;

  @Value("${quickbooks.oauth2.app.client.secret}")
  private String quickBookClientSecret;

  @Value("${quickbooks.target.environment}")
  Environment environment;

  @PostConstruct
  public void init() {
    oauth2Config = new OAuth2Config.OAuth2ConfigBuilder(quickBookClientId, quickBookClientSecret)
        .callDiscoveryAPI(environment)
        .buildConfig();
    client = new OAuth2PlatformClient(oauth2Config);
  }

  public OAuth2PlatformClient getOAuth2PlatformClient() {
    return client;
  }

  public OAuth2Config getOAuth2Config() {
    return oauth2Config;
  }

}
