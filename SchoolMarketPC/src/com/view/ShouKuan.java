/**
 *  收款界面，用于实现收款
 *  
 *  修改日期：2013-07-27
 *  	1.细化面向对象思想，抽象出更进一步的函数，提高复用行
 *  	2.设置相应的快捷键，方便快速的操作
 *  	  确认添加：Enter	删除：Delete	清空：Ctrl+Delete		结账：Ctrl+Enter
 */

package com.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import com.model.ShowKuanModel;
import com.mytools.*;

@SuppressWarnings("serial")
public class ShouKuan extends JPanel implements KeyListener, MouseListener, FocusListener {
	
	    // 用于获得窗口的大小
		final static int width=Toolkit.getDefaultToolkit().getScreenSize().width;
		final static int height=Toolkit.getDefaultToolkit().getScreenSize().height;
		// 公用颜色值
		Color color = new Color(22, 120, 195);
		// 显示信息的面板
		JPanel addproduct, showtabel, showinfoall, showinfo, handle;
		// 添加产品信息的面板控件
		JLabel id, num;
		JTextField getid, getnum;
		JButton confirm;
		// 装载信息面板的面板
		JPanel showjp;
		JTable saltable = null;
		
		JButton delete, clear;
		
		// 右侧面板组件
		JLabel sum, form, to; // 用sum表示总的金额，from表示实收，to表示应找
		static JLabel setsum, setto;
		JTextField getform;
		JButton jiezhang;
		JPanel jjiezhang;
		
		//定义一个鼠标指针的类型
		Cursor myCursor=new Cursor(Cursor.HAND_CURSOR);//手型鼠标
		JScrollPane jsp;
		
		ShowKuanModel skm = new ShowKuanModel();
		String[] paras = {"1"};
		
		// 设置按钮样式函数
		private void setbutton(JButton jb) {
			
			jb.setContentAreaFilled(false);
			jb.setBorderPainted(false);
			jb.setFocusPainted(false);
			jb.addMouseListener(this);
			jb.addKeyListener(this);
			jb.setCursor(myCursor);
			jb.setOpaque(false);
		}
		
		// 设置标题样式
		private void setlab(JLabel jlb, int i) {
			
			if (i == 1) {
				
				addproduct.add(jlb);
			}
			if (i == 2) {
				
				showinfo.add(jlb);
			}
			jlb.setForeground(Color.WHITE);
			jlb.setFont(MyFont.Infolab);
			jlb.setHorizontalAlignment(JLabel.CENTER);
		}
		
		// 设置输入框样式
		private void setjtf(final JTextField jtf, int i) {
			
			if (i == 1) {
				
				addproduct.add(jtf);
				MatteBorder ubderline0 = new MatteBorder(0, 1, 1, 1, color);
				jtf.setBorder(ubderline0);
			}
			if (i == 2) {
				
				showinfo.add(jtf);
				jtf.setForeground(Color.white);
				jtf.setBorder(null);
			}

			jtf.setOpaque(false);
			jtf.setHorizontalAlignment(JTextField.CENTER);
			jtf.setFont(MyFont.Infotext);
			jtf.setSelectionColor(new Color(60, 128, 250));
			jtf.addMouseListener(this);
		}
		
		// 初始化左侧
		private void initLeft() {
			
			// 处理左侧
			addproduct = new JPanel(new GridLayout(1, 4));
			addproduct.setOpaque(false);
			addproduct.setPreferredSize(new Dimension((int)(width*0.8)-255, 80));
			
			id = new JLabel("产品ID");
			setlab(id, 1);
			
			getid = new JTextField(10);
			setjtf(getid, 1);
			getid.addFocusListener(this);
			
			num = new JLabel("购买数量");
			setlab(num, 1);
			
			getnum = new JTextField("1");
			setjtf(getnum, 1);
			getnum.addFocusListener(this);
			
			confirm = new JButton(new ImageIcon("image/confirm.png"));
			setbutton(confirm);
			addproduct.add(confirm);
			
			// 设置快捷键
			ActionListener addListener = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
			    	confirm.doClick();
			        addproduct();   
				}
			};
			
			setQkey(addListener, KeyEvent.VK_ENTER, 0);
			
			//1.设计jtable
			skm.query("select Pid, Pname, Price, Num, (Price*Num) as Allsum from temp where 1=?", paras);
			saltable = new JTable(skm);
			
			// 调用工具Tools类中的设置表格样式方法
			Tools.setTableStyle(saltable);
			
			// 滚动面板
			jsp = new JScrollPane(saltable);
			jsp.setBorder(new MatteBorder(1, 1, 1, 0, color));
			Tools.setJspStyle(jsp);
			
			showtabel = new JPanel(new BorderLayout());
			showtabel.setBackground(Color.white);
			
			// 设置面板的大小
			showtabel.setPreferredSize(new Dimension((int)(width*0.8)-250, (int)(height*0.8)-155));
			
			showtabel.add(jsp);
			
			handle = new JPanel(new GridLayout(1, 2, 250, 4));
			handle.setPreferredSize(new Dimension((int)(width*0.8)-250, 91));
			handle.setOpaque(false);
			delete = new JButton(new ImageIcon("image/del.png"));
			setbutton(delete);
			
			// 设置删除的快捷键  		设置为：delete
			ActionListener delListener = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					delete.doClick();
			    	delproduct();
				}
			};
			setQkey(delListener, KeyEvent.VK_DELETE, 0);
			
			
			clear = new JButton(new ImageIcon("image/clear.png"));
			setbutton(clear);
			
			// 设置清空的快捷键 设置为：ctrl+delete
			ActionListener clearListener = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					clear.doClick();
			    	clearproduct();
				}
			};
			setQkey(clearListener, KeyEvent.VK_DELETE, InputEvent.CTRL_DOWN_MASK);
			
			handle.add(delete);
			handle.add(clear);
			
			showjp = new JPanel(new BorderLayout());
			showjp.setOpaque(false);
			// 用于设置面板的边框
			MatteBorder border = new MatteBorder(0, 1, 0, 1, color);
			showjp.setBorder(border);
			showjp.add(addproduct, "North");
			showjp.add(showtabel, "Center");
			showjp.add(handle, "South");
		}
		
		// 初始化右侧
		private void initRight() {
			
			// 处理右侧
			showinfo = new JPanel(new GridLayout(1, 2, -100, -20));
			showinfo.setPreferredSize(new Dimension(350, (int)(height*0.8)-91));
			showinfo.setOpaque(false);
			
			sum = new JLabel("合计：");
			setlab(sum, 2);
			setsum = new JLabel("0.0");
			updatesum();
			setlab(setsum, 2);
		
			jiezhang = new JButton(new ImageIcon("image/jiezhang.png"));
			setbutton(jiezhang);
			
			// 设置快捷键
			ActionListener jiezhangListener = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
			    	jiezhang.doClick();
			    	jiezhang();
				}
			};
			
			setQkey(jiezhangListener, KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK);
			
			jjiezhang = new JPanel();
			jjiezhang.setOpaque(false);
			jjiezhang.add(jiezhang);
			jjiezhang.setPreferredSize(new Dimension(350, 400));
			
			showinfoall = new JPanel(new BorderLayout());
			showinfoall.setOpaque(false);
			showinfoall.addKeyListener(this);
			showinfoall.add(showinfo, "Center");
			showinfoall.add(jjiezhang, "South");	
		}
		
		// 收款面板构造函数
		public ShouKuan() {
			
			initLeft();
			initRight();
			
			this.setOpaque(false);
			this.setFocusable(false);
			this.setLayout(new BorderLayout());
			this.add(showjp, "Center");
			this.add(showinfoall, "East");	
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == confirm) {
				
				// 调用添加函数
				addproduct();
				Tools.selsectAll(getid);
			}
			if (e.getSource() == delete) {
				
				// 调用删除函数
				delproduct();
			}
			if (e.getSource() == clear) {
				
				// 调用清空函数
				clearproduct();
			}
			if (e.getSource() == jiezhang) {
				
				// 调用结账函数
				jiezhang();
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
			if (e.getSource() == confirm) {
				
				confirm.setIcon(new ImageIcon("image/confirmC.png"));
			}
			if (e.getSource() == delete) {
				
				delete.setIcon(new ImageIcon("image/delC.png"));
			}
			if (e.getSource() == clear) {
				
				clear.setIcon(new ImageIcon("image/clearC.png"));
			}
			if (e.getSource() == jiezhang) {
				
				jiezhang.setIcon(new ImageIcon("image/jiezhangC.png"));
			}
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == confirm) {
				
				confirm.setIcon(new ImageIcon("image/confirm.png"));
			}
			if (e.getSource() == delete) {
				
				delete.setIcon(new ImageIcon("image/del.png"));
			}
			if (e.getSource() == clear) {
				
				clear.setIcon(new ImageIcon("image/clear.png"));
			}
			if (e.getSource() == jiezhang) {
				
				jiezhang.setIcon(new ImageIcon("image/jiezhang.png"));
			}
		}
		// 更新表格模型函数，当表格需要更新是调用
		private void updatetable() {
			
			skm = new ShowKuanModel();
			skm.query("select Pid, Pname, Price, Num, (Price*Num) as Allsum from temp where 1=?", paras);
			saltable.setModel(skm);
			Tools.setTableStyle(saltable);
		}
		
		// 更新合计总额，当总金额变化时调用
		private void updatesum() {
			
			ShowKuanModel skmp = new ShowKuanModel();
			String setsum = "select sum(Price*Num) as 合计 from temp where 1=?";
			skmp.query(setsum, paras);
			// 判断合计是否为空，空则设置为0.0
			String i = (String)skmp.getValueAt(0, 0);
			if (i == null) {
				
				ShouKuan.setsum.setText("0.0");
				return;
			}
			ShouKuan.setsum.setText((String)skmp.getValueAt(0, 0));
		}
		
		// 清空temp表函数,清空和结账中调用
		private void cleartemp() {
			
			String[] pid = {"1"};
			String sql = "delete from temp where 1 = ?";
			ShowKuanModel.update(sql, pid);
			updatetable();
			updatesum();
		}
		
		// 确定按钮，添加产品的函数
		private void addproduct() {
			
			String pid = getid.getText();
			String num = getnum.getText();
			while (pid.isEmpty() && num.isEmpty()) {
				
				JOptionPane.showMessageDialog(this, "请输入产品编号和产品数量");
				return;
			}
			while (pid.isEmpty()) {
				
				JOptionPane.showMessageDialog(this, "产品编号为空，请输入产品编号！");
				return;
			}
			while (num.isEmpty()) {
				
				JOptionPane.showMessageDialog(this, "产品数量为空，请输入产品数量！");
				return;
			}
			// 判断是否是合法的数字
			while (!Tools.isNum(num) || Integer.valueOf(num) < 0) {
				
				JOptionPane.showMessageDialog(this, "购买数量非法，请输入大于0的数字");
				return;
			}
			
			// 检查产品是否存在
			while (!ShowKuanModel.check("select count(*) from ProductInfo where Pid = ?",pid)) {
				
				JOptionPane.showMessageDialog(this, "抱歉，没有该产品，请重新输入", "　提示", JOptionPane.WARNING_MESSAGE);
				return;
			}
			// 检查库存中是否有该产品
			if (!ShowKuanModel.check("select count(*) from Stcok where Pid = ?", pid)) {
				
				JOptionPane.showMessageDialog(this, "<html><font size = '3'>该产品没有库存量，不能添加，请进货　", "　温馨提示", JOptionPane.WARNING_MESSAGE);
				return;
			}
			// 检查产品的库存量是否大于o
			int truenum = ShowKuanModel.get_p_num(pid);
			while (Integer.valueOf(num) > truenum) {
				
				JOptionPane.showMessageDialog(this, "<html><font style = 'font-size:12'>该产品的库存量为：<font color = 'red'>"+truenum+"　</font><br/>库存不足，不能以完成添加，请进货！</font><br/><br/>", " 温馨提示", JOptionPane.WARNING_MESSAGE);
				return;
			}
			// 用Pid去得到产品信息的结果集
			String[] parasp = {pid};
			String sql = "select Pid,PName,Price from ProductInfo where Pid =?";
			ShowKuanModel skmp = new ShowKuanModel();
			skmp.query(sql, parasp);
			// 获取插入temp的数组
			String id = (String)skmp.getValueAt(0, 0);
			String pname = (String)skmp.getValueAt(0, 1);
			String price = (String)skmp.getValueAt(0, 2);
			
			String[] addparas = {id,pname,price,num};
			
			// 判断temp中是否已经有同样的产品
			if (ShowKuanModel.check("select count(*) from temp where Pid = ?", pid)) {
				
				String setnum = "update temp set Num = ?+Num where Pid = ?";
				String[] updatenum = {num, pid};
				ShowKuanModel.update(setnum, updatenum);
			}else {
				
				String addsql = "insert into temp values (?,?,?,?)";
				ShowKuanModel.update(addsql, addparas);
			}
			
			updatetable();
			
			// 更新总的合计金额
			updatesum();
		}

		// 删除函数
		private void delproduct() {
			
			double sum = Double.parseDouble(ShouKuan.setsum.getText());
			
			// 判断是否有购买的商品
			if (sum == 0.0) {
				
				JOptionPane.showMessageDialog(this, "<html>没有购买商品，请选择购买商品");
				return;
			}
			
			int selrow=saltable.getSelectedRow();
			int i = saltable.getSelectedColumnCount();
			if (i > 1) {
				
				JOptionPane.showMessageDialog(this, "只能选中一条删除！");
				return;
			}
			if(selrow == -1)
			{
				JOptionPane.showMessageDialog(this, "请选中要删除的商品！");
				return;
			}
			
			int j = JOptionPane.showConfirmDialog(this, "<html><font size = '5'>是否要删除选中的商品？<br /><br /><font size = '4' color = 'red'>请注意慎重操作<br /><br />", "温馨提示", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (j ==0 ) {
				
				String[] pid={(String)skm.getValueAt(selrow, 0)};
				String sql = "delete from temp where Pid = ?";
				ShowKuanModel.update(sql, pid);
				
				updatetable();
				updatesum();
			}
		}
		
		// 清空函数
		private void clearproduct() {
			
			double sum = Double.parseDouble(ShouKuan.setsum.getText());
			
			// 判断是否有购买的商品
			if (sum == 0.0) {
				
				JOptionPane.showMessageDialog(this, "<html>没有购买商品，请选择购买商品");
				return;
			}
			
			int i = JOptionPane.showConfirmDialog(this, "<html><font size = '4'>是否要清空购买的商品？<br /><br /><font size = '4' color = 'red'>请注意慎重操作<br /><br />", "温馨提示", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			if (i == 0) {
				
				cleartemp();
			}
		}
		// 结账按钮，结账函数
		private void jiezhang() {
			
			double sum = Double.parseDouble(ShouKuan.setsum.getText());
			double from = 0.0;
			saltable.clearSelection();
			// 判断是否有购买的商品
			if (sum == 0.0) {
				
				JOptionPane.showMessageDialog(this, "<html>没有购买商品，请选择购买商品");
				return;
			}
			
			String input;
			// 确保收款的输入框是数字
			do {
				
				// 弹出输入实收的输入框
				input = JOptionPane.showInputDialog(this, "<html><font style = 'font-size:12'>实收：", "输入实收金额", JOptionPane.PLAIN_MESSAGE);
				
			} while (!Tools.isNum(input));
			
			// 实收要大于总合计金额
			do {
				
				from = Double.parseDouble(input);
				if (sum > from){
					
					input = JOptionPane.showInputDialog(this, "<html><font style = 'font-size:12'>重新输入：", "实收金额小于合计金额", JOptionPane.PLAIN_MESSAGE);
					from = Double.parseDouble(input);
				}
			} while (sum > from);
			
			// 提示应找
			double to = from - sum;
			
			int i = JOptionPane.showConfirmDialog(this, "<html><font size = '5'>&nbsp&nbsp&nbsp&nbsp合计："+sum+"&nbsp&nbsp<br/>&nbsp&nbsp&nbsp&nbsp实收："+from+"<hr>&nbsp&nbsp<br/><font size = '13' color = 'blue'>&nbsp&nbsp应找："+to+"<br/><br/>", "　 完成交易", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			
			// 完成结账，包括清空temp表和将数据插入销售表
			if (i == 0 ) {
				
				// 用Pid去得到产品信息的结果集
				String[] parasp = {"1"};
				String sql = "select Pid,Num from temp where 1 =?";
				ShowKuanModel skmp = new ShowKuanModel();
				skmp.query(sql, parasp);
				
				// 循环的将购买的商品加入销售信息表，并从库存中减去想对应的购买数量
				int j = 0;
				boolean b = false;
				System.out.println(skmp.getRowCount());
				while (j <= skmp.getRowCount()-1) {
					
					String id = (String)skmp.getValueAt(j, 0);
					String num = (String)skmp.getValueAt(j, 1);
					
					// 获取插入temp的数组
					String[] addrecord = {id, num};
					// 再一次检查库存表中的数量是否满足
					int truenum = ShowKuanModel.get_p_num(id);
					if (truenum < Integer.valueOf(num)) {
						
						JOptionPane.showMessageDialog(this, "<html><font size = '5'>"+id+"产品的库存为：<font color = 'red'>"+truenum+"　<br/></font>不足以完成购买量为"+num+"的购买</font>　", "　温馨提示", JOptionPane.WARNING_MESSAGE);
						return;
					}
					// 1.加入销售信息表
					String SalRecord = "insert into SellInfo (Pid, Num, OutDate) values (?, ?, getdate())";
					b = ShowKuanModel.update(SalRecord, addrecord);
					
					// 2.减去库存中对应的数量
					String[] subparas = {num, id};
					ShowKuanModel.update("update Stcok set Num = Num-? where Pid = ?", subparas);
					j++;
				}
				
				// 如果插入成功则清空temp表的信息
				if (b == true) {
					
					cleartemp();
				}
			}
		}
		// 设置快捷键函数
		private void setQkey(ActionListener actionListener, int KeyEvent, int InputEvent) {
			
			Tools.selsectAll(getid);
			KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent, InputEvent);
			registerKeyboardAction(actionListener,keystroke,JComponent.WHEN_IN_FOCUSED_WINDOW);
		}
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			Component comp =e.getComponent();
			if (comp instanceof JTextField) {
				
				Tools.selsectAll(getid);
				Tools.selsectAll(getnum);
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
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
}
