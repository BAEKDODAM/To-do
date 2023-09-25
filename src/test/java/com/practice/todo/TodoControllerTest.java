package com.practice.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.practice.auth.jwt.JwtTokenizer;
import com.practice.todo.dto.TodoPatchDto;
import com.practice.todo.dto.TodoPostDto;
import com.practice.todo.dto.TodoResponseDto;
import com.practice.todo.entity.Todo;
import com.practice.todo.mapper.TodoMapper;
import com.practice.todo.service.TodoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {
    @MockBean
    private TodoService todoService;
    @MockBean
    private TodoMapper todoMapper;
    @MockBean
    private JwtTokenizer jwtTokenizer;
    @Autowired
    private Gson gson;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void postTodoTest() throws Exception {
        String token = "Bearer test-token";
        long cardId = 1L;
        TodoPostDto post = new TodoPostDto("test todo", Todo.TodoPriority.TODO_PRIORITY_FIRST);
        Todo mockTodo = new Todo();
        mockTodo.setTodoId(1L);
        mockTodo.setTitle("test todo");

        given(jwtTokenizer.getMemberId(eq(token))).willReturn(1L);
        given(todoService.createTodo(Mockito.any(Todo.class), Mockito.anyLong(), Mockito.anyLong())).willReturn(mockTodo);
        given(todoMapper.todoPostDtoToTodo(Mockito.any(TodoPostDto.class))).willReturn(new Todo());

        String content = gson.toJson(post);

        URI uri = UriComponentsBuilder.newInstance().path("/todo/{cardId}").buildAndExpand(cardId).toUri();

        // when
        ResultActions actions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post(uri)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer test-token")
                                .content(content)
                );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/todo/1"))));

    }

    @Test
    public void patchTodo() throws Exception {
        long todoId = 1L;
        TodoPatchDto patch = new TodoPatchDto();
        patch.setTodoId(1L);
        patch.setTitle("patch");
        TodoResponseDto mockTodo = TodoResponseDto.builder().todoId(1L).build();

        given(todoService.updateTodo(Mockito.any(Todo.class), Mockito.anyLong())).willReturn(new Todo());
        given(todoMapper.todoPatchDtoToTodo(Mockito.any(TodoPatchDto.class))).willReturn(new Todo());
        given(todoMapper.todoToTodoResponseDto(Mockito.any(Todo.class))).willReturn(mockTodo);

        String content = gson.toJson(patch);

        URI uri = UriComponentsBuilder.newInstance().path("/todo/{todoId}").buildAndExpand(todoId).toUri();

        ResultActions actions =
                mockMvc.perform(
                        MockMvcRequestBuilders.patch(uri)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer test-token")
                                .content(content)
                );
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.todoId", Matchers.is(1)));
    }

    @Test
    public void deleteTodoTest() throws Exception {
        long todoId = 1L;
        String token = "Bearer test-token";

        given(jwtTokenizer.getMemberId(eq(token))).willReturn(1L);
        doNothing().when(todoService)
                        .deleteTodo(eq(todoId), eq(1L));

        URI uri = UriComponentsBuilder.newInstance().path("/todo/{todoId}").buildAndExpand(todoId).toUri();

        ResultActions actions = mockMvc.perform(
                MockMvcRequestBuilders.delete(uri)
                        .header("Authorization", "Bearer test-token")
        );
        actions.andExpect(status().isNoContent());

    }
}
