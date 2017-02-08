package hmap.core.cache.impl;

import com.hand.hap.cache.impl.HashStringRedisCache;
import hmap.core.hms.api.dto.HeaderAndLineDTO;
import hmap.core.hms.api.mapper.HmsHeaderMapper;
import hmap.core.hms.api.mapper.HmsLineMapper;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by user on 2016/8/1.
 */
public class ApiCache extends HashStringRedisCache<HeaderAndLineDTO> {

    private static final Logger logger = LoggerFactory.getLogger(ApiCache.class);
    private String apiSql = HmsHeaderMapper.class.getName() + ".getAllHeaderAndLine";
    private String lineSql = HmsLineMapper.class.getName() + ".getHeaderLineByLineId";


    @Override
    public void init() {
        setType(HeaderAndLineDTO.class);
        strSerializer = getRedisTemplate().getStringSerializer();
        initLoad();
    }

    @Override
    public HeaderAndLineDTO getValue(String key) {
        return super.getValue(key);
    }

    @Override
    public void setValue(String key, HeaderAndLineDTO headerAndLineDTO) {
        super.setValue(key, headerAndLineDTO);
    }


    public void initLoad() {

        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            sqlSession.select(apiSql, (resultContext) -> {
                HeaderAndLineDTO headerAndLineDTO = (HeaderAndLineDTO) resultContext.getResultObject();
                logger.info("cache result:{}", headerAndLineDTO.getInterfaceCode() + headerAndLineDTO.getLineCode());
                setValue(headerAndLineDTO.getInterfaceCode() + headerAndLineDTO.getLineCode(), headerAndLineDTO);
            });

        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("init api cache error:", e);
            }
        }

    }


    public void reload(Object lineId) {
        logger.info("test  lineId:{}", lineId);
        try (SqlSession sqlSession = getSqlSessionFactory().openSession()) {
            HeaderAndLineDTO headerAndLineDTO = sqlSession.selectOne(lineSql, lineId);
            setValue(headerAndLineDTO.getInterfaceCode() + headerAndLineDTO.getLineCode(), headerAndLineDTO);

        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("reload api cache error:", e);
            }
        }


    }


}
