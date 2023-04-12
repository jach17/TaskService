package com.axity.task.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axity.task.model.TaskDO;

/**
 * Persistence interface of  de {@link com.axity.task.model.TaskDO}
 * 
 * @author jonathan.aldana@axity.com
 */
@Repository
public interface TaskPersistence extends JpaRepository<TaskDO, Integer>
{
  // Agregar consultas personalizadas
}
