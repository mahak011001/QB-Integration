package com.qbintegration.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

/**
 * @author Mahak Agrawal
 */
@Entity
@Table(name = "quickbooks_auth_details")
@Data
@NoArgsConstructor
public class QuickBooksAuthDetail {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  private UUID id;

  @Column(length = 40)
  private String partnerId;

  @Column(columnDefinition = "text")
  private String accessToken;

  @Column(length = 100)
  private String refreshToken;

  @Column(length = 50)
  private String realmId;

  @Column(length = 80)
  private String csrf;

  @Column(length = 100)
  private String authCode;

  public QuickBooksAuthDetail(String partnerId, String csrf) {
    this.partnerId = partnerId;
    this.csrf = csrf;
  }

  public QuickBooksAuthDetail(String partnerId, String accessToken, String refreshToken, String realmId, String csrf, String authCode) {
    this.partnerId = partnerId;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.realmId = realmId;
    this.csrf = csrf;
    this.authCode = authCode;
  }

}

