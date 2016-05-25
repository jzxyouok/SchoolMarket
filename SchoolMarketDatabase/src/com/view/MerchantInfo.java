/**
 * 用于显示和管理商家信息的面板
 */
package com.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import com.mytools.*;
import com.model.*;

@SuppressWarnings("serial")
public class MerchantInfo extends JPanel implements MouseListener, FocusListener, KeyListener {
	
	 // 用于获得窗口的大小
	final static int width=Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height=Toolkit.getDefaultToolkit().getScreenSize().height;
	// 公用颜色值
	Color color = new Color(22, 120, 195);
	
	// 显示信息的面板
	JPanel showtabel, showinfoall, showinfo, handle;
	// 确定添加按钮放置面板
	JPanel jadd;
	// 装载信息面板的面板
	JPanel showjp;
	JTable Merchanttable = null;

	JButton add, addc, modify, delete;
	
	// 右侧面板组件
	JLabel id, sname,mname, sex, card, phone, address, mpasswd; 
	
	JTextField idt, snamet,mnamet, cardt, phonet, addresst, mpasswdt;
	JRadioButton boy, gril;
	ButtonGroup sext;
	
	 MerchantModel mer=new MerchantModel();
	 MerchantModel mernew = new MerchantModel();
	 
		//定义一个鼠标指针的类型
		Cursor myCursor=new Cursor(Cursor.HAND_CURSOR);//手型鼠标
		
		JScrollPane jsp;
		
		String []paras={"1"};
		
		public void setbutton(JButton jb) {
			
			jb.setContentAreaFilled(false);
			jb.setBorderPainted(false);
			jb.setFocusPainted(false);
			jb.addMouseListener(this);
			jb.setCursor(myCursor);
			jb.setOpaque(false);
		}
		private void setlab(JLabel jlb) {
			
			showinfo.add(jlb);
			jlb.setFont(MyFont.Infolab);
			jlb.setForeground(Color.WHITE);
		}
		private void setjtf(final JTextField jtf) {
			
			showinfo.add(jtf);
			MatteBorder ubderline = new MatteBorder(0, 0, 1, 0, Color.white);
			jtf.setBorder(ubderline);
			jtf.setOpaque(false);
			jtf.setFont(MyFont.Infotext);
			jtf.setForeground(Color.white);
		}
		public MerchantInfo(){
			// 处理左侧
			//1.设计jtable
			mer.query("select Mid, Mshopname, Mname, Sex, Card, Phone, Address, Mpasswd from merchantinfo where 1=?", paras);
			Merchanttable = new JTable(mer);
			
			// 调用工具Tools类中的设置表格样式方法
			Tools.setTableStyle(Merchanttable);
			Merchanttable.addMouseListener(this);
			Merchanttable.addKeyListener(this);
			Merchanttable.setOpaque(false);
			
			// 滚动面板
			jsp = new JScrollPane(Merchanttable);
			jsp.setBorder(new MatteBorder(0, 1, 1, 0, color));
			Tools.setJspStyle(jsp);
			
			showtabel = new JPanel(new BorderLayout());
			showtabel.setBackground(Color.white);
			// 设置边框
			MatteBorder border = new MatteBorder(0, 1, 1, 0, color);
			showtabel.setBorder(border);
			// 设置面板的大小
			showtabel.setPreferredSize(new Dimension((int)(width*0.8)-250, (int)(height*0.8)-155));
			
			showtabel.add(jsp);
			
			handle = new JPanel(new GridLayout(1, 3, ((int)(width*0.8)-625)/6, 10));
			handle.setPreferredSize(new Dimension((int)(width*0.8)-250, 91));
			
			// 设置只有右边框
			MatteBorder border2 = new MatteBorder(0, 0, 0, 1, new Color(22, 120, 195));
			handle.setBorder(border2);
			handle.setOpaque(false);
			add = new JButton(new ImageIcon("image/add.png"));
			//add.setToolTipText("添加一条商家信息");
			setbutton(add);
			modify = new JButton(new ImageIcon("image/modify.png"));
			//modify.setToolTipText("修改商家的信息");
			setbutton(modify);
			delete = new JButton(new ImageIcon("image/del.png"));
			//delete.setToolTipText("删除选中的商家");
			setbutton(delete);
			
			handle.add(add);
			handle.add(modify);
			handle.add(delete);
			
			showjp = new JPanel(new BorderLayout());
			showjp.setOpaque(false);
			showjp.add(showtabel, "Center");
			showjp.add(handle, "South");
			
			// 处理右侧
			showinfo = new JPanel(new GridLayout(9, 2, -90, 30));
			showinfo.setPreferredSize(new Dimension(350, (int)(height*0.8)-85));
			showinfo.setOpaque(false);
			
			// 第一列
			id = new JLabel(" 商家编号");
			setlab(id);
			idt = new JTextField(10);
			idt.addFocusListener(this);
			setjtf(idt);
			
			sname = new JLabel(" 店铺名称");
			setlab(sname);
			snamet = new JTextField(10);
			setjtf(snamet);
			
			mname = new JLabel(" 商家姓名");
			setlab(mname);
			mnamet = new JTextField(10);
			setjtf(mnamet);
			
			sex = new JLabel(" 性    别");
			setlab(sex);
			boy =new JRadioButton("男");
			boy.setOpaque(false);
			boy.setFocusPainted(false);
			boy.setBorderPainted(false);
			
			gril = new JRadioButton("女");
			gril.setOpaque(false);
			gril.setFocusPainted(false);
			gril.setBorderPainted(false);
			sext = new ButtonGroup();
			sext.add(boy);
			sext.add(gril);
			JPanel sextp = new JPanel(new GridLayout(1, 2));
			sextp.setOpaque(false);
			sextp.add(boy);
			sextp.add(gril);
			showinfo.add(sextp);
			
			card = new JLabel(" 身份证号");
			setlab(card);
			cardt = new JTextField(10);
			setjtf(cardt);
			
			phone = new JLabel(" 联系电话");
			setlab(phone);
			phonet =new JTextField(10);
			setjtf(phonet);
			
			address = new JLabel(" 联系地址");
			setlab(address);
			addresst = new JTextField(10);
			setjtf(addresst);
			addresst.setFont(new Font("新宋体",Font.PLAIN,13));
			
			mpasswd = new JLabel(" 登录密码");
			setlab(mpasswd);
			mpasswdt = new JTextField(10);
			setjtf(mpasswdt);
			
			jadd = new JPanel();
			jadd.setPreferredSize(new Dimension(350, 85));
			jadd.setOpaque(false);
			
			addc = new JButton(new ImageIcon("image/addconfirm.png"));
			addc.setVisible(false);
			setbutton(addc);
			
			jadd.add(addc);
			
			showinfoall = new JPanel(new BorderLayout());
			showinfoall.setOpaque(false);
			showinfoall.setPreferredSize(new Dimension(350, (int)(height*0.8)));
			
			showinfoall.add(showinfo, "Center");
			showinfoall.add(jadd, "South");
			
			this.setOpaque(false);
			this.setLayout(new BorderLayout());
			this.add(showjp, "Center");
			this.add(showinfoall, "East");
			this.setVisible(true);
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == Merchanttable) {
				
				mernew.query("select * from merchantinfo where 1 = ?", paras);
				idt.setText((String)mernew.getValueAt(Merchanttable.getSelectedRow(), 0));
				idt.setEditable(false);
				idt.setForeground(Color.lightGray);
				snamet.setText((String)mernew.getValueAt(Merchanttable.getSelectedRow(), 1));
				mnamet.setText((String)mernew.getValueAt(Merchanttable.getSelectedRow(), 2));
				// 设置性别的显示
				if (mernew.getValueAt(Merchanttable.getSelectedRow(), 3).equals("男")) {
					boy.setSelected(true);
				}else {
					gril.setSelected(true);
				}
				cardt.setText((String)mernew.getValueAt(Merchanttable.getSelectedRow(), 4));
				phonet.setText((String)mernew.getValueAt(Merchanttable.getSelectedRow(), 5));
				addresst.setText((String)mernew.getValueAt(Merchanttable.getSelectedRow(), 6));
				mpasswdt.setText((String)mernew.getValueAt(Merchanttable.getSelectedRow(), 7));
				
				addc.setVisible(false);
				delete.setEnabled(true);
				modify.setEnabled(true);
				idt.removeFocusListener(this);
			}
			// 添加按钮
			if (e.getSource() == add) {
				
				// 1.清空所有的信息
				idt.setText("");
				idt.setEditable(true);
				idt.setForeground(Color.WHITE);
				snamet.setText("");
				mnamet.setText("");
				//boy.setSelected(false);
				gril.setSelected(true);
				cardt.setText("");
				phonet.setText("");
				addresst.setText("");
				mpasswd.setText("");
				
				addc.setVisible(true);
				Merchanttable.clearSelection();
				delete.setEnabled(false);
				modify.setEnabled(false);
				
				idt.addFocusListener(this);
			}
			if (e.getSource() == addc) {
				
				// 1.得到信息
				String str1 = idt.getText();
				String str2 = snamet.getText();
				String str3 = mnamet.getText();
				String str4 =null;
				if (boy.isSelected()) {
					
					str4 = boy.getText();
				} else if (gril.isSelected()) {
					
					str4 = gril.getText();
				}
				
				String str5 = cardt.getText().trim();
				String str6 = phonet.getText();
				String str7 = addresst.getText();
				String str8 = mpasswdt.getText();
				
				// 2.判断信息是否为空
				if (str1.equals("")||str2.equals("")||str3.equals("")||str4.equals("")||str5.equals("")
						||str6.equals("")||str7.equals("")||str8.equals("")) {
					
					JOptionPane.showMessageDialog(this, "<html><font color = 'red'>不能为空，请输入相应的信息！");
					return;
				}
				
				// 3.添加操作
				String[] newparas={str1, str2, str3, str4, str5, str6, str7, str8};
				String sql="insert into merchantinfo values (?, ?, ?, ?, ?, ?, ?, ?)";
				boolean result = mernew.Merchantupdate(sql, newparas);
				if (result) {
					
					JOptionPane.showMessageDialog(this, "<html><font size = '5' color = 'blue'>添加成功");
				}else {
					
					JOptionPane.showMessageDialog(this, "<html><font size = '5' color = red>抱歉的通知您，没有添加成功!</font>" +
							"<br />请检查信息是否符合要求！<br />");
				}
				mer = new MerchantModel();
				mer.query("select Mid, Mshopname, Mname, Sex, Card, Phone, Address,Mpasswd from merchantinfo where 1 = ?", paras);
				Merchanttable.setModel(mer);
			}
			if (e.getSource() == modify) {
				
				if (modify.isEnabled()) {
					int selrow=Merchanttable.getSelectedRow();
					int i = Merchanttable.getSelectedRowCount();
					while(i > 1)
					{
						JOptionPane.showMessageDialog(this, "只能操作一行数据，请选中一行操作");
						return;
					}
					while(selrow==-1)
					{
						JOptionPane.showMessageDialog(this, "请选择一行，再进行操作");
						return;
					}
					
					// 1.得到修改的内容数组
					String str1 = idt.getText();
					String str2 = snamet.getText();
					String str3 = mnamet.getText();
					String str4 =null;
					if (boy.isSelected()) {
						
						str4 = boy.getText();
					} else if (gril.isSelected()) {
						
						str4 = gril.getText();
					}
					
					String str5 = cardt.getText().trim();
					String str6 = phonet.getText();
					String str7 = addresst.getText();
					String str8 = mpasswdt.getText();
					
					if (mesconfirm() && str4.equals((String)mernew.getValueAt(Merchanttable.getSelectedRow(), 2))) {
						
						JOptionPane.showMessageDialog(this, "<html><font size = '5' color = 'red'>亲！信息没有修改过！</font><br /><font size = '5' color = 'blue'>温馨提示:<br />您可以对右侧相应的信息修改之后再保存修改");
						return;
					}
					if(str2.equals("")||str3.equals("")||str4.equals("")||str5.equals("")
							||str6.equals("")||str7.equals("")||str8.equals(""))
					{
						JOptionPane.showMessageDialog(this, "<html><font color = 'red'>不能为空，请输入相应的信息！");
						return;
						
					}
					String[] newparas={str2, str3, str4, str5, str6, str7, str8, str1};
					String sql="update merchantinfo set Mshopname=?,Mname=?,Sex=?,Card=?, Phone=?,Address=?,Mpasswd=? where Mid=?";
					MerchantModel mernew = new MerchantModel();
					boolean result = mernew.Merchantupdate(sql, newparas);
					if (result) {
						
						JOptionPane.showMessageDialog(this, "<html><font size = '4' color = 'blue'>修改成功");
					}else {
						
						JOptionPane.showMessageDialog(this, "<html><font size = '5' color = red>抱歉的通知您，修改没有成功!</font>" +
								"<br />请检查信息是否符合要求！<br />");
					}
					mer = new MerchantModel();
					mer.query("select Mid, Mshopname, Mname, Sex, Card, Phone, Address, Mpasswd from merchantinfo where 1 = ?", paras);
					Merchanttable.setModel(mer);
				}

			}
			if (e.getSource() == delete) {
				
				if (delete.isEnabled()) {
					
					int selrow=Merchanttable.getSelectedRow();
					int i = Merchanttable.getSelectedRowCount();
					while(i > 1)
					{
						JOptionPane.showMessageDialog(this, "只能操作一行数据，请选中一行操作");
						return;
					}
					if(selrow == -1)
					{
						JOptionPane.showMessageDialog(this, "请选择一行，再进行操作");
						return;
					}
					int j = JOptionPane.showConfirmDialog(this, "<html><font size = '5'>是否要删除选中商家信息？<br /><br /><font size = '5' color = 'red'>请注意慎重操作<br /><br />", "温馨提示", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (j == 0) {
						
						String[] eid={(String)mer.getValueAt(selrow, 0)};
						String sql = "delete from merchantinfo where Mid = ?";
						boolean result = mernew.Merchantupdate(sql, eid);
						if (result) {
							

							JOptionPane.showMessageDialog(this, "<html><font size = '5' color = 'blue'>恭喜您，删除成功啦");
							mer = new MerchantModel();
							mer.query("select Mid, Mshopname, Mname, Sex, Card, Phone, Address, Mpasswd from merchantinfo where 1 = ?", paras);
							Merchanttable.setModel(mer);
						}else {
							
							JOptionPane.showMessageDialog(this, "<html><font size = '5' color = red>抱歉的通知您，删除没有成功!<br />请检查原因！");
						}
					}else {
						
						return;
					}
				}
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
			if (e.getSource() == add) {
				
				add.setIcon(new ImageIcon("image/addC.png"));
			}
			if (e.getSource() == addc) {
				
				addc.setIcon(new ImageIcon("image/addconfirmC.png"));
			}
			if (e.getSource() == modify) {
				
				modify.setIcon(new ImageIcon("image/modifyC.png"));
			}
			if (e.getSource() == delete) {
				
				delete.setIcon(new ImageIcon("image/delC.png"));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == add) {
				
				add.setIcon(new ImageIcon("image/add.png"));
			}
			if (e.getSource() == addc) {
				
				addc.setIcon(new ImageIcon("image/addconfirm.png"));
			}
			if (e.getSource() == modify) {
				
				modify.setIcon(new ImageIcon("image/modify.png"));
			}
			if (e.getSource() == delete) {
				
				delete.setIcon(new ImageIcon("image/del.png"));
			}
		}
		
		// 信息判断函数
		private boolean mesconfirm() {
			
			boolean b = false;
			if (idt.getText().equals((String)mernew.getValueAt(Merchanttable.getSelectedRow(), 0)) 
					&& snamet.getText().equals((String)mernew.getValueAt(Merchanttable.getSelectedRow(), 1)) 
					&& mnamet.getText().equals((String)mernew.getValueAt(Merchanttable.getSelectedRow(), 2)) 
					&& cardt.getText().equals((String)mernew.getValueAt(Merchanttable.getSelectedRow(), 4))
					&& phonet.getText().equals((String)mernew.getValueAt(Merchanttable.getSelectedRow(), 5))
					&& addresst.getText().equals((String)mernew.getValueAt(Merchanttable.getSelectedRow(), 6))
					&& mpasswdt.getText().equals((String)mernew.getValueAt(Merchanttable.getSelectedRow(), 7))
				) 
			{
				b = true;
			}
			
			return b;
		}
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub

			
		}
		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			Component comp =e.getComponent();
			if (comp instanceof JTextField) {
				
				if (!idt.getText().trim().isEmpty()) {
					
					if (!Tools.isNum(idt.getText().trim()) || Integer.valueOf(idt.getText().trim()) < 0) {
							
							JOptionPane.showMessageDialog(this, "<html>商家编号只能由数字组成，请重新输入！");
							return;
					} else {
						
						if (mernew.checkid(idt.getText().trim())) {
							
							JOptionPane.showMessageDialog(this, "<html><br /><font size = '5'>抱歉的通知您，商家编号:<font color = 'red'>"+idt.getText()+"</font>已经存在　<br/>请输入其他的再试！<br />");
						}
					}

				}
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
