package com.micro.fast.demo.db.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.micro.fast.mybatisplus.base.BaseEntity;
import lombok.*;

import java.io.Serializable;


/**
 * 用户entity
 *
 * @author shoufeng
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fast_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FastUserEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */
	private String userName;

	public static void main(String[] args) {
		FastUserEntity fastUserEntity = new FastUserEntity("kkkkkk");
		fastUserEntity.setId(1L);
		System.out.println(JSON.toJSONString(fastUserEntity));
	}
}
