package com.atozinfoway.expantablelistwithjsonstring;

/**
 * Created by vfinava on 8/30/2016.
 */
public class NameCheck {
    String groupId;
    String childId;
    boolean isChecked;

    public NameCheck(String groupId, String childId, boolean isChecked) {
        this.groupId = groupId;
        this.childId = childId;
        this.isChecked = isChecked;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "NameCheck{" +
                "groupId='" + groupId + '\'' +
                ", childId='" + childId + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
