package com.gugu.upload.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gugu.upload.common.entity.Menu;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * The type Menu vo.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /08/28
 * @since 1.8
 */
@Data
@NoArgsConstructor
public class MenuVo {
    @JsonProperty("path")
    private String path;
    @JsonProperty("name")
    private String name;
    @JsonProperty("children")
    private List<MenuVo> children;

    public MenuVo(Menu menu){
        BeanUtils.copyProperties(menu, this);
    }
}
