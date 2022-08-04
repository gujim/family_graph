package top.gujm.modules.family.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import top.gujm.modules.family.entity.FamilyMember;
import top.gujm.modules.family.entity.FamilyMemberProperties;
import top.gujm.modules.family.service.FamilyMemberService;

/**
 * 家庭成员Controller
 * @author gujimeng
 * @version 2022-08-03
 */
@Controller
@RequestMapping(value = "${adminPath}/family/familyMember")
public class FamilyMemberController extends BaseController {

	@Autowired
	private FamilyMemberService familyMemberService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FamilyMember get(String id, boolean isNewRecord) {
		return familyMemberService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("family:familyMember:view")
	@RequestMapping(value = {"list", ""})
	public String list(FamilyMember familyMember, Model model) {
		model.addAttribute("familyMember", familyMember);
		return "modules/family/familyMemberList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("family:familyMember:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FamilyMember> listData(FamilyMember familyMember, HttpServletRequest request, HttpServletResponse response) {
		familyMember.setPage(new Page<>(request, response));
		Page<FamilyMember> page = familyMemberService.findPage(familyMember);
		return page;
	}
	
	/**
	 * 查询子表数据
	 */
	@RequiresPermissions("family:familyMember:view")
	@RequestMapping(value = "familyMemberPropertiesListData")
	@ResponseBody
	public Page<FamilyMemberProperties> subListData(FamilyMemberProperties familyMemberProperties, HttpServletRequest request, HttpServletResponse response) {
		familyMemberProperties.setPage(new Page<>(request, response));
		Page<FamilyMemberProperties> page = familyMemberService.findSubPage(familyMemberProperties);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("family:familyMember:view")
	@RequestMapping(value = "form")
	public String form(FamilyMember familyMember, Model model) {
		model.addAttribute("familyMember", familyMember);
		return "modules/family/familyMemberForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("family:familyMember:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FamilyMember familyMember) {
		familyMemberService.save(familyMember);
		return renderResult(Global.TRUE, text("保存成员成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("family:familyMember:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(FamilyMember familyMember) {
		familyMemberService.delete(familyMember);
		return renderResult(Global.TRUE, text("删除成员成功！"));
	}
	
}