package com.taskbuddy.repository;

import com.taskbuddy.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByUserIdAndCategory(Long userId, Task.Category category);
    List<Task> findByUserIdAndStatus(Long userId, Task.TaskStatus status);
}