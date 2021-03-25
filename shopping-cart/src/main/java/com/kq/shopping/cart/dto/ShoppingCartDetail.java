package com.kq.shopping.cart.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kq
 * @date 2021-03-25 16:10
 * @since 2020-0630
 */
public class ShoppingCartDetail implements Serializable {

    /** 菜品 */
    private String inventoryId;
    /** 数量 */
    private int size;
    /** 选中 */
    private boolean checked;

    /** 创建时间 */
    private Date createTime;
    /** 修改时间 */
    private Date updateTime;


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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ShoppingCartDetail{" +
                "inventoryId='" + inventoryId + '\'' +
                ", size=" + size +
                ", checked=" + checked +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
