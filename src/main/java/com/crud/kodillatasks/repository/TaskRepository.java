package com.crud.kodillatasks.repository;

import com.crud.kodillatasks.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TaskRepository extends CrudRepository<Task, Long> {
    @Override
    List<Task> findAll();

    @Override
    Task findOne(Long taskId);

    @Override
    Task save(Task task);

    Optional<Task> findById(Long id);

    void deleteById(Long taskId);

    @Override
    long count();

}
