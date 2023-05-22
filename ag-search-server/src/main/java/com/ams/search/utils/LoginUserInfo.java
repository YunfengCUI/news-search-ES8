/**
 * 
 */
package com.ams.search.utils;


//import com.ams.search.model.personnelManagement.User;
import com.ams.search.model.personnelManagement.User;
import org.apache.shiro.SecurityUtils;

/**
 * 【平台工具类】
 * @author AMAD
 *
 */
public class LoginUserInfo {

	/**
	 * 
	 */
	public LoginUserInfo() {
	}
	
	
	/**
	 * 获取当前登录用户实体
	 * @return 返回用户实体
	 */
	public static User getLoginUser() {
		return (User) SecurityUtils.getSubject().getPrincipal();
	}
	
	/**
	 * 获取当前登录用户ID
	 * @return
	 */
	public static String getLoginUserId() {
		User user=(User) SecurityUtils.getSubject().getPrincipal();
		return user.getUserId();
	}
	
	/**
	 * 获取当前登录用户名称
	 * @return
	 */
	public static String getLoginUserName() {
		String loginUserName = "";
		try {
			User user=(User) SecurityUtils.getSubject().getPrincipal();
			loginUserName = user.getUserName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginUserName ;
	}


}
