package com.glorystudent.entity;

import java.util.List;

/**
 * Created by 1 on 2016/10/19.
 */
public class GroupEntity {
    private String groupName;
    private List<FriendEntity> friends;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<FriendEntity> getFriends() {
        return friends;
    }
    public void setFriends(List<FriendEntity> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "GroupEntity{" +
                "groupName='" + groupName + '\'' +
                ", friends=" + friends +
                '}';
    }
}
