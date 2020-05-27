package com.micro.fast.utils.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 统一结果封装
 *
 * @author zhihao.mao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> extends HashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String massage;

    /**
     * 数据
     */
    private T data;

}
