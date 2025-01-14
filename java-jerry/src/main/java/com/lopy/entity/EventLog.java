package com.lopy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_event_log")
public class EventLog {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String requestUrl;
    private String requestArgs;
    private String requestMethod;
    private String requestBody;
    private Date logTime;
}
