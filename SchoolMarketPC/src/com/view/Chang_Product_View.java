/**
 * 添加产品信息界面
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
import javax.swing.JComboBox;
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
public class Chang_Product_View extends JDialog implements MouseListener, ActionListener {
	
	//全局的位置变量，用于表示鼠标在窗口上的位置
	static Point origin = new Point();
	
	// 定义组件
	JButton close;
	JPanel showinput, all;
	JLabel id, name,price, jifen, type;
	JTextField idt, namet,pricet, jifent;
	JComboBox<String> typet;
	JButton confirm, cancel;
	
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
	// 显示输入信息面板初始化
	private void initShowinput(ProductModel pm, int selrow) {
		
		showinput = new JPanel(new GridLayout(5, 2, -180, 10));
		showinput.setBounds(50, 40, 400, 250);
		
		id = new JLabel("产品编号");
		setlab(id);
		idt = new JTextField();
		idt.setEditable(false);
		idt.setText(pm.getValueAt(selrow, 0).toString());
		setjtf(idt);
		idt.setForeground(Color.GRAY);
		
		name = new JLabel("产品名称");
		setlab(name);
		namet = new JTextField();
		namet.setText(pm.getValueAt(selrow, 1).toString());
		setjtf(namet);
		
		price = new JLabel("产品价格");
		setlab(price);
		pricet = new JTextField();
		pricet.setText(pm.getValueAt(selrow, 2).toString());
		setjtf(pricet);
		
		jifen = new JLabel("产品积分");
		setlab(jifen);
		jifent = new JTextField();
		jifent.setText(pm.getValueAt(selrow, 3).toString());
		setjtf(jifent);
		
		type = new JLabel("产品类别");
		setlab(type);
		
		typet = new JComboBox<String>();
		typet.addItem("个人护理品");
		typet.addItem("家居日用品");
		typet.addItem("健康食品");
		typet.addItem("玛丽艳彩妆系");
		typet.addItem("玛丽艳敏感品");
		typet.addItem("玛丽艳清爽系");
		typet.addItem("玛丽艳特别护理");
		typet.addItem("玛丽艳滋润品");
		typet.addItem("其他类别");
		typet.addActionListener(this);
		typet.setSelectedItem(pm.getValueAt(selrow, 4));
		typet.setFont(MyFont.PaddInfotext);
		showinput.add(typet);
		showinput.setOpaque(false);
		
		all.add(showinput);
	}
	// 构造函数
	public Chang_Product_View(ProductModel pm, int selrow) {
		
		// 设置窗体的样式为当前系统的样式
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// 关闭按钮
		close = new JButton(new ImageIcon("image/JDialogClose.png"));
		close.setRolloverIcon(new ImageIcon("image/JDialogCloseC.png"));
		close.setBounds(525, 13, 22, 22);
		close.setForeground(Color.red);
		setbutton(close, 1);
		
		confirm = new JButton("确 定");
		confirm.setBounds(100, 350, 110, 50);
		setbutton(confirm, 2);
		cancel = new JButton("取 消");
		cancel.setBounds(300, 350, 110, 50);
		setbutton(cancel, 2);
		
		this.add(close);
		
		
		all = new JPanel(null);
		all.setBackground(Color.white);
		all.setBounds(0, 0, 560, 498);
		all.setBorder(new MatteBorder(2, 2, 2, 2, Color.GRAY));
		initShowinput(pm, selrow);
		all.add(confirm);
		all.add(cancel);
		
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
			String pname = namet.getText().trim();
			String pprice = pricet.getText().trim();
			String pjifen = jifent.getText().trim();
			String ptype = typet.getSelectedItem().toString().trim();
			
			if (pid.equals("") || pname.equals("") || pprice.equals("") || pjifen.equals("") || ptype.equals("")) {
				
				JOptionPane.showMessageDialog(this, "信息不能为空，请输入对应的信息");
				return;
			}
			if (!(Tools.isNum(pprice) || Tools.isNum(pjifen))) {
				
				JOptionPane.showMessageDialog(this, "<html><font style = 'font-size:18'>产品价格或者产品积分非法，请检查　<br/><br/>是否存在下列问题：<br/>1.是否大于0?<br/>2.是否是合法的数字?<br/><br/>", " 温馨提示", JOptionPane.WARNING_MESSAGE);
				return;
			}
			String addsql = "update ProductInfo set PName = ?, Price = ?, JFen = ?, Ptype = ? where Pid = ?";
			String[] addparas = {pname, pprice, pjifen, ptype, pid};
			boolean b = ProductModel.update(addsql, addparas);
			while (!b) {
				
				JOptionPane.showMessageDialog(this, "抱歉的通知您，修改失败");
				return;
			}
			
			JOptionPane.showMessageDialog(this, "修改完成");
			this.dispose();
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
					
					AWTUtilities.setWindowOpacity(Chang_Product_View.this, alpha+=0.1);
				}
				else {
					AWTUtilities.setWindowOpacity(Chang_Product_View.this, 1);
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
		if (e.getSource() == typet) {
			
			if (typet.getSelectedItem().equals("其他类别")) {
				
				typet.setEditable(true);
			}else {
				
				typet.setEditable(false);
			}
		}
	}

}



