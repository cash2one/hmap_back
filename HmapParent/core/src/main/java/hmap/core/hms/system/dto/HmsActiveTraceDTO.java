package hmap.core.hms.system.dto;

import com.hand.hap.system.dto.BaseDTO;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by machenike on 2016/10/21.
 */
@Table(name = "hms_active_trace")
public class HmsActiveTraceDTO extends BaseDTO {
    @Id
    private String id;
    private String userName;
    private Date lastActive;
    private Date dayTime;
    private Long dayCount;
    private Date weekTime;
    private Long weekCount;
    private Date monthTime;
    private Long monthCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLastActive() {
        return lastActive;
    }

    public void setLastActive(Date lastActive) {
        this.lastActive = lastActive;
    }

    public Date getDayTime() {
        return dayTime;
    }

    public void setDayTime(Date dayTime) {
        this.dayTime = dayTime;
    }

    public Long getDayCount() {
        return dayCount;
    }

    public void setDayCount(Long dayCount) {
        this.dayCount = dayCount;
    }

    public Date getWeekTime() {
        return weekTime;
    }

    public void setWeekTime(Date weekTime) {
        this.weekTime = weekTime;
    }

    public Long getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(Long weekCount) {
        this.weekCount = weekCount;
    }

    public Date getMonthTime() {
        return monthTime;
    }

    public void setMonthTime(Date monthTime) {
        this.monthTime = monthTime;
    }

    public Long getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(Long monthCount) {
        this.monthCount = monthCount;
    }

    @Override
    public String toString() {
        return "HrmsUserManageDTO{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", lastActive=" + lastActive +
                ", dayTime=" + dayTime +
                ", dayCount=" + dayCount +
                ", weekTime=" + weekTime +
                ", weekCount=" + weekCount +
                ", monthTime=" + monthTime +
                ", monthCount=" + monthCount +
                '}';
    }
}
