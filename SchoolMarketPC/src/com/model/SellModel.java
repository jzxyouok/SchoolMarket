/**
 *  对销售表进行操作的数据模型
 *  日期： 2013-07-28
 *  
 *  功能说明：
 *  	1.完成对销售表信息的查询
 *  	2.可以返回统计需要的各种数据
 */

package com.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.db.SqlHelper;

@SuppressWarnings("serial")
public class SellModel  extends AbstractTableModel{

	Vector<String> colums;// 列
	@SuppressWarnings("rawtypes")
	Vector<Vector> rows;// 行

	// 用于查询需要显示的员工信息
	@SuppressWarnings("rawtypes")
	public void query(String sql, String[] paras) {
		// 初始化列，及每一列的名字
		this.colums = new Vector<String>();
		
		colums.add("销售编号");
		colums.add("产品编号");
		colums.add("产品名称");
		colums.add("产品价格");
		colums.add("销售数量");
		colums.add("合计金额");
		colums.add("出售日期");
		colums.add("产品种类");
		
		this.rows = new Vector<Vector>();
		// 创建sqlhelper对象
		SqlHelper sh = new SqlHelper();
		ResultSet rs = sh.query(sql, paras);
		try {

			// 从rs对象中可以得到一个ResultSetMetaData
			// rsmt可以得到结果有多少列，而且可以知道每列的名字，加入表头的信息
			ResultSetMetaData rsmt =  rs.getMetaData();
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
	
	
	// 查询函数，返回一个布尔值
	public static boolean check(String sql, String[] paras) {
		
		boolean b = false;
		SqlHelper sh=new SqlHelper();
		try {
			
			ResultSet rs = sh.query(sql, paras);
			if(rs.next())
			{
				//如果进去，则比较
				if (rs.getInt(1) >= 1) {
					
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
	
	// 得到想要的数值
	public static double find(String sql, String[] paras) {
		
		double i = 0.0;
		SqlHelper sh=new SqlHelper();
		try {
			
			ResultSet rs = sh.query(sql, paras);
			while(rs.next())
			{
				//如果进去，则取出第一个值
				i = rs.getDouble(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			sh.close();
		}
		
		return i;
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
