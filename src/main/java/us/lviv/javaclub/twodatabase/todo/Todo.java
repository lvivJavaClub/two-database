package us.lviv.javaclub.twodatabase.todo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity(name = "todo")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String text;
  @Column(name = "created_at")
  private OffsetDateTime createdAt;
}
