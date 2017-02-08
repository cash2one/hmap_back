/**
 * Copyright (c) 2016. Hand Enterprise Solution Company. All right reserved. Project
 * Name:hmap Package Name:hmap.core.hms.mapper Date:2016/7/13 0013 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.uploader.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.hap.mybatis.common.Mapper;

import hmap.core.hms.uploader.domain.HmsUploadObject;


public interface HmsUploadObjectMapper extends Mapper<HmsUploadObject> {
  public List<HmsUploadObject> selectByFunctionNameAndDataId(
      @Param("functionName") String functionName, @Param("dataId") String dataId);

  public List<HmsUploadObject> selectRecentByFunctionNameAndDataId(
      @Param("functionName") String functionName, @Param("dataId") String dataId);

  public HmsUploadObject selectByObjectUrl(@Param("objectUrl") String objectUrl);
}
