package com.lauro.resource.server.service;

import com.lauro.resource.server.dto.TaskDto;
import com.lauro.resource.server.dto.TasksDto;
import com.lauro.resource.server.exception.TaskCreationException;
import com.lauro.resource.server.exception.TaskNotFoundException;
import com.lauro.resource.server.model.Task;
import com.lauro.resource.server.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository repository;

    @Transactional(rollbackOn = RuntimeException.class)
    public TaskDto create(TaskDto taskDto) {
        final var entity = Task.toTask(taskDto);
        try {
            this.repository.save(entity);
            return taskDto;
        } catch (RuntimeException e) {
            throw new TaskCreationException("Error ti persist on database:", e.fillInStackTrace());
        }
    }

    public TasksDto getAllNotes() {
        final var tasks = repository.findAll();
        final var taskDtoList = tasks.stream().map(TaskDto::new).toList();
        return new TasksDto(taskDtoList);
    }


    public TasksDto update(final TaskDto taskDto) {
        final var taskToUpdate = this.repository.findById(taskDto.id())
                .orElseThrow(() -> new RuntimeException(""));
        final var taskUpdated = new Task(taskToUpdate.getId(), taskDto.title(), taskDto.description());
        repository.save(taskUpdated);
        return this.getAllNotes();
    }

    public TaskDto getNoteById(final Long id) {
        return this.repository.findById(id)
                .map(TaskDto::new)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));
    }

    public void deleteTask(final Long id) {
        final var task = this.repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with Id:" + id));
        this.repository.deleteById(task.getId());
    }

}
