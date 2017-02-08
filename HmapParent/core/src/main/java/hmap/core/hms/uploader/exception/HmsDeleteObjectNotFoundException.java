package hmap.core.hms.uploader.exception;

import com.hand.hap.core.exception.BaseException;

public class HmsDeleteObjectNotFoundException extends BaseException {
    protected HmsDeleteObjectNotFoundException(String code, String descriptionKey,
        Object[] parameters) {
            super(code, descriptionKey, parameters);
        }
        public HmsDeleteObjectNotFoundException(String code, String descriptionKey) {
            super(code, descriptionKey, null);
        }
}
