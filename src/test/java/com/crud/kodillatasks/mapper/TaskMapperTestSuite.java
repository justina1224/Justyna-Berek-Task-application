package com.crud.kodillatasks.mapper;

import com.crud.kodillatasks.domain.Task;
import com.crud.kodillatasks.domain.TaskDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTestSuite {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void testMapToTaskDto() {
        //Given
        Task task = new Task(1L, "test_task", "for test usage");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        Assert.assertNotNull(taskDto);
        Assert.assertEquals(Long.decode("1"), taskDto.getId());
        Assert.assertEquals("test_task", taskDto.getTitle());
        Assert.assertEquals("for test usage", taskDto.getContent());
    }

    @Test
    public void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(Long.decode("1"), "test_task", "for test usage");
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        Assert.assertNotNull(task);
        Assert.assertEquals(Long.decode("1"), task.getId());
        Assert.assertEquals("test_task", task.getTitle());
        Assert.assertEquals("for test usage", task.getContent());
    }

    @Test
    public void testMapToTaskDtoList() {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "test_task", "for test usage"));
        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);
        //Then
        Assert.assertNotNull(taskDtoList);
        Assert.assertEquals(Long.decode("1"), taskDtoList.get(0).getId());
        Assert.assertEquals("test_task", taskDtoList.get(0).getTitle());
        Assert.assertEquals("for test usage", taskDtoList.get(0).getContent());
    }
}