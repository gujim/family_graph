package top.gujm.modules.family.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import top.gujm.modules.family.entity.FamilyMemberProperties;

/**
 * 家庭成员DAO接口
 * @author gujimeng
 * @version 2022-08-03
 */
@MyBatisDao
public interface FamilyMemberPropertiesDao extends CrudDao<FamilyMemberProperties> {
	
}