package com.crud.kodillatasks.controller;

import com.crud.kodillatasks.domain.Task;
import com.crud.kodillatasks.domain.TaskDto;
import com.crud.kodillatasks.mapper.TaskMapper;
import com.crud.kodillatasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.hasSize;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private DbService dbService;

    @Test
    public void shouldfetchListOfTasks() throws Exception {
        //Given
        Task task = new Task(
               1L,
               "test task",
               "test content"
        );
        TaskDto taskDto = new TaskDto(
                1L,
                "test task",
                "test content"
        );
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        List<TaskDto> tasksDto = new ArrayList<>();
        tasksDto.add(taskDto);

        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(tasksDto);
        when(dbService.getAllTasks()).thenReturn(tasks);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test task")))
                .andExpect(jsonPath("$[0].content", is("test content")));
    }

    @Test
    public void shouldfetchEmptyList() throws Exception {
        //Given
        List<Task> tasks = new ArrayList<>();
        List<TaskDto> tasksDto = new ArrayList<>();

        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(tasksDto);
        when(dbService.getAllTasks()).thenReturn(tasks);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    public void shouldFetchTask() throws Exception {
        //Given
        Task task = new Task(
                1L,
                "test task",
                "test content"
        );
        TaskDto taskDto = new TaskDto(
                1L,
                "test task",
                "test content"
        );

        when(dbService.getTask(1L)).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When&Then
        mockMvc.perform(get("/v1/task/getTask").param("taskId", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1) ))
                .andExpect(jsonPath("$.title", is("test task")))
                .andExpect(jsonPath("$.content", is("test content")));
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Test()
    public void testGetTaskWhenIdDoesntExist() throws Exception {
        //Given
        when(dbService.getTask(1L)).thenReturn(Optional.empty());
        expectedException.expectCause(isA(TaskNotFoundException.class));

        //When & Then
        mockMvc.perform(get("/v1/task/getTask").param("taskId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given
        Task task = new Task(
                1L,
                "test title",
                "test content"
        );

        when(dbService.getTask(1L)).thenReturn(Optional.of(task));

        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

 @Test
    public void updateTask() throws Exception {
        //Given
        Task task = new Task(
                1L,
                "updated test task",
                "updated test content"
        );
        TaskDto taskDto = new TaskDto(
                1L,
                "updated test task",
                "updated test content"
        );

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        when(dbService.saveTask(task)).thenReturn(task);

        //When&Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("updated test task")))
                .andExpect(jsonPath("$.content", is("updated test content")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //Given

        Task task = new Task(
                1L,
                "test task",
                "test content"
        );
        TaskDto taskDto = new TaskDto(
                1L,
                "test task",
                "test content"
        );

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When&Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}