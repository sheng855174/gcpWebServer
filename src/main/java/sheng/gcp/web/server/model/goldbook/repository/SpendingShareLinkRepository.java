package sheng.gcp.web.server.model.goldbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sheng.gcp.web.server.model.goldbook.entity.SpendingShareLink;



@Repository
@Transactional(value = "goldbookTransactionManager")
public interface SpendingShareLinkRepository extends JpaRepository<SpendingShareLink, String> {


}
