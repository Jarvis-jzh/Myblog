package com.jzh.myblog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/24 17:52
 * @description 类型分页DTO
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CategoryPageDTO extends PageDTO {
    private String category;
}
