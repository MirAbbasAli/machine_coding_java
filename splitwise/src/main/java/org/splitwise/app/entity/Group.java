package org.splitwise.app.entity;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private final Integer groupId;
    private String groupName;
    private static Integer idGenerator = 1;
    private final List<User> members;

    public Group(String groupName){
        this.groupName = groupName;
        this.groupId = idGenerator++;
        this.members = new ArrayList<>();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public List<User> getMembers() {
        return members;
    }

    public void addMember(User user){
        members.add(user);
    }
}
