package sheng.gcp.web.server.service.goodbook;


import org.springframework.security.core.userdetails.UserDetailsService;
import sheng.gcp.web.server.model.goldbook.entity.User;


public interface UserService extends UserDetailsService {

    User findByUsername(String username);
    User save(User user);
    void updateLoginState(String username,int login_state);

}
