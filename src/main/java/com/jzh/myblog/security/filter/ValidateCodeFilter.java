package com.jzh.myblog.security.filter;

import com.jzh.myblog.constant.SiteConstant;
import com.jzh.myblog.exception.ValidateCodeException;
import com.jzh.myblog.service.impl.RedisServiceImpl;
import com.jzh.myblog.util.cookie.CookieUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/25 19:51
 * @description security验证码过渡器，用于校验验证码是否正确
 */
public class ValidateCodeFilter extends AbstractAuthenticationProcessingFilter {

    //拦截的url
    private String processUrl;

    private RedisServiceImpl redisServiceImpl;


    public ValidateCodeFilter(String defaultFilterProcessesUrl, String failureUrl, RedisServiceImpl redisServiceImpl) {
        super(defaultFilterProcessesUrl);
        this.processUrl = defaultFilterProcessesUrl;
        setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(failureUrl));
        this.redisServiceImpl = redisServiceImpl;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 拦截登录的 form 表单路径
        if (processUrl.equals(req.getServletPath()) && "POST".equalsIgnoreCase(req.getMethod())) {
            HttpSession session = req.getSession();

            // 获取表单提交的验证码
            String verifyCode = req.getParameter("verify-code");
            // 从redis获取验证信息
            verifyCode = redisServiceImpl.checkImgCode(verifyCode, session);
            if (null == verifyCode) {
                // 将错误信息返回
                CookieUtil.setCookie(req, resp, "err", "验证码输入错误", 60, true, SiteConstant.cookieDomain);
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                CookieUtil.setCookie(req, resp, "username", username, 60, true, SiteConstant.cookieDomain);
                CookieUtil.setCookie(req, resp, "password", password, 60, true, SiteConstant.cookieDomain);
                // 验证失败，交给 SimpleUrlAuthenticationFailureHandler 解决
                this.getFailureHandler().onAuthenticationFailure(req, resp, new ValidateCodeException("验证码错误"));
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        return null;
    }
}
