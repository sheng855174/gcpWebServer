package sheng.gcp.web.server.service.goodbook;

import sheng.gcp.web.server.model.goldbook.entity.IncomeType;
import sheng.gcp.web.server.model.goldbook.entity.Spending;
import sheng.gcp.web.server.model.goldbook.entity.SpendingItem;
import sheng.gcp.web.server.model.goldbook.entity.SpendingOwner;

import java.util.List;

public interface SpendingService {

    Spending save(Spending spending);
    IncomeType save(IncomeType incomeType);
    SpendingItem save(SpendingItem spendingItem);
    List<Spending> getSpendingList(String username);
    List<String> getSpendingOwnList(Long spending_id);
    Spending findOneSpending(Long id);
    SpendingOwner getSpendingOwn(Long spending_id,String username);
    void updateIncome(Long spending_id,int income);
    List<Object[]> getSpendingItemList(Long spending_id,int month);
}
