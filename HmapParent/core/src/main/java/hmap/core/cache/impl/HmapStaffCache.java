package hmap.core.cache.impl;

import hmap.core.hms.contact.domain.HmsStaff;
import hmap.core.hms.contact.mapper.HmsStaffMapper;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hand.hap.cache.impl.HashStringRedisCache;
@Service
public class HmapStaffCache extends HashStringRedisCache<HmsStaff> {
	private Logger logger = LoggerFactory.getLogger(HmapStaffCache.class);
	private String staffQuerySqlId = HmsStaffMapper.class.getName() + ".selectAll";

	  
	  @Override
	  public void init() {
	    setType(HmsStaff.class);
	    strSerializer = getRedisTemplate().getStringSerializer();
	    //initLoad();
	  }

	  @Override
	  protected void initLoad() {
	    Map<String, HmsStaff> map = new HashMap<>();
	    try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
	      sqlSession.select(staffQuerySqlId, (resultContext) -> {
	        HmsStaff staff = (HmsStaff) resultContext.getResultObject();
	        map.put(staff.getAccountNumber(), staff);
	      });
	      map.forEach((k, v) -> {
	        setValue(v.getAccountNumber(), v);
	      });
	      map.clear();

	    } catch (Throwable e) {
	      if (logger.isErrorEnabled()) {
	        logger.error("init HmapStaffToken cache exception: ", e);
	      }
	    }
	  }
	  @Override
	  public void setValue(String key, HmsStaff staff) {
	    super.setValue(key, staff);
	  }
	  @Override
	  public HmsStaff getValue(String key) {
	    return super.getValue(key);
	  }
	  
}
