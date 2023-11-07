package us.lviv.javaclub.twodatabase.user;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserException extends RuntimeException {
  public UserException(@NonNull final Long id) {
    super("User with id= " + id + " not found.");
  }
}
