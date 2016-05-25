package com.model;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.db.SqlHelper;
@SuppressWarnings("serial")
public class ProductModel extends AbstractTableModel{
	Vector<String> colums;// 列
	@SuppressWarnings("rawtypes")
	Vector<Vector> rows;// 行
	
	// 用于查询需要显示的员工信息
	@SuppressWarnings("rawtypes")
	public void query(String sql, String paras[]) {
		// 初始化列，及每一列的名字
		this.colums = new Vector<String>();
		
		colums.add("产品编号");
		colums.add("产品名称");
		colums.add("价        格");
		colums.add("产品数量");
		colums.add("产品种类");
		
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
	
	// 信息更新，包含增删改
	static public boolean update(String sql, String[] paras) {
		
		boolean b = false;
		SqlHelper sh = new SqlHelper();
		b = sh.update(sql, paras);
		
		return b;
	}
	
	// 用一个字段来查询
		public static boolean check(String sql, String tiaojian) {
			
			boolean b = false;
			String []paras = {tiaojian};
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
		
		// 用一个字段来查询
		public static boolean checknum(String sql, String[] paras) {
			
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
		
		// 得到产品的id
		public static String getpid(String tiaojian) {
			
			String pid = null;
			String []paras = {tiaojian};
			String sql = "select Pid from productinfo where Pid = ?";
			SqlHelper sh=new SqlHelper();
			try {
				
				ResultSet rs = sh.query(sql, paras);
				if(rs.next())
				{
					pid = rs.getString(1);	
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				sh.close();
			}
			
			return pid;
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
