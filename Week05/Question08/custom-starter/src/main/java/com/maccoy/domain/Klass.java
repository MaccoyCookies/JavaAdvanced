package com.maccoy.domain;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Klass {

    private String klassName;

    private List<Student> studentList;

}
