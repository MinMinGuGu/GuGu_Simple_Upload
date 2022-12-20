package com.gugu.upload.config.filter;

import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.entity.AppKey;
import com.gugu.upload.mapper.IAccountMapper;
import com.gugu.upload.mapper.IAppKeyMapper;
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

    /**
     * Instantiates a new App key auth filter.
     *
     * @param accountMapper the account mapper
     * @param appKeyMapper  the app key mapper
     */
    public AppKeyAuthFilter(IAccountMapper accountMapper, IAppKeyMapper appKeyMapper) {
        this.accountMapper = accountMapper;
        this.appKeyMapper = appKeyMapper;
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
            saveLoginToCache(account, sessionId);
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

    private void saveLoginToCache(Account account, String sessionId) {
        CacheUtil.CacheObject accountCacheObj = new CacheUtil.CacheObject(account, 24, CacheUtil.CacheObject.TimeUnit.HOUR);
        CacheUtil.pull(sessionId, accountCacheObj);
    }

    private Account analyzeAccount(String appKeyValue) {
        List<AppKey> appKeyList = appKeyMapper.selectByMap(MapUtil.toMap("value", appKeyValue));
        if (CollectionUtils.isEmpty(appKeyList)) {
            log.info("用户传入了一个错误的appKey: {}", appKeyValue);
            return null;
        }
        AppKey appKey = appKeyList.get(0);
        return accountMapper.selectById(appKey.getUserId());
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
