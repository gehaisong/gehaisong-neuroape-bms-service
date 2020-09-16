package com.utechworld.neuroape.common.result;


/**
 * Created by gehaisong
 */
public enum CodeEnum {
    SUCCESS(0,"SUCCESS"),
    FAILED (-1,"FAILED");

    private Integer value;
    private String describing;

    private CodeEnum(Integer value, String describing) {
        this.value = value;
        this.describing = describing;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getDescribing() {
        return this.describing;
    }

    public static CodeEnum parse(Integer value) {
        CodeEnum[] codes = values();
        CodeEnum[] arr$ = codes;
        int len$ = codes.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            CodeEnum code = arr$[i$];
            if(code.getValue() .equals(value) ) {
                return code;
            }
        }
        return null;
    }
}
