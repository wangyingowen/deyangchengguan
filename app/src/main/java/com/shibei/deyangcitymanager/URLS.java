package com.shibei.deyangcitymanager;

/**
 * Created by wy250 on 2017/11/12.
 */

public class URLS {
//   /api/
    public static String HTTPHEAD = "http://7byte.iask.in/rscc/";
//    public static String HTTPHEAD = "http://178.178.0.4/";
    public static String GET_TOKEN = HTTPHEAD + "api/org/tokenauth2/";//获取Token认证
    public static String GET_POLICEINFO = HTTPHEAD + "api/org/police/";//获取警察信息

    public static String UPLOAD_HOUSEINFO = HTTPHEAD + "api/base/building/";//上传房屋信息以及列表
    public static String UPLOAD_COMPANYINFO = HTTPHEAD + "api/base/corp/";//上传单位信息以及列表

    public static String UPLOAD_ORGANIZATIONINFO = HTTPHEAD + "api/base/organization/";//上传组织信息以及列表

    public static String UPLOAD_JYZINFO = HTTPHEAD + "api/base/gasstation/";//上传油站信息以及列表

    public static String UPLOAD_PERONINFO = HTTPHEAD + "api/base/citizen/";//上传个人基础信息以及列表

    public static String EVENT_UPLOAD = HTTPHEAD + "api/event/event/";//事件上传
    public static String EVENT_SHENHE = HTTPHEAD + "api/event/audit";//事件审核
    public static String GET_BASE_ZD = HTTPHEAD + "api/core/sysdic/";//获取字典列表
    public static String GET_JOB_STATUS = HTTPHEAD + "api/task/stat/forreciver_count/";//获取工作状态
    public static String GET_SINGED_LIST = HTTPHEAD + "api/attnd/checklocat/";//获取签到列表/签到api/attnd/checklocat
    public static String GET_TASK_LIST = HTTPHEAD + "api/task/task/";//获取任务列表
    public static String DAY_RECOD = HTTPHEAD + "api/task/workdiary/";//日志
    public static String DAY_RECOD_UPLOAD = HTTPHEAD + "api/task/workdiary/";//日志
    public static String HEADICON_UPLOAD = HTTPHEAD + "api/org/police/";//修改个人信息
    public static String GET_EVENTDETAIL = HTTPHEAD + "api/event/event/";//获取事件详情
    public static String GET_TASK_DOLIST = HTTPHEAD + "api/task/action/?";//获取任务执行信息详情
    public static String DO_TASK = HTTPHEAD + "api/task/action/";//获取任务执行信息详情
    public static String GET_BUMEN = HTTPHEAD + "api/org/department/";//获取部门列表
    public static String GET_CAR= HTTPHEAD + "api/base/vehicle/";//车辆信息相关
    public static String UPDATE_LOCAITON= HTTPHEAD + "api/attnd/trackpoint/";//上传坐标
    public static String ADDADVACE= HTTPHEAD + "api/mainten/feedback/?format=json";//意见反馈
    public static String GET_NEWVERSION= HTTPHEAD + "api/mainten/applastvertion/RSCC_ANDRIODAPP?format=json";//版本升级
    public static String UPLOAD_XUNLUO= HTTPHEAD + "api/patrol/patrol/";//巡逻巡查
    public static String GET_TASKTJ= HTTPHEAD + "api/task/stat/group_count/";//工作统计
    public static String GET_EVENT_DOS= HTTPHEAD + "api/task/task/taskbytype/?format=json&";//事件-处理过程
    public static String GET_TXL= HTTPHEAD + "api/org/police/?format=json";//巡逻巡查
    public static String BIND_FAMILY= HTTPHEAD + "api/base/citizenrelation/";//绑定家庭关系
    public static String BIND_WORK= HTTPHEAD + "api/base/workrelation/";//工作关系
    public static String FILE= HTTPHEAD + "api/litefiler/filebox/";//文件

}
