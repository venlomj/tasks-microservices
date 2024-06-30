package fact.it.taskservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(value = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Task
{
    @Id
    private String id;
    private String taskCode;
    private String name;
    private String description;
    private LocalDateTime creationDate;
    private Date dueDate;
    private String rNumber;
    private TaskStatus status;
}
