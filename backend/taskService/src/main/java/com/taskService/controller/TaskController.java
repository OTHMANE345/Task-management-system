package com.taskService.controller;


import com.taskService.common.response.StandardResponse;
import com.taskService.dto.TaskDto;
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

       System.out.println("UserID "+ userId);

    return ResponseEntity.ok(taskService.getAlltasks(userId));
    }

    @GetMapping("/admin")
    public ResponseEntity<StandardResponse<List<TaskDto>>> getAllTasksForAdmin(@RequestHeader("USER-ROLE") String role){
        System.out.println("role : "+ role);
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
    public ResponseEntity<StandardResponse<TaskDto>> addTask(@RequestHeader("USER-ID") String userId, @Valid @RequestBody TaskDto taskDto){
        System.out.println("User Id"+ userId);
       return ResponseEntity.ok(taskService.addTask(userId, taskDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<StandardResponse<TaskDto>> updtedTask(@RequestHeader("USER-ID") String userId,@PathVariable String id, @Valid @RequestBody TaskDto taskDto){
        return ResponseEntity.ok(taskService.updateTask(userId, id, taskDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<StandardResponse<Void>> deleteTask(@RequestHeader("USER-ID") String userId,@PathVariable String id){
        return ResponseEntity.ok(taskService.deleteTask(userId, id));
    }
}
