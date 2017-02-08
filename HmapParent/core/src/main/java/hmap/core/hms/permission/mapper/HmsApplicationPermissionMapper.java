package hmap.core.hms.permission.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hmap.core.hms.permission.domain.HmsApplicationPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Koma.Tshu on 2016/8/25.
 */
public interface HmsApplicationPermissionMapper extends Mapper<HmsApplicationPermission> {

    List<HmsApplicationPermission> selectByDataKey(
            @Param("visibilityType") String visibilityType,
            @Param("dataType") String dataType,
            @Param("dataKey") String dataKey);

    List<HmsApplicationPermission> selectByValueId(
            @Param("visibilityType") String visibilityType,
            @Param("dataType") String dataType,
            @Param("valueId") String valueId);

    List<HmsApplicationPermission> preSelect(
            @Param("visibilityType") String visibilityType,
            @Param("dataType") String dataType);

    List<HmsApplicationPermission> selectByDataId(
            @Param("dataId") String dataId);

    HmsApplicationPermission selectById(
            @Param("id") String id);

    void deleteByDataId(
            @Param("dataId") String dataId);
}
