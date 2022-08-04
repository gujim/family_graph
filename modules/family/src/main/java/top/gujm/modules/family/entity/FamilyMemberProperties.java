package top.gujm.modules.family.entity;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 家庭成员Entity
 * @author gujimeng
 * @version 2022-08-03
 */
@Table(name="family_member_properties", alias="a", label="成员信息", columns={
		@Column(name="id", attrName="id", label="ID", isPK=true),
		@Column(name="property_name", attrName="propertyName", label="属性名", isQuery=false),
		@Column(name="property_value", attrName="propertyValue", label="属性值", isQuery=false),
		@Column(name="sort", attrName="sort", label="排序值", isQuery=false, isUpdateForce=true),
		@Column(name="member_id", attrName="memberId.id", label="家庭成员"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.create_date ASC"
)
public class FamilyMemberProperties extends DataEntity<FamilyMemberProperties> {
	
	private static final long serialVersionUID = 1L;
	private String propertyName;		// 属性名
	private String propertyValue;		// 属性值
	private Long sort;		// 排序值
	private FamilyMember memberId;		// 家庭成员 父类
	
	public FamilyMemberProperties() {
		this(null);
	}

	public FamilyMemberProperties(FamilyMember memberId){
		this.memberId = memberId;
	}
	
	@Size(min=0, max=64, message="属性名长度不能超过 64 个字符")
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	@Size(min=0, max=255, message="属性值长度不能超过 255 个字符")
	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
	
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
	public FamilyMember getMemberId() {
		return memberId;
	}

	public void setMemberId(FamilyMember memberId) {
		this.memberId = memberId;
	}
	
}