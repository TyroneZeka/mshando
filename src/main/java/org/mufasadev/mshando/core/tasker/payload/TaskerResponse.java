package org.mufasadev.mshando.core.tasker.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class TaskerResponse {
    private List<TaskerDTO> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalElements;
    private Integer totalPages;
    private Boolean isLastPage;
}