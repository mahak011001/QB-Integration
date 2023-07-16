package com.qbintegration.controller;

import com.intuit.ipp.data.Item;
import com.qbintegration.service.IQuickBooksService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mahak Agrawal
 */

@RestController
@RequestMapping("/api")
public class InventoryController {

  private static Logger logger = LoggerFactory.getLogger(InventoryController.class);

  @Autowired
  private IQuickBooksService quickBooksService;

  @GetMapping("/items")
  public ResponseEntity<List<Item>> findAll(@RequestParam String partnerId) {
    try {
      List<Item> items = quickBooksService.getItems(partnerId);
      if (!CollectionUtils.isEmpty(items)) {
        logger.info("Inventory Item has been fetched successfully");
        return ResponseEntity.status(HttpStatus.OK).body(items);
      }
      return ResponseEntity.status(HttpStatus.OK).body(null);
    } catch (Exception exception) {
      logger.error("Exception in method :{} at line : {} while saving credential Message: {}, ", exception.getStackTrace()[0].getMethodName(), exception.getStackTrace()[0].getLineNumber(), exception.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
