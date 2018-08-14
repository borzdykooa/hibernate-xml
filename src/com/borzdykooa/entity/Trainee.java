package com.borzdykooa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
Класс, соответствующий таблице trainee в базе данных
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trainee implements Serializable {

    private Long id;
    private String name;
    private Trainer trainer;

    public Trainee(String name, Trainer trainer) {
        this.name = name;
        this.trainer = trainer;
    }
}
