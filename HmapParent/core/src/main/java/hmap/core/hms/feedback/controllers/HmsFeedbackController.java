package hmap.core.hms.feedback.controllers;

import com.codahale.metrics.annotation.Timed;
import com.hand.hap.account.dto.User;
import com.hand.hap.account.service.IUserService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import hmap.core.hms.feedback.domain.HmsFeedback;
import hmap.core.hms.feedback.domain.HmsFeedbackReply;
import hmap.core.hms.feedback.dto.HmsFeedbackDTO;
import hmap.core.hms.feedback.dto.HmsFeedbackReplyDTO;
import hmap.core.hms.feedback.service.IHmsFeedbackReplyService;
import hmap.core.hms.feedback.service.IHmsFeedbackService;
import hmap.core.search.FeedbackSearchRepository;
import hmap.core.security.SecurityUtils;
import hmap.core.token.JdbcTokenStore;
import hmap.core.util.PageRequest;
import hmap.core.util.StatelessRequestHelper;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by maoyuhuan on 2016/11/4.
 */
@Controller
@RequestMapping("/api")
public class HmsFeedbackController {
    @Autowired
    IHmsFeedbackService feedbackServiceImpl;

    @Autowired
    IHmsFeedbackReplyService feedbackReplyService;

    @Autowired
    FeedbackSearchRepository feedbackSearchRepository;

    @Autowired
    IUserService userService;
    /**
     *
     */
    @Resource(name="tokenStore")
    private JdbcTokenStore tokenStore;
    @RequestMapping(value = "/feedquery", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    @Timed
    public ResponseData query(HttpServletRequest request,@RequestBody(required=false)
    HmsFeedbackDTO hmsFeedbackDTO) {
        IRequest iRequest = StatelessRequestHelper.createServiceRequest(request);
        User user=feedbackReplyService.selectUserIdByUserName(SecurityUtils.getCurrentUserLogin());
        HmsFeedbackDTO fbDTO = new HmsFeedbackDTO();
        fbDTO.setUserId(user.getUserId());
        List<HmsFeedbackDTO> listNoReply = feedbackServiceImpl.selectNotReply(iRequest, fbDTO);
        int count = listNoReply.size();
        List<HmsFeedbackDTO> list = feedbackServiceImpl.selectFeedback(iRequest, hmsFeedbackDTO, hmsFeedbackDTO
            .getPage(), hmsFeedbackDTO.getPageSize());
        for (int i=0;i<list.size();i++){
            list.get(i).setCount(count);
            String feedbackId=list.get(i).getFeedbackId();
            List<HmsFeedbackReplyDTO> songList= feedbackReplyService.selectReplyByFeedbackId(iRequest,feedbackId,
                hmsFeedbackDTO.getPage() , hmsFeedbackDTO.getPageSize());
            list.get(i).setHmsFeedbackReplyDTOs(songList);
        }
        return new ResponseData(list);
    }


    @RequestMapping(value = "/createFeedback", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public ResponseData createFeedback(HttpServletRequest request,@RequestBody(required=false)
    HmsFeedbackDTO hmsFeedbackDTO) {
        IRequest iRequest = StatelessRequestHelper.createServiceRequest(request);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String str=sdf.format(date);
        User user=feedbackReplyService.selectUserIdByUserName(SecurityUtils.getCurrentUserLogin());
        HmsFeedback hmsFeedback = new HmsFeedback();
        hmsFeedback.setFeedbackData(hmsFeedbackDTO.getFeedbackData());
        hmsFeedback.setFeedbackType(hmsFeedbackDTO.getFeedbackType());
        hmsFeedback.setUserId(user.getUserId());
        hmsFeedback.setFeedbackDate(str);
        hmsFeedback.setReplyFlag(0l);
        feedbackServiceImpl.insert(iRequest, hmsFeedback);
        HmsFeedbackDTO fbDTO = new HmsFeedbackDTO();
        fbDTO.setUserId(user.getUserId());
        List<HmsFeedbackDTO> list1 = feedbackServiceImpl.selectNotReply(iRequest, fbDTO);
        int count = list1.size();
        List<HmsFeedbackDTO> e = new ArrayList<HmsFeedbackDTO>();
        List<HmsFeedback> list = feedbackServiceImpl.selectFeedbackByKey(iRequest,hmsFeedback.getFeedbackData());
        for(int i=0;i<list.size();i++){
            User user1= new User();
            HmsFeedback feedback = list.get(i);
            user1.setUserId(feedback.getUserId());
            User theUser = userService.selectByPrimaryKey(iRequest,user1);
            List<HmsFeedbackReplyDTO> songList= feedbackReplyService.selectReplyByFeedbackId(iRequest, feedback.getFeedbackId(), hmsFeedbackDTO
                .getPage(), hmsFeedbackDTO.getPageSize());
            HmsFeedbackDTO feedbackDTO = new HmsFeedbackDTO();
            feedbackDTO.setUserId(feedback.getUserId());
            feedbackDTO.setReplyFlag(feedback.getReplyFlag());
            feedbackDTO.setFeedbackDate(feedback.getFeedbackDate());
            feedbackDTO.setUserName(theUser.getUserName());
            feedbackDTO.setFeedbackData(feedback.getFeedbackData());
            feedbackDTO.setFeedbackId(feedback.getFeedbackId());
            feedbackDTO.setFeedbackType(feedback.getFeedbackType());
            feedbackDTO.setUserType(theUser.getUserType());
            feedbackDTO.setCount(count);
            feedbackDTO.setHmsFeedbackReplyDTOs(songList);
            e.add(feedbackDTO);
        }
        return new ResponseData(e);
    }

    @RequestMapping(value = {"/insertReply"}, method = {RequestMethod.POST})
    @ResponseBody
    @Timed
    public ResponseData insert(HttpServletRequest request ,@RequestBody(required = false)
    HmsFeedbackReplyDTO hmsFeedbackReplyDTO) throws ParseException {
        ResponseData responseData=new ResponseData();
        IRequest iRequest = StatelessRequestHelper.createServiceRequest(request);
        User user=feedbackReplyService.selectUserIdByUserName(SecurityUtils.getCurrentUserLogin());
        HmsFeedbackReply hmsFeedbackReply = new HmsFeedbackReply();
        hmsFeedbackReply.setUserId(user.getUserId());
        hmsFeedbackReply.setFeedbackId(hmsFeedbackReplyDTO.getFeedbackId());
        hmsFeedbackReply.setReplyData(hmsFeedbackReplyDTO.getReplyData());
        hmsFeedbackReply.setReplyFlag(hmsFeedbackReplyDTO.getReplyFlag());
        hmsFeedbackReply.setReplyFlag(0l);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        String str=sdf.format(date);
        hmsFeedbackReply.setReplyDate(str);
        feedbackReplyService.reply(hmsFeedbackReply);
        feedbackReplyService.insert(iRequest, hmsFeedbackReply);
        feedbackServiceImpl.updateFeedback();
        return responseData;
    }


    @RequestMapping(value = "/queryNotReply", method = RequestMethod.GET)
    @ResponseBody
    @Timed
    public ResponseData  queryNotReply(HttpServletRequest request,
                                     @RequestBody(required = false) HmsFeedbackDTO hmsFeedbackDTO,PageRequest pr) {
        IRequest iRequest = StatelessRequestHelper.createServiceRequest(request);
        User user=feedbackReplyService.selectUserIdByUserName(SecurityUtils.getCurrentUserLogin());
        HmsFeedbackDTO fbDTO = new HmsFeedbackDTO();
        fbDTO.setUserId(user.getUserId());
        List<HmsFeedbackDTO> list = feedbackServiceImpl.selectNotReply(iRequest, fbDTO, pr.getPage(), pr.getPagesize());
        int count = list.size();
        for (int i=0;i<list.size();i++){
            list.get(i).setCount(count);
            String  feedbackId=list.get(i).getFeedbackId();
            List<HmsFeedbackReplyDTO> songList= feedbackReplyService.selectReplyByFeedbackId(iRequest,feedbackId,pr.getPage(), pr.getPagesize());
            list.get(i).setHmsFeedbackReplyDTOs(songList);
        }
        return new ResponseData(list);
    }


    @RequestMapping(value = "/printFeedback", method = RequestMethod.POST)
    @ResponseBody
    @Timed
    public void   printFeedback(HttpServletRequest request,
                                       @RequestBody(required = false) List<HmsFeedbackDTO> hmsFeedbackDTOs) throws Exception {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("信息反馈一");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("feedbackId");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("用户ID");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("用户类型");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("反馈信息");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("反馈日期");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("回复人ID");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("回复人");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("回复内容");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("回复时间");
        cell.setCellStyle(style);

        int x=0;
        for (int i = 0; i < hmsFeedbackDTOs.size(); i++)
        {
            x+=1;
            row = sheet.createRow(x);
            HmsFeedbackDTO stu = (HmsFeedbackDTO) hmsFeedbackDTOs.get(i);
            // 第四步，创建单元格，并设置值
            row.createCell(0).setCellValue( stu.getFeedbackId());
            row.createCell(1).setCellValue( stu.getUserName());
            row.createCell(2).setCellValue(stu.getUserId());
            row.createCell(3).setCellValue( stu.getUserType());
            row.createCell(4).setCellValue( stu.getFeedbackData());
            row.createCell(5).setCellValue( stu.getFeedbackDate());
            for(int z=0;z<(stu.getHmsFeedbackReplyDTOs().size());z++){
                HmsFeedbackReplyDTO hfbr = (HmsFeedbackReplyDTO)stu.getHmsFeedbackReplyDTOs().get(z);
                row.createCell(6).setCellValue(hfbr.getUserId());
                row.createCell(7).setCellValue(hfbr.getUserName());
                row.createCell(8).setCellValue(hfbr.getReplyData());
                row.createCell(9).setCellValue(hfbr.getReplyDate());
                x++;
                row = sheet.createRow(x);
            }
        }
        // 第六步，将文件存到指定位置
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String printDate = sdf.format(new Date());
            FileOutputStream fout = new FileOutputStream("c:/"+printDate+".xls");
            wb.write(fout);
            fout.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
