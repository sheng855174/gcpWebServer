package sheng.gcp.web.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import sheng.gcp.web.server.model.goldbook.entity.User;
import sheng.gcp.web.server.service.goodbook.UserService;

@Slf4j
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        // 登入次數驗證
        if(user.getLogin_state() >= 10){
            log.error(username + " 帳號鎖定");
            throw new BadCredentialsException("帳號鎖定");
        }
        // 密碼驗證
        Object salt = null;
        if (this.getSaltSource() != null) {
            salt = this.getSaltSource().getSalt(userDetails);
        }
        if (authentication.getCredentials() == null) {
            log.error(username + " 密碼錯誤 : " + user.getLogin_state());
            throw new BadCredentialsException("密碼錯誤");
        }
        String presentedPassword = authentication.getCredentials().toString();
        if(!this.getPasswordEncoder().isPasswordValid(userDetails.getPassword(), presentedPassword, salt)){
            log.error(username + " 密碼錯誤 : " +  + user.getLogin_state());
            throw new BadCredentialsException("密碼錯誤");
        }
        //登入成功
        userService.updateLoginState(user.getUsername(), 0);

    }
}
