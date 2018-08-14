package com.borzdykooa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/*
Класс, соответствующий таблице trainer в базе данных
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "trainees")
public class Trainer implements Serializable {

    private Long id;
    private String name;
    private String language;
    private Integer experience;
    private Set<Trainee> trainees = new HashSet<>();

    public Trainer(String name, String language, Integer experience) {
        this.name = name;
        this.language = language;
        this.experience = experience;
    }
}
