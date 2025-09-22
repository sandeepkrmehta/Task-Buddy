package com.taskbuddy.controller;

import com.taskbuddy.entity.Task;
import com.taskbuddy.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    @GetMapping("/user/{userId}")
    public List<Task> getUserTasks(@PathVariable Long userId) {
        return taskService.getUserTasks(userId);
    }
    
    @GetMapping("/user/{userId}/category/{category}")
    public List<Task> getUserTasksByCategory(@PathVariable Long userId, @PathVariable Task.Category category) {
        return taskService.getUserTasksByCategory(userId, category);
    }
    
    @GetMapping("/user/{userId}/status/{status}")
    public List<Task> getUserTasksByStatus(@PathVariable Long userId, @PathVariable Task.TaskStatus status) {
        return taskService.getUserTasksByStatus(userId, status);
    }
    
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        Optional<Task> task = taskService.getTaskById(taskId);
        return task.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }
    
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task taskDetails) {
        try {
            Task updatedTask = taskService.updateTask(taskId, taskDetails);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        try {
            taskService.deleteTask(taskId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{taskId}/toggle")
    public ResponseEntity<Task> toggleTaskStatus(@PathVariable Long taskId) {
        try {
            Task updatedTask = taskService.toggleTaskStatus(taskId);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Task> markAsComplete(@PathVariable Long taskId) {
        try {
            Task task = taskService.getTaskById(taskId)
                    .orElseThrow(() -> new RuntimeException("Task not found"));
            task.setStatus(Task.TaskStatus.COMPLETED);
            Task updatedTask = taskService.updateTask(taskId, task);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/{taskId}/pending")
    public ResponseEntity<Task> markAsPending(@PathVariable Long taskId) {
        try {
            Task task = taskService.getTaskById(taskId)
                    .orElseThrow(() -> new RuntimeException("Task not found"));
            task.setStatus(Task.TaskStatus.PENDING);
            Task updatedTask = taskService.updateTask(taskId, task);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}