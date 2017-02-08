/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:hstaffParent
 * Package Name:hstaff.core.file 
 * Date:2016/7/14 0014
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.uploader.exception;

import com.hand.hap.core.exception.BaseException;

public class HmsUploadObjectInvalidException extends BaseException {
    public static final String EXCEPTION_CODE = "hms.objectUpload";
    public static final String INVALIDA_FILE = "error.objectUpload.invalidFile";

    public HmsUploadObjectInvalidException(String code, String descriptionKey,
        Object[] parameters) {
        super(code, descriptionKey, parameters);
    }
    public HmsUploadObjectInvalidException(String code, String descriptionKey) {
        super(code, descriptionKey, null);
    }
}
