package com.skillscan.model.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Difficulty {
    INITIAL(1),
    INTERMEDIATE(2),
    ADVANCED(3);

    private final int weight;

    Difficulty(int weight) {
        this.weight = weight;
    }
}
