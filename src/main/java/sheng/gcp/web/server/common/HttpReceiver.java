package sheng.gcp.web.server.common;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;

@Slf4j
public class HttpReceiver {

    public static JSONObject receiveData(HttpServletRequest request) throws Exception {
        JSONObject jsonResult = new JSONObject();
        BufferedReader bufferedReader = null;
        try {
            String contentType = request.getContentType();

            // none、application/x-www-form-urlencoded、multipart/form-data
            Enumeration paramNames = request.getParameterNames();
            while(paramNames.hasMoreElements()) {
                String key = (String)paramNames.nextElement();
                String value = request.getParameter(key);
                jsonResult.put(key,value);
            }

            // text/plain、text/json;charset=UTF-8
            if(jsonResult.size() == 0){
                bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
                StringBuilder sb = new StringBuilder();
                while (bufferedReader.ready()) {
                    sb.append(bufferedReader.readLine());
                }
                jsonResult = JSONObject.parseObject(sb.toString());
            }

        } catch (Exception e) {
            log.error("接收方式錯誤 : " + e.getMessage());
            throw new Exception(e);
        }
        finally {
            if(bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return jsonResult;
    }

}
