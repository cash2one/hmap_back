package hmap.core.message.impl;

import com.hand.hap.message.IMessageConsumer;
import com.hand.hap.message.TopicMonitor;
import hmap.core.hms.contact.domain.HmsStaff;
import hmap.core.search.StaffSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by machenike on 2016/9/18.
 */
@TopicMonitor(channel={"public.es.staff.syn.insert","public.es.staff.syn.update","public.es.staff.syn.delete"} )
public class StaffIndexConsumer implements IMessageConsumer<HmsStaff> {
    @Autowired
    private StaffSearchRepository staffSearchRepository;

    @Override
    public void onMessage(HmsStaff staff, String pattern) {

        if (pattern.equals("public.es.staff.syn.insert")) {
            staffSearchRepository.save(staff);
        }else if(pattern.equals("public.es.staff.syn.update")){
            if(staffSearchRepository.exists(staff.getAccountNumber())){
                staffSearchRepository.save(staff);
            }
        }else if(pattern.equals("public.es.staff.syn.delete")){
            staffSearchRepository.delete(staff.getAccountNumber());
        }

    }
}

