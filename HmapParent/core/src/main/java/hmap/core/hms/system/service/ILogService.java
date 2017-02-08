/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.hms.service 
 * Date:2016/11/30 0030
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.system.service;

import net.logstash.logback.marker.LogstashMarker;

import java.util.Map;

public interface ILogService {
    public void auditLogInfo(String s);
    public void auditLogInfo(LogstashMarker lm);
    public void ctrlLogDebug(String s);
    public void ctrlLogInfo(String s);
    public void ctrlLogInfo(Map logInfo);
    public void ctrlLogError(String s);
    public void ctrlLogError(String s, Throwable e);
    public void serviceLogDebug(String s);
    public void serviceLogInfo(String s);
    public void serviceLogError(String s);
    public void serviceLogError(String s, Throwable e);
    public void jobLogDebug(String s);
    public void jobLogInfo(String s);
    public void jobLogError(String s);
    public void jobLogError(String s, Throwable e);
    public void consumerLogDebug(String s);
    public void consumerLogInfo(String s);
    public void consumerLogError(String s);
    public void consumerLogError(String s, Throwable e);
}
