/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.cache.impl Date:2016/9/17 0017 Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.cache.impl;

import java.util.HashMap;
import java.util.Map;

import com.hand.hap.system.mapper.LovMapper;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hand.hap.cache.impl.HashStringRedisCache;

import hmap.core.hms.api.dto.HmsInterfaceAuthDTO;
import hmap.core.hms.api.mapper.HmsInterfaceAuthMapper;
import org.springframework.stereotype.Service;

@Service
public class HmsInterfaceAuthCache extends HashStringRedisCache<HmsInterfaceAuthDTO> {
  private final static String IN_SYSTEM_TYPE = "IN";
  private final static String OUT_SYSTEM_TYPE = "OUT";
  private Logger logger = LoggerFactory.getLogger(HmapStaffCache.class);
  private String queryAllInterfaceHeadSqlId =
      HmsInterfaceAuthMapper.class.getName() + ".selectAllInterfaceHeadAuth";
  private String queryAllInterfaceLineSqlId =
      HmsInterfaceAuthMapper.class.getName() + ".selectAllInterfaceLineAuth";

  @Override
  public void init() {
    setType(HmsInterfaceAuthDTO.class);
    strSerializer = getRedisTemplate().getStringSerializer();
    // initLoad();
  }

  @Override
  protected void initLoad() {

    // 接口头权限
    // key =appid+interfaceCode
    // 对于外部api，使用如下key
    // 接口行权限
    // key =appid+interfaceCode+apiName
    // 对于平台api，使用如下key
    // 接口行权限
    // key =appid+interfaceCode+url

    Map<String, HmsInterfaceAuthDTO> map = new HashMap<>();
    try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
      // 放入头数据
      sqlSession.select(queryAllInterfaceHeadSqlId, (resultContext) -> {
        HmsInterfaceAuthDTO authDTO = (HmsInterfaceAuthDTO) resultContext.getResultObject();
        map.put(authDTO.getAppId().concat(authDTO.getSysName().toUpperCase()), authDTO);
      });
      map.forEach((k, v) -> {
        setValue(k, v);
      });
      map.clear();
      // 放入行数据
      sqlSession.select(queryAllInterfaceLineSqlId, (resultContext) -> {
        HmsInterfaceAuthDTO authDTO = (HmsInterfaceAuthDTO) resultContext.getResultObject();
        if (IN_SYSTEM_TYPE.equalsIgnoreCase(authDTO.getSystemType())) {
          map.put(authDTO.getAppId().concat(authDTO.getSysName().toUpperCase())
              .concat(authDTO.getIftUrl()), authDTO);
        } else if (OUT_SYSTEM_TYPE.equalsIgnoreCase(authDTO.getSystemType())) {
          map.put(authDTO.getAppId().concat(authDTO.getSysName().toUpperCase())
              .concat(authDTO.getApiName().toUpperCase()), authDTO);
        }
      });
      map.forEach((k, v) -> {
        setValue(k, v);
      });
      map.clear();
    } catch (Throwable e) {
      if (logger.isErrorEnabled()) {
        logger.error("init HmapStaffToken cache exception: ", e);
      }
    }
  }

  public void reload(Long interfaceLineId) {
    try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
      HmsInterfaceAuthDTO auth =
          sqlSession.selectOne(LovMapper.class.getName() + ".selectAuthByLineId", interfaceLineId);

      if (IN_SYSTEM_TYPE.equalsIgnoreCase(auth.getSystemType())) {
        setValue(auth.getAppId().concat(auth.getSysName().toUpperCase()).concat(auth.getIftUrl()),
            auth);
      } else if (OUT_SYSTEM_TYPE.equalsIgnoreCase(auth.getSystemType())) {
        setValue(auth.getAppId().concat(auth.getSysName().toUpperCase())
            .concat(auth.getApiName().toUpperCase()), auth);
      }
    }
  }

  @Override
  public void setValue(String key, HmsInterfaceAuthDTO auth) {
    super.setValue(key, auth);
  }

  @Override
  public HmsInterfaceAuthDTO getValue(String key) {
    return super.getValue(key);
  }
}
