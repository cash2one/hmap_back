package hmap.core.hms.mapper;

import hmap.core.hms.dto.WxWorkflowDetailDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by machenike on 2016/9/21.
 */
public class WxWorkflowDetailMapper  implements RowMapper<WxWorkflowDetailDTO> {
    @Override
    public WxWorkflowDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        WxWorkflowDetailDTO dto = new WxWorkflowDetailDTO();
        dto.setApprover(rs.getString(1));
        dto.setReciever(rs.getString(2));
        dto.setApplier(rs.getString(3));
        dto.setTitle(rs.getString(4));
        dto.setDescription(rs.getString(5));
        dto.setApply_time(rs.getString(6));
        return dto;
    }
}
