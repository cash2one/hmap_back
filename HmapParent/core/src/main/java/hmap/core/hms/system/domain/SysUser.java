package hmap.core.hms.system.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Administrator on 2016/10/28 0028.
 */
public class SysUser {
    @Id
    @Column
    @GeneratedValue(
            generator = "IDENTITY"
    )
    private Long userId;
    @NotEmpty
    @Column
    private String userName;
}
