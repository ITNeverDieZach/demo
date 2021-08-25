package com.zachcc.common.enums;

import lombok.Getter;

@Getter
public enum UploadEnum {
    UPLOAD_IMG("updateByUUIDSelective", "setImg", "img"),
    UPLOAD_BACKGROUND("updateByUUIDSelective", "setBackground", "background"),
    ;
    String uploadMethod;
    String setPropertyMethod;
    String resultKey;

    UploadEnum(String uploadMethod, String setPropertyMethod, String resultKey) {
        this.uploadMethod = uploadMethod;
        this.setPropertyMethod = setPropertyMethod;
        this.resultKey = resultKey;
    }

}
