package sheng.gcp.web.server.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import javax.servlet.http.HttpServletRequest;

@Slf4j
public class LoggerOutputFormat {

    public static void api_before(HttpServletRequest request,String api){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String ip = HttpCommon.getIpAddr(request);
        log.info(auth.getName() + "(" + ip + ") request " + api);
    }

    public static void api_error(HttpServletRequest request,String api,Exception e){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String ip = HttpCommon.getIpAddr(request);
        log.error(auth.getName() + "(" + ip + ") request " + api + " 異常 : " + e.getMessage(),e);
    }

}
