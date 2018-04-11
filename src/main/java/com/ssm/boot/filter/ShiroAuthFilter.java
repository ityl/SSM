package com.ssm.boot.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;


/**
 * Created by yanglei on 17/7/28.
 *
 * 过滤器说明：必须为登录状态
 *           shiroExceptionFilter[1，2] 1,2拥有其中一种即可访问
 */
@Repository
public class ShiroAuthFilter extends AuthorizationFilter {

    private static final Logger logger = LoggerFactory.getLogger(ShiroAuthFilter.class);

    @SuppressWarnings("unchecked")
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        String[] rolesArray = (String[]) mappedValue;
        Subject subject = getSubject(request, response);
        if (null != subject.getPrincipals()) {

            if (rolesArray == null || rolesArray.length == 0) {
                //没有权限要求
                return true;
            }
            Set<String> roles = CollectionUtils.asSet(rolesArray);
            for (String role : roles) {
                if (subject.hasRole(role)) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * 会话超时或权限校验未通过的，统一返回401，由前端页面弹窗提示
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws IOException {
        logger.info("onAccessDenied");
        if (isAjax((HttpServletRequest) request)) {
            WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            String unauthorizedUrl = getUnauthorizedUrl();
            if (StringUtils.hasText(unauthorizedUrl)) {
                WebUtils.issueRedirect(request, response, unauthorizedUrl);
            } else {
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

        return false;
    }

    private boolean isAjax(HttpServletRequest request) {
        String header = request.getHeader("x-requested-with");
        if (null != header && "XMLHttpRequest".endsWith(header)) {
            return true;
        }
        return false;
    }

}