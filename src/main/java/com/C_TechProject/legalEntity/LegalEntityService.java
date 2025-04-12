package com.C_TechProject.legalEntity;

import com.C_TechProject.bank.Bank;
import com.C_TechProject.bank.BankRepository;
import com.C_TechProject.bankAccount.BankAccount;
import com.C_TechProject.bankAccount.BankAccountRepository;
import com.C_TechProject.user.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LegalEntityService {
    private final LegalEntityRepository legalEntityRepository;
    private final BankAccountRepository bankAccountRepository;


    public LegalEntity addEntity(LegalEntity legalEntity) {
        return legalEntityRepository.save(legalEntity);
    }
    public List<LegalEntity> findAllEntities() {
        return legalEntityRepository.findAll();
    }

    public LegalEntity findById(Integer id) {
        Optional<LegalEntity> legalEntity = legalEntityRepository.findById(id);
        if (legalEntity.isPresent()) {
            return legalEntity.get();
        } else {
            throw new RuntimeException("Legal Entity not found with ID: " + id);
        }
    }
    public LegalEntity updateLegalEntity(Integer id, LegalEntity newLegalEntity) {
        LegalEntity legalEntity = legalEntityRepository.findLegalEntityById(id)
                .orElseThrow(() -> new UserNotFoundException("Legal Entity not found with ID: " + id));

        if (newLegalEntity.getCode() != null && !newLegalEntity.getCode().equals(legalEntity.getCode())) {
            List<LegalEntity> legalEntitiesWithSameCode = legalEntityRepository.findEntityByCode(newLegalEntity.getCode());
            legalEntitiesWithSameCode.removeIf(l -> l.getId().equals(id));
            if (!legalEntitiesWithSameCode.isEmpty()) {
                throw new UserNotFoundException("Legal Entity code already exists in other legal entities");
            }
            legalEntity.setCode(newLegalEntity.getCode());
        }
        if (newLegalEntity.getNameEntity() != null && !newLegalEntity.getNameEntity().equals(legalEntity.getNameEntity())) {
            List<LegalEntity> legalEntitiesWithSameName = legalEntityRepository.findEntityByNameEntity(newLegalEntity.getNameEntity());
            legalEntitiesWithSameName.removeIf(l -> l.getId().equals(id));
            if (!legalEntitiesWithSameName.isEmpty()) {
                throw new UserNotFoundException("Legal Entity name already exists in other legal entities");
            }
            legalEntity.setNameEntity(newLegalEntity.getNameEntity());
        }

        legalEntityRepository.save(legalEntity);
        return legalEntity;
    }

    public void deleteLegalEntity(Integer id) {
        LegalEntity legalEntity = legalEntityRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Legal entity not found with ID: " + id));

        List<BankAccount> bankAccounts = bankAccountRepository.findByLegalEntity(legalEntity);
        if (!bankAccounts.isEmpty()) {
            throw new RuntimeException("Cannot delete legal entity with related bank accounts.");
        }

        legalEntityRepository.delete(legalEntity);
    }


}
