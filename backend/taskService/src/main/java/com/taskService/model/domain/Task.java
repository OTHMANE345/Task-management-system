package com.taskService.model.domain;

import com.taskService.model.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@Entity
public class Task extends BaseEntity {
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="status")
    private String status;
    @Column(name="user_id")
    private String userID;
    @Column(name="priority")
    private String priority;
    @Column(name="duration")
    private String duration;
    @Lob
    @Column(name="image", columnDefinition = "TEXT")
    private String image;



    public Task() {
    }




}
