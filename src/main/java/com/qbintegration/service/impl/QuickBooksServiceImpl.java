package com.qbintegration.service.impl;

import static com.intuit.ipp.query.GenerateQuery.$;
import static com.intuit.ipp.query.GenerateQuery.select;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Item;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.query.GenerateQuery;
import com.intuit.ipp.security.OAuth2Authorizer;
import com.intuit.ipp.services.DataService;
import com.intuit.ipp.services.QueryResult;
import com.intuit.ipp.util.Config;
import com.qbintegration.model.QuickBooksAuthDetail;
import com.qbintegration.service.IQuickBooksAuthDetailService;
import com.qbintegration.service.IQuickBooksService;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Mahak Agrawal
 */

@Service
public class QuickBooksServiceImpl implements IQuickBooksService {

  @Value("${quickbooks.intuit.accounting.api.host}")
  private String intuitAccountingAPIHost;

  @Value("${quickbooks.company.api.url}")
  private String quickbooksCompanyApiUrl;

  private DataService quickBooksDataService;

  @Override
  public void initializeDataService(QuickBooksAuthDetail quickBooksAuthDetails)
      throws FMSException {
    String realmId = quickBooksAuthDetails.getRealmId();
    String accessToken = quickBooksAuthDetails.getAccessToken();
    String url = intuitAccountingAPIHost + quickbooksCompanyApiUrl;
    Config.setProperty(Config.BASE_URL_QBO, url);
    OAuth2Authorizer oauth = new OAuth2Authorizer(accessToken);
    Context context = new Context(oauth, ServiceType.QBO, realmId);
    quickBooksDataService = new DataService(context);
  }

  @Autowired
  private IQuickBooksAuthDetailService quickBooksAuthDetailService;

  public List<Item> getItems(String partnerId) throws FMSException {
    Optional<QuickBooksAuthDetail> optionalQuickBooksAuthDetail = quickBooksAuthDetailService.findByPartnerId(
        partnerId);
    List<Item> items = new LinkedList<>();
    if (optionalQuickBooksAuthDetail.isPresent()) {
      QuickBooksAuthDetail quickBooksAuthDetail = optionalQuickBooksAuthDetail.get();
      initializeDataService(quickBooksAuthDetail);
      Item item = GenerateQuery.createQueryEntity(Item.class);
      String query = select($(item)).orderBy($(item.getId())).take(1000).generate();
      QueryResult result = quickBooksDataService.executeQuery(query);
      if (!result.getEntities().isEmpty()) {
        items.addAll((List<Item>) result.getEntities());
      }
    }
    return items;
  }
}
