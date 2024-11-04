package com.hustler.scalerschool.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data //lombok annotation for getters,setters etc,..
public class BaseEntity {
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
//So inside this BaseEntity pojo class, I have created all these four fields.
//The reason is I'm going to need the same four fields in all the model classes that I'm going to create in the future.
// so in future fro any model class we just extend this class to use them, as to avoid again writing the same attributes.
