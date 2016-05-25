/**
 * 这是商家的数据模型，完成对商家表的个各种操作
 */
package com.model;
import com.db.*;

import javax.swing.table.*;
import java.util.*;
import java.sql.*;

public class MerchantModel extends AbstractTableModel{
	
	Vector<String> colums;// 列
	@SuppressWarnings("rawtypes")
	Vector<Vector> rows;// 行
	
	// 用于查询需要显示的商家信息
	@SuppressWarnings("rawtypes")
	public void query(String sql, String[] paras) {
		// 初始化列，及每一列的名字
		this.colums = new Vector<String>();
		
		colums.add("商家编号");
		colums.add("店         铺");
		colums.add("店         主");
		colums.add("性         别");
		colums.add("许  可  证");
		colums.add("电        话");
		colums.add("地        址");
		colums.add("登录密码");
		
		this.rows = new Vector<Vector>();
		// 创建sqlhelper对象
		SqlHelper sh = new SqlHelper();
		ResultSet rs = sh.query(sql, paras);
		try {

			// 从rs对象中可以得到一个ResultSetMetaData
			// rsmt可以的到结果有多少列，而且可以知道每列的名字，加入表头的信息
			ResultSetMetaData rsmt = rs.getMetaData();
			// 把rs的结果放入到rows
			while (rs.next()) {

				Vector<String> temp = new Vector<String>();
				for (int i = 0; i < rsmt.getColumnCount(); i++) {
					temp.add(rs.getString(i + 1));
				}
				rows.add(temp);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			sh.close();
		}
		
	}
	
	// 商家信息更新，包含增删改
	public boolean Merchantupdate(String sql, String[] newparas) {
		
		boolean b = false;
		SqlHelper sh = new SqlHelper();
		b = sh.update(sql, newparas);
		
		return b;
	}
	
	// 用一个字段来查询
	public boolean checkid(String id) {
		
		boolean b = false;
		String sql = "select count(*) from merchantinfo where Mid = ";
		String[] paras = {id};
		SqlHelper sh=new SqlHelper();
		try {
			
			ResultSet rs = sh.query(sql, paras);
			if(rs.next())
			{
				//如果进去，则比较
				if (rs.getInt(1) == 1) {
					
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
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return this.colums.get(column);
	}

	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.rows.size();
	}

	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.colums.size();
	}

	@SuppressWarnings("rawtypes")
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return ((Vector) rows.get(rowIndex)).get(columnIndex);
	}

}
