package hmap.core.hms.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.domain.HmsWxSyn;

import java.util.List;

/**
 * Created by machenike on 2016/9/23.
 */
public interface HmsWxSynMapper  extends Mapper<HmsWxSyn> {
   // List<HmsWxSyn> selectByTypeAndOperation(String type,String operation);
    //List<HmsWxSyn> selectByType(String type);
    List<HmsWxSyn> selectPending();

}
