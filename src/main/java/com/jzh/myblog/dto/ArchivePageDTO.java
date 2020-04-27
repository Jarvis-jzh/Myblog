package com.jzh.myblog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/24 17:52
 * @description 归档分页DTO
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ArchivePageDTO extends PageDTO {
    private String archiveName;
}
