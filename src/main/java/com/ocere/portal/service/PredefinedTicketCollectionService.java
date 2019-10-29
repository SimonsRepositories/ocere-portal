package com.ocere.portal.service;

import com.ocere.portal.enums.ProductType;
import com.ocere.portal.model.PredefinedTicketCollection;

import java.util.List;
import java.util.Optional;

public interface PredefinedTicketCollectionService {

    PredefinedTicketCollection findByProductType(ProductType productType);

    List<PredefinedTicketCollection> findAll();

    Optional<PredefinedTicketCollection> findById(int id);
}
