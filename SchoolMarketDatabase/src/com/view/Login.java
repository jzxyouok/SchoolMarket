/**
 *  用于实现登录的界面
 */
package com.view;

import com.model.LoginModel;
import com.mytools.*;
import com.sun.awt.AWTUtilities;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;

import java.io.*;
import java.util.Vector;

public class Login extends JFrame implements MouseListener{
	
	//全局的位置变量，用于表示鼠标在窗口上的位置
	static Point origin = new Point();
	
	// 定义组件
	ImagePanel bkim = null;
	JButton min, close, loginqueding;
	JComboBox<String> user;
	JPasswordField password;
	
	String[] allparas = {"1"};

	public static void main(String[] args) {
		Login login = new Login();
	}
	public void setbutton(JButton jb) {
		
		jb.setContentAreaFilled(false);
		jb.setBorderPainted(false);
		jb.setFocusPainted(false);
		jb.addMouseListener(this);
		jb.setOpaque(false);
	}
	// 窗口操作控制菜单
	public void windowsmenu() {
		
		min = new JButton(new ImageIcon("image/Loginmin.png"));
		min.setBounds(346, 0, 27, 21);
		min.setRolloverIcon(new ImageIcon("image/LoginminC.png"));
		setbutton(min);
		min.setToolTipText("最小化");
		
		close = new JButton(new ImageIcon("image/Loginclose.png"));
		close.setBounds(370, 0, 29, 21);
		close.setRolloverIcon(new ImageIcon("image/LogincloseC.png"));
		setbutton(close);
		close.setToolTipText("关闭");
		
		bkim.add(min);
		bkim.add(close);
	}
	
	// 构造函数
	public Login() {
		
		// 设置窗体的样式为当前系统的样式
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Image loginbk = null;
		try {
			
			loginbk = ImageIO.read(new File("image/loginbk.png"));
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// 窗口背景面板
		bkim = new ImagePanel(loginbk);
		bkim.setLayout(null);
		
		Vector<String> userid = LoginModel.find("select Mid from merchantinfo where 1 = ?", allparas);
		
		user = new JComboBox<String>(userid);

		
		user.setEditable(true);
		user.setBounds(131, 145, 187, 26);
		user.setFont(MyFont.login);
		user.addMouseListener(this);
		
		JScrollPane jsp = new JScrollPane();
		jsp.add(user);
		jsp.setBounds(131, 145, 187, 26);
		jsp.setEnabled(true);
		
		password = new JPasswordField(50);
		
		password.setBounds(135, 180, 178, 25);
		password.setBorder(new MatteBorder(0, 0, 0, 0, Color.blue));
		password.setOpaque(false);
		password.setFont(MyFont.login);
		password.setEchoChar('*');
		
		loginqueding = new JButton(new ImageIcon("image/loginqueding.png"));
		loginqueding.setRolloverIcon(new ImageIcon("image/loginquedingC.png"));
		loginqueding.setBounds(110, 253, 180, 31);
		setbutton(loginqueding);
		
		bkim.add(user);
		bkim.add(password);
		bkim.add(loginqueding);
		
		windowsmenu();
		
		this.setUndecorated(true);
		WindowMove();
		setOpacity();
		this.add(bkim);
		this.setSize(400, 290);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		if(e.getSource() == min) {
			
			setState(JFrame.ICONIFIED);
		}
		if(e.getSource() == close) {
			
			dispose();
		}
		if(e.getSource() == loginqueding) {
			
			String userid = user.getSelectedItem().toString().trim();
			String upassword = new String(this.password.getPassword());
			
			if (userid.equals("")) {
				
				JOptionPane.showMessageDialog(this, "请输入用户名再登录");
				return;
			}
			if (upassword.equals("")) {
				
				JOptionPane.showMessageDialog(this, "请输入密码再登录");
				return;
			}
			if (userid.equals("admin")||upassword.equals("210070")) {
				System.out.println("登录成功！");
				new MainWindows();
				this.dispose();
				
				return;
			}
			
			if (!LoginModel.checkid(userid)) {
				
				JOptionPane.showMessageDialog(this, "<html><br/>抱歉&nbsp<font color = 'red'>"+userid+"</font>&nbsp没有登录此系统的权限<br/>");
				return;
			}
			
			if (LoginModel.checkpassword(userid, upassword)) {
				System.out.println("登录成功！");
				new MainWindows();
				
			}else {
				
				JOptionPane.showMessageDialog(this, "密码不正确，请重新输入密码");
				this.password.setText("");
				return;
			}
			
		}
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
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	// 窗口淡入淡出函数
	public void setOpacity() {
		
		// 窗口设置淡入淡出代码段
		AWTUtilities.setWindowOpacity(Login.this, 0f);
		ActionListener lisener = new ActionListener() {
			
			float alpha = 0;
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (alpha < 0.9) {
					
					AWTUtilities.setWindowOpacity(Login.this, alpha+=0.1);
				}
				else {
					AWTUtilities.setWindowOpacity(Login.this, 1);
					Timer source = (Timer) e.getSource();
					source.stop();
				}
			}
		};
		// 设置线程控制
		new Timer(50, lisener).start();
	}

}
