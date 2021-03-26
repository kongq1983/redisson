package com.kq.redission.dto;

import java.io.Serializable;

/**
 * @author kq
 * @date 2021-03-26 13:51
 * @since 2020-0630
 */
public class ShoppingCart implements Serializable {

    /** 菜品 */
    private String inventoryId;
    /** 数量 */
    private int size;

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "inventoryId='" + inventoryId + '\'' +
                ", size=" + size +
                '}';
    }
}
