/**
 *  查看产品入库记录窗口
 */

package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.UIManager;
import com.model.ProductModel;
import com.model.RecordModel;
import com.mytools.MyFont;
import com.mytools.Tools;
import com.sun.awt.AWTUtilities;

@SuppressWarnings("serial")
public class LookRecord_View extends JDialog implements ActionListener, MouseListener {
	
	//全局的位置变量，用于表示鼠标在窗口上的位置
	static Point origin = new Point();
	 // 用于获得窗口的大小
	final static int width=Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height=Toolkit.getDefaultToolkit().getScreenSize().height;
	
	// 定义组件
	JPanel closejp,findpanel, showtabeljp, all;
	
	JButton close;
	
	JComboBox<String> type, startY, startM, startD, endY, endM, endD;
	
	JTable recordtable;
	JScrollPane jsp;
	
	RecordModel rm;
	String[] paras = {"1"};
	
	public void setbutton(JButton jb, int type) {
		
		if (type == 1) {
			
			jb.setContentAreaFilled(false);
		}
		
		jb.setForeground(Color.blue);
		jb.setBorderPainted(false);
		jb.setFocusPainted(false);
		jb.addMouseListener(this);
		jb.setOpaque(false);
		jb.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jb.setFont(MyFont.PaddInfotext);
	}
	// 初始化关闭按钮
	public void initColse() {
			
		// 关闭按钮
		close = new JButton(new ImageIcon("image/JDialogClose.png"));
		close.setRolloverIcon(new ImageIcon("image/JDialogCloseC.png"));
		close.setBounds((int)(width*0.8f)-365, 13, 22, 22);
		close.setToolTipText("关闭");
		setbutton(close, 1);
		
		closejp.add(close);
	}
	// 初始化查找面板
	public void initfindpanel() {
			
		Vector<String> temp=new Vector<String>();
		temp.add("--产品种类--");
		// 从产品表中查询类别
		String typesql = "select distinct Ptype from ProductInfo where 1 = ?";
		ProductModel pm = new ProductModel();
		pm.query(typesql, paras);
		// 循环的加如temp中
		for (int i = 0; i < pm.getRowCount(); i++) {
			
			temp.add((String) pm.getValueAt(i, 0));
		}
		
		type = new JComboBox<String>(temp);
		type.addActionListener(this);
		JPanel typejp = new JPanel(new GridLayout(1, 1));
		typejp.setPreferredSize(new Dimension(115, 35));
		typejp.setOpaque(false);
		
		typejp.add(type);
		
		// 获取当前系统的年月日
		Calendar cal=Calendar.getInstance();
		int nowyears = cal.get(Calendar.YEAR);
		
		// 开始年
		Vector<String> temp1=new Vector<String>();
		
		for (int i = 2000; i < nowyears+1; i++) {
			
			temp1.add(String.valueOf(i)+"年");
		}
		startY = new JComboBox<String>(temp1);
		startY.addActionListener(this);
		
		// 开始月
		Vector<String> temp2=new Vector<String>();
		
		for (int i = 1; i < 13; i++) {
			
			temp2.add(String.valueOf(i)+"月");
		}
		startM = new JComboBox<String>(temp2);
		startM.addActionListener(this);

		
		// 开始日
		Vector<String> temp3=new Vector<String>();
		
		for (int i = 1; i < 32; i++) {
			
			temp3.add(String.valueOf(i)+"日");
		}
		startD = new JComboBox<String>(temp3);
		startD.addActionListener(this);
		
		JPanel startjp = new JPanel(new GridLayout(1, 3));
		startjp.setPreferredSize(new Dimension(200, 35));
		startjp.setOpaque(false);
		
		startjp.add(startY);
		startjp.add(startM);
		startjp.add(startD);
		
		// 结束年
		Vector<String> temp4=new Vector<String>();
		
		for (int i = nowyears; i >= 2000; i--) {
			
			temp4.add(String.valueOf(i)+"年");
		}
		endY = new JComboBox<String>(temp4);
		endY.addActionListener(this);
		
		
		// 结束月
		Vector<String> temp5=new Vector<String>();
		
		for (int i = 12; i >= 1; i--) {
			
			temp5.add(String.valueOf(i)+"月");
		}
		endM = new JComboBox<String>(temp5);
		endM.addActionListener(this);
		
		// 结束日
		Vector<String> temp6=new Vector<String>();
		
		for (int i = 31; i >= 1; i--) {
			
			temp6.add(String.valueOf(i)+"日");
		}
		endD = new JComboBox<String>(temp6);
		endD.addActionListener(this);
		//endD.setSelectedIndex(nowday-1);
		
		JPanel endjp = new JPanel(new GridLayout(1, 3));
		endjp.setPreferredSize(new Dimension(200, 35));
		endjp.setOpaque(false);
		
		endjp.add(endY);
		endjp.add(endM);
		endjp.add(endD);
		
		findpanel = new JPanel();
		findpanel.setPreferredSize(new Dimension(this.getX(), 60));
		findpanel.setOpaque(false);
		
		
		findpanel.add(typejp);
		JLabel kong = new JLabel("　　　　　");
		findpanel.add(kong);
		findpanel.add(startjp);
		JLabel to = new JLabel("　--至--　");
		findpanel.add(to);
		findpanel.add(endjp);
		
		closejp = new JPanel(null);
		closejp.setPreferredSize(new Dimension(this.getX(), 45));
		closejp.setOpaque(false);
		
		initColse();
		
		JPanel findpanelall = new JPanel(new BorderLayout());
		findpanelall.setPreferredSize(new Dimension(this.getX(), 100));
		
		findpanelall.add(closejp, "North");
		findpanelall.add(findpanel, "Center");
		findpanelall.setOpaque(false);		
		
		all.add(findpanelall, "North");
	}

	public void initshowtable() {
		
		String sql = "select a.ToId, a.Pid, b.PName, b.PType, a.Num, convert(varchar(19), a.ToDate, 120), a.ForWho from ProductToInfo a, ProductInfo b where a.Pid = b.Pid and 1 = ?";
		rm = new RecordModel();
		rm.query(sql, paras);
		
		recordtable = new JTable(rm);
		Tools.setTableStyle(recordtable);
		
		jsp = new JScrollPane(recordtable);
		Tools.setJspStyle(jsp);
		
		showtabeljp = new JPanel(new BorderLayout());
		showtabeljp.setOpaque(false);
		showtabeljp.setPreferredSize(new Dimension(this.getX(),	398));
		
		showtabeljp.add(jsp);
		
		all.add(showtabeljp, "Center");
	}
	// 构造函数
	public LookRecord_View() {
		
		// 设置控件的样式为当前系统的样式
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
		all = new JPanel(new BorderLayout());
		all.setPreferredSize(new Dimension(560,	498));
		all.setBackground(new Color(60, 148, 212));
		all.setBorder(BorderFactory.createEtchedBorder());
		
		initfindpanel();
		initshowtable();
		
		this.add(all);
		
		this.setUndecorated(true);
		this.setSize((int)(width*0.8f)-330, (int)(height*0.8f)-200);
		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowMove();
		setOpacity();
		this.setModal(true);
		this.setVisible(true);
	}

	// 窗体移动函数
	public void WindowMove() {
		
		//设置没有标题的窗口可以拖动
		this.addMouseListener(new MouseAdapter() 
		{
	        public void mousePressed(MouseEvent e)
	        {  //按下（mousePressed 不是点击，而是鼠标被按下没有抬起）
	                origin.x = e.getX();  //当鼠标按下的时候获得窗口当前的位置
	                origin.y = e.getY();
	        }
		});
		this.addMouseMotionListener(new MouseMotionAdapter()
		{
	        public void mouseDragged(MouseEvent e) 
	        {  
	                Point p =getLocation();  //当鼠标拖动时获取窗口当前位置
	                //设置窗口的位置
	                //窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
	                setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
	        }
	     });
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == close) {
			
			dispose();
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
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	// 窗口淡入淡出函数
	public void setOpacity() {
		
		// 窗口设置淡入淡出代码段
		AWTUtilities.setWindowOpacity(this, 0f);
		ActionListener lisener = new ActionListener() {
			
			float alpha = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (alpha < 0.9) {
					
					AWTUtilities.setWindowOpacity(LookRecord_View.this, alpha+=0.1);
				}
				else {
					AWTUtilities.setWindowOpacity(LookRecord_View.this, 1);
					Timer source = (Timer) e.getSource();
					source.stop();
				}
			}
		};
		// 设置线程控制
		new Timer(50, lisener).start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource() == type) {
			
			updatetable();
		}
		if (e.getSource() == startY) {
			
			updatetable();
		}
		if (e.getSource() == startM) {
			
			updatetable();
		}
		if (e.getSource() == startD) {
			
			updatetable();
		}
		if (e.getSource() == endY) {
			
			updatetable();
		}
		if (e.getSource() == endM) {
			
			updatetable();
		}
		if (e.getSource() == endD) {
			
			updatetable();
		}
	}
	// 更新表格函数
	private void updatetable() {
		
		check();
		rm = new RecordModel();
		String[] paras = getselect();
		if (paras[0].equals("--产品种类--")) {
			
			String[] newparas = {paras[1],paras[2]};
			String sql = "select a.ToId, a.Pid, b.PName, b.PType, a.Num, convert(varchar(19), a.ToDate, 120), a.ForWho from ProductToInfo a, ProductInfo b where a.Pid = b.Pid and a.ToDate between ? and ?";
			rm.query(sql, newparas);
			
		}else {
			
			String sql = "select a.ToId, a.Pid, b.PName, b.PType, a.Num, convert(varchar(19), a.ToDate, 120), a.ForWho from ProductToInfo a, ProductInfo b where a.Pid = b.Pid and b.PType = ? and a.ToDate between ? and ?";
			rm.query(sql, paras);
		}
		
		recordtable.setModel(rm);
		Tools.setTableStyle(recordtable);
	}
	// 得到每个选择框中的内容
	private String[] getselect() {
		
		String gettype = type.getSelectedItem().toString();
		
		// 得到起始日期
		String getstart = getsubdate(startY)+"-"+getsubdate(startM)+"-"+getsubdate(startD);

		// 得到结束的日期
		String getend = getsubdate(endY)+"-"+getsubdate(endM)+"-"+getsubdate(endD);
		
		String[] tiaojian = {gettype, getstart, getend};
		return tiaojian;
	}
	// 确保起始日期要小于结束的日期
	private void check() {
		
		if (Integer.valueOf(getsubdate(startY)) > Integer.valueOf(getsubdate(endY))) {
			
			JOptionPane.showMessageDialog(this, "结束日期要大于起始日期");
			return;
		}else if (Integer.valueOf(getsubdate(startM)) > Integer.valueOf(getsubdate(endM))) {
			
			JOptionPane.showMessageDialog(this, "结束日期要大于起始日期");
			return;
		}else if (Integer.valueOf(getsubdate(startD)) > Integer.valueOf(getsubdate(endD))) {
			
			JOptionPane.showMessageDialog(this, "结束日期要大于起始日期");
			return;
		}
	}
	// 得到去掉后面字的年月日
	private String getsubdate(JComboBox<String> jcb) {
		
		return jcb.getSelectedItem().toString().substring(0, jcb.getSelectedItem().toString().length()-1);
	}
}
