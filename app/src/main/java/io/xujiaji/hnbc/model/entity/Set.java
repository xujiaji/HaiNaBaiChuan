package io.xujiaji.hnbc.model.entity;

/**
 * Created by jiana on 01/12/16.
 */

public class Set {
    private String typeName;
    private String value;

    public Set(String typeName, String value) {
        this.typeName = typeName;
        this.value = value;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
