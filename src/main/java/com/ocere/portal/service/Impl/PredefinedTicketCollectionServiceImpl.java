package com.ocere.portal.service.Impl;

import com.ocere.portal.enums.ProductType;
import com.ocere.portal.model.PredefinedTicketCollection;
import com.ocere.portal.repository.PredefinedTicketsRepository;
import com.ocere.portal.service.PredefinedTicketCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PredefinedTicketCollectionServiceImpl implements PredefinedTicketCollectionService {

    private PredefinedTicketsRepository predefinedTicketsRepository;

    @Autowired
    public PredefinedTicketCollectionServiceImpl(PredefinedTicketsRepository predefinedTicketsRepository) {
        this.predefinedTicketsRepository = predefinedTicketsRepository;
    }

    @Override
    public PredefinedTicketCollection findByProductType(ProductType productType) {
        return this.predefinedTicketsRepository.findByProductType(productType);
    }

    @Override
    public List<PredefinedTicketCollection> findAll() {
        return this.predefinedTicketsRepository.findAll();
    }

    @Override
    public Optional<PredefinedTicketCollection> findById(int id) {
        return this.predefinedTicketsRepository.findById(id);
    }
}
