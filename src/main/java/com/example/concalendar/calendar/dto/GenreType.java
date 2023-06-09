package com.example.concalendar.calendar.dto;

import lombok.Getter;

@Getter
public enum GenreType {
    POP(1,"팝"),
    ROCK(2,"락"),
    JAZZ(3,"재즈"),
    HIPHOP(4,"힙합"),
    ELECTRONIC(5,"일렉트로닉"),
    CLASSIC(6,"클래식"),
    ETC(7,"기타");

    private int id;
    private String GenreStr;

    GenreType(int id, String GenreStr){
        this.id = id;
        this.GenreStr = GenreStr;
    }

}
