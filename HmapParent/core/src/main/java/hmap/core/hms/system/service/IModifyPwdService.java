package hmap.core.hms.system.service;

import com.hand.hap.account.exception.UserException;
import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;

public interface IModifyPwdService extends ProxySelf<IModifyPwdService> {

	boolean validatePassword(IRequest var1, String var2, String var3, String var4, Long var5) throws UserException;

}
