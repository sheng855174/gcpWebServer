package sheng.gcp.web.server.service.goodbook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sheng.gcp.web.server.model.goldbook.entity.*;
import sheng.gcp.web.server.model.goldbook.repository.*;

import java.util.List;

@Service
@Slf4j
public class SpendingServiceImpl implements SpendingService{

    @Autowired
    private SpendingRepository spendingRepository;

    @Autowired
    private SpendingOwnerRepository spendingOwnerRepository;

    @Autowired
    private IncomeTypeRepository incomeTypeRepository;

    @Autowired
    private SpendingItemRepository spendingItemRepository;

    @Autowired
    private SpendingShareLinkRepository spendingShareLinkRepository;

    @Override
    public Spending save(Spending spending){
        return spendingRepository.save(spending);
    }

    @Override
    public IncomeType save(IncomeType incomeType){
        return incomeTypeRepository.save(incomeType);
    }

    @Override
    public SpendingItem save(SpendingItem spendingItem){
        return spendingItemRepository.save(spendingItem);
    }

    @Override
    public List<Spending> getSpendingList(String username){
        return spendingRepository.getSpendingList(username);
    }

    @Override
    public List<String> getSpendingOwnList(Long spending_id){
        return spendingOwnerRepository.getSpendingOwnList(spending_id);
    }

    @Override
    public Spending findOneSpending(Long id){
        return spendingRepository.findOne(id);
    }

    @Override
    public SpendingOwner getSpendingOwn(Long spending_id, String username){
        return spendingOwnerRepository.getSpendingOwn(spending_id,username);
    }

    @Override
    public void updateIncome(Long spending_id,int income){
        spendingRepository.updateIncome(spending_id,income);
    }

    @Override
    public List<Object[]> getSpendingItemList(Long spending_id,int month){
        return spendingItemRepository.getSpendingItemList(spending_id,month);
    }

    @Override
    public SpendingShareLink save(SpendingShareLink spendingShareLink){
        return spendingShareLinkRepository.save(spendingShareLink);
    }

    @Override
    public SpendingShareLink findOneSpendingShareLink(String id){
        return spendingShareLinkRepository.findOne(id);
    }

    @Override
    public SpendingOwner save(SpendingOwner spendingShareLink){
        return spendingOwnerRepository.save(spendingShareLink);
    }
}
