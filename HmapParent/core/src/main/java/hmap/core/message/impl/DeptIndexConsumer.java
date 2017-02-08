/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved.
 * Project Name:HmapParent
 * Package Name:hmap.core.message.impl 
 * Date:2016/11/19 0019
 * Create By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.message.impl;

import com.hand.hap.message.IMessageConsumer;
import com.hand.hap.message.TopicMonitor;
import hmap.core.hms.contact.domain.HmsDept;
import hmap.core.search.DeptSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;

@TopicMonitor(channel = {"public.es.dept.syn.insert", "public.es.dept.syn.update",
    "public.es.dept.syn.delete"})
public class DeptIndexConsumer implements IMessageConsumer<HmsDept> {
    @Autowired
    private DeptSearchRepository deptSearchRepository;
    @Override public void onMessage(HmsDept dept, String pattern) {
        if (pattern.equals("public.es.dept.syn.insert")) {
            deptSearchRepository.save(dept);
        } else if (pattern.equals("public.es.dept.syn.update")) {
            if (deptSearchRepository.exists(dept.getDeptId().toString())) {
                deptSearchRepository.save(dept);
            }
        } else if (pattern.equals("public.es.dept.syn.delete")) {
            deptSearchRepository.delete(dept.getDeptId().toString());
        }
    }
}
