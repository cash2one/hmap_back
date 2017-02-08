package hmap.core.hms.mapper;

import hmap.core.hms.dto.WxWorkflowPushDataDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by machenike on 2016/9/21.
 */
public class WxWorkflowPushDataMapper implements RowMapper<WxWorkflowPushDataDTO> {
    @Override
    public WxWorkflowPushDataDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        WxWorkflowPushDataDTO dto = new WxWorkflowPushDataDTO();
        dto.setSourceWorkflowId(rs.getString(1));
        dto.setSourceNodeId(rs.getString(2));
        dto.setSourceRecordId(rs.getString(3));
        dto.setSourceInstanceId(rs.getString(4));
        dto.setSourceCode("WX");
        dto.setStatusWx("N");
        dto.setStatusApp("N");
        dto.setCreatedBy(0L);
        dto.setUpdatedBy(0L);
        return dto;
    }
}
