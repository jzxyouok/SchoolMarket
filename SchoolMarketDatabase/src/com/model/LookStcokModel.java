/*
 * 操作库存表的数据模型
 */
package com.model;

import com.db.*;
import javax.swing.table.*;
import java.util.*;
import java.sql.*;

@SuppressWarnings("serial")
public class LookStcokModel extends AbstractTableModel{
	
	Vector<String> colums;// 列
	@SuppressWarnings("rawtypes")
	Vector<Vector> rows;// 行
	
	// 用于查询需要显示的库存信息
	@SuppressWarnings("rawtypes")
	public void query(String sql, String paras[]) {
		// 初始化列，及每一列的名字
		this.colums = new Vector<String>();
		
		colums.add("产品编号");
		colums.add("产品名称");
		colums.add("产品类别");
		colums.add("库存数量");
		
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
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return ((Vector) rows.get(rowIndex)).get(columnIndex);
	}

}
