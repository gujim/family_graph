package top.gujm.modules.family.service;

import java.util.List;
import java.util.UUID;

import com.jeesite.common.config.Global;
import com.jeesite.common.io.FileUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.mybatis.mapper.MapperHelper;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.common.shiro.realms.IIiiiiiIiiIi;
import com.jeesite.common.shiro.realms.IiiiIiiiiIII;
import com.jeesite.modules.sys.dao.UserDao;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import top.gujm.modules.family.entity.FamilyMember;
import top.gujm.modules.family.dao.FamilyMemberDao;
import com.jeesite.modules.file.utils.FileUploadUtils;
import top.gujm.modules.family.entity.FamilyMemberProperties;
import top.gujm.modules.family.dao.FamilyMemberPropertiesDao;

/**
 * 家庭成员Service
 * @author gujimeng
 * @version 2022-08-03
 */
@Service
public class FamilyMemberService extends CrudService<FamilyMemberDao, FamilyMember> {
	
	@Autowired
	private FamilyMemberPropertiesDao familyMemberPropertiesDao;


	private String getExtColumnSql(FamilyMember familyMember){
		return "(SELECT name FROM "+ MapperHelper.getTableName(familyMember) + " WHERE id=a.father) as fatherName, " +
			"(SELECT name FROM "+ MapperHelper.getTableName(familyMember) + " WHERE id=a.mother) as motherName, " +
			"(SELECT name FROM "+ MapperHelper.getTableName(familyMember) + " WHERE id=a.levers) as leversName ";
	}
	// 设置数据权限(仅个人)
	private void setDataScope(FamilyMember familyMember){
		familyMember.getSqlMap().getWhere().and("user_id", QueryType.EQ, UserUtils.getUser().getId());
	}

	/**
	 * 获取单条数据
	 * @param familyMember
	 * @return
	 */
	@Override
	public FamilyMember get(FamilyMember familyMember) {
		familyMember.getSqlMap().add("familyMemberExtColumn", getExtColumnSql(familyMember));
		setDataScope(familyMember);
		// 判断是否管理员，
		FamilyMember entity = super.get(familyMember);
		if (entity != null){
			FamilyMemberProperties familyMemberProperties = new FamilyMemberProperties(entity);
			familyMemberProperties.setStatus(FamilyMemberProperties.STATUS_NORMAL);
			entity.setFamilyMemberPropertiesList(familyMemberPropertiesDao.findList(familyMemberProperties));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param familyMember 查询条件
	 * @param familyMember.page 分页对象
	 * @return
	 */
	@Override
	public Page<FamilyMember> findPage(FamilyMember familyMember) {
		familyMember.setUserId(UserUtils.getUser().getId());
		setDataScope(familyMember);
		familyMember.getSqlMap().add("familyMemberExtColumn", getExtColumnSql(familyMember));
		return super.findPage(familyMember);
	}
	
	/**
	 * 查询列表数据
	 * @param familyMember
	 * @return
	 */
	@Override
	public List<FamilyMember> findList(FamilyMember familyMember) {
		familyMember.setUserId(UserUtils.getUser().getId());
		setDataScope(familyMember);
		familyMember.getSqlMap().add("familyMemberExtColumn", getExtColumnSql(familyMember));
		return super.findList(familyMember);
	}
	
	/**
	 * 查询子表分页数据
	 * @param familyMemberProperties
	 * @param familyMemberProperties.page 分页对象
	 * @return
	 */
	public Page<FamilyMemberProperties> findSubPage(FamilyMemberProperties familyMemberProperties) {
		Page<FamilyMemberProperties> page = familyMemberProperties.getPage();
		page.setList(familyMemberPropertiesDao.findList(familyMemberProperties));
		return page;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param familyMember
	 */
	@Override
	@Transactional
	public void save(FamilyMember familyMember) {
		if(StringUtils.isNotBlank(familyMember.getAvatarBase64())) {
			String path = "avatar/" + UUID.randomUUID().toString().replaceAll("-", "") + "." + FileUtils.getFileExtensionByImageBase64(familyMember.getAvatarBase64());
			FileUtils.writeToFileByImageBase64(Global.getUserfilesBaseDir(path), familyMember.getAvatarBase64());
			familyMember.setAvatar("/userfiles/" + path);
		}
		familyMember.setUserId(UserUtils.getUser().getId());
		super.save(familyMember);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(familyMember, familyMember.getId(), "familyMember_image");
		// 保存 FamilyMember子表
		for (FamilyMemberProperties familyMemberProperties : familyMember.getFamilyMemberPropertiesList()){
			if (!FamilyMemberProperties.STATUS_DELETE.equals(familyMemberProperties.getStatus())){
				familyMemberProperties.setMemberId(familyMember);
				if (familyMemberProperties.getIsNewRecord()){
					familyMemberPropertiesDao.insert(familyMemberProperties);
				}else{
					familyMemberPropertiesDao.update(familyMemberProperties);
				}
			}else{
				familyMemberPropertiesDao.delete(familyMemberProperties);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param familyMember
	 */
	@Override
	@Transactional
	public void updateStatus(FamilyMember familyMember) {
		super.updateStatus(familyMember);
	}
	
	/**
	 * 删除数据
	 * @param familyMember
	 */
	@Override
	@Transactional
	public void delete(FamilyMember familyMember) {
		super.delete(familyMember);
		FamilyMemberProperties familyMemberProperties = new FamilyMemberProperties();
		familyMemberProperties.setMemberId(familyMember);
		familyMemberPropertiesDao.deleteByEntity(familyMemberProperties);
	}
	
}