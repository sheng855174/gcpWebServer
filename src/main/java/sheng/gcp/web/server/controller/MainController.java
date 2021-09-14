package sheng.gcp.web.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import sheng.gcp.web.server.common.LoggerOutputFormat;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class MainController {

    @GetMapping({"/", "/index","/main/index"})
    public String index(HttpServletRequest request) {
        LoggerOutputFormat.api_before(request, "/index");
        return "main/index";
    }

}
