package com.gugu.upload.common.bo;

import com.gugu.upload.common.entity.Role;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * The type Role bo.
 *
 * @author minmin
 * @version 1.0
 * @date 2022 /02/22
 * @since 1.8
 */
@Data
public class RoleBo implements IBo2Entity<Role> {
    private Integer id;
    private String name;
    private Boolean enable;

    @Override
    public Role bo2Entity() {
        Role role = new Role();
        BeanUtils.copyProperties(this, role);
        role.setSystemDefault(false);
        return role;
    }
}
