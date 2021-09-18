package sheng.gcp.web.server.model.goldbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sheng.gcp.web.server.model.goldbook.entity.Spending;
import sheng.gcp.web.server.model.goldbook.entity.SpendingOwner;

import java.util.List;


@Repository
@Transactional(value = "goldbookTransactionManager")
public interface SpendingOwnerRepository extends JpaRepository<SpendingOwner, Long> {

    @Query(value = "SELECT user.nickname\n" +
            "FROM spendingowner,user \n" +
            "WHERE spending_id=?1\n" +
            "AND spendingowner.username=user.username;\n", nativeQuery = true)
    List<String> getSpendingOwnList(Long spending_id);

    @Query(value = "SELECT * \n" +
            "FROM spendingowner \n" +
            "WHERE spendingowner.spending_id=?1\n" +
            "AND username=?2", nativeQuery = true)
    SpendingOwner getSpendingOwn(Long spending_id,String username);

}
