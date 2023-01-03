package com.gugu.upload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gugu.upload.common.entity.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * The interface Permission mapper.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/29
 * @since 1.8
 */
public interface IPermissionMapper extends BaseMapper<Permission> {
    /**
     * Gets ids.
     *
     * @return the ids
     */
    @Select("select `id` from permission")
    List<Integer> getIds();

    /**
     * Gets permission by account id.
     *
     * @param id the id
     * @return the permission by account id
     */
    @Select("SELECT * FROM permission WHERE permission.id IN ( SELECT  role_permission.permission_id  FROM  role_permission  WHERE role_permission.role_id = ( SELECT account.role_id FROM account WHERE account.id = #{value} )  )")
    List<Permission> getPermissionByAccountId(Integer id);
}
