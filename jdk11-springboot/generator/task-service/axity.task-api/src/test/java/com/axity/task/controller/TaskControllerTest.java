package com.axity.task.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.axity.task.commons.dto.TaskDto;
import com.axity.task.commons.request.PaginatedRequestDto;
import com.axity.task.commons.response.GenericResponseDto;
import com.axity.task.commons.response.PaginatedResponseDto;
import com.axity.task.facade.TaskFacade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * Task Controller Test class
 * 
 * @author jonathan.aldana@axity.com
 */
@Slf4j
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TaskControllerTest
{

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TaskFacade taskFacade;
  
  
  
  @BeforeEach
  void setUp() {
    
  }

  /**
   * Test method for {@link com.axity.task.controller.TaskController#findTasks(int, int)}.
   * 
   * @throws Exception
   */
  @Test
  void testFindTasks() throws Exception
  {
    int pageSize = 20;

    var data = new ArrayList<TaskDto>();
    for( int i = 0; i < pageSize; i++ )
    {
      data.add( this.createTask( i + 1 ) );
    }
    var paginated = new PaginatedResponseDto<TaskDto>( 0, pageSize, 50, data );
    when( this.taskFacade.findTasks( any( PaginatedRequestDto.class ) ) ).thenReturn( paginated );

    MvcResult result = mockMvc.perform( MockMvcRequestBuilders.get( "/api/tasks?limit=20&offset=0" ) )
        .andExpect( status().isOk() )
        .andExpect( jsonPath( "$.header.code" ).value( "0" ) )
        .andExpect( jsonPath( "$.page" ).value( "0" ) )
        .andExpect( jsonPath( "$.size" ).value( "20" ) )
        .andExpect( jsonPath( "$.data" ).isArray() )
        .andExpect( jsonPath( "$.data[0].id" ).value( 1 ) ).andReturn();

    assertNotNull( result );
  }

  /**
   * Test method for {@link com.axity.task.controller.TaskController#findTask(java.lang.String)}.
   * 
   * @throws Exception
   */
  @Test
  void testFindTask() throws Exception
  {
    var task = this.createTask( 1 );
    var generic = new GenericResponseDto<>(task);
    when( this.taskFacade.find( anyInt() ) ).thenReturn( generic );
    
    MvcResult result = mockMvc.perform( MockMvcRequestBuilders.get( "/api/tasks/1" ) )
        .andExpect( status().isOk() )
        .andExpect( jsonPath( "$.header.code" ).value( "0" ) )
        .andExpect( jsonPath( "$.body.id" ).value( 1 ) ).andReturn();

    assertNotNull( result );
  }

  /**
   * Test method for
   * {@link com.axity.task.controller.TaskController#create(com.axity.task.commons.dto.TaskDto)}.
   * 
   * @throws Exception
   */
  @Test
  void testCreate() throws Exception
  {
    var task = this.createTask( 9 );
    var generic = new GenericResponseDto<>(task);
    when(this.taskFacade.create( any( TaskDto.class ) )).thenReturn( generic );
    
    Gson gson = new GsonBuilder().create();
    
    MvcResult result = mockMvc.perform( MockMvcRequestBuilders.post( "/api/tasks"  )
            .content(gson.toJson(task))
            .accept( MediaType.APPLICATION_JSON )
            .contentType( MediaType.APPLICATION_JSON ))
        .andExpect( status().isCreated() )
        .andExpect( jsonPath( "$.header.code" ).value( "0" ) )
        .andExpect( jsonPath( "$.body.id" ).value( 9 ) ).andReturn();

    assertNotNull( result );
  }

  /**
   * Test method for
   * {@link com.axity.task.controller.TaskController#update(java.lang.String, com.axity.task.commons.dto.TaskDto)}.
   * 
   * @throws Exception
   */
  @Test
  void testUpdate() throws Exception
  {
    var task = this.createTask( 1 );
    var generic = new GenericResponseDto<>(true);
    when(this.taskFacade.update( any( TaskDto.class ) )).thenReturn( generic );
    
    Gson gson = new GsonBuilder().create();
    
    MvcResult result = mockMvc.perform( MockMvcRequestBuilders.put( "/api/tasks/1"  )
            .content(gson.toJson(task))
            .accept( MediaType.APPLICATION_JSON )
            .contentType( MediaType.APPLICATION_JSON ))
        .andExpect( status().isOk() )
        .andExpect( jsonPath( "$.header.code" ).value( "0" ) )
        .andExpect( jsonPath( "$.body" ).value( "true" ) ).andReturn();

    assertNotNull( result );
  }

  /**
   * Test method for {@link com.axity.task.controller.TaskController#delete(java.lang.String)}.
   * 
   * @throws Exception
   */
  @Test
  void testDelete() throws Exception
  {
    var generic = new GenericResponseDto<>(true);
    when(this.taskFacade.delete( anyInt() )).thenReturn( generic );
    
    MvcResult result = mockMvc.perform( MockMvcRequestBuilders.delete( "/api/tasks/1"  )
            .accept( MediaType.APPLICATION_JSON ))
        .andExpect( status().isOk() )
        .andExpect( jsonPath( "$.header.code" ).value( "0" ) )
        .andExpect( jsonPath( "$.body" ).value( "true" ) ).andReturn();

    assertNotNull( result );
  }

  private TaskDto createTask( int i )
  {
    var task = new TaskDto();
    task.setId( i );
    return task;
  }
}
