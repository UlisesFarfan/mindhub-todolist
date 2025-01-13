package com.mindhub.todolist.dtos.get_with_content;

import com.mindhub.todolist.dtos.TaskDTO;
import com.mindhub.todolist.dtos.UserDTO;
import com.mindhub.todolist.models.UserModel;

import java.util.List;

public class GetUserDTO extends UserDTO {
    private final List<TaskDTO> task;
    public GetUserDTO(UserModel userModel) {
        super(userModel);
        this.task = userModel.getTask()
                .stream()
                .map(TaskDTO::new)
                .toList();
    }

    public List<TaskDTO> getTask() {
        return task;
    }
}
