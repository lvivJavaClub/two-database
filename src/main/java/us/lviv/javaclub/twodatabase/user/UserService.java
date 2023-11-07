package us.lviv.javaclub.twodatabase.user;

import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  @Transactional
  public void create(@NonNull final UserDto userDto) {
    userRepository.save(User.builder()
        .username(userDto.getUsername())
        .createdAt(OffsetDateTime.now())
        .build());
  }

  @Transactional(readOnly = true)
  public List<UserDto> getUsers() {
    return StreamSupport.stream(userRepository.findAll().spliterator(), false)
        .map(user -> UserDto.builder()
            .username(user.getUsername())
            .build())
        .toList();
  }

  @Transactional(readOnly = true)
  public UserDto getUser(@NonNull final Long id) {
    return userRepository.findById(id)
        .map(user -> UserDto.builder()
            .username(user.getUsername())
            .build())
        .orElseThrow(() -> new UserException(id));
  }
}
