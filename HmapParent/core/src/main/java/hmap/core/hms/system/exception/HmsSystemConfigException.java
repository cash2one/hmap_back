/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.exception Date:2016/9/23 0023 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.exception;

import com.hand.hap.core.exception.BaseException;

public class HmsSystemConfigException extends BaseException {
  public static final String EXCEPTION_CODE = "hms.systemconfig";
  // configKey 只读
  public static final String CONFIGKEY_READONLY = "error.systemconfig.configkey.readonly";
  public static final String CONFIGKEY_UNIQUE = "error.systemconfig.configkey.unique";
  private static final long serialVersionUID = -3250576758107608016L;

  protected HmsSystemConfigException(String code, String descriptionKey, Object[] parameters) {
    super(code, descriptionKey, parameters);
  }

  public HmsSystemConfigException(String code, String descriptionKey) {
    super(code, descriptionKey, null);
  }
}
