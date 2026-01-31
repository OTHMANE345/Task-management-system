package com.taskService.controller;


import com.taskService.common.response.StandardResponse;
import com.taskService.dto.TaskDto;
import com.taskService.dto.TaskDtoForAdmin;
import com.taskService.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;
import java.util.List;

@RequestMapping(path = "/task/")
@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping
    public ResponseEntity<StandardResponse<List<TaskDto>>> getAllTasks(@RequestHeader("USER-ID") String userId){
       return ResponseEntity.ok(taskService.getAlltasks(userId));
    }

    @GetMapping("/admin")
    public ResponseEntity<StandardResponse<List<TaskDtoForAdmin>>> getAllTasksForAdmin(@RequestHeader("USER-ROLE") String role){
       if(!"admin".equals(role)){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new StandardResponse<>(null, false, "Access Denied: Admin role required", null, HttpStatus.FORBIDDEN));
       }
        return ResponseEntity.ok(taskService.getAlltasksForAdmin());
    }


    @GetMapping("{id}")
    public ResponseEntity<StandardResponse<TaskDto>> getTask(@PathVariable String id){
        return  ResponseEntity.ok(taskService.getTask(id));
    }

    @PostMapping()
    public ResponseEntity<StandardResponse<TaskDto>> addTask(@RequestHeader("USER-ID") String userId, @RequestHeader("EMAIL") String email,  @Valid @RequestBody TaskDto taskDto){
        StandardResponse<TaskDto> res = taskService.addTask(userId, taskDto, email);
       return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("{id}")
    public ResponseEntity<StandardResponse<TaskDto>> updateTask(@RequestHeader("USER-ID") String userId,@PathVariable String id, @Valid @RequestBody TaskDto taskDto){
        return ResponseEntity.ok(taskService.updateTask(userId, id, taskDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTask(@RequestHeader("USER-ID") String userId,@PathVariable String id){
        taskService.deleteTask(userId, id);
        return ResponseEntity.noContent().build();
    }
}
