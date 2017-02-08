package hmap.core.hms.system.mapper;

/**
 * Created by Koma.Tshu on 2016/8/11.
 */
import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.system.domain.SysPrompt;

import java.util.List;

public interface SysPromptMapper  extends Mapper<SysPrompt>{
    public List<SysPrompt> selectAllPrompts(SysPrompt sysPrompt);
    public SysPrompt selectByPromptId(Long promptId);
}
