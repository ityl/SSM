package com.ssm.boot.config.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ytanglei on 2018/4/24.
 *
 * 继承ShiroHttpServletResponse
 *
 * 去掉JSESSIONID
 */
public class MyShiroHttpServletResponse extends ShiroHttpServletResponse{


    public MyShiroHttpServletResponse(HttpServletResponse wrapped, ServletContext context, ShiroHttpServletRequest request) {
        super(wrapped, context, request);
    }

    @Override
    protected String toEncoded(String url, String sessionId) {
        if(url != null && sessionId != null) {
            String path = url;
            String query = "";
            String anchor = "";
            int question = url.indexOf(63);
            if(question >= 0) {
                path = url.substring(0, question);
                query = url.substring(question);
            }

            int pound = path.indexOf(35);
            if(pound >= 0) {
                anchor = path.substring(pound);
                path = path.substring(0, pound);
            }

            StringBuilder sb = new StringBuilder(path);
          /*  if(sb.length() > 0) {
                sb.append(";");
                sb.append("JSESSIONID");
                sb.append("=");
                sb.append(sessionId);
            }*/
            sb.append(anchor);
            sb.append(query);
            return sb.toString();
        } else {
            return url;
        }
    }
}
