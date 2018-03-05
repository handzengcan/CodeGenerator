package tech.jebsun.codegenerator.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.UUID;

/**
 * 树形控件节点实体类
 * Created by JebSun on 2018/2/28.
 */
public class TreeNode {

    /**
     * 节点名称
     */
    private String label;

    /**
     * 节点唯一键
     */
    private String nodeKey = UUID.randomUUID().toString();

    /**
     * 节点图标
     */
    private String icon;

    /***
     * 数据库Schema
     */
    private String schema;

    /**
     * 节点类型 root/child
     */
    private String nodeType;

    /**
     * 是否可展开
     */
    private Boolean expandable = true;

    /**
     * 是否禁用
     */
    private Boolean disabled  = false;

    /**
     * 节点是否懒加载
     */
    private Boolean lazy = false;


    /**
     * checkbox是否可禁用
     */
    private Boolean tickable = false;


    /**
     * 使用ticket策略时,checkbox是否隐藏
     */
    private Boolean noTick = false;

    /**
     * 重写全局ticket策略仅适用于当前节点 , ‘leaf’, ‘leaf-filtered’, ‘strict’, ‘none’.
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String tickStrategy;

    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TreeNode> children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Boolean getExpandable() {
        return expandable;
    }

    public void setExpandable(Boolean expandable) {
        this.expandable = expandable;
    }

    public Boolean getTickable() {
        return tickable;
    }

    public void setTickable(Boolean tickable) {
        this.tickable = tickable;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getLazy() {
        return lazy;
    }

    public void setLazy(Boolean lazy) {
        this.lazy = lazy;
    }

    public Boolean getNoTick() {
        return noTick;
    }

    public void setNoTick(Boolean noTick) {
        this.noTick = noTick;
    }

    public String getTickStrategy() {
        return tickStrategy;
    }

    public void setTickStrategy(String tickStrategy) {
        this.tickStrategy = tickStrategy;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }
}
