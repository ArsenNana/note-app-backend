package de.note.app.io.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private  Long id;
    private  @NonNull String title;
    private  @NonNull String body;
}
