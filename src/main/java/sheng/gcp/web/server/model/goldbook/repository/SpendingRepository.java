package sheng.gcp.web.server.model.goldbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sheng.gcp.web.server.model.goldbook.entity.Spending;

import java.util.List;

@Repository
@Transactional(value = "goldbookTransactionManager")
public interface SpendingRepository extends JpaRepository<Spending, Long> {

    @Query(value = "SELECT spending.* \n" +
            "FROM spending,spendingowner \n" +
            "WHERE spending.id=spendingowner.spending_id\n" +
            "AND spendingowner.username=?1", nativeQuery = true)
    List<Spending> getSpendingList(String username);

    @Modifying
    @javax.transaction.Transactional
    @Query(value = "UPDATE spending \n" +
            "SET income=income+?2\n" +
            "WHERE spending.id=?1", nativeQuery = true)
    void updateIncome(Long spending_id,int income);
}
