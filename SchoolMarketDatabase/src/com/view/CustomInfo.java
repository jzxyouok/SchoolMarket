package com.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import com.model.MerchantModel;
import com.model.CustomModel;
import com.mytools.*;

@SuppressWarnings("serial")
public class CustomInfo extends JPanel implements ActionListener, MouseListener, FocusListener {
	
	 // 用于获得窗口的大小
	final static int width=Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height=Toolkit.getDefaultToolkit().getScreenSize().height;
	// 公用颜色值
	Color color = new Color(22, 120, 195);

	// 显示信息的面板
	JPanel showtabel, showinfoall, showinfo, handle;
	JPanel jadd;
	JButton addc;
	// 装载信息面板的面板
	JPanel showjp;
	JTable Customtable = null;

	JButton add, modify, delete;
	
	// 右侧面板组件
	JLabel id, name, sex, phone, address, cpasswd; 
	
	JTextField idt, namet, phonet, addresst, cpasswdt;
	JRadioButton boy, gril;
	
	//定义一个鼠标指针的类型
	Cursor myCursor=new Cursor(Cursor.HAND_CURSOR);//手型鼠标
	
	JScrollPane jsp;
	
	CustomModel cus = new CustomModel();
	CustomModel cusnew = new CustomModel();
	String paras[] = {"1"};
	
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
		jlb.setForeground(Color.white);
	}
	private void setjtf(final JTextField jtf) {
		
		showinfo.add(jtf);
		MatteBorder ubderline = new MatteBorder(0, 0, 1, 0, Color.white);
		jtf.setBorder(ubderline);
		jtf.setOpaque(false);
		jtf.setFont(MyFont.Infotext);
		jtf.setForeground(Color.white);
	}
	
	public CustomInfo() {
		
		// 处理左侧
		//1.设计jtable
		cus = new CustomModel();
		cus.query("select Cid, Cname, Sex, Phone, Address, Cpasswd from custominfo where 1=?", paras);
		Customtable = new JTable(cus);
		
		// 调用工具Tools类中的设置表格样式方法
		Tools.setTableStyle(Customtable);
		Customtable.addMouseListener(this);
		
		// 滚动面板
		jsp=new JScrollPane(Customtable);
		jsp.setBorder(new MatteBorder(0, 1, 1, 0, color));
		Tools.setJspStyle(jsp);
		
		showtabel = new JPanel(new BorderLayout());
		showtabel.setBackground(Color.white);
		// 设置只有左边框
		MatteBorder border = new MatteBorder(0, 1, 1, 0, new Color(22, 120, 195));
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
		add.setToolTipText("添加一条信息");
		setbutton(add);
		modify = new JButton(new ImageIcon("image/modify.png"));
		modify.setToolTipText("修改信息");
		setbutton(modify);
		delete = new JButton(new ImageIcon("image/del.png"));
		delete.setToolTipText("删除选中的信息");
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
		showinfo.setPreferredSize(new Dimension(350, (int)(height*0.8)));
		showinfo.setOpaque(false);
		
		// 第一列
		id = new JLabel(" 顾客编号");
		setlab(id);
		idt = new JTextField(10);
		idt.addFocusListener(this);
		setjtf(idt);
		
		name = new JLabel(" 顾客姓名");
		setlab(name);
		namet = new JTextField(10);
		setjtf(namet);
		
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
		ButtonGroup sext = new ButtonGroup();
		sext.add(boy);
		sext.add(gril);
		JPanel sextp = new JPanel(new GridLayout(1, 2));
		sextp.setOpaque(false);
		sextp.add(boy);
		sextp.add(gril);
		showinfo.add(sextp);
		 
		phone = new JLabel(" 联系电话");
		setlab(phone);
		phonet =new JTextField(10);
		setjtf(phonet);
		
		address = new JLabel(" 联系地址");
		setlab(address);
		addresst = new JTextField(10);
		setjtf(addresst);
		addresst.setFont(new Font("新宋体",Font.PLAIN,13));
		
		
		cpasswd = new JLabel(" 登录密码");
		setlab(cpasswd);
		cpasswdt = new JTextField(10);
		setjtf(cpasswdt);
		
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
		if (e.getSource() == Customtable) {
			
			
			showmes();
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
			namet.setText("");
			gril.setSelected(true);
			phonet.setText("");
			addresst.setText("");
			cpasswdt.setText("");
			
			addc.setVisible(true);
			delete.setEnabled(false);
			modify.setEnabled(false);
			
			idt.addFocusListener(this);
		}
		
		if (e.getSource() == addc) {
			
			// 1.得到信息
			String str1 = idt.getText();
			String str2 = namet.getText();
			String str3 =null;
			if (boy.isSelected()) {
				
				str3 = boy.getText();
			} else if (gril.isSelected()) {
				
				str3 = gril.getText();
			}
		
			String str4 = phonet.getText();
			String str5 = addresst.getText();
			String str6 = cpasswdt.getText();
			
			// 2.判断信息是否为空
			if (str1.equals("")||str2.equals("")||str3.equals("")||str4.equals("")||str5.equals("")
					||str6.equals("")) {
				
				JOptionPane.showMessageDialog(this, "<html><font color = 'red'>不能为空，请输入相应的信息！");
				return;
			}
			
			// 3.添加操作
			String[] newparas={str1, str2, str3, str4, str5, str6};
			String sql="insert into custominfo values (?, ?, ?, ?, ?, ?)";
			boolean result = cusnew. Customupdate(sql, newparas);
			if (result) {
				
				JOptionPane.showMessageDialog(this, "<html><font size = '5' color = 'blue'>添加成功");
			}else {
				
				JOptionPane.showMessageDialog(this, "<html><font size = '5' color = red>抱歉的通知您，没有添加成功!</font>" +
						"<br />请检查信息是否符合要求！<br />" );
			}
			cus = new CustomModel();
			cus.query("select Cid, Cname, Sex, Phone, Address, Cpasswd from custominfo where 1 = ?", paras);
			Customtable.setModel(cus);
		}
		if (e.getSource() == modify) {
			
			if (modify.isEnabled()) {

				int selrow=Customtable.getSelectedRow();
				int i = Customtable.getSelectedRowCount();
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
				
				// 1.得到修改的内容数组
				String str1 = idt.getText();
				String str2 = namet.getText();
				String str3 =null;
				if (boy.isSelected()) {
					
					str3 = boy.getText();
				} else if (gril.isSelected()) {
					
					str3 = gril.getText();
				}
			
				String str4 = phonet.getText();
				String str5 = addresst.getText();
				String str6 = cpasswdt.getText();
				
				if (mesconfirm() && str3.equals((String)cusnew.getValueAt(Customtable.getSelectedRow(), 2))) {
					
					JOptionPane.showMessageDialog(this, "<html><font size = '5' color = 'red'>亲！信息没有修改过！</font><br /><font size = '5' color = 'blue'>温馨提示:<br />您可以对右侧相应的信息修改之后再保存修改");
					return;
				}
				if(str2.equals("")||str3.equals("")||str4.equals("")||str5.equals("")
						||str6.equals(""))
				{
					JOptionPane.showMessageDialog(this, "<html><font color = 'red'>不能为空，请输入相应的信息！");
					return;
					
				}
				int y = JOptionPane.showConfirmDialog(this, "确定要修改吗？", "温馨提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (y == 1) {
					
					return;
				}else {
					
					String[] newparas={str2, str3, str4, str5, str6, str1};
					String sql="update ccustominfo set Cname=?,Sex=?,Phone=?,Adress=?,Cpasswd=? where Cid=?";
					MerchantModel mernew = new MerchantModel();
					boolean result = mernew.Merchantupdate(sql, newparas);
					if (result) {
						
						JOptionPane.showMessageDialog(this, "<html><font size = '5'>　修改成功");
					}else {
						
						JOptionPane.showMessageDialog(this, "<html><font size = '5' color = red>抱歉的通知您，修改没有成功!</font>" +
								"<br />请检查信息是否符合要求！<br />" );
					}
					cus = new CustomModel();
					cus.query("select Cid, Cname, Sex, Phone, Address, Cpasswd from custominfo where 1 = ?", paras);
					Customtable.setModel(cus);
				}
			}
		}
		
		if (e.getSource() == delete) {
			
			if (delete.isEnabled()) {
				
				int selrow=Customtable.getSelectedRow();
				int i = Customtable.getSelectedRowCount();
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
				int j = JOptionPane.showConfirmDialog(this, "<html><font size = '5'>是否要删除选中员工信息？<br /><br /><font size = '5' color = 'red'>请注意慎重操作<br /><br />", "温馨提示", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (j == 0) {
					
					String[] eid={(String)cus.getValueAt(selrow, 0)};
					String sql = "delete from custominfo where Cid = ?";
					boolean result = cusnew.Customupdate(sql, eid);
					if (result) {
						
						JOptionPane.showMessageDialog(this, "<html><font size = '5' color = 'blue'>恭喜您，删除成功啦");
						cus = new CustomModel();
						cus.query("select Cid, Cname, Sex, Phone, Address, Cpasswd from custominfo where 1 = ?", paras);
						Customtable.setModel(cus);
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
		if (idt.getText().equals((String)cusnew.getValueAt(Customtable.getSelectedRow(), 0)) 
				&& namet.getText().equals((String)cusnew.getValueAt(Customtable.getSelectedRow(), 1)) 
				&& phonet.getText().equals((String)cusnew.getValueAt(Customtable.getSelectedRow(), 3))
				&& addresst.getText().equals((String)cusnew.getValueAt(Customtable.getSelectedRow(), 4))
				&& cpasswdt.getText().equals((String)cusnew.getValueAt(Customtable.getSelectedRow(), 5))
			) 
		{
			b = true;
		}
		
		return b;
	}
	
	// 信息显示函数
	private void showmes() {
		
		cusnew.query("select * from custominfo where 1 = ?", paras);
		idt.setText((String)cusnew.getValueAt(Customtable.getSelectedRow(), 0));
		idt.setEditable(false);
		idt.setForeground(Color.lightGray);
		namet.setText((String)cusnew.getValueAt(Customtable.getSelectedRow(), 1));
		// 设置性别的显示
		if (cusnew.getValueAt(Customtable.getSelectedRow(), 2).equals("男")) {
			boy.setSelected(true);
		}else {
			gril.setSelected(true);
		}
		phonet.setText((String)cusnew.getValueAt(Customtable.getSelectedRow(), 3));
		addresst.setText((String)cusnew.getValueAt(Customtable.getSelectedRow(), 4));
		cpasswdt.setText((String)cusnew.getValueAt(Customtable.getSelectedRow(),5));
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
					
					JOptionPane.showMessageDialog(this, "<html>会员编号只能由数字组成，请重新输入！");
					return;
				}else {
					
					if (cusnew.checkid(idt.getText().trim())) {
						
						JOptionPane.showMessageDialog(this, "<html><br /><font size = '5'>抱歉的通知您，会员编号:<font color = 'red'>"+idt.getText()+"</font>已经存在　<br/>请输入其他的再试！<br />");
					}
				}

			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
