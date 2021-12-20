package com.gugu.upload.controller;

import com.gugu.upload.common.Result;
import com.gugu.upload.common.entity.Account;
import com.gugu.upload.common.vo.LoginVo;
import com.gugu.upload.controller.helper.LoginHelper;
import com.gugu.upload.service.IAccountService;
import com.gugu.upload.utils.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @GetMapping
    @ApiOperation("验证是否登录")
    public Result<String> verify(HttpServletRequest request){
        Result.Builder<String> resultBuilder = new Result.Builder<>();
        return LoginHelper.isLogged(request) ? resultBuilder.success().build() : resultBuilder.code(401).message("Login required.").build();
    }

    /**
     * Login result.
     *
     * @param loginVo            the login vo
     * @param httpServletRequest the http servlet request
     * @return the result
     */
    @PostMapping
    @ApiOperation("进行登录")
    @ApiImplicitParam(paramType = "body", name = "loginVo", value = "登录信息", required = true, dataType = "LoginVo")
    public Result<String> login(@RequestBody LoginVo loginVo, HttpServletRequest httpServletRequest) {
        if (LoginHelper.checkCache(httpServletRequest)){
            log.info("User is already logged in. session id :{}", SessionUtil.getKeyBySessionId(httpServletRequest));
            return Result.fastSuccess();
        }
        log.info("loginVo : {}", loginVo);
        if (LoginHelper.checkVo(loginVo)) {
            return Result.fastFail("params is error");
        }
        Account account = LoginHelper.findAccount(loginVo, accountService);
        if (LoginHelper.checkDto(account)){
            return Result.fastFail("username or password is error");
        }
        log.info("query result : {}", account);
        LoginHelper.saveBySession(loginVo, account, httpServletRequest);
        log.info("User logged in successfully...");
        return Result.fastSuccess();
    }

    @PutMapping
    @ApiOperation("注销登录")
    public Result<String> logout(HttpServletRequest request){
        Result.Builder<String> resultBuilder = new Result.Builder<>();
        return LoginHelper.logout(request) ? resultBuilder.success().build() : resultBuilder.code(404).message("Do not repeat the request.").build();
    }
}
