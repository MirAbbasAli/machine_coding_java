package org.splitwise.app.service;

import org.splitwise.app.entity.Group;
import org.splitwise.app.entity.User;
import org.splitwise.app.repo.GroupRepository;
import org.splitwise.app.repo.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public UserService(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public void createGroup(String groupName){
        Group group = new Group(groupName);
        groupRepository.addGroup(group);
        System.out.printf("Group %s created successfully%n", groupName);
    }

    public void addGroupMember(List<String> memberNames){
        memberNames.stream()
                .map(userRepository::findByName)
                .forEach(groupRepository::addGroupMember);
        System.out.printf("Members added to group %s%n", groupRepository.getGroup().getGroupName());
    }

    public void addUser(String name, String email){
        User user = new User(name, email);
        userRepository.addUser(user);
        System.out.printf("User %s added successfully%n", name);
    }

}
