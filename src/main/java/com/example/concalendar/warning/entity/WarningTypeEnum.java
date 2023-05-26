package com.example.concalendar.warning.entity;

import lombok.Getter;

@Getter
public enum WarningTypeEnum {

    SPAM(1,"스팸홍보/도배글입니다."),
    HARMFUL(2,"청소년에게 유해한 내용입니다."),
    ABUSE(3,"지나친 욕설이 포함되어있습니다."),
    PRIVACY(4,"개인정보가 포함되어 있습니다");

    private long warningTypeId;
    private String warningTypeString;

    WarningTypeEnum(long warningTypeId, String warningTypeString){
        this.warningTypeId = warningTypeId;
        this.warningTypeString = warningTypeString;
    }

}
