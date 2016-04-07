/**
 *  对用户登录表操作的数据模型
 */

package com.model;

import java.sql.ResultSet;
import java.util.Vector;

import com.db.SqlHelper;

public class LoginModel {
	
	
	
	public static Vector<String> find(String sql, String[] paras) {
		
		Vector<String> temp = new Vector<String>();
		SqlHelper sh=new SqlHelper();
		try {
			
			ResultSet rs = sh.query(sql, paras);
			while(rs.next())
			{
				//如果进去，则取出第一个值
				temp.add(rs.getString(1));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			sh.close();
		}
		
		return temp;
	}
	
	// 判断是否存在登录权限
	public static boolean checkid(String eid) {
		
		boolean b = false;
		SqlHelper sh = new SqlHelper();
		String[] paras = {eid};
		String sql  = "select count(*) from UserLogin where Eid = ?";
		ResultSet rs = sh.query(sql, paras);
		try {
			
			while (rs.next()) {
				
				if(rs.getInt(1) == 1) {
					
					b = true;
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			
			sh.close();
		}
		return b;
	}
	
	// 匹配用户名和密码
	public static boolean checkpassword(String uid, String password) {
		
		boolean b = false;
		String[] paras = {uid};
		SqlHelper sh = new SqlHelper();
		ResultSet rs = sh.query("select Upassword from UserLogin where Eid = ?", paras);
		try {
			
			while (rs.next()) {
				
				if(rs.getString(1).equals(password)) {
					
					b = true;
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return b;
	}
}
