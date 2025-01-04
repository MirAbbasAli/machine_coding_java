package org.splitwise.app.repo;

import org.splitwise.app.entity.Group;
import org.splitwise.app.entity.User;

import java.util.ArrayList;
import java.util.List;

public class GroupRepository {
    private final List<Group> groups;

    public GroupRepository(){
        groups = new ArrayList<>();
    }

    public void addGroup(Group group){
        this.groups.add(group);
    }

    public void addGroupMember(User member){
        this.groups.get(0).addMember(member);
    }

    public Group getGroup(){
        return this.groups.get(0);
    }
}
