/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.distribution.exception Date:2017/2/7 0007 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.distribution.exception;

import com.hand.hap.core.exception.BaseException;

public class HmsAppAnalyseException extends BaseException {
  public static final String EXCEPTION_CODE = "hms.appAnalyse";
  // App无效
  public static final String APP_INVALID = "error.app.invalid";

  protected HmsAppAnalyseException(String code, String descriptionKey, Object[] parameters) {
    super(code, descriptionKey, parameters);
  }

  public HmsAppAnalyseException(String code, String descriptionKey) {
    super(code, descriptionKey, null);
  }
}
