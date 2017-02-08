/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.exception Date:2016/11/14 0014 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.contact.exception;

import com.hand.hap.core.exception.BaseException;

public class HmsContactTagException extends BaseException {
  public static final String EXCEPTION_CODE = "hms.contactTag";
  // configKey 只读
  public static final String TAG_LOCKED = "error.contacttag.lock";

  protected HmsContactTagException(String code, String descriptionKey, Object[] parameters) {
    super(code, descriptionKey, parameters);
  }

  public HmsContactTagException(String code, String descriptionKey) {
    super(code, descriptionKey, null);
  }
}
