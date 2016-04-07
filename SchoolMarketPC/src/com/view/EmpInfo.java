/**
 * 用于显示和管理雇员信息的面板
 */
package com.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import com.mytools.*;
import com.model.*;

@SuppressWarnings("serial")
public class EmpInfo extends JPanel implements MouseListener, FocusListener, KeyListener {
	
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
	JTable Emptable = null;

	JButton add, addc, modify, delete;
	
	// 右侧面板组件
	JLabel id, name, sex, age, card, phone, adress, todate, job; 
	
	JTextField idt, namet, aget, cardt, phonet, addresst, todatet, jobt;
	JRadioButton boy, gril;
	ButtonGroup sext;
	
	 EmpModel em=new EmpModel();
	 EmpModel emnew = new EmpModel();
	
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
	public EmpInfo() {
		
		// 处理左侧
		//1.设计jtable
		em.query("select Eid, EName, Sex, Age, Job from EmployeeInfo where 1=?", paras);
		Emptable = new JTable(em);
		
		// 调用工具Tools类中的设置表格样式方法
		Tools.setTableStyle(Emptable);
		Emptable.addMouseListener(this);
		Emptable.addKeyListener(this);
		Emptable.setOpaque(false);
		
		// 滚动面板
		jsp = new JScrollPane(Emptable);
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
		//add.setToolTipText("添加一条员工信息");
		setbutton(add);
		modify = new JButton(new ImageIcon("image/modify.png"));
		//modify.setToolTipText("修改员工的信息");
		setbutton(modify);
		delete = new JButton(new ImageIcon("image/del.png"));
		//delete.setToolTipText("删除选中的员工");
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
		id = new JLabel(" 员工编号");
		setlab(id);
		idt = new JTextField(10);
		idt.addFocusListener(this);
		setjtf(idt);
		
		name = new JLabel(" 员工姓名");
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
		sext = new ButtonGroup();
		sext.add(boy);
		sext.add(gril);
		JPanel sextp = new JPanel(new GridLayout(1, 2));
		sextp.setOpaque(false);
		sextp.add(boy);
		sextp.add(gril);
		showinfo.add(sextp);
		
		age = new JLabel(" 年    龄");
		setlab(age);
		aget = new JTextField(10);
		setjtf(aget);
		
		card = new JLabel(" 身份证号");
		setlab(card);
		cardt = new JTextField(10);
		setjtf(cardt);
		 
		phone = new JLabel(" 联系电话");
		setlab(phone);
		phonet =new JTextField(10);
		setjtf(phonet);
		
		adress = new JLabel(" 联系地址");
		setlab(adress);
		addresst = new JTextField(10);
		setjtf(addresst);
		addresst.setFont(new Font("新宋体",Font.PLAIN,13));
		
		todate = new JLabel(" 入职日期");
		setlab(todate);
		todatet = new JTextField(10);
		setjtf(todatet);
		
		job = new JLabel(" 职    位");
		setlab(job);
		jobt = new JTextField(10);
		setjtf(jobt);
		
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
		if (e.getSource() == Emptable) {
			
			emnew.query("select * from EmployeeInfo where 1 = ?", paras);
			idt.setText((String)emnew.getValueAt(Emptable.getSelectedRow(), 0));
			idt.setEditable(false);
			idt.setForeground(Color.lightGray);
			namet.setText((String)emnew.getValueAt(Emptable.getSelectedRow(), 1));
			// 设置性别的显示
			if (emnew.getValueAt(Emptable.getSelectedRow(), 2).equals("男")) {
				boy.setSelected(true);
			}else {
				gril.setSelected(true);
			}
			aget.setText((String)emnew.getValueAt(Emptable.getSelectedRow(), 3));
			cardt.setText((String)emnew.getValueAt(Emptable.getSelectedRow(), 4));
			phonet.setText((String)emnew.getValueAt(Emptable.getSelectedRow(), 5));
			addresst.setText((String)emnew.getValueAt(Emptable.getSelectedRow(), 6));
			String fdate = (String)emnew.getValueAt(Emptable.getSelectedRow(), 7);
			todatet.setText(fdate.substring(0, 19));
			jobt.setText((String)emnew.getValueAt(Emptable.getSelectedRow(), 8));
			
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
			//boy.setSelected(false);
			gril.setSelected(true);
			aget.setText("");
			cardt.setText("");
			phonet.setText("");
			addresst.setText("");
			todatet.setText(Tools.getlocaldatetime());
			jobt.setText("");
			
			addc.setVisible(true);
			Emptable.clearSelection();
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
			
			String str4 = aget.getText();
			String str5 = cardt.getText().trim();
			String str6 = phonet.getText();
			String str7 = addresst.getText();
			String str8 = todatet.getText();
			String str9 = jobt.getText();
			
			// 2.判断信息是否为空
			if (str1.equals("")||str2.equals("")||str3.equals("")||str4.equals("")||str5.equals("")
					||str6.equals("")||str7.equals("")||str8.equals("")||str9.equals("")) {
				
				JOptionPane.showMessageDialog(this, "<html><font color = 'red'>不能为空，请输入相应的信息！");
				return;
			}
			
			// 3.添加操作
			String[] newparas={str1, str2, str3, str4, str5, str6, str7, str8, str9};
			String sql="insert into EmployeeInfo values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			boolean result = emnew.Empupdate(sql, newparas);
			if (result) {
				
				JOptionPane.showMessageDialog(this, "<html><font size = '5' color = 'blue'>添加成功");
			}else {
				
				JOptionPane.showMessageDialog(this, "<html><font size = '5' color = red>抱歉的通知您，没有添加成功!</font>" +
						"<br />请检查信息是否符合要求！<br />" +
						"常见问题：<br />　　①年龄超出范围<br />　　②输入日期格式不符合(年-月-日,如:2013-07-20) <br />　　③身份证是否为18位");
			}
			em = new EmpModel();
			em.query("select Eid, EName, Sex, Age, Job from EmployeeInfo where 1 = ?", paras);
			Emptable.setModel(em);
		}
		if (e.getSource() == modify) {
			
			if (modify.isEnabled()) {
				int selrow=Emptable.getSelectedRow();
				int i = Emptable.getSelectedRowCount();
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
				String str2 = namet.getText();
				String str3 =null;
				if (boy.isSelected()) {
					
					str3 = boy.getText();
				} else if (gril.isSelected()) {
					
					str3 = gril.getText();
				}
				
				String str4 = aget.getText();
				String str5 = cardt.getText().trim();
				String str6 = phonet.getText();
				String str7 = addresst.getText();
				String str8 = todatet.getText();
				String str9 = jobt.getText();
				
				if (mesconfirm() && str3.equals((String)emnew.getValueAt(Emptable.getSelectedRow(), 2))) {
					
					JOptionPane.showMessageDialog(this, "<html><font size = '5' color = 'red'>亲！信息没有修改过！</font><br /><font size = '5' color = 'blue'>温馨提示:<br />您可以对右侧相应的信息修改之后再保存修改");
					return;
				}
				if(str2.equals("")||str3.equals("")||str4.equals("")||str5.equals("")
						||str6.equals("")||str7.equals("")||str8.equals("")||str9.equals(""))
				{
					JOptionPane.showMessageDialog(this, "<html><font color = 'red'>不能为空，请输入相应的信息！");
					return;
					
				}
				String[] newparas={str2, str3, str4, str5, str6, str7, str8, str9, str1};
				String sql="update EmployeeInfo set EName=?,Sex=?,Age=?,Card=?, Phone=?,Adress=?,ToDate=?,Job=? where Eid=?";
				EmpModel emnew = new EmpModel();
				boolean result = emnew.Empupdate(sql, newparas);
				if (result) {
					
					JOptionPane.showMessageDialog(this, "<html><font size = '4' color = 'blue'>修改成功");
				}else {
					
					JOptionPane.showMessageDialog(this, "<html><font size = '5' color = red>抱歉的通知您，修改没有成功!</font>" +
							"<br />请检查信息是否符合要求！<br />" +
							"常见问题：<br />　　①年龄超出范围<br />　　②输入日期格式不符合(年-月-日)　");
				}
				em = new EmpModel();
				em.query("select Eid, EName, Sex, Age, Job from EmployeeInfo where 1 = ?", paras);
				Emptable.setModel(em);
			}

		}
		if (e.getSource() == delete) {
			
			if (delete.isEnabled()) {
				
				int selrow=Emptable.getSelectedRow();
				int i = Emptable.getSelectedRowCount();
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
					
					String[] eid={(String)em.getValueAt(selrow, 0)};
					String sql = "delete from EmployeeInfo where Eid = ?";
					boolean result = emnew.Empupdate(sql, eid);
					if (result) {
						

						JOptionPane.showMessageDialog(this, "<html><font size = '5' color = 'blue'>恭喜您，删除成功啦");
						em = new EmpModel();
						em.query("select Eid, EName, Sex, Age, Job from EmployeeInfo where 1 = ?", paras);
						Emptable.setModel(em);
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
		if (idt.getText().equals((String)emnew.getValueAt(Emptable.getSelectedRow(), 0)) 
				&& namet.getText().equals((String)emnew.getValueAt(Emptable.getSelectedRow(), 1)) 
				&& aget.getText().equals((String)emnew.getValueAt(Emptable.getSelectedRow(), 3))
				&& cardt.getText().equals((String)emnew.getValueAt(Emptable.getSelectedRow(), 4))
				&& phonet.getText().equals((String)emnew.getValueAt(Emptable.getSelectedRow(), 5))
				&& addresst.getText().equals((String)emnew.getValueAt(Emptable.getSelectedRow(), 6))
				&& todatet.getText().equals(((String)emnew.getValueAt(Emptable.getSelectedRow(), 7)).substring(0, 19))
				&& jobt.getText().equals((String)emnew.getValueAt(Emptable.getSelectedRow(), 8))
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
						
						JOptionPane.showMessageDialog(this, "<html>员工编号只能由数字组成，请重新输入！");
						return;
				} else {
					
					if (emnew.checkid(idt.getText().trim())) {
						
						JOptionPane.showMessageDialog(this, "<html><br /><font size = '5'>抱歉的通知您，员工编号:<font color = 'red'>"+idt.getText()+"</font>已经存在　<br/>请输入其他的再试！<br />");
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
