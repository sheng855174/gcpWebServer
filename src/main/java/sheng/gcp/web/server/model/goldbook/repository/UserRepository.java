package sheng.gcp.web.server.model.goldbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sheng.gcp.web.server.model.goldbook.entity.User;

@Repository
@Transactional(value = "goldbookTransactionManager")
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM user WHERE username=?1", nativeQuery = true)
    User findByUsername(String username);

    @Modifying
    @javax.transaction.Transactional
    @Query(value = "UPDATE user\n" +
            "SET login_state=?2\n" +
            "WHERE username=?1", nativeQuery = true)
    void updateLoginState(String username,int login_state);

}
