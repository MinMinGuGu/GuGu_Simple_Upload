package com.gugu.upload.controller;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.vo.LoginVo;
import com.gugu.upload.service.IAccountService;
import com.gugu.upload.utils.CacheUtil;
import com.gugu.upload.utils.LoginUtil;
import com.gugu.upload.utils.MD5Util;
import com.gugu.upload.utils.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * The type Login controller.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Api("登录相关")
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private IAccountService accountService;

    /**
     * Verify result.
     *
     * @param request the request
     * @return the result
     */
    @ApiOperation("验证是否登录")
    @GetMapping
    public Result<String> verify(HttpServletRequest request){
        return LoginUtil.isLogged(request) ? Result.fastSuccess() : Result.fastFail();
    }

    /**
     * Login result.
     *
     * @param loginVo            the login vo
     * @param httpServletRequest the http servlet request
     * @return the result
     */
    @ApiOperation("进行登录")
    @ApiImplicitParam(paramType = "body", name = "loginVo", value = "登录信息", required = true, dataType = "LoginVo")
    @PostMapping
    public Result<String> login(@RequestBody LoginVo loginVo, HttpServletRequest httpServletRequest) {
        if (checkCache(httpServletRequest)){
            log.info("User is already logged in. session id :{}", SessionUtil.getKeyBySessionId(httpServletRequest));
            return Result.fastSuccess();
        }
        log.info("loginVo : {}", loginVo);
        if (checkVo(loginVo)) {
            return Result.fastFail("params is error");
        }
        Account account = findAccount(loginVo);
        if (checkDto(account)){
            return Result.fastFail("username or password is error");
        }
        log.info("query result : {}", account);
        saveBySession(loginVo, account, httpServletRequest);
        log.info("User logged in successfully...");
        return Result.fastSuccess();
    }

    private boolean checkCache(HttpServletRequest httpServletRequest) {
        return CacheUtil.isThere(SessionUtil.getKeyBySessionId(httpServletRequest));
    }

    /**
     * Save by session.
     *
     * @param loginVo            the login vo
     * @param httpServletRequest the http servlet request
     */
    public void saveBySession(LoginVo loginVo, Account account, HttpServletRequest httpServletRequest){
        CacheUtil.CacheObject cacheObject = new CacheUtil.CacheObject(account);
        if (Boolean.TRUE.toString().equalsIgnoreCase(loginVo.getRememberMe())){
            cacheObject.setTime(24, CacheUtil.CacheObject.TimeUnit.HOUR);
        }
        CacheUtil.pull(SessionUtil.getKeyBySessionId(httpServletRequest), cacheObject);
    }

    /**
     * Find account account.
     *
     * @param loginVo the login vo
     * @return the account
     */
    public Account findAccount(LoginVo loginVo){
        return accountService
                .query()
                .eq("user_name", loginVo.getUsername())
                .eq("user_password", MD5Util.encrypt(loginVo.getPassword()))
                .one();
    }

    /**
     * Check dto boolean.
     *
     * @param account the account
     * @return the boolean
     */
    public boolean checkDto(Account account){
        return account == null || account.getUserName() == null || account.getUserPassword() == null;
    }

    /**
     * Check vo boolean.
     *
     * @param loginVo the login vo
     * @return the boolean
     */
    public boolean checkVo(LoginVo loginVo) {
        return loginVo.getUsername() == null || loginVo.getPassword() == null;
    }
}
