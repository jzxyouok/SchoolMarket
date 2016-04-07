/**
 * 
 */
package com.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import com.model.ProductModel;
import com.mytools.MyFont;
import com.mytools.Tools;
import com.sun.awt.AWTUtilities;

@SuppressWarnings("serial")
public class ProductTo_View extends JDialog implements ActionListener, MouseListener {
	
	//全局的位置变量，用于表示鼠标在窗口上的位置
	static Point origin = new Point();
	
	// 定义组件
	JButton close;
	JPanel showinput, all;
	JLabel id, num, date, who;
	JTextField idt, numt, datet, whot;

	JButton confirm, cancel;
	
	public static String ptype;
	
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
	private void setlab(JLabel jlb) {
		
		showinput.add(jlb);
		jlb.setFont(MyFont.Infolab);
	}
	private void setjtf(final JTextField jtf) {
		
		showinput.add(jtf);
		jtf.setOpaque(false);
		jtf.setFont(MyFont.PaddInfotext);
	}
	// 初始化放置所有信息的面板
	public void initall() {
		
		// 设置窗体的样式为当前系统的样式
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		all = new JPanel(null);
		all.setBackground(Color.white);
		all.setBounds(0, 0, 560, 498);
		all.setBorder(new MatteBorder(3, 3, 3, 3, new Color(60, 148, 212)));		
	}
	// 初始化关闭按钮
	public void initColse() {
			
		// 关闭按钮
		close = new JButton(new ImageIcon("image/JDialogClose.png"));
		close.setRolloverIcon(new ImageIcon("image/JDialogCloseC.png"));
		close.setBounds(525, 13, 22, 22);
		close.setForeground(Color.red);
		setbutton(close, 1);
	}
	// 显示输入信息面板初始化
	private void initShowinput() {
		
		showinput = new JPanel(new GridLayout(4, 2, -100, 10));
		showinput.setBounds(50, 40, 400, 200);
		
		id = new JLabel("  产品编号");
		setlab(id);
		idt = new JTextField();
		setjtf(idt);
		
		num = new JLabel("  产品数量");
		setlab(num);
		numt = new JTextField();
		setjtf(numt);
		
		date = new JLabel("  入库日期");
		setlab(date);
		datet = new JTextField();
		setjtf(datet);
		datet.setText(Tools.getlocaldatetime());
		datet.setEditable(false);
		datet.setForeground(Color.GRAY);
		
		who = new JLabel("登记负责人");
		setlab(who);
		whot = new JTextField();
		setjtf(whot);
		
		showinput.setOpaque(false);
		
		all.add(showinput);
	}

	public void initWindowsStyle() {
		
		confirm = new JButton("确 定");
		confirm.setBounds(100, 350, 110, 50);
		setbutton(confirm, 2);
		cancel = new JButton("退 出");
		cancel.setBounds(300, 350, 110, 50);
		setbutton(cancel, 2);
		
		all.add(confirm);
		all.add(cancel);
		
		this.add(close);
		this.add(all);
		
		this.setUndecorated(true);
		this.setLayout(null);
		this.setSize(560, 498);
		this.setLocationRelativeTo(null);
		WindowMove();
		setOpacity();
		this.setModal(true);
		this.setVisible(true);
	}
	// 构造函数1
	public ProductTo_View() {
		
		initall();
		initColse();
		initShowinput();
		initWindowsStyle();
	}
	
	// 构造函数2
	public ProductTo_View(ProductModel pm, int selrow) {
		
		initall();
		initColse();
		initShowinput();
		// 设置产品编号
		idt.setText(pm.getValueAt(selrow, 0).toString());
		initWindowsStyle();

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
		if (e.getSource() == confirm) {
			
			String pid = idt.getText().trim();
			String num = numt.getText().trim();
			String todate = datet.getText();
			String forwho = whot.getText().trim();
			
			// 1.确定所有信息都不为空
			if (pid.equals("") || num.equals("") || todate.equals("") || forwho.equals("")) {
				
				JOptionPane.showMessageDialog(this, "信息不能为空，请输入对应的信息");
				return;
			}
			
			// 2.确保数量是合法的
			if (!Tools.isNum(num) || Double.valueOf(num) <= 0.0) {
				
				JOptionPane.showMessageDialog(this, "<html><font style = 'font-size:16'>输入的数量非法　<br/><br/>是否存在下列问题：<br/>1.是否大于0?<br/>2.是否是合法的数字?<br/><br/>", " 温馨提示", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			// 3.判断是否存在该产品
			if (!ProductModel.check("select count(*) from ProductInfo where Pid = ?", pid)) {
				
				int i = JOptionPane.showConfirmDialog(this, "<html><br/><font style= 'font-size:18'>没有产品编号为:<font color = 'red'>"+pid+"</font>　的产品信息　<br/>是否需要添加?</font><br/><br/>", " 温馨提示", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (i == 0) {
					
					new Add_Product_View(this.getX()+20, this.getY()+50);
				}else {
					
					return;
				}
			}else {
				
				String addsql = "insert into ProductToInfo values (?, ?, ?, ?)";
				pid = ProductModel.getpid(pid);
				String[] addparas = {pid, num, todate, forwho};
				boolean b = ProductModel.update(addsql, addparas);
				
				while (!b) {
					
					JOptionPane.showMessageDialog(this, "抱歉的通知您，入库没有成功");
					return;
				}
				// 如果成功了，弹出提示成功信息，并提供选择是否继续
				if ( b == true) {
					
					// 更新产品库存表中的库存数量
					// 1.检查库存表中是否有该产品
					if (ProductModel.check("select count(*) from Stcok where Pid = ?", pid)) {
						
						// 表示有，更新数量
						String[] paras = {pid};
						b = ProductModel.update("update Stcok set Num = Num + num where Pid = ?", paras);
						if (b) {
							System.out.println("更新库存成功");
						}
					} else {
						
						String[] paras = {pid, num};
						b = ProductModel.update("insert into Stcok values (?, ?)", paras);
						if (b) {
							System.out.println("插入库存成功");
						}
					}
					this.dispose();
					int i = JOptionPane.showConfirmDialog(this, "<html><font style = 'font-size:15'>入库成功，是否继续添加?<br/>", " 温馨提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (i == 0) {				
						
						new ProductTo_View();
					}
				}		
			}
		}
		if (e.getSource() == cancel) {
			
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
					
					AWTUtilities.setWindowOpacity(ProductTo_View.this, alpha+=0.1);
				}
				else {
					AWTUtilities.setWindowOpacity(ProductTo_View.this, 1);
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
		
	}

}

