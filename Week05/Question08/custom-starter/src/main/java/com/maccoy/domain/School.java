package com.maccoy.domain;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class School {

    private String schoolName;

    private List<Klass> klassList;

}
