package com.gugu.upload.common.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gugu.upload.common.entity.Account;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * The type Account query request.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /01/16
 * @since 1.8
 */
@Data
public class AccountQueryRequest implements ISupportQuery<Account> {

    private String searchName;

    @Override
    public QueryWrapper<Account> toQueryWrapper() {
        QueryWrapper<Account> queryWrapper = getQueryWrapper();
        if (StringUtils.hasText(searchName)) {
            queryWrapper.like("user_name", searchName);
        }
        return queryWrapper;
    }
}
