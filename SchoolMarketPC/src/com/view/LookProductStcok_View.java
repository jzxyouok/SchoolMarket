/**
 * 查看产品库存情况的界面
 */
package com.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
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
import com.model.LookStcokModel;
import com.model.ProductModel;
import com.mytools.MyFont;
import com.mytools.Tools;
import com.sun.awt.AWTUtilities;

@SuppressWarnings("serial")
public class LookProductStcok_View extends JDialog implements ActionListener, MouseListener {
	
	//全局的位置变量，用于表示鼠标在窗口上的位置
	static Point origin = new Point();
	 // 用于获得窗口的大小
	final static int width=Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height=Toolkit.getDefaultToolkit().getScreenSize().height;
	
	// 定义组件
	JPanel closejp,findpanel, showtabeljp, all;
	
	JButton close;
	
	JComboBox<String> type, startnum, endnum;
	
	JTable lookstcoktable;
	JScrollPane jsp;
	
	String[] paras = {"1"};
	LookStcokModel lsm;
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
					
					AWTUtilities.setWindowOpacity(LookProductStcok_View.this, alpha+=0.1);
				}
				else {
					AWTUtilities.setWindowOpacity(LookProductStcok_View.this, 1);
					Timer source = (Timer) e.getSource();
					source.stop();
				}
			}
		};
		// 设置线程控制
		new Timer(50, lisener).start();
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
		
		
		Font PaddInfotext=new Font("新宋体",Font.PLAIN,20);
		
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
		type.setFont(new Font("新宋体",Font.PLAIN,15));
		JPanel typejp = new JPanel(new GridLayout(1, 1));
		typejp.setPreferredSize(new Dimension(145, 40));
		typejp.setOpaque(false);
		
		typejp.add(type);
		
		// 开始的数量
		Vector<String> temp1=new Vector<String>();
		
		for (int i = 0; i <= 38; i++) {
			
			temp1.add(String.valueOf(i));
		}
		startnum = new JComboBox<String>(temp1);
		startnum.setEditable(true);
		startnum.setFont(PaddInfotext);
		startnum.addActionListener(this);
		
		JPanel startjp = new JPanel(new GridLayout(1, 1));
		startjp.setPreferredSize(new Dimension(115, 40));
		startjp.add(startnum);
		
		// 结束日
		Vector<String> temp2=new Vector<String>();
		
		for (int i = 68; i >= 1; i--) {
			
			temp2.add(String.valueOf(i));
		}
		endnum = new JComboBox<String>(temp2);
		endnum.setEditable(true);
		endnum.setFont(PaddInfotext);
		endnum.addActionListener(this);
		
		JPanel endjp = new JPanel(new GridLayout(1, 1));
		endjp.setPreferredSize(new Dimension(115, 40));
		endjp.add(endnum);
		
		findpanel = new JPanel();
		findpanel.setPreferredSize(new Dimension(this.getX(), 60));
		findpanel.setOpaque(false);
		
		
		findpanel.add(typejp);
		JLabel kong = new JLabel("　　");
		findpanel.add(kong);
		JLabel info = new JLabel("产品库存量区间:");
		info.setFont(PaddInfotext);
		findpanel.add(info);
		findpanel.add(startjp);
		JLabel to = new JLabel("----");
		to.setFont(PaddInfotext);
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
		
		String sql = "select a.Pid, b.PName, b.PType, a.Num from Stcok a, ProductInfo b where a.Pid = b.Pid and 1 = ? order by a.Num";
		
		lsm = new LookStcokModel();
		lsm.query(sql, paras);
		
		lookstcoktable = new JTable(lsm);
		Tools.setTableStyle(lookstcoktable);
		
		jsp = new JScrollPane(lookstcoktable);
		Tools.setJspStyle(jsp);
		
		showtabeljp = new JPanel(new BorderLayout());
		showtabeljp.setOpaque(false);
		showtabeljp.setPreferredSize(new Dimension(this.getX(),	398));
		
		showtabeljp.add(jsp);
		
		all.add(showtabeljp, "Center");
	}
	// 构造函数
	public LookProductStcok_View() {
		
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
		//all.setBorder(new MatteBorder(2, 1, 1, 1, Color.GRAY));
		
		initfindpanel();
		initshowtable();
		
		this.add(all);
		
		this.setUndecorated(true);
		this.setSize((int)(width*0.8f)-330, (int)(height*0.8f)-200);
		this.setLocationRelativeTo(null);
		setOpacity();
		WindowMove();
		this.setModal(true);
		this.setVisible(true);
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource() == type) {
			
			updatetable();
		}
		if (e.getSource() == startnum) {
			
			updatetable();
		}
		if (e.getSource() == endnum) {
			
			updatetable();
		}
	}
	// 更新表格函数
	private void updatetable() {
		
		check();
		String[] paras = getselect();
		lsm = new LookStcokModel();
		if (paras[0].equals("--产品种类--")) {
			
			String[] newparas = {paras[1],paras[2]};
			String sql = "select a.Pid, b.PName, b.PType, a.Num from Stcok a, ProductInfo b where a.Pid = b.Pid and a.Num between ? and ? order by a.Num";
			lsm.query(sql, newparas);
			
		}else {
			
			String sql = "select a.Pid, b.PName, b.PType, a.Num from Stcok a, ProductInfo b where a.Pid = b.Pid and b.PType = ? and a.Num between ? and ? order by a.Num";
			lsm.query(sql, paras);
		}
		
		lookstcoktable.setModel(lsm);
		Tools.setTableStyle(lookstcoktable);
	}
	// 得到每个选择框中的内容
	private String[] getselect() {
		
		// 类别
		String gettype = type.getSelectedItem().toString();
		// 开始数量
		String getstartnum = startnum.getSelectedItem().toString();
		// 结束数量
		String getendnum = endnum.getSelectedItem().toString();
		
		String[] tiaojian = {gettype, getstartnum, getendnum};
		return tiaojian;
	}
	// 确保起始的数量要小于结束的数量
	private void check() {
		
		if (Integer.valueOf(startnum.getSelectedItem().toString()) > Integer.valueOf(startnum.getSelectedItem().toString())) {
			
			JOptionPane.showMessageDialog(this, "注意，开始的数量要小于结束的数量！");
			return;
		}
	}
	
}