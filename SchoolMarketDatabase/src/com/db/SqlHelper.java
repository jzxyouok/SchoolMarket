package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlHelper {
	// 定义需要的对象
	Connection ct = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	// 连接数据库需要的字符串
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/myshopdb";
	String user = "root";
	String passwd = "1314520";
	
	// 构造函数，初始化ct
		public SqlHelper() {
			try {
				// 加载驱动
				Class.forName(driver);
				// 得到连接
				ct = DriverManager.getConnection(url, user, passwd);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("驱动没有加载成功,原因是没有导入驱动！请检查");
			} catch (NullPointerException e) {
				// TODO Auto-generated catch bloc
				e.printStackTrace();
				System.out.println("数据库服务没有开启，请打开数据库服务，再重试");
			} catch (SQLException e) {
				// TODO Auto-generated catch bloc
				e.printStackTrace();
			} 
		}
		
		// 关闭资源的方法
		public void close() {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}

				if (ct != null) {
					ct.close();
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		
		// []paras，通过?赋值方式可以防止漏洞注入方式，保证安全性
		public ResultSet query(String sql, String[] paras) {
			try {
				ps = ct.prepareStatement(sql);
				// 对sql的参数赋值
				for (int i = 0; i < paras.length; i++) {
					ps.setString(i + 1, paras[i]);
				}
				// 执行查询
				rs = ps.executeQuery();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 返回结果集
			return rs;
		}
		
		// 增删改方法
		public boolean update(String sql, String[] paras) {
			
			boolean b = true;
			try {

				ps = ct.prepareStatement(sql);
				// 循环的对paras赋值，？赋值法
				for (int i = 0; i < paras.length; i++) {
					ps.setString(i+1, paras[i]);
				}
				// 执行操作
				ps.execute();
				
			} catch (Exception e) {
				// TODO:  handle exception
				e.printStackTrace();
				System.out.println("SqlHelper中增删改中出错啦，请检查！");
				b = false;
			}
			return b;
		} 
}
