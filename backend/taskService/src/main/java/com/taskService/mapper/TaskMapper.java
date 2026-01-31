package com.taskService.mapper;


import com.taskService.dto.TaskDto;
import com.taskService.dto.TaskDtoForAdmin;
import com.taskService.model.domain.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskDto toTaskDto(Task task){
        TaskDto taskDto = new TaskDto();
        taskDto.setId(String.valueOf(task.getId()));
        taskDto.setName(task.getName());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setUserID(task.getUserID());
        taskDto.setImage(task.getImage());
        taskDto.setPriority(task.getPriority());
        taskDto.setDuration(task.getDuration());
        return taskDto;
    }

    public TaskDtoForAdmin toTaskDtoForAdmin(Task task){
        TaskDtoForAdmin taskDto = new TaskDtoForAdmin();
        taskDto.setId(String.valueOf(task.getId()));
        taskDto.setName(task.getName());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setUserID(task.getUserID());
        taskDto.setImage(task.getImage());
        taskDto.setPriority(task.getPriority());
        taskDto.setDuration(task.getDuration());
        taskDto.setEmail(task.getEmail());
        return taskDto;
    }

    public Task toTaskEntity(TaskDto taskDto){
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setPriority(taskDto.getPriority());
        task.setStatus(taskDto.getStatus());
        task.setUserID(taskDto.getUserID());
        task.setDuration(taskDto.getDuration());
        task.setImage(taskDto.getImage());
        return task;
    }
}
