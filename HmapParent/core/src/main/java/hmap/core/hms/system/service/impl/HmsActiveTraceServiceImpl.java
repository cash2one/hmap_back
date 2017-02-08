package hmap.core.hms.system.service.impl;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.impl.RequestHelper;
import com.hand.hap.system.service.impl.BaseServiceImpl;
import hmap.core.hms.system.dto.HmsActiveTraceDTO;
import hmap.core.hms.system.mapper.HmsActiveTraceMapper;
import hmap.core.hms.system.service.IHmsActiveTraceService;
import hmap.core.util.CountDateDiff;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * Created by machenike on 2016/10/21.
 */
@Service
public class HmsActiveTraceServiceImpl extends BaseServiceImpl<HmsActiveTraceDTO>
        implements IHmsActiveTraceService {
    @Resource
    private HmsActiveTraceMapper hmm;

    public void countRequest(String account) {
        Date now = new Date();
        Date today = new Timestamp(now.getTime());
        IRequest i = RequestHelper.newEmptyRequest();
        HmsActiveTraceDTO dto = hmm.selectByUserName(account);
        if (dto == null || dto.getLastActive() == null) {
            dto = new HmsActiveTraceDTO();
            dto.setUserName(account);
            dto.setId(UUID.randomUUID().toString());
            dto.setLastActive(today);
            dto.setDayTime(today);
            dto.setDayCount(1L);
            dto.setWeekTime(today);
            dto.setWeekCount(1L);
            dto.setMonthTime(today);
            dto.setMonthCount(1L);
            this.insertSelective(i, dto);
        } else {
            dto.setLastActive(today);
            Date dayDate = dto.getDayTime();
            Date weekDate = dto.getWeekTime();
            Date monthDate = dto.getMonthTime();

            if (CountDateDiff.weeksOfTwo(weekDate, today) > 0) {
                dto.setWeekTime(today);
                dto.setWeekCount(1L);
            } else {
                dto.setWeekTime(null);
                dto.setWeekCount(dto.getWeekCount() + 1);
            }
            if (CountDateDiff.monthsOfTwo(monthDate, today) >0) {
                dto.setDayTime(today);
                dto.setMonthTime(today);
                dto.setMonthCount(1L);
                dto.setDayCount(1L);
            } else {
                dto.setMonthTime(null);
                dto.setMonthCount(dto.getMonthCount() + 1);
                if (CountDateDiff.daysOfTwo(dayDate, today) >= 1) {
                    dto.setDayTime(today);
                    dto.setDayCount(1L);
                } else {
                    dto.setDayTime(null);
                    dto.setDayCount(dto.getDayCount() + 1);
                }
            }

            this.updateByPrimaryKeySelective(i, dto);


        }

    }
}
