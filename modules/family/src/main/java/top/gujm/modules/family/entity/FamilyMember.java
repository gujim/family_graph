package top.gujm.modules.family.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 家庭成员Entity
 * @author gujimeng
 * @version 2022-08-03
 */
@Table(name="family_member", alias="a", label="成员信息", columns={
		@Column(name="id", attrName="id", label="ID", isPK=true),
		@Column(name="name", attrName="name", label="姓名", queryType=QueryType.LIKE),
		@Column(name="sex", attrName="sex", label="成员性别"),
		@Column(name="phone", attrName="phone", label="手机号", isQuery=false),
		@Column(name="email", attrName="email", label="邮箱", isQuery=false),
		@Column(name="father", attrName="father", label="父亲"),
		@Column(name="mother", attrName="mother", label="母亲"),
		@Column(name="marriage", attrName="marriage", label="已婚"),
		@Column(name="levers", attrName="levers", label="对象"),
		@Column(name="birthday", attrName="birthday", label="出生日期", isUpdateForce=true),
		@Column(name="user_id", attrName="userId", label="用户", isInsert=true, isUpdate=false, isQuery=true),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.create_date DESC",
		extColumnKeys = "familyMemberExtColumn"
)
public class FamilyMember extends DataEntity<FamilyMember> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String sex;		// 成员性别
	private String phone;		// 手机号
	private String email;		// 邮箱
	private String father;		// 父亲
	private String mother;		// 母亲
	private String marriage;		// 已婚
	private String levers;		// 对象
	private Date birthday;		// 出生日期
	private String userId;		// 用户
	private String fatherName;    // 父亲姓名
	private String motherName;    //母亲姓名
	private String leversName;    // 伴侣姓名
	private List<FamilyMemberProperties> familyMemberPropertiesList = ListUtils.newArrayList();		// 子表列表
	
	public FamilyMember() {
		this(null);
	}
	
	public FamilyMember(String id){
		super(id);
	}
	
	@NotBlank(message="姓名不能为空")
	@Size(min=0, max=64, message="姓名长度不能超过 64 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Size(min=0, max=1, message="成员性别长度不能超过 1 个字符")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Size(min=0, max=11, message="手机号长度不能超过 11 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Size(min=0, max=64, message="邮箱长度不能超过 64 个字符")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Size(min=0, max=64, message="父亲长度不能超过 64 个字符")
	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}
	
	@Size(min=0, max=64, message="母亲长度不能超过 64 个字符")
	public String getMother() {
		return mother;
	}

	public void setMother(String mother) {
		this.mother = mother;
	}
	
	@Size(min=0, max=1, message="已婚长度不能超过 1 个字符")
	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	
	@Size(min=0, max=64, message="对象长度不能超过 64 个字符")
	public String getLevers() {
		return levers;
	}

	public void setLevers(String levers) {
		this.levers = levers;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Size(min=0, max=64, message="用户长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getLeversName() {
		return leversName;
	}

	public void setLeversName(String leversName) {
		this.leversName = leversName;
	}

	@Valid
	public List<FamilyMemberProperties> getFamilyMemberPropertiesList() {
		return familyMemberPropertiesList;
	}

	public void setFamilyMemberPropertiesList(List<FamilyMemberProperties> familyMemberPropertiesList) {
		this.familyMemberPropertiesList = familyMemberPropertiesList;
	}
	
}