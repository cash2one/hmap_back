package hmap.core.hms.mapper;

import hmap.core.hms.dto.WxWorkflowPushDataDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by machenike on 2016/9/21.
 */
public class SelectStatusWxMapper  implements RowMapper<WxWorkflowPushDataDTO> {
    @Override
    public WxWorkflowPushDataDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        WxWorkflowPushDataDTO dto = new WxWorkflowPushDataDTO();
        dto.setId(rs.getInt(1));
        dto.setSourceInstanceId(rs.getString(2));
        dto.setSourceNodeId(rs.getString(3));
        dto.setSourceRecordId(rs.getString(4));
        dto.setSourceWorkflowId(rs.getString(5));
        dto.setSourceCode(rs.getString(6));
        return dto;
    }
}

