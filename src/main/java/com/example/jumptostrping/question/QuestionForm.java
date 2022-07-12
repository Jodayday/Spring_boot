package com.example.jumptostrping.question;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
public class QuestionForm {

    @NotEmpty(message = "제목은 필수사항입니다.")
    @Size(max = 200)
    private String subject;
    @NotEmpty(message = "내용은 필수사항입니다.")
    private String content;


}
