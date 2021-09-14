package sheng.gcp.web.server.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import sheng.gcp.web.server.model.goldbook.entity.User;
import sheng.gcp.web.server.service.goodbook.UserService;

@Component
@Aspect
public class NickNameAop {

    @Autowired
    private UserService userService;

    // 放入nickname
    @Before("execution(* sheng.gcp.web.server.controller.*.*(..))")
    public void doBeforeMethodUser(JoinPoint joinPoint) {
        Object[] obj = joinPoint.getArgs();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Model model = null;
        for (Object argItem : obj) {
            if (argItem instanceof Model) {
                model = (Model)argItem;
            }
        }
        if(model != null) {
            User user = userService.findByUsername(auth.getName());
            model.addAttribute("user", user);
        }
    }

}
