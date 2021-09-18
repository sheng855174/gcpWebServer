package sheng.gcp.web.server.model.goldbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sheng.gcp.web.server.model.goldbook.entity.IncomeType;
import sheng.gcp.web.server.model.goldbook.entity.SpendingOwner;

import java.util.List;


@Repository
@Transactional(value = "goldbookTransactionManager")
public interface IncomeTypeRepository extends JpaRepository<IncomeType, Long> {

    @Query(value = "SELECT * \n" +
            "FROM incometype \n" +
            "WHERE type=?1", nativeQuery = true)
    List<IncomeType> getType(int type);

}
