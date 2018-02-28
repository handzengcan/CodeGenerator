package tech.jebsun.codegenerator.entity;

import java.util.List;

/**
 * 树形控件节点实体类
 * Created by JebSun on 2018/2/28.
 */
public class TreeNode {

    /**
     * 节点名称
     */
    private String title;

    /***
     * 数据库Schema
     */
    private String schema;

    /**
     * 节点类型 root/child
     */
    private String nodeType;

    /**
     * 是否正在加载
     */
    private Boolean loading = false;

    /**
     * 是否展开
     */
    private Boolean expand = false;

    /**
     * 是否选择
     */
    private Boolean selected = false;

    /**
     * 是否选中
     */
    private Boolean checked = false;

    /**
     * 是否禁用
     */
    private Boolean disabled  = false;

    /**
     * 子节点
     */
    private List<TreeNode> children;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Boolean getLoading() {
        return loading;
    }

    public void setLoading(Boolean loading) {
        this.loading = loading;
    }

    public Boolean getExpand() {
        return expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
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
}
