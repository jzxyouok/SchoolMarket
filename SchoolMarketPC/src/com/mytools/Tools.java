/*
 *  工具集，用来收集一些公共的函数做成静态，完成代码的复用性
 */

package com.mytools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Enumeration;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;

public class Tools {
	
	// 判断一个字符串是否是数字，用是否抛异常来判断
    @SuppressWarnings("unused")
	public static boolean isNum(String ch)  
    {  
        try  
        {     
            double i = Double.parseDouble(ch);
            return true;  
        }  
        catch (NumberFormatException e)  
        {  
            return false;  
        }
    }
    
    // 当焦点在一个文本框中时全选
    public static void selsectAll(JTextField jlb) {
    	
    	jlb.setSelectionStart(0);
    	jlb.setSelectionColor(new Color(60, 148, 212));
    	jlb.setSelectionEnd(jlb.getText().length());
    }
    
    // 设置表格的样式
    @SuppressWarnings("static-access")
	public static void setTableStyle(JTable jtb) {
    	
		//设置选中行的背景色
    	jtb.setSelectionBackground(new Color(51,153,255));
		//设置表格每行的高度
    	jtb.setRowHeight(40);
    	// 设置点击表头自动实现排序
    	jtb.setAutoCreateRowSorter(true);
    	// 设置表头文字居中显示
		DefaultTableCellRenderer  renderer = (DefaultTableCellRenderer) jtb.getTableHeader().getDefaultRenderer();
		renderer.setHorizontalAlignment(renderer.CENTER);
		
		// 设置表格中的数据居中显示
		DefaultTableCellRenderer r=new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		jtb.setDefaultRenderer(Object.class,r);
		
		jtb.setFont(new Font("新宋体", Font.PLAIN, 15));
		fitTableColumns(jtb);
    }
    
    // 根据内容自动调节表格的列宽度
    @SuppressWarnings("rawtypes")
	private static void fitTableColumns(JTable myTable)
    {
         myTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
         JTableHeader header = myTable.getTableHeader();
         int rowCount = myTable.getRowCount();
         Enumeration columns = myTable.getColumnModel().getColumns();
         while(columns.hasMoreElements())
         {
             TableColumn column = (TableColumn)columns.nextElement();
             int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
             int width = (int)header.getDefaultRenderer().getTableCellRendererComponent
             (myTable, column.getIdentifier(), false, false, -1, col).getPreferredSize().getWidth();
             for(int row = 0; row < rowCount; row++)
             {
                 int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent
                 (myTable, myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
                 width = Math.max(width, preferedWidth);
             }
             header.setResizingColumn(column); // 此行很重要
             column.setWidth(width+myTable.getIntercellSpacing().width);
         }
    }
    
    // 设置滚动面板的样式, 注意将滚动面板设置成全局的变量，不然会出现刷新不了表格
    public static void setJspStyle(JScrollPane jsp) {
    	
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp.getViewport().setOpaque(false);
		jsp.getVerticalScrollBar().setOpaque(false);
    }
    
    // 获取系统当前时间
    public static String getlocaldatetime() {
    	
        java.util.Calendar c=java.util.Calendar.getInstance();
        java.text.SimpleDateFormat f=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        return f.format(c.getTime());
    }
    
    // 设置报表的样式
    public static void setReportStyle(JFreeChart chart) {
    	
    	// 获取图表区域对象
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(new Color(51,153,255));
		plot.setBackgroundAlpha(0.3f);
		// 水平底部列表
		CategoryAxis domainAxis = plot.getDomainAxis(); 
		// 隐藏尺度线
		domainAxis.setAxisLineVisible(false);
		domainAxis.setAxisLineStroke(new BasicStroke(5));
		domainAxis.setCategoryMargin(0.6);
		// 水平底部标题
		domainAxis.setLabelFont(new Font("新宋体", Font.BOLD, 14)); 
		// 垂直标题
		domainAxis.setTickLabelFont(new Font("新宋体", Font.BOLD, 12)); 
		// 获取柱状,可以设置柱形的大小
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(new Font("新宋体", Font.BOLD, 14));
		// 设置标题字体
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 22));
		
		BarRenderer redBarRenderer = (BarRenderer)plot.getRenderer();
		StandardBarPainter barpaint = new StandardBarPainter();
		// 设置为普通的柱形
		redBarRenderer.setBarPainter(barpaint);
		redBarRenderer.setSeriesPaint(0, new Color(51,153,255));
		
    }
}
