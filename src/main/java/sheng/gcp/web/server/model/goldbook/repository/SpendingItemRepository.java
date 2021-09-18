package sheng.gcp.web.server.model.goldbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sheng.gcp.web.server.model.goldbook.entity.SpendingItem;
import sheng.gcp.web.server.model.goldbook.entity.SpendingOwner;

import java.util.List;


@Repository
@Transactional(value = "goldbookTransactionManager")
public interface SpendingItemRepository extends JpaRepository<SpendingItem, Long> {

    @Query(value = "SELECT DATE(spendingitem.time) as date\n" +
            ",incometype.description as type_name\n" +
            ",spendingitem.description as remark\n" +
            ",spendingitem.income as income\n" +
            ",spendingitem.nickname as nickname\n" +
            ",incometype.type\n" +
            "FROM spendingitem,incometype\n" +
            "WHERE spendingitem.type_id=incometype.id\n" +
            "AND spendingitem.spending_id=?1\n" +
            "AND MONTH(spendingitem.time)=?2\n" +
            "ORDER BY spendingitem.time DESC", nativeQuery = true)
    List<Object[]> getSpendingItemList(Long spending_id,int month);

}
