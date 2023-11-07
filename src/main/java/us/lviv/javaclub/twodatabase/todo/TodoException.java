package us.lviv.javaclub.twodatabase.todo;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TodoException extends RuntimeException {
  public TodoException(@NonNull final Long id) {
    super("Todo with id= " + id + " not found.");
  }
}
