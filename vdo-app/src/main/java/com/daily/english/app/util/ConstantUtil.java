package com.daily.english.app.util;

public class ConstantUtil {

    public static final String path = "/data";

    public static final String domain = "www.vdoenglish.com";

    public static final String fileDataPath = "https://api.cdeclips.com";

//	public static final String fileDataPath = "http://203.184.141.249";

    /**
     * DES加密key
     */
    public static final String decryptKey = "hang!@#$";

    /**
     * SHA-1加密key
     */
    public static final String sha1Key = "Fd8dhlKE3efqQ90";

    /**
     * 有道翻译接口get
     */
    public static final String keyfrom = "EDCMS2015";
    public static final String key = "292449268";
    public static final String youdaoUrl = "http://fanyi.youdao.com/openapi.do?keyfrom=" + keyfrom + "&key=" + key + "&type=data&doctype=json&version=1.1&q=";

    //用户注册校验
    public static final int R1 = 1;//注册成功
    public static final int R2 = 2;//用户名已存在

    //用户找回密码校验
    public static final int F1 = 1;//邮件发送成功
    public static final int F2 = 2;//邮箱（用户名）不存在

    //系统常用常量
    public static final boolean TRUE = true;//是
    public static final boolean FALSE = false;//是
    public static final int YES = 1;//是
    public static final int NO = 0;//否   
    public static final int FAIL = 0;//失败
    public static final int SUCCESS = 1;//成功
    public static final int EXCEPTION = -1;//失败 

    public static final String S1 = "1";//正常

    //1**: 缺少必要参数
    public static final String S100 = "100";//参数为空 
    public static final String S101 = "101";//用户账号不能为空
    public static final String S102 = "102";//用户账号格式不正确
    public static final String S103 = "103";//密码不能为空
    public static final String S104 = "104";//密码格式不正确
    public static final String S105 = "105";//密码长度必须大于6位，小于13位
    public static final String S106 = "106";//再次输入密码
    public static final String S107 = "107";//密码输入不匹配
    public static final String S108 = "108";//手机号码不能为空
    public static final String S109 = "109";//手机号码为11位数字
    public static final String S110 = "110";//邮箱地址不能为空
    public static final String S111 = "111";//邮箱地址格式不正确
    public static final String S112 = "112";//验证码不能为空
    public static final String S113 = "113";//旧密码不能为空
    public static final String S114 = "114";//新密码不能为空
    public static final String S115 = "115";//再次输入新密码
    public static final String S116 = "116";//新旧密码不匹配

    //2**: 参数无效
    public static final String S201 = "201";//客户端数据签名失败
    public static final String S202 = "202";//客户端数据签名验证失败
    public static final String S203 = "203";//客户端加密失败
    public static final String S204 = "204";//客户端解密失败
    public static final String S205 = "205";//服务端数据签名失败
    public static final String S206 = "206";//服务端数据签名验证失败
    public static final String S207 = "207";//服务端加密失败
    public static final String S208 = "208";//服务端解密失败
    public static final String S209 = "209";//Mac地址获取失败
    public static final String S210 = "210";//TOKEN生成失败 
    public static final String S211 = "211";//密钥生成失败 

    public static final String S404 = "404";//页面未找到
    public static final String S500 = "500";// 服务器内部错误

    //11**: 功能校验错误（服务端返回的错误）
    public static final String S1101 = "1101";//Mac地址不存在
    public static final String S1102 = "1102";//Mac地址非法
    public static final String S1103 = "1103";//TOKEN非法
    public static final String S1104 = "1104";//TOKEN过期
    public static final String S1105 = "1105";//授权失败
    public static final String S1106 = "1106";//用户名不存在
    public static final String S1107 = "1107";//用户名格式不正确
    public static final String S1108 = "1108";//用户冻结
    public static final String S1109 = "1109";//用户非法
    public static final String S1110 = "1110";//用户异常
    public static final String S1111 = "1111";//密码错误
    public static final String S1112 = "1112";//验证码错误
    public static final String S1113 = "1113";//验证码发送失败
    public static final String S1114 = "1114";//手机号码非法或不存在
    public static final String S1115 = "1115";//用户账号已绑定其他终端
    public static final String S1116 = "1116";//用户账号未绑定当前终端
    public static final String S1117 = "1117";//旧密码错误
    public static final String S1118 = "1118";//新旧密码不匹配 
    public static final String S1119 = "1119";//没有登录 
    public static final String S1120 = "1120";//登录失败 
    public static final String S1121 = "1121";//验证码接收类型为空
    public static final String S1122 = "1122";//验证码接收类型有误
    public static final String S1123 = "1123";//验证码已过期
    public static final String S1124 = "1124";//手机绑定失败

    //12**: 系统校验错误（系统运行时的错误）
    public static final String S1201 = "1201";//系统发生数据错误或运行时异常  
    public static final String S1202 = "1202";//报文解析错误
    public static final String S1203 = "1203";//查找实体为空
    public static final String S1204 = "1204";//查找列表为空
    public static final String S1205 = "1205";//调用头部信息为空
    public static final String S1206 = "1206";//参数有误

    //2016-08-11 VDO用户提示信息
    public static final String Login_UserPwd_Error = "Login_UserPwd_Error ";//用户名或密码错误
    public static final String Register_Code_Error = "Register_Code_Error";//验证码错误提示
    public static final String Register_SendCode_Failed = "Register_SendCode_Failed";//发送验证码失败提示
    public static final String Register_SendCode_Done = "Register_SendCode_Done";//发送验证码成功提示
    public static final String Register_Signup_Done = "Register_Signup_Done";//注册成功提示
    public static final String Register_Signup_Failed = "Register_Signup_Failed";//注册失败提示
    public static final String USER_SENDPWD_Failed = "USER_SENDPWD_Failed";//发送密码失败提示
    public static final String User_SendPwd_Done = "User_SendPwd_Done";//发送密码成功提示
    public static final String User_Modify_Failed = "User_Modify_Failed";//提交修改失败提示
    public static final String User_Modify_Done = "User_Modify_Done";//提交修改成功提示
    public static final String User_ChangePwd_Failed = "User_ChangePwd_Failed";//提交修改失败提示
    public static final String User_ChangePwd_Done = "User_ChangePwd_Done";//提交成功提示
    public static final String Feedback_Submit_Done = "Feedback_Submit_Done";//提交成功提示
    public static final String Feedback_Submit_Failed = "Feedback_Submit_Failed";//提交失败提示
    public static final String Video_Collect_Done = "Video_Collect_Done";//收藏成功提示
    public static final String Video_Collect_Failed = "Video_Collect_Failed";//收藏失败提示
    public static final String Video_QuizSubmit_Done = "Video_QuizSubmit_Done";//提交测试题成功
    public static final String Video_QuizSubmit_Failed = "Video_QuizSubmit_Failed";//提交测试题失败

    //video类型
    public static final String Editor_Pick = "1";//Editor's Pick
    public static final String OneMin_News = "3";//One-Min News
    public static final String Feature_Story = "4";//Feature Story
    public static final String Real_IELTS = "5";//Real IELTS
    public static final String HKDSE = "6";//HKDSE
    public static final String Movie = "8";//Movie
    public static final String Travel = "10";//Travel
    public static final String University_Info = "11";//University Info.
    public static final String Business_English = "12";//Business English
    public static final String science = "13";//science
    public static final String D_Campus = "14";//D-Campus

}
