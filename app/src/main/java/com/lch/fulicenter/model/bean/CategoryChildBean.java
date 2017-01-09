package com.lch.fulicenter.model.bean;

/**
 * Created by LCH on 2017/1/9.
 */

public class CategoryChildBean {
    public CategoryChildBean() {
    }

    /**
     * id : 262
     * parentId : 261
     * name : 防晒
     * imageUrl : cat_image/261_1.png
     */

    private int id;
    private int parentId;
    private String name;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "CategoryChildBean{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
