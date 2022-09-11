package com.ralerculte.yandex.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ralerculte.yandex.utils.ParentSerializer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "system_item")
public class SystemItem {
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "url")
    private String url;
    @Column(name = "date", nullable = false)
    private String date;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonSerialize(using = ParentSerializer.class)
    private SystemItem parent;
    @Enumerated(EnumType.STRING)
    private SystemItemType type;
    @Column(name = "size")
    private Integer size;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<SystemItem> children;

    public SystemItem() {
    }

    public SystemItem(String id,
                      String url,
                      String date,
                      SystemItem parent,
                      SystemItemType type,
                      Integer size) {
        this.id = id;
        this.url = url;
        this.date = date;
        this.parent = parent;
        this.type = type;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public SystemItem getParent() {
        return parent;
    }

    public void setParent(SystemItem parent) {
        this.parent = parent;
    }

    public SystemItemType getType() {
        return type;
    }

    public void setType(SystemItemType type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<SystemItem> getChildren() {
        return children;
    }

    public void setChildren(List<SystemItem> children) {
        this.children = children;
    }

    public void addChildren(SystemItem item) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(item);
    }
}
