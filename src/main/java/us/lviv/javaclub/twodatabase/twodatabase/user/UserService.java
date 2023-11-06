package us.lviv.javaclub.twodatabase.twodatabase.user;

import lombok.RequiredArgsConstructor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  @Transactional
  public void create(@NonNull final UserDto userDto) {
    userRepository.save(User.builder()
        .username(userDto.getUsername())
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
