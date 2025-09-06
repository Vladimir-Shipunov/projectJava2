package com.mpt.journal.service;

import com.mpt.journal.entity.PassportEntity;
import com.mpt.journal.repository.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class PassportServiceImpl implements PassportService {

    @Autowired
    private PassportRepository passportRepository;

    @Override
    public List<PassportEntity> getAll() {
        return passportRepository.findAll();
    }

    @Override
    public PassportEntity getById(Long id) {
        return passportRepository.findById(id).orElse(null);
    }

    @Override
    public void save(PassportEntity passport) {
        passportRepository.save(passport);
    }

    @Override
    public void delete(Long id) {
        passportRepository.deleteById(id);
    }

    @Override
    public List<PassportEntity> search(String type, String value) {
        if (value == null || value.trim().isEmpty()) {
            return getAll();
        }
        
        switch (type.toLowerCase()) {
            case "ownername":
                return passportRepository.findByOwnerNameContainingIgnoreCase(value);
            case "passportnumber":
                return passportRepository.findByPassportNumberContainingIgnoreCase(value);
            case "issuedate":
                return passportRepository.findByIssueDateContainingIgnoreCase(value);
            case "issuedby":
                return passportRepository.findByIssuedByContainingIgnoreCase(value);
            default:
                return getAll();
        }
    }
}