package com.gugu.upload.config.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.entity.AppKey;
import com.gugu.upload.mapper.IAccountMapper;
import com.gugu.upload.mapper.IAppKeyMapper;
import com.gugu.upload.service.ILoginService;
import com.gugu.upload.utils.CacheUtil;
import com.gugu.upload.utils.MapUtil;
import com.gugu.upload.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The type App key auth filter.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /12/20
 * @since 1.8
 */
@Slf4j
public class AppKeyAuthFilter extends BaseFilter implements Filter {

    /**
     * The constant URL_PATTERN_LIST.
     */
    public static final List<String> URL_PATTERN_LIST = Collections.singletonList("/file");

    private final IAccountMapper accountMapper;

    private final IAppKeyMapper appKeyMapper;

    private final ILoginService loginService;

    /**
     * Instantiates a new App key auth filter.
     *
     * @param accountMapper the account mapper
     * @param appKeyMapper  the app key mapper
     * @param loginService  the login service
     */
    public AppKeyAuthFilter(IAccountMapper accountMapper, IAppKeyMapper appKeyMapper, ILoginService loginService) {
        this.accountMapper = accountMapper;
        this.appKeyMapper = appKeyMapper;
        this.loginService = loginService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String appKeyValue = findAppKey(servletRequest);
        if (StringUtils.hasText(appKeyValue)) {
            String sessionId = SessionUtil.getKeyBySessionId(servletRequest);
            Account account = analyzeAccount(appKeyValue);
            if (Objects.isNull(account)) {
                refuse(servletResponse);
                return;
            }
            saveLoginToCache(account, servletRequest);
            pass(servletRequest, servletResponse, filterChain);
            CacheUtil.remove(sessionId);
            return;
        }
        pass(servletRequest, servletResponse, filterChain);
    }

    private String findAppKey(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        return request.getParameter("appKey");
    }

    private void saveLoginToCache(Account account, ServletRequest servletRequest) {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        CacheUtil.CacheObject accountCacheObj = new CacheUtil.CacheObject(loginService.account2LoginVo(account), session.getMaxInactiveInterval(), CacheUtil.CacheObject.TimeUnit.SECOND);
        CacheUtil.pull(session.getId(), accountCacheObj);
    }

    private Account analyzeAccount(String appKeyValue) {
        List<AppKey> appKeyList = appKeyMapper.selectByMap(MapUtil.toMap("value", appKeyValue));
        if (CollectionUtils.isEmpty(appKeyList)) {
            log.info("用户传入了一个错误的appKey: {}", appKeyValue);
            return null;
        }
        AppKey appKey = appKeyList.get(0);
        QueryWrapper<Account> query = Wrappers.query();
        query.eq("user_name", appKey.getUserName());
        return accountMapper.selectOne(query);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
