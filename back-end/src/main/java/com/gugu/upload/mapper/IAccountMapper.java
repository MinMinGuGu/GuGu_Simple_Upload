package com.gugu.upload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gugu.upload.common.entity.Account;
import org.apache.ibatis.annotations.Select;

/**
 * The interface Account mapper.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
public interface IAccountMapper extends BaseMapper<Account> {
    /**
     * Select by user name account.
     *
     * @param username the username
     * @return the account
     */
    @Select("select * from account where user_name = #{value}")
    Account selectByUserName(String username);
}
