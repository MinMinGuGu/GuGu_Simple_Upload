
package com.gugu.upload.common.vo;

import com.gugu.upload.common.entity.Permission;
import lombok.Data;

import java.util.List;

/**
 * The type Login vo.
 *
 * @author minmin
 * @version 1.0
 * @since 1.8
 */
@Data
public class LoginVo {
    private String username;
    private List<Permission> permissionList;
}
