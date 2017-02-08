/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project Name:HmapParent
 * Package Name:hmap.core.hms.common.service.impl Date:2017/1/23 0023 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package hmap.core.hms.common.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import hmap.core.hms.common.service.IQrCodeService;
import hmap.core.hms.uploader.dto.UploadFileDTO;
import hmap.core.hms.system.service.IHmsSystemConfigService;
import hmap.core.hms.uploader.service.IHmsUploadObjectService;
import hmap.core.hms.system.service.ILogService;
import hmap.core.util.MatrixToImageWriter;
import org.springframework.stereotype.Service;

@Service
public class QrCodeServiceImpl implements IQrCodeService {
  @Autowired
  private IHmsUploadObjectService hmsUploadObjectService;
  @Autowired
  private ILogService logService;
  @Autowired
  private IHmsSystemConfigService systemConfigService;

  public String qrCodeUpload(String content) {
    String result = "";
    try {
      String path = systemConfigService.getConfigValue("qrCode.path");
      MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
      Map hints = new HashMap();
      hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
      BitMatrix bitMatrix =
          multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
      long timestamp = System.currentTimeMillis();
      String qrFileName = timestamp + ".jpg";
      File file = new File(path, qrFileName);
      MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file);

      InputStream in = new FileInputStream(file);
      UploadFileDTO upd = null;
      try {
        upd = hmsUploadObjectService.uploadFile(in, qrFileName);
      } catch (IOException e) {
        logService.serviceLogError("文件上传错误:" + e.getMessage());
        e.printStackTrace();
      }
      logService.serviceLogInfo("upload url:" + upd.getObjectUrl());
      result = upd.getObjectUrl();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
}
