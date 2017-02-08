package hmap.core.util;

/**
 * Created by shanhd on 2016/9/27.
 */
public final class Constant {
    /*验证码类型以及有效时长*/
    public static final String REGISTER_TYPE="register";
    public static final String RESET_TYPE="reset";
    public static final String REPLACE_TYPE="replace";
    public static final int VALID_TIME=10; //单位分钟

    /*用户状态*/
    public static final String USER_ACTIVE="active";
    public static final String USER_INVALID="invalid";

    /*附件类型*/
    public static final String ATTACHE_IMAGE="IMAGE";
    public static final String IMG_TYPE="jpeg,jpg,png,gif,bmp";


    /*未分组*/
    public static final String UN_GROUP_ID="unGroup";
    public static final String UN_GROUP_NAME="未分组";

    /*保存图文操作类型*/
    public static final String ADD_SAVE="add";
    public static final String EDIT_SAVE="edit";

    /*图文消息状态*/
    public static final String ARTICLE_WAIT_SEND="wait_send";  //未发送
    public static final String ARTICLE_SENT="sent"; //已发送

    /*微信素材用途类型*/
    public static final String MATERIAL_NEWS_COVER="NEWS_COVER";          //图文封面
    public static final String MATERIAL_NEWS_CONTENT="NEWS_CONTENT";      //图文正文
    public static final String MATERIAL_MASS_MESSAGE="MASS_MESSAGE";    //群发消息

    /*微信素材类型*/
    public static final String MATERIAL_TYPE_IMAGE="image";          //图片
    public static final String MATERIAL_TYPE_NEWS="news";       //图文

    /*AES密钥*/
    public static final String AES_KEY="0102030405060708";
    public static final String AES_VI="0102030405060708";

    /*燃气表类型*/
    public static final String METER_TYPE_C="C"; //卡表用户
    public static final String METER_TYPE_R="R"; //普表用户
}
