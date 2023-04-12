package com.axity.task.facade;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.axity.task.commons.dto.TaskDto;
import com.axity.task.commons.request.PaginatedRequestDto;
import com.axity.task.commons.response.GenericResponseDto;
import com.axity.task.commons.response.PaginatedResponseDto;
import com.axity.task.facade.impl.TaskFacadeImpl;
import com.axity.task.service.TaskService;

/**
 * Class TaskFacadeTest
 * 
 * @author jonathan.aldana@axity.com
 */
class TaskFacadeTest
{
  private TaskFacade taskFacade;
  private TaskService taskService;

  @BeforeEach
  public void setUp()
  {
    this.taskFacade = new TaskFacadeImpl();

    this.taskService = mock( TaskService.class );
    ReflectionTestUtils.setField( this.taskFacade, "taskService", this.taskService );
  }

  /**
   * Test method for
   * {@link com.axity.task.facade.impl.TaskFacadeImpl#findTasks(com.axity.task.commons.request.PaginatedRequestDto)}.
   */
  @Test
  void testFindTasks()
  {
    int pageSize = 20;

    var data = new ArrayList<TaskDto>();
    for( int i = 0; i < pageSize; i++ )
    {
      data.add( this.createTask( i + 1 ) );
    }
    var paginated = new PaginatedResponseDto<TaskDto>( 0, pageSize, 50, data );
    when( this.taskService.findTasks( any( PaginatedRequestDto.class ) ) ).thenReturn( paginated );

    var result = this.taskFacade.findTasks( new PaginatedRequestDto( pageSize, 0 ) );
    assertNotNull( result );
  }

  /**
   * Test method for {@link com.axity.task.facade.impl.TaskFacadeImpl#find(java.lang.String)}.
   */
  @Test
  void testFind()
  {
    var response = new GenericResponseDto<TaskDto>( this.createTask( 1 ) );
    when( this.taskService.find( anyInt() ) ).thenReturn( response );

    var result = this.taskFacade.find( 1 );
    assertNotNull( result );
  }

  /**
   * Test method for
   * {@link com.axity.task.facade.impl.TaskFacadeImpl#create(com.axity.task.commons.dto.TaskDto)}.
   */
  @Test
  void testCreate()
  {
    var task = this.createTask( 8 );
    var response = new GenericResponseDto<>( task );
    when( this.taskService.create( any( TaskDto.class ) ) ).thenReturn( response );

    var result = this.taskFacade.create( task );
    assertNotNull( result );
  }

  /**
   * Test method for
   * {@link com.axity.task.facade.impl.TaskFacadeImpl#update(com.axity.task.commons.dto.TaskDto)}.
   */
  @Test
  void testUpdate()
  {
    var task = this.createTask( 1 );

    var response = new GenericResponseDto<>( true );
    when( this.taskService.update( any( TaskDto.class ) ) ).thenReturn( response );
    var result = this.taskFacade.update( task );
    assertNotNull( result );
  }

  /**
   * Test method for {@link com.axity.task.facade.impl.TaskFacadeImpl#delete(java.lang.String)}.
   */
  @Test
  void testDelete()
  {
    var response = new GenericResponseDto<>( true );
    when( this.taskService.delete( anyInt() ) ).thenReturn( response );

    var result = this.taskFacade.delete( 9 );
    assertNotNull( result );
  }

  private TaskDto createTask( int i )
  {
    var task = new TaskDto();
    task.setId( i );
    return task;
  }
}
