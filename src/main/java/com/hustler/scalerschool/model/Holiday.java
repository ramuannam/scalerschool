package com.hustler.scalerschool.model;

import lombok.Data;


/*
@Data annotation is provided by Lombok library which generates getter, setter,
equals(), hashCode(), toString() methods & Constructor at compile time.
This makes our code short and clean.
* */
@Data
public class Holiday {
    private final String day;
    private final String reason;
    private final Type type;

    public enum Type{ // enum is used for constants.
         FESTIVAL, FEDERAL
    }
}
//In this class:  since my fields are final in nature, you can see the constructor that is generated is of type all arguments constructor.
//It is accepting all the arguments like what is the day?
//What is the reason?
//What is the type of the holiday?
//And we also have getter methods for this and we don't have setter methods because we define these fields as final.
//So Lombok also playing a very smart role here based upon how you are defining your fields.
//Based upon that, it is generating the getters, setters and constructors.
