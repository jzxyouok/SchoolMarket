/**
 * 用户登录进入的主界面
 */
package com.view;

import com.mytools.*;
import com.sun.awt.AWTUtilities;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class MainWindows extends JFrame implements ActionListener, MouseListener, WindowListener,ItemListener{

	//全局的位置变量，用于表示鼠标在窗口上的位置
	static Point origin = new Point();
	 // 用于获得窗口的大小
	final static int width=Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height=Toolkit.getDefaultToolkit().getScreenSize().height;
	ImagePanel im = null;// 主窗体背景面板
	
	JPanel jTop, jMenu, jMenu1, jControl;
	// 窗口控制菜单
	JLabel omenu, min, max1, close;
	JToggleButton max;
	
	// 窗口菜单
	JLabel merchant,custom, product,stcok;
	
	JPanel conjp;
	CardLayout card;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//UserMainWindows T = new UserMainWindows();
		
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
	// 优化定义菜单label
	public  JLabel CreateMenuLabel(JLabel jlb, String name, JPanel who) {
		
		jlb = new JLabel(name, JLabel.CENTER);
		jlb.setFont(MyFont.TopMenu);
		jlb.addMouseListener(this);
		jlb.setForeground(Color.white);
		who.add(jlb);
		return jlb;
	}
	// 主界面左侧菜单栏定义
	public void initTopMenu() {
		
		jMenu = new JPanel(new GridLayout(1, 4));
		jMenu.setPreferredSize(new Dimension((int)(width*0.55), 62));
		jMenu.setOpaque(false);
		
		String [] nameStrings = {"商家信息", "顾客信息", "产品信息", "库存信息"};
		
		merchant = CreateMenuLabel(merchant, nameStrings[0], jMenu);
		merchant.setForeground(Color.yellow);
		merchant.setName("merchant");
		custom = CreateMenuLabel(custom, nameStrings[1], jMenu);
		custom.setName("custom");
		product = CreateMenuLabel(product, nameStrings[2], jMenu);
		product.setName("product");
		stcok = CreateMenuLabel(stcok, nameStrings[3], jMenu);
		stcok.setName("stock");
		
		// 为了方便布局使用的空面板
		jMenu1 = new JPanel();
		jMenu1.setPreferredSize(new Dimension(40, 62));
		jMenu1.setOpaque(false);
	}
	// 顶层窗口右侧窗口控制菜单
	public void initTopControl() {
		
		jControl = new JPanel(new GridLayout(1, 4));
		jControl.setPreferredSize(new Dimension(140, 62));
		jControl.setOpaque(false);
		
		omenu = new JLabel(new ImageIcon("image/omenu.png"));
		omenu.addMouseListener(this);
		omenu.setToolTipText("更多");
		min = new JLabel(new ImageIcon("image/min.png"));
		min.addMouseListener(this);
		min.setToolTipText("最小化");
		max = new JToggleButton(new ImageIcon("image/max.png"));
		max.setFocusPainted(false);
		max.setBorderPainted(false);
		max.setContentAreaFilled(false);
		max.addItemListener(this);
		max.addMouseListener(this);
		max.setToolTipText("最大化");
		
		close = new JLabel(new ImageIcon("image/close.png"));
		close.addMouseListener(this);
		close.setToolTipText("关闭");
		
		jControl.add(omenu);
		jControl.add(min);
		jControl.add(max);
		jControl.add(close);
	}
	// 顶层窗口控制菜单
	public void initTop() {
		
		initTopMenu();
		initTopControl();
		
		jTop = new JPanel(new BorderLayout());
		jTop.setPreferredSize(new Dimension(width, 62));
		jTop.setBackground(new Color(22, 120, 195));
		
		jTop.add(jMenu, "West");
		jTop.add(jMenu1, "Center");
		jTop.add(jControl, "East");
	}
	// 中间内容面板
		public void initCenter() {
			 
			this.card = new CardLayout();
			conjp = new JPanel(card);
			conjp.setOpaque(false);

			// 商家信息面板，放入卡片面板conjp
			MerchantInfo merinfo = new MerchantInfo();
			
			// 顾客信息面板
//++			CustomInfo cusinfo = new CustomInfo();
			
			// 产品相关管理面板
			ProductInfo proinfo = new ProductInfo();
			
			// 库存信息面板
			
			// conjp.add(empInfo, "empInfo"), 里面的字符串是个标识符，卡片不分先后
			// 不过值得注意的是第一个加入的会在窗口实例化的时候显示为第一个
			
			conjp.add(merinfo, "merinfo");
//++			conjp.add(cusinfo, "cusinfo");
			conjp.add(proinfo, "proinfo");
			
		}
		// 创建背景图片面板
		public void initBkPanel() {
			
			// 使用工具包里的图片面板设置窗体的背景图片
			Image bk = null;
			try {
				bk=ImageIO.read(new File("image/bk.jpg"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			im = new ImagePanel(bk);
			im.setLayout(new BorderLayout());
			
			initTop();
			initCenter();
			
			im.add(jTop,"North");
			im.add(conjp,"Center");
			
			this.add(im);
		}
		// 主窗体的构造函数
		public MainWindows() {
			
			// 设置窗体的样式为当前系统的样式
			try {
				
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		    UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("微软雅黑", Font.PLAIN, 12));
		    
			this.WindowMove();
			this.initBkPanel();

			this.setUndecorated(true);
			
			this.addWindowListener(this);
			
			AWTUtilities.setWindowOpacity(MainWindows.this, 0f);
			
			this.setSize((int)(width*0.8f), (int)(height*0.8f));
			this.setVisible(true);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);
		}
		// 设置
		// 定义顶层菜单样式函数
		private void setTopMenuStyle(JLabel jlb, String type) {
			
			if (type.equals("enter")) {
				
				//jlb.setForeground(Color.yellow);
				jlb.setFont(MyFont.TopMenuC);
				jlb.setLocation(jlb.getX(), jlb.getY()-1);
			}
			if (type.equals("exit")) {
				
				//jlb.setForeground(Color.white);
				jlb.setBackground(Color.red);
				jlb.setFont(MyFont.TopMenu);
				jlb.setLocation(jlb.getX(), jlb.getY()+1);
			}
			if (type.equals("click")) {
				
				String [] name = {"merchant", "custom", "product", "stcok"};
				JLabel [] jlb1 = {merchant,custom, product,stcok};
				for (int i = 0; i < name.length; i++) {
					
					if (jlb.getName().equals(name[0])) {
						
						jlb.setForeground(Color.yellow);
						if(i != 0){
							jlb1[i].setForeground(Color.white);
						}
					}
					if (jlb.getName().equals(name[1])) {
						
						jlb.setForeground(Color.yellow);
						if(i != 1){
							jlb1[i].setForeground(Color.white);
						}
					}
					if (jlb.getName().equals(name[2])) {
						
						jlb.setForeground(Color.yellow);
						if(i != 2){
							jlb1[i].setForeground(Color.white);
						}
					}
					if (jlb.getName().equals(name[3])) {
						
						jlb.setForeground(Color.yellow);
						if(i != 3){
							jlb1[i].setForeground(Color.white);
						}
					}
				}
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == this.omenu) {
				
				
			}
			if(e.getSource() == this.min) {
				
				setState(JFrame.ICONIFIED);
			}
			if(e.getSource() == this.close) {
				
				int i = JOptionPane.showConfirmDialog(this, "是否要退出？", "温馨提示", 
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(i == 0) {
					this.dispose();
				}else {
					return;
				}
			}
			
			if (e.getSource() == this.merchant) {
				
				setTopMenuStyle(merchant, "click");
				this.card.show(conjp, "merinfo");
			}
			
			if (e.getSource() == this.custom) {
				
				setTopMenuStyle(custom, "click");
				this.card.show(conjp, "cusinfo");
			}
			
			if (e.getSource() == product) {
				
				setTopMenuStyle(product, "click");
				this.card.show(conjp, "proinfo");
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODD
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == this.omenu) {
				
				omenu.setIcon(new ImageIcon("image/omenuC.png"));
			}
			if(e.getSource() == this.min) {

				min.setLocation(min.getX(), min.getY()-1);
			}
			if(e.getSource() == this.max) {
				
				max.setIcon(new ImageIcon("image/maxC.png"));
			}
			if(e.getSource() == this.close) {
				
				close.setIcon(new ImageIcon("image/closeC.png"));
			}	
			if (e.getSource() == this.merchant) {
				
				setTopMenuStyle(merchant, "enter");
			}
			if (e.getSource() == this.custom) {
				
				setTopMenuStyle(custom, "enter");
			}
			if (e.getSource() == this.product) {
				
				setTopMenuStyle(product, "enter");
			}
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == this.omenu) {

				omenu.setIcon(new ImageIcon("image/omenu.png"));
			}
			if(e.getSource() == this.min) {
				
				min.setLocation(min.getX(), min.getY()+1);
			}
			if(e.getSource() == this.max) {
				
				max.setIcon(new ImageIcon("image/max.png"));
			}
			if(e.getSource() == this.close) {
				
				close.setIcon(new ImageIcon("image/close.png"));
			}
			if (e.getSource() == this.merchant) {
				
				setTopMenuStyle(merchant, "exit");
			}
			if (e.getSource() == this.custom) {
				
				setTopMenuStyle(custom, "exit");
			}
			if (e.getSource() == this.product) {
				
				setTopMenuStyle(product, "exit");
			}
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			// 调用淡入淡出函数
			setOpacity();
		}
		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowActivated(WindowEvent e) {
			
			
		}
		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			
			if (e.getSource() == max) {
				
				if (e.getStateChange() == ItemEvent.SELECTED) {     
		            
					this.setSize(width, height-30);
					this.setLocation(0, 0);
		            max.setToolTipText("还原");
		    		// 调用淡入淡出函数
		    		setOpacity();
		        } else {
		        	
		        	this.setSize((int)(width*0.8f), (int)(height*0.8f));
		        	this.setLocationRelativeTo(null);
		            //setExtendedState(NORMAL);
		            max.setToolTipText("最大化");
		    		// 调用淡入淡出函数
		    		setOpacity();
		    		
		        } 
			}		
		}
		// 窗口淡入淡出函数
		final public void setOpacity() {
			
			// 窗口设置淡入淡出代码段
			AWTUtilities.setWindowOpacity(this, 0f);
			ActionListener lisener = new ActionListener() {
				
				float alpha = 0;
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (alpha < 0.9) {
						
						AWTUtilities.setWindowOpacity(MainWindows.this, alpha+=0.1);
					}
					else {
						AWTUtilities.setWindowOpacity(MainWindows.this, 1);
						Timer source = (Timer) e.getSource();
						source.stop();
					}
				}
			};
			// 设置线程控制
			new Timer(50, lisener).start();
		}
}
