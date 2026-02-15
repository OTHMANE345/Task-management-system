package com.taskService.services;


import com.taskService.common.response.StandardResponse;
import com.taskService.dto.TaskDto;
import com.taskService.dto.TaskDtoForAdmin;
import com.taskService.exception.ForbiddenException;
import com.taskService.exception.TaskNotFoundException;
import com.taskService.mapper.TaskMapper;
import com.taskService.model.domain.Task;
import com.taskService.repository.TaskRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }


    public StandardResponse<List<TaskDto>> getAlltasks(String userId) {
        List<TaskDto> tasks =  taskRepository.findAll().stream().filter(
                task -> userId.equals(task.getUserID())
        ).map(taskMapper::toTaskDto).toList();
            return new StandardResponse<>(tasks, true, "Data fetched succefully", null, HttpStatus.OK);

    }

    public StandardResponse<List<TaskDtoForAdmin>> getAlltasksForAdmin() {
        List<TaskDtoForAdmin> tasks =  taskRepository.findAll().stream().map(taskMapper::toTaskDtoForAdmin).toList();
        return new StandardResponse<>(tasks, true, "Data fetched succefully", null, HttpStatus.OK);

    }

    public StandardResponse<TaskDto> getTask(String id) {
        Task task = this.taskRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new TaskNotFoundException(id));

        TaskDto taskDto = taskMapper.toTaskDto(task);
        return new StandardResponse<>(taskDto, true, "Data fetched successfully", null, HttpStatus.OK);
    }

    public  StandardResponse<TaskDto> addTask(String userId, TaskDto taskDto, String email) {
        Task task = taskMapper.toTaskEntity(taskDto);
        task.setUserID(userId);
        task.setEmail(email);
        Task savedTask = taskRepository.save(task);
        taskRepository.save(task);
        TaskDto responseDto = taskMapper.toTaskDto((savedTask));
        return  new StandardResponse<>(responseDto, true, "task added successfully", null, HttpStatus.CREATED);
    }

    public  StandardResponse<TaskDto> updateTask(String userId,String taskId, TaskDto taskDto) {
        Task task = this.taskRepository.findById(UUID.fromString(taskId))
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        if(!userId.equals(task.getUserID())){
            throw new ForbiddenException("You are not authorized to update this task");
        }
            task.setName(taskDto.getName());
            task.setDescription(taskDto.getDescription());
            task.setPriority(taskDto.getPriority());
            task.setStatus(taskDto.getStatus());
            task.setDuration(taskDto.getDuration());
            task.setImage(taskDto.getImage());
            taskRepository.save(task);

        Task updatedtask = taskRepository.save(task);
        TaskDto responseDto = taskMapper.toTaskDto(updatedtask);
        return  new StandardResponse<>(responseDto, true, "task updated successfully", null, HttpStatus.OK);
    }

    public  StandardResponse<Void> deleteTask(String userId,String taskId) {
        Task task = this.taskRepository.findById(UUID.fromString(taskId))
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        if(!userId.equals(task.getUserID())){
            throw new ForbiddenException("You are not authorized to delete this task");
        }

        taskRepository.delete(task);
        return  new StandardResponse<>(null, true, "task deleted successfully", null, HttpStatus.NO_CONTENT);
    }
}
