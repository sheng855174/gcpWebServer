package sheng.gcp.web.server.service.goodbook;

import sheng.gcp.web.server.model.goldbook.entity.*;

import java.util.List;

public interface SpendingService {

    Spending save(Spending spending);
    IncomeType save(IncomeType incomeType);
    SpendingItem save(SpendingItem spendingItem);
    SpendingShareLink save(SpendingShareLink spendingShareLink);
    SpendingOwner save(SpendingOwner spendingShareLink);
    List<Spending> getSpendingList(String username);
    List<String> getSpendingOwnList(Long spending_id);
    Spending findOneSpending(Long id);
    SpendingShareLink findOneSpendingShareLink(String link);
    SpendingOwner getSpendingOwn(Long spending_id,String username);
    void updateIncome(Long spending_id,int income);
    List<Object[]> getSpendingItemList(Long spending_id,int month);
}
