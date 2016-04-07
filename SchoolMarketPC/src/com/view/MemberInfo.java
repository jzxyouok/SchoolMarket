package com.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import com.model.EmpModel;
import com.model.MemberModel;
import com.mytools.*;

@SuppressWarnings("serial")
public class MemberInfo extends JPanel implements ActionListener, MouseListener, FocusListener {
	
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
	JTable Menbertable = null;

	JButton add, modify, delete;
	
	// 右侧面板组件
	JLabel id, name, sex, age, card, phone, adress, ondate, wmid; 
	
	JTextField idt, namet, aget, cardt, phonet, addresst, ondatet, wmidt;
	JRadioButton boy, gril;
	
	//定义一个鼠标指针的类型
	Cursor myCursor=new Cursor(Cursor.HAND_CURSOR);//手型鼠标
	
	JScrollPane jsp;
	
	MemberModel mbm = new MemberModel();
	MemberModel mbmnew = new MemberModel();
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
	public MemberInfo() {
		
		// 处理左侧
		//1.设计jtable
		mbm = new MemberModel();
		mbm.query("select Mid, MName, Sex, Age, WMid from MemberInfo where 1=?", paras);
		Menbertable = new JTable(mbm);
		
		// 调用工具Tools类中的设置表格样式方法
		Tools.setTableStyle(Menbertable);
		Menbertable.addMouseListener(this);
		
		// 滚动面板
		jsp=new JScrollPane(Menbertable);
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
		id = new JLabel(" 会员编号");
		setlab(id);
		idt = new JTextField(10);
		idt.addFocusListener(this);
		setjtf(idt);
		
		name = new JLabel(" 会员姓名");
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
		
		ondate = new JLabel(" 加入日期");
		setlab(ondate);
		ondatet = new JTextField(10);
		setjtf(ondatet);
		
		wmid = new JLabel(" 完美卡号");
		setlab(wmid);
		wmidt = new JTextField(10);
		setjtf(wmidt);
		
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
		if (e.getSource() == Menbertable) {
			
			
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
			aget.setText("");
			cardt.setText("");
			phonet.setText("");
			addresst.setText("");
			ondatet.setText(Tools.getlocaldatetime());
			wmidt.setText("");
			
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
			
			String str4 = aget.getText();
			String str5 = cardt.getText().trim();
			String str6 = phonet.getText();
			String str7 = addresst.getText();
			String str8 = ondatet.getText();
			String str9 = wmidt.getText();
			
			// 2.判断信息是否为空
			if (str1.equals("")||str2.equals("")||str3.equals("")||str4.equals("")||str5.equals("")
					||str6.equals("")||str7.equals("")||str8.equals("")||str9.equals("")) {
				
				JOptionPane.showMessageDialog(this, "<html><font color = 'red'>不能为空，请输入相应的信息！");
				return;
			}
			
			// 3.添加操作
			String[] newparas={str1, str2, str3, str4, str5, str6, str7, str8, str9};
			String sql="insert into MemberInfo values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			boolean result = mbmnew. Memberupdate(sql, newparas);
			if (result) {
				
				JOptionPane.showMessageDialog(this, "<html><font size = '5' color = 'blue'>添加成功");
			}else {
				
				JOptionPane.showMessageDialog(this, "<html><font size = '5' color = red>抱歉的通知您，没有添加成功!</font>" +
						"<br />请检查信息是否符合要求！<br />" +
						"常见问题：<br />　　①年龄超出范围<br />　　②输入日期格式不符合(年-月-日,如:2013-07-20) <br />　　③身份证是否为18位");
			}
			mbm = new MemberModel();
			mbm.query("select Mid, MName, Sex, Age, WMid from MemberInfo where 1 = ?", paras);
			Menbertable.setModel(mbm);
		}
		if (e.getSource() == modify) {
			
			if (modify.isEnabled()) {

				int selrow=Menbertable.getSelectedRow();
				int i = Menbertable.getSelectedRowCount();
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
				
				String str4 = aget.getText();
				String str5 = cardt.getText().trim();
				String str6 = phonet.getText();
				String str7 = addresst.getText();
				String str8 = ondatet.getText();
				String str9 = wmidt.getText();
				
				if (mesconfirm() && str3.equals((String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 2))) {
					
					JOptionPane.showMessageDialog(this, "<html><font size = '5' color = 'red'>亲！信息没有修改过！</font><br /><font size = '5' color = 'blue'>温馨提示:<br />您可以对右侧相应的信息修改之后再保存修改");
					return;
				}
				if(str2.equals("")||str3.equals("")||str4.equals("")||str5.equals("")
						||str6.equals("")||str7.equals("")||str8.equals("")||str9.equals(""))
				{
					JOptionPane.showMessageDialog(this, "<html><font color = 'red'>不能为空，请输入相应的信息！");
					return;
					
				}
				int y = JOptionPane.showConfirmDialog(this, "确定要修改吗？", "温馨提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (y == 1) {
					
					return;
				}else {
					
					String[] newparas={str2, str3, str4, str5, str6, str7, str8, str9, str1};
					String sql="update MemberInfo set MName=?,Sex=?,Age=?,Card=?, Phone=?,Adress=?,OnDate=?,WMid=? where Mid=?";
					EmpModel emnew = new EmpModel();
					boolean result = emnew.Empupdate(sql, newparas);
					if (result) {
						
						JOptionPane.showMessageDialog(this, "<html><font size = '5'>　修改成功");
					}else {
						
						JOptionPane.showMessageDialog(this, "<html><font size = '5' color = red>抱歉的通知您，修改没有成功!</font>" +
								"<br />请检查信息是否符合要求！<br />" +
								"常见问题：<br />　　①年龄超出范围<br />　　②输入日期格式不符合(年-月-日)　");
					}
					mbm = new MemberModel();
					mbm.query("select Mid, MName, Sex, Age, WMid from MemberInfo where 1 = ?", paras);
					Menbertable.setModel(mbm);
				}
			}
		}
		if (e.getSource() == delete) {
			
			if (delete.isEnabled()) {
				
				int selrow=Menbertable.getSelectedRow();
				int i = Menbertable.getSelectedRowCount();
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
					
					String[] eid={(String)mbm.getValueAt(selrow, 0)};
					String sql = "delete from MemberInfo where Mid = ?";
					boolean result = mbmnew.Memberupdate(sql, eid);
					if (result) {
						
						JOptionPane.showMessageDialog(this, "<html><font size = '5' color = 'blue'>恭喜您，删除成功啦");
						mbm = new MemberModel();
						mbm.query("select Mid, MName, Sex, Age, WMid from MemberInfo where 1 = ?", paras);
						Menbertable.setModel(mbm);
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
		if (idt.getText().equals((String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 0)) 
				&& namet.getText().equals((String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 1)) 
				&& aget.getText().equals((String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 3))
				&& cardt.getText().equals((String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 4))
				&& phonet.getText().equals((String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 5))
				&& addresst.getText().equals((String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 6))
				&& ondatet.getText().equals(((String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 7)).substring(0, 19))
				&& wmidt.getText().equals((String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 8))
			) 
		{
			b = true;
		}
		
		return b;
	}
	
	// 信息显示函数
	private void showmes() {
		
		mbmnew.query("select * from MemberInfo where 1 = ?", paras);
		idt.setText((String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 0));
		idt.setEditable(false);
		idt.setForeground(Color.lightGray);
		namet.setText((String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 1));
		// 设置性别的显示
		if (mbmnew.getValueAt(Menbertable.getSelectedRow(), 2).equals("男")) {
			boy.setSelected(true);
		}else {
			gril.setSelected(true);
		}
		aget.setText((String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 3));
		cardt.setText((String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 4));
		phonet.setText((String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 5));
		addresst.setText((String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 6));
		String fdate = (String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 7);
		ondatet.setText(fdate.substring(0, 19));
		wmidt.setText((String)mbmnew.getValueAt(Menbertable.getSelectedRow(), 8));
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
					
					if (mbmnew.checkid(idt.getText().trim())) {
						
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
