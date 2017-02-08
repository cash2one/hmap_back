package hmap.core.hms.service;

import com.hand.hap.core.ProxySelf;

public interface IWxTokenService  extends ProxySelf<IWxTokenService>{
	public String connectForToken();
	public String post(String body,String https_url);
	public String get(String https_url);
}
