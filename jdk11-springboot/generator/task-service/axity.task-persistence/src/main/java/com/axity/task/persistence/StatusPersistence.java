package com.axity.task.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axity.task.model.StatusDO;

/**
 * Persistence interface of  de {@link com.axity.task.model.StatusDO}
 * 
 * @author jonathan.aldana@axity.com
 */
@Repository
public interface StatusPersistence extends JpaRepository<StatusDO, Integer>
{
  // Agregar consultas personalizadas
}
