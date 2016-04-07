/**
 * 对收款界面操作的数据模型
 */
package com.model;

import com.db.*;

import javax.swing.table.*;

import java.util.*;
import java.sql.*;

@SuppressWarnings("serial")
public class ShowKuanModel extends AbstractTableModel {

	Vector<String> colums;// 列
	@SuppressWarnings("rawtypes")
	Vector<Vector> rows;// 行

	// 用于查询需要显示的员工信息
	@SuppressWarnings("rawtypes")
	public void query(String sql, String[] paras) {
		// 初始化列，及每一列的名字
		this.colums = new Vector<String>();
		
		colums.add("商品编号");
		colums.add("商品名称");
		colums.add("价        格");
		colums.add("购买数量");
		colums.add("合计金额");
		
		this.rows = new Vector<Vector>();
		// 创建sqlhelper对象
		SqlHelper sh = new SqlHelper();
		ResultSet rs = sh.query(sql, paras);
		try {

			// 从rs对象中可以得到一个ResultSetMetaData
			// rsmt可以的到结果有多少列，而且可以知道每列的名字，加入表头的信息
			ResultSetMetaData rsmt = rs.getMetaData();
//			for (int i = 0; i < rsmt.getColumnCount(); i++) {
//				this.colums.add(rsmt.getColumnName(i + 1));
//			}
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
	
	// 信息更新，包含增删改
	static public boolean update(String sql, String[] pid) {
		
		boolean b = false;
		SqlHelper sh = new SqlHelper();
		b = sh.update(sql, pid);
		
		return b;
	}
	
	// 用产品id检查产品是否存在
	public static boolean check(String sql,String pid) {
		
		boolean b = false;
		String[] paras = {pid};
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
	
	// 返回库存
	public static int get_p_num(String pid) {
		
		int num = 0;
		
		String[] paras = {pid};
		String sql = "select sum(Num) from Stcok where Pid = ";
		SqlHelper sh=new SqlHelper();
		try {
			
			ResultSet rs = sh.query(sql, paras);
			if(rs.next())
			{
				num = rs.getInt(1);
			}			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			sh.close();
		}
		
		return num;
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
