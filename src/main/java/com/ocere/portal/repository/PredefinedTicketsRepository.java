package com.ocere.portal.repository;

import com.ocere.portal.enums.ProductType;
import com.ocere.portal.model.PredefinedTicketCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredefinedTicketsRepository extends JpaRepository<PredefinedTicketCollection, Integer> {
    PredefinedTicketCollection findByProductType(ProductType productType);
}
