package com.gugu.upload.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The type Update file dto.
 *
 * @author minmin
 * @version 1.0
 * @date 2021 /12/19
 * @since 1.8
 */
@Data
@NoArgsConstructor
public class UpdateFileDto {
    private Integer id;
    private String fileDesc;
    private LocalDateTime updateTime = LocalDateTime.now();

    /**
     * Instantiates a new Update file dto.
     *
     * @param id       the id
     * @param fileDesc the file desc
     */
    public UpdateFileDto(Integer id, String fileDesc) {
        this.id = id;
        this.fileDesc = fileDesc;
    }
}
