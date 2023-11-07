package us.lviv.javaclub.twodatabase.todo;

import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TodoService {
  private final TodoRepository todoRepository;

  @Transactional
  public void create(@NonNull final TodoDto todoDto) {
    todoRepository.save(Todo.builder()
        .text(todoDto.getText())
        .createdAt(OffsetDateTime.now())
        .build());
  }

  @Transactional(readOnly = true)
  public List<TodoDto> getUsers() {
    return StreamSupport.stream(todoRepository.findAll().spliterator(), false)
        .map(todo -> TodoDto.builder()
            .text(todo.getText())
            .build())
        .toList();
  }

  @Transactional(readOnly = true)
  public TodoDto getUser(@NonNull final Long id) {
    return todoRepository.findById(id)
        .map(todo -> TodoDto.builder()
            .text(todo.getText())
            .build())
        .orElseThrow(() -> new TodoException(id));
  }
}
