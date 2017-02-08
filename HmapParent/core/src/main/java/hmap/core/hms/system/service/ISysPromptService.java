package hmap.core.hms.system.service;

import com.hand.hap.account.exception.UserException;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hmap.core.hms.system.domain.SysPrompt;

import java.util.List;

/**
 * Created by Koma.Tshu on 2016/8/11.
 */
public interface ISysPromptService extends
        IBaseService<SysPrompt>,ProxySelf<ISysPromptService> {
    public List<SysPrompt> selectAllPrompts(SysPrompt sysPrompt,int pageNum, int pageSize) throws UserException;
    public SysPrompt selectByPromptId(Long promptId) throws UserException;
}
