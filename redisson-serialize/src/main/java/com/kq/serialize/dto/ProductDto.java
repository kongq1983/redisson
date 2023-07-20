package com.kq.serialize.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author kq
 * @date 2022-04-13 14:26
 * @since 2020-0630
 */
@Data
public class ProductDto {

    private String id;
    private String name;
    private String code;
    private String photo;
    private BigDecimal price;
    private String remark;

}
