package org.example.lab06.expection;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceType, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceType, fieldName, fieldValue));
    }
}