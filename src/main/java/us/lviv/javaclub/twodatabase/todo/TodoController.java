package us.lviv.javaclub.twodatabase.todo;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
  private final TodoService todoService;

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@RequestBody final TodoDto todoDto) {
    todoService.create(todoDto);
  }

  @GetMapping
  public List<TodoDto> list() {
    return todoService.getUsers();
  }

  @GetMapping("/{id}")
  public TodoDto getUser(@PathVariable final Long id) {
    return todoService.getUser(id);
  }
}
