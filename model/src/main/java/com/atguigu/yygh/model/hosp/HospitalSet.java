package com.atguigu.yygh.model.hosp;

import com.atguigu.yygh.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * HospitalSet
 * </p>
 *
 * @author qy
 */
@Data
@ApiModel(description = "医院设置")
@TableName("hospital_set")
public class HospitalSet extends BaseEntity {
	//序列化操作的时候系统会把当前类的serialVersionUID写入到序列化文件中，当反序列化时系统会去检测文件中的serialVersionUID，
	// 判断它是否与当前类的serialVersionUID一致，如果一致就说明序列化类的版本与当前类版本是一样的，可以反序列化成功，否则失败。
	//serialVersionUID有两种显示的生成方式：
	//一是默认的1L，比如：private static final long serialVersionUID = 1L;
	private static final long serialVersionUID = 1L;
	//@ApiModelProperty()用于方法，字段； 表示对model属性的说明或者数据操作更改
	//value–字段说明
	@ApiModelProperty(value = "医院名称")
	@TableField("hosname")
	private String hosname;

	@ApiModelProperty(value = "医院编号")
	@TableField("hoscode")
	private String hoscode;

	@ApiModelProperty(value = "api基础路径")
	@TableField("api_url")
	private String apiUrl;

	@ApiModelProperty(value = "签名秘钥")
	@TableField("sign_key")
	private String signKey;

	@ApiModelProperty(value = "联系人姓名")
	@TableField("contacts_name")
	private String contactsName;

	@ApiModelProperty(value = "联系人手机")
	@TableField("contacts_phone")
	private String contactsPhone;

	@ApiModelProperty(value = "状态")
	@TableField("status")
	private Integer status;

}

