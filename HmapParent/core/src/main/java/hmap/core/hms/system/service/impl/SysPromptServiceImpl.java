package hmap.core.hms.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.hand.hap.account.exception.UserException;
import com.hand.hap.cache.CacheDelete;
import com.hand.hap.cache.CacheSet;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.system.domain.SysPrompt;
import hmap.core.hms.system.mapper.SysPromptMapper;
import hmap.core.hms.system.service.ISysPromptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Koma.Tshu on 2016/8/11.
 */
@Service
public class SysPromptServiceImpl extends BaseServiceImpl<SysPrompt> implements ISysPromptService{
    @Autowired
    private SysPromptMapper sysPromptMapper;
    public List<SysPrompt> selectAllPrompts(SysPrompt sysPrompt,int pageNum, int pageSize) throws UserException{
        PageHelper.startPage(pageNum,pageSize);
        List<SysPrompt> list = sysPromptMapper.selectAllPrompts(sysPrompt);
        return list;
    }
    public SysPrompt selectByPromptId(Long promptId) throws UserException{
        SysPrompt sp = sysPromptMapper.selectByPromptId(promptId);
        return sp;
    }
    @CacheSet( cache = "prompt")
  public SysPrompt insertSelective(IRequest request, SysPrompt prompt) {
        super.insertSelective(request, prompt);
        return prompt;
    }

    @CacheDelete(cache = "prompt")
    public int deleteByPrimaryKey(SysPrompt prompt) {
        return super.deleteByPrimaryKey(prompt);
    }

    @CacheSet(cache = "prompt")
    public SysPrompt updateByPrimaryKeySelective(IRequest request, SysPrompt prompt) {
        super.updateByPrimaryKeySelective(request, prompt);
        return prompt;
    }
}
