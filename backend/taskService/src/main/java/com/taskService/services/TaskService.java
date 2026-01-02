package com.taskService.services;


import com.taskService.common.response.StandardResponse;
import com.taskService.dto.TaskDto;
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
//        tasksDb.stream().map(e -> e.getUserID().equals(userId));
//        List<TaskDto> tasks = new ArrayList<>();
//        for (Task t : tasksDb)
//                tasks.add(taskMapper.toTaskDto(t));
            return new StandardResponse<>(tasks, true, "Data fetched succefully", null, HttpStatus.OK);

    }

    public StandardResponse<List<TaskDto>> getAlltasksForAdmin() {
        List<TaskDto> tasks =  taskRepository.findAll().stream().map(taskMapper::toTaskDto).toList();
        return new StandardResponse<>(tasks, true, "Data fetched succefully", null, HttpStatus.OK);

    }

    public StandardResponse<TaskDto> getTask(String id) {
        Task task = this.taskRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("task not found with id: " + id));

        TaskDto taskDto = taskMapper.toTaskDto(task);
        return new StandardResponse<>(taskDto, true, "Data fetched successfully", null, HttpStatus.OK);
    }

    public  StandardResponse<TaskDto> addTask(String userId, TaskDto taskDto) {
        Task task = taskMapper.toTaskEntity(taskDto);
        System.out.println("user Id in add"+ userId);
        task.setUserID(userId);
        taskDto.setUserID(userId);
        taskRepository.save(task);
        return  new StandardResponse<>(taskDto, true, "task added successfully", null, HttpStatus.OK);
    }

    public  StandardResponse<TaskDto> updateTask(String userId,String taskId, TaskDto taskDto) {
        Task task = this.taskRepository.findById(UUID.fromString(taskId))
                .orElseThrow(() -> new RuntimeException("task not found with id: " + taskId));
        if(userId.equals(task.getUserID())){
            task.setName(taskDto.getName());
            task.setDescription(taskDto.getDescription());
            task.setPriority(taskDto.getPriority());
            task.setStatus(taskDto.getStatus());
            taskRepository.save(task);
        }
        taskDto.setUserID(userId);
        taskDto.setId(String.valueOf(task.getId()));
        return  new StandardResponse<>(taskDto, true, "task updated successfully", null, HttpStatus.OK);
    }

    public  StandardResponse<Void> deleteTask(String userId,String taskId) {
        Task task = this.taskRepository.findById(UUID.fromString(taskId))
                .orElseThrow(() -> new RuntimeException("task not found with id: " + taskId));
        if(!userId.equals(task.getUserID())){
            throw new RuntimeException("Unauthorized to delete this task");
        }

        taskRepository.delete(task);
        return  new StandardResponse<>(null, true, "task deleted successfully", null, HttpStatus.OK);
    }
}
