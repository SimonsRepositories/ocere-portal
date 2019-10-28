package com.ocere.portal.service.Impl;

import com.ocere.portal.model.Contact;
import com.ocere.portal.repository.ContactRepository;
import com.ocere.portal.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    private ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }
}
