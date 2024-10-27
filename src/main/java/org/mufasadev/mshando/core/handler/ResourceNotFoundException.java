package org.mufasadev.mshando.core.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException{
    private String resource;
    private String field;
    private String fieldName;
    private Integer fieldId;

    public ResourceNotFoundException(String resource, String field, Integer fieldId) {
        super(String.format("%s with field %s: %d not found", resource, field, fieldId));
        this.resource = resource;
        this.field = field;
        this.fieldId = fieldId;
    }

    public ResourceNotFoundException(String resource, String field, String fieldName) {
        super(String.format("%s with %s: %s Not Found.", resource, field, fieldName));
        this.resource = resource;
        this.field = field;
        this.fieldName = fieldName;
    }
}