/**
 *  销售情况显示面板
 *  
 *  日期：2013-07-27
 *  
 *  实现功能: 1.可以显示查看销售的信息
 *  	    2.销售的报表统计
 */

package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

import com.model.ReportModel;
import com.model.SellModel;
import com.mytools.MyFont;
import com.mytools.MySelfTabbedPane;
import com.mytools.Tools;

@SuppressWarnings("serial")
public class SellInfo extends JPanel implements ActionListener, MouseListener, FocusListener {
    
	// 用于获得窗口的大小
	final static int width=Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height=Toolkit.getDefaultToolkit().getScreenSize().height;
	
	// 公用颜色值
	Color color = new Color(22, 120, 195);
	
	// 选项卡面板,用来放置所有的面板
	JTabbedPane alljtp;
	
	// 每一个选项卡的面板
	JPanel SellRecord, AllYear, EveryYear;
	JComboBox<String> selectyear;
	ChartPanel chartpanel;
	
	// 第一个选项卡显示信息的面板
	JPanel findproduct, showtabel, showinfo, handle;
	
	// 查找记录输入面板的空间
	JLabel IdorName, type;
	JTextField getIdorName;
	JComboBox<String> gettype;
	JButton find;
	// 装载信息面板的面板
	JPanel showjp;
	JTable sellRecordtable = null;
	
	JComboBox<String> getYear, getMonth, gettypeshow;
	
	JLabel sum, min, max, average, everymonth;
	
	//定义一个鼠标指针的类型
	Cursor myCursor=new Cursor(Cursor.HAND_CURSOR);//手型鼠标
	
	JScrollPane jsp;
	
	String[] updateparas = {"1"};
	//convert(varchar(19), a.OutDate ,120),
	String updatesql = "select a.SIid, a.Pid, b.PName, b.Price, a.Num, (b.Price*a.Num), DATE_FORMAT(a.OutDate,'%Y-%m-%d %T'), b.Ptype from SellInfo a, ProductInfo b where a.Pid = b.Pid and 1 = ?";
	SellModel psell = new SellModel();
	
	MySelfTabbedPane all;
	
	public void setbutton(JButton jb, int type) {
		
		if (type == 1) {
			
			jb.setContentAreaFilled(false);
			jb.setBorderPainted(false);
			
		}
		
		jb.setFocusPainted(false);
		jb.addMouseListener(this);
		jb.setCursor(myCursor);
		jb.setOpaque(false);
	}
	private void setlab(JLabel jlb, int i) {
		
		if (i == 1) {
			
			findproduct.add(jlb);
			Font infofont = new Font("新宋体", Font.PLAIN, 19);
			jlb.setFont(infofont);
			jlb.setHorizontalAlignment(JLabel.CENTER);
			
		}
		if (i == 2) {
			
			Font infofont = new Font("新宋体", Font.PLAIN, 23);
			jlb.setFont(infofont);
		}		
	}
	private void setjtf(final JTextField jtf, int i) {
		
		if (i == 1) {
			
			findproduct.add(jtf);
			MatteBorder ubderline0 = new MatteBorder(0, 1, 0, 1, color);
			jtf.setBorder(ubderline0);
		}

		jtf.setOpaque(false);
		jtf.setHorizontalAlignment(JTextField.CENTER);
		jtf.setFont(MyFont.Infotext);
	}
	// 设置年份和月份的样式
	private JComboBox<String> setComboBox(Vector<String> vector) {
		
		JComboBox<String> jcb = new JComboBox<String>();
		jcb = new JComboBox<String>(vector);
		jcb.setFocusable(false);
		jcb.setPreferredSize(new Dimension(90, 30));
		jcb.setFont(new Font("新宋体", Font.PLAIN, 15));
		jcb.addActionListener(this);
		
		return jcb;
	}
	// 返回一个vector
	private Vector<String> getaVector(String sql) {
		
		Vector<String> vector = new Vector<String>();
		SellModel find = new SellModel();
		find.query(sql, updateparas);
		// 循环的加如yesrs中
		for (int i = 0; i < find.getRowCount(); i++) {
			
			vector.add((String) find.getValueAt(i, 0));
		}
		
		return vector;
	}
	// 粗略统计函数
	private void Rough_Statistics() {
		
		String getyear = getYear.getSelectedItem().toString();
		String getmonth = getMonth.getSelectedItem().toString();
		
		String[] find_for_year = {getyear};
		String[] find_for_month = {getyear, getmonth};
		
		// 获得年销售总额
		String yearssql = "select sum(a.Num*b.Price) as 总销售额,DATE_FORMAT(a.OutDate, '%Y') months from SellInfo a, ProductInfo b where a.Pid = b.Pid and DATE_FORMAT(a.OutDate, '%Y') = ?  group by DATE_FORMAT(a.OutDate, '%Y')";
		double sum = SellModel.find(yearssql, find_for_year);
		
		// 获得年销售最小值
		String minsql = "select * from (select DATE_FORMAT(a.OutDate, '%M') as whichmonth,sum(a.Num*b.Price) as months from SellInfo a, ProductInfo b where a.Pid = b.Pid and DATE_FORMAT(a.OutDate, '%Y') = ?  group by DATE_FORMAT(a.OutDate, '%M')) as temp order by temp.months";
		String min = String.valueOf(SellModel.find(minsql, find_for_year));
		
		// 获得年销售最大值
		String maxsql = "select * from (select DATE_FORMAT(a.OutDate, '%M') as whichmonth,sum(a.Num*b.Price) as months from SellInfo a, ProductInfo b where a.Pid = b.Pid and DATE_FORMAT(a.OutDate, '%Y') = ?  group by DATE_FORMAT(a.OutDate, '%M')) as temp order by temp.months desc";
		String max = String.valueOf(SellModel.find(maxsql, find_for_year));
		
		// 获得指定月的销售额
		String everysql = "select * from (select sum(a.Num*b.Price) as months, DATE_FORMAT(a.OutDate, '%M') as whichmonth from SellInfo a, ProductInfo b where a.Pid = b.Pid and DATE_FORMAT(a.OutDate, '%Y') = ? and DATE_FORMAT(a.OutDate, '%M') = ?  group by DATE_FORMAT(a.OutDate, '%M')) as temp";
		double onemonth = SellModel.find(everysql, find_for_month);
		
		// 双精度设置小数点
		DecimalFormat df = new DecimalFormat( "0.00");
		// 显示不同的值
		this.sum.setText(df.format(sum));
		this.min.setText(min.substring(0, min.length()-2)+"月");
		this.max.setText(max.substring(0, min.length()-2)+"月");
		this.average.setText(df.format(sum/12));
		
		// 选择不同的月份进行显示,如果没有则会显示0.0
		this.everymonth.setText(df.format(onemonth));
	}
	// 粗略统计面板初始化
	private void initHand() {
		
		// 销售大致情况显示面板
		handle = new JPanel(new BorderLayout());
		handle.setPreferredSize(new Dimension((int)(width*0.8), 90));
		handle.setBackground(new Color(186, 231, 253));
		
		JPanel sellYinfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		sellYinfo.setOpaque(false);
		sellYinfo.setPreferredSize(new Dimension((int)(width*0.8)-120, 45));
		
		// 得到一个年份的vector
		Vector<String> yesrs= getaVector("select distinct DATE_FORMAT(OutDate, '%Y') from SellInfo where 1 = ? order by DATE_FORMAT(OutDate, '%Y') desc");
	
		getYear = setComboBox(yesrs);
		
		sum = new JLabel("30000");
		setlab(sum, 2);
		
		min = new JLabel("3000");
		setlab(min, 2);
		
		max = new JLabel("5000");
		setlab(max, 2);
		
		average = new JLabel(String.valueOf(30000/12));
		setlab(average, 2);
		
		sellYinfo.add(new JLabel("<html><font style = 'font-size:16'>&nbsp&nbsp"));
		sellYinfo.add(getYear);
		sellYinfo.add(new JLabel("<html><font style = 'font-size:17'>&nbsp年销售总额："));
		sellYinfo.add(sum);
		sellYinfo.add(new JLabel("<html><font style = 'font-size:17'>&nbsp最小销售额月份："));
		sellYinfo.add(min);
		sellYinfo.add(new JLabel("<html><font style = 'font-size:17'>&nbsp最大销售额月份："));
		sellYinfo.add(max);
		sellYinfo.add(new JLabel("<html><font style = 'font-size:17'>&nbsp月平均销售额："));
		sellYinfo.add(average);
		
		JPanel sellMinfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		sellMinfo.setOpaque(false);
		sellMinfo.setPreferredSize(new Dimension((int)(width*0.8)-130, 45));
		
		// 得到一个月份的vector
		Vector<String> month = getaVector("select distinct DATE_FORMAT(OutDate, '%M') from SellInfo where 1 = ?");
		
		getMonth = setComboBox(month);
		
		everymonth = new JLabel("3000");
		setlab(everymonth, 2);
		
		Rough_Statistics();
		
		sellMinfo.add(new JLabel("<html><font style = 'font-size:16'>&nbsp&nbsp"));
		sellMinfo.add(getMonth);
		sellMinfo.add(new JLabel("<html><font style = 'font-size:17'>&nbsp月销售总额："));
		sellMinfo.add(everymonth);
		
		JPanel danwei = new JPanel(new FlowLayout(FlowLayout.LEFT));
		danwei.setOpaque(false);
		danwei.setPreferredSize(new Dimension(130, 90));
		danwei.add(new JLabel("<html><font style = 'font-size:18'>单位：元"));
		
		handle.add(sellYinfo, "North");
		handle.add(sellMinfo, "Center");
		handle.add(danwei, "East");
	}
	
	// 初始化销售记录面板
	private void initSellRecord() {
		
		// 处理左侧
		findproduct = new JPanel(new GridLayout(1, 4));
		findproduct.setBackground(new Color(186, 231, 253));
		findproduct.setPreferredSize(new Dimension((int)(width*0.8)-255, 80));
		IdorName = new JLabel("<html>产品编号或产品名称<br/>&nbsp(不区分大小写)");
		setlab(IdorName, 1);
		
		getIdorName = new JTextField(10);
		getIdorName.addFocusListener(this);
		setjtf(getIdorName, 1);
		
		find = new JButton(new ImageIcon("image/find.png"));
		setbutton(find, 1);
		findproduct.add(find);
		
		type = new JLabel("　　产品种类");
		type.setFont(MyFont.Infolab);
		setlab(type, 1);
		Vector<String> temp=new Vector<String>();
		temp.add("--所有产品--");
		// 从产品表中查询类别
		String typesql = "select distinct Ptype from ProductInfo where 1 = ?";
		psell.query(typesql, updateparas);
		// 循环的加如temp中
		for (int i = 0; i < psell.getRowCount(); i++) {
			
			temp.add((String) psell.getValueAt(i, 0));
		}
		
		gettype = new JComboBox<String>(temp);
		gettype.setFocusable(false);
		gettype.setOpaque(false);
		gettype.setBounds(0, 20, 150, 40);
		gettype.setBackground(Color.white);
		gettype.setFont(new Font("新宋体",Font.PLAIN,15));
		
		gettype.addActionListener(this);
		
		JPanel jtype = new JPanel(null);
		jtype.setOpaque(false);
		jtype.add(gettype);
		
		findproduct.add(jtype);
		
		// 设计表格
		psell.query(updatesql, updateparas);
		sellRecordtable = new JTable(psell);
		
		// 调用工具Tools类中的设置表格样式方法
		Tools.setTableStyle(sellRecordtable);
		sellRecordtable.addMouseListener(this);
		sellRecordtable.setOpaque(false);
		
		// 滚动面板
		jsp=new JScrollPane(sellRecordtable);
		jsp.setBorder(new MatteBorder(1, 0, 1, 0, color));
		Tools.setJspStyle(jsp);
		
		showtabel = new JPanel(new BorderLayout());
		showtabel.setOpaque(false);
		// 设置面板的大小
		showtabel.setPreferredSize(new Dimension((int)(width*0.8), (int)(height*0.8)-155));
		
		showtabel.add(jsp, "Center");		
		
		// 调用初始化统计面板函数
		initHand();
		
		showjp = new JPanel(new BorderLayout());
		showjp.setOpaque(false);
		// 用于设置面板的边框
		showjp.setBorder(new MatteBorder(0, 0, 0, 0, color));
		showjp.add(findproduct, "North");
		showjp.add(showtabel, "Center");
		showjp.add(handle, "South");		
		
		
		SellRecord = new JPanel(new BorderLayout());
		
		SellRecord.add(showjp, "Center");
		
		alljtp.add(SellRecord, "产品销售记录");
	}
	
	// 初始化历年销售统计面板
	private void initAllYears() {
		
		// 得到一个年份的vector
		Vector<String> yesrs= getaVector("select distinct DATE_FORMAT(OutDate, '%Y') from SellInfo where 1 = ? order by DATE_FORMAT(OutDate, '%Y')");
		// 创建柱形图统计面板
		
		// 1.得到数据集
		String getyearssql = "select DATE_FORMAT(a.OutDate, '%Y') 年份, sum(a.Num*b.Price) 年销售总额 from SellInfo a, ProductInfo b where a.Pid = b.Pid and 1 = ? group by DATE_FORMAT(a.OutDate, '%Y')";
		CategoryDataset dateset =  ReportModel.getsum("年具体销售额",getyearssql, updateparas);
		// 2.创建JfreeChart对象
		JFreeChart chart = ChartFactory.createBarChart(yesrs.get(0)+"年—"+yesrs.get(yesrs.size()-1)+"年"+"销售情况统计(单位:元)", // 图表标题
				"", // X轴标签
				"销售额",  // Y轴标签
				dateset, // 数据集
				PlotOrientation.VERTICAL, // 图表方向
				false, // 是否显示图例(对于简单的柱状图必须是false)
				true, // 是否生成工具
				false // 是否生成URL链接
				);

		// 3.用JfreeChart对象创建一个ChartPanel面板
		ChartPanel chartpanel = new ChartPanel(chart);
		chartpanel.setOpaque(false);
		chartpanel.setPreferredSize(new Dimension((int)(width*0.8)-60, (int)(height*0.8)-240));
		// 设置样式
		Tools.setReportStyle(chart);
		
		
		AllYear = new JPanel(new BorderLayout());
		AllYear.setOpaque(false);
		
		JPanel top = new JPanel();
		top.setPreferredSize(new Dimension((int)(width*0.8), 20));
		top.setBackground(Color.white);
		
		JPanel up = new JPanel();
		up.setPreferredSize(new Dimension((int)(width*0.8), 40));
		up.add(new JLabel("<html><font size = '5'>年份(指存在销售记录的年份)<br/><br/>"));
		up.setBackground(Color.white);
		
		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(30, (int)(height*0.8)));
		left.setBackground(Color.white);
		
		JPanel right = new JPanel();
		right.setPreferredSize(new Dimension(30, (int)(height*0.8)));
		right.setBackground(Color.white);
		
		AllYear.add(top, "North");
		AllYear.add(chartpanel);
		AllYear.add(left, "East");
		AllYear.add(right, "West");
		AllYear.add(up, "South");
		
		alljtp.add(AllYear, "历年销售统计");
	}
	// 创建一个指定年份的月统计报表面板
	private void getchart_forYear() {
		
		
		// 创建柱形图统计面板
		
		// 1.得到数据集
		String[] monthparas = {selectyear.getSelectedItem().toString()};
		String getmonthsql = "select DATE_FORMAT(a.OutDate, '%M') 月份, sum(a.Num*b.Price) 年销售总额 from SellInfo a, ProductInfo b where a.Pid = b.Pid and DATE_FORMAT(a.OutDate, '%Y') = ? group by DATE_FORMAT(a.OutDate, '%M')";
		CategoryDataset dateset =  ReportModel.getsum("月具体销售额",getmonthsql, monthparas);
		// 2.创建JfreeChart对象
		JFreeChart chart = ChartFactory.createBarChart("", // 图表标题
				"", // X轴标签
				"销售额",  // Y轴标签
				dateset, // 数据集
				PlotOrientation.VERTICAL, // 图表方向
				false, // 是否显示图例(对于简单的柱状图必须是false)
				true, // 是否生成工具
				false // 是否生成URL链接
				);

		
		// 3.用JfreeChart对象创建一个ChartPanel面板
		chartpanel = new ChartPanel(chart);
		chartpanel.setOpaque(false);
		chartpanel.setPreferredSize(new Dimension((int)(width*0.8)-60, (int)(height*0.8)-240));
		// 设置样式
		Tools.setReportStyle(chart);
		
		EveryYear.add(chartpanel);
	}
	// 初始化每年销售统计面板
	private void initEveryYear() {
		
		
		// 得到一个年份的vector
		Vector<String> yesrs= getaVector("select distinct DATE_FORMAT(OutDate, '%Y') from SellInfo where 1 = ?");
	
		this.selectyear = setComboBox(yesrs);
		
		EveryYear = new JPanel(new BorderLayout());
		EveryYear.setOpaque(false);
		
		JPanel top = new JPanel();
		top.setPreferredSize(new Dimension((int)(width*0.8), 40));
		top.add(selectyear);
		top.add(new JLabel("<html><font size = '6'>年&nbsp&nbsp每月销售情况统计"));
		top.setBackground(Color.white);
		
		getchart_forYear();
		
		JPanel up = new JPanel();
		up.setPreferredSize(new Dimension((int)(width*0.8), 40));
		up.add(new JLabel("<html><font size = '5'>月份(指存在销售记录的月份)<br/><br/>", JLabel.CENTER));
		up.setBackground(Color.white);
		
		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(30, (int)(height*0.8)));
		left.setBackground(Color.white);
		
		JPanel right = new JPanel();
		right.setPreferredSize(new Dimension(30, (int)(height*0.8)));
		right.setBackground(Color.white);
		
		
		EveryYear.add(top, "North");
		
		EveryYear.add(left, "East");
		EveryYear.add(right, "West");
		EveryYear.add(up, "South");
		
		alljtp.add(EveryYear, "每年销售统计");
	}
	public SellInfo() {
		
		// 设置窗体的样式为当前系统的样式
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	    UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("微软雅黑", Font.PLAIN, 12));
		
		alljtp = new JTabbedPane();
		
		// 初始化每一个选项卡面板
		initSellRecord();
		initAllYears();
		initEveryYear();
							
		alljtp.setFont(new Font("新宋体", Font.PLAIN, 16));
		alljtp.setBorder(new MatteBorder(0, 2, 2, 2, color));
		
		MySelfTabbedPane ui = new MySelfTabbedPane();
		
		alljtp.setUI(ui);
		alljtp.setOpaque(false);
		
		this.setLayout(new BorderLayout());
		this.add(alljtp);
		this.setOpaque(false);
		this.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == find) {
			
			findsellrecord();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == find) {
			
			find.setIcon(new ImageIcon("image/findC.png"));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == find) {
			
			find.setIcon(new ImageIcon("image/find.png"));
		}
	}
	
	// 更新表格模型函数，当表格需要更新是调用
	public void updatetable(String sql, String[] paras) {
		
		psell = new SellModel();
		psell.query(sql, paras);
		sellRecordtable.setModel(psell);
		Tools.setTableStyle(sellRecordtable);
	}
	
	// 查询函数
	private void findsellrecord() {
		
		// 得到查找的条件
		String tiaojian = getIdorName.getText().trim();
		
		while (tiaojian.isEmpty()) {
			
			JOptionPane.showMessageDialog(this, "请输入要查找的内容");
			return;
		}
		
		String[] tiaojianparas = {tiaojian, tiaojian};
		
		if (gettype.getSelectedItem().equals("--所有产品--")) {
			
			// 对SellInfo ProductInfo表进行联合查询
	 		if (!SellModel.check("select count(*) from SellInfo a, ProductInfo b where a.Pid = b.Pid and (a.Pid like '%'+?+'%' or b.PName like '%'+?+'%')", tiaojianparas)) {
				
				JOptionPane.showMessageDialog(this, "抱歉，没有相关产品的销售记录");
	 		}	
	 		
	 		updatetable("select a.SIId, a.Pid, b.PName, b.Price, a.Num, (b.Price*a.Num), DATE_FORMAT(a.OutDate,'%Y-%m-%d %T'), b.Ptype from SellInfo a, ProductInfo b where a.Pid = b.Pid and (a.Pid like '%'+?+'%' or b.PName like '%'+?+'%')", tiaojianparas);
			
		} else {
			
			String[] newtiaojianparas = {tiaojian, tiaojian, gettype.getSelectedItem().toString()};
			
			// 对SellInfo ProductInfo表进行联合查询
	 		if (!SellModel.check("select count(*) from SellInfo a, ProductInfo b where a.Pid = b.Pid" +
	 				" and (a.Pid like '%'+?+'%' or b.PName like '%'+?+'%') and b.Ptype = ?", newtiaojianparas)) {
				
	 			JOptionPane.showMessageDialog(this, "<html><br/><font size = '5'>在产品类别：<font color = 'red'>"+gettype.getSelectedItem()+"</font>中<br/>没有找到与：<font color = 'red'>"+tiaojian+"</font>&nbsp&nbsp相关的销售记录</font><br/><br/>");
	 		}
			updatetable("select a.SIId, a.Pid, b.PName, b.Price, a.Num, (b.Price*a.Num), DATE_FORMAT(a.OutDate,'%Y-%m-%d %T'), b.Ptype " +
						"from SellInfo a, ProductInfo b where a.Pid = b.Pid and (a.Pid like '%'+?+'%' or b.PName like '%'+?+'%') " +
						"and b.Ptype = ?", newtiaojianparas);
		}

	}
	// 查看只是指定年份的销售记录
	private void look_for_year() {
		
		String getyear = this.getYear.getSelectedItem().toString();

		String gettype = this.gettype.getSelectedItem().toString();
		
		String[] look_more = {getyear};
		String[] look_more_withtype = {getyear, gettype};
		
		if (gettype.equals("--所有产品--")) {
			
				
	 		updatetable("select a.SIId, a.Pid, b.PName, b.Price, a.Num, (b.Price*a.Num), DATE_FORMAT(a.OutDate,'%Y-%m-%d %T'), b.Ptype from SellInfo a, ProductInfo b where a.Pid = b.Pid and DATE_FORMAT(a.OutDate, '%Y') = ? ", look_more);
	 		
			
		}else {
			
			// 对SellInfo ProductInfo表进行联合查询
	 		if (!SellModel.check("select count(*) from SellInfo a, ProductInfo b where a.Pid = b.Pid" +
	 				" and DATE_FORMAT(OutDate, '%Y') = ? and b.Ptype = ?", look_more_withtype)) {
				
	 			JOptionPane.showMessageDialog(this, "<html><br/><font size = '4'><font color = 'red'>"+getyear+"年"+"&nbsp&nbsp</font><font color = 'red'>"+gettype+"</font>类别"+"&nbsp&nbsp没有销售记录</font><br/>");
	 			
				
			}
				
			updatetable("select a.SIId, a.Pid, b.PName, b.Price, a.Num, (b.Price*a.Num), DATE_FORMAT(a.OutDate,'%Y-%m-%d %T'), b.Ptype " +
						"from SellInfo a, ProductInfo b where a.Pid = b.Pid " +
						"and DATE_FORMAT(OutDate, '%Y') = ? and b.Ptype = ?", look_more_withtype);
			
		}
	}
	// 查看指定年月的销售记录
	private void look_more_sellrecord() {
		
		String getyear = this.getYear.getSelectedItem().toString();
		String getmonth = this.getMonth.getSelectedItem().toString();
		String gettype = this.gettype.getSelectedItem().toString();
		
		String[] look_more = {getyear, getmonth};
		String[] look_more_withtype = {getyear, getmonth, gettype};
		
		if (gettype.equals("--所有产品--")) {
			
			// 不带产品类别的更新
	 		if (!SellModel.check("select count(*) from SellInfo  where DATE_FORMAT(OutDate, '%Y') = ? and DATE_FORMAT(OutDate, '%M') = ?", look_more)) {
				
				JOptionPane.showMessageDialog(this, "<html><br/><font color = 'red'>"+getyear+"年"+getmonth+"月"+"</font>&nbsp&nbsp没有销售记录<br/>");	
			}
				
	 		updatetable("select a.SIId, a.Pid, b.PName, b.Price, a.Num, (b.Price*a.Num), DATE_FORMAT(a.OutDate,'%Y-%m-%d %T'), b.Ptype from SellInfo a, ProductInfo b where a.Pid = b.Pid and DATE_FORMAT(a.OutDate, '%Y') = ? and DATE_FORMAT(a.OutDate, '%M') = ?", look_more);
	 		
		}else {
			
			// 对SellInfo ProductInfo表进行联合查询
	 		if (!SellModel.check("select count(*) from SellInfo a, ProductInfo b where a.Pid = b.Pid" +
	 				" and DATE_FORMAT(OutDate, '%Y') = ? and DATE_FORMAT(OutDate, '%M') = ? and b.Ptype = ?", look_more_withtype)) {
				
	 			JOptionPane.showMessageDialog(this, "<html><br/><font size = '4'><font color = 'red'>"+getyear+"年"+getmonth+"月</font><font color = 'red'>"+gettype+"</font>类别"+"&nbsp&nbsp没有销售记录</font>");
	 			
				
			}
				
			updatetable("select a.SIId, a.Pid, b.PName, b.Price, a.Num, (b.Price*a.Num), DATE_FORMAT(a.OutDate,'%Y-%m-%d %T'), b.Ptype " +
						"from SellInfo a, ProductInfo b where a.Pid = b.Pid " +
						"and DATE_FORMAT(OutDate, '%M') = ? and DATE_FORMAT(OutDate, '%M') = ? and b.Ptype = ?", look_more_withtype);
			
		}
	}
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		Component comp =e.getComponent();
		if (comp instanceof JTextField) {
			
			Tools.selsectAll(getIdorName);
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		Component comp =e.getComponent();
		if (comp instanceof JTextField) {
			
			
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.gettype) {
			
			if (getIdorName.getText().trim().isEmpty()) {
				
				if (gettype.getSelectedItem().equals("--所有产品--")) {
					
					updatetable(updatesql, updateparas);
					
				}else {
					
					String[] parasnew={gettype.getSelectedItem().toString()};
					String sql = "select a.SIId, a.Pid, b.PName, b.Price, a.Num, (b.Price*a.Num), DATE_FORMAT(a.OutDate,'%Y-%m-%d %T'), b.Ptype from SellInfo a, ProductInfo b where a.Pid = b.Pid and b.Ptype = ?";
					updatetable(sql, parasnew);
				}
				
			} else {
				
				findsellrecord();
			}
		}
		
		if (e.getSource() == this.getYear) {
			
			this.Rough_Statistics();
			look_for_year();
		}
		if (e.getSource() == this.getMonth) {
			
			this.Rough_Statistics();
			look_more_sellrecord();
		}
		
		if (e.getSource() == this.selectyear) {
			
			this.EveryYear.remove(chartpanel);
			getchart_forYear();
		}
	}
}
