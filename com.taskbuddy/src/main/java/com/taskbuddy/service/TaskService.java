package com.taskbuddy.service;

import com.taskbuddy.entity.Task;
import com.taskbuddy.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;
    
    public List<Task> getUserTasks(Long userId) {
        return taskRepository.findByUserId(userId);
    }
    
    public List<Task> getUserTasksByCategory(Long userId, Task.Category category) {
        return taskRepository.findByUserIdAndCategory(userId, category);
    }
    
    public List<Task> getUserTasksByStatus(Long userId, Task.TaskStatus status) {
        return taskRepository.findByUserIdAndStatus(userId, status);
    }
    
    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }
    
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
    
    public Task updateTask(Long taskId, Task taskDetails) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        
        if (taskDetails.getTitle() != null) {
            task.setTitle(taskDetails.getTitle());
        }
        if (taskDetails.getDescription() != null) {
            task.setDescription(taskDetails.getDescription());
        }
        if (taskDetails.getCategory() != null) {
            task.setCategory(taskDetails.getCategory());
        }
        if (taskDetails.getDueDate() != null) {
            task.setDueDate(taskDetails.getDueDate());
        }
        if (taskDetails.getStatus() != null) {
            task.setStatus(taskDetails.getStatus());
        }
        
        return taskRepository.save(task);
    }
    
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        taskRepository.delete(task);
    }
    
    public Task toggleTaskStatus(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        
        task.setStatus(task.getStatus() == Task.TaskStatus.PENDING ? 
                      Task.TaskStatus.COMPLETED : Task.TaskStatus.PENDING);
        
        return taskRepository.save(task);
    }
}