/**
 *  销售报表统计的数据模型
 *  日期： 2013-07-29
 *  
 *  作用：
 *  	1.根据不同的统计需求可以返回不同的数据集以完成不同报表的创建
 */

package com.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import org.jfree.data.*;
import org.jfree.data.category.*;
import org.jfree.data.general.DatasetUtilities;

import com.db.SqlHelper;

	
public class ReportModel {
	
	// 传入条件得到需要的数据集，用以完成不同需要报表数据集的创建
	public static CategoryDataset getsum(String titletext, String sql, String[] paras) {
		
		SqlHelper sh = new SqlHelper();

		ResultSet rs = sh.query(sql, paras);
		
		DefaultKeyedValues keyvalues = new DefaultKeyedValues();
		
		// 取出结果
		try {
			
			ResultSetMetaData rsmt = rs.getMetaData();
			while (rs.next()) {
				
				Vector<String> temp = new Vector<String>();
				for (int i = 0; i < rsmt.getColumnCount(); i++) {
					
					temp.add(rs.getString(i + 1));
				}
				keyvalues.addValue(temp.get(0), Double.valueOf(temp.get(1)));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
			sh.close();
		}
		// 创建数据集实例
		CategoryDataset dateset = DatasetUtilities.createCategoryDataset(titletext, keyvalues);
		
		return dateset;
	}	
}
