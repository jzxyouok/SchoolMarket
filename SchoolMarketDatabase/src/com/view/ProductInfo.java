/**
 *  产品信息管理界面
 */
package com.view;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import com.mytools.*;
import com.model.*;

@SuppressWarnings("serial")
public class ProductInfo extends JPanel implements ActionListener, MouseListener, FocusListener{
	
    // 用于获得窗口的大小
	final static int width=Toolkit.getDefaultToolkit().getScreenSize().width;
	final static int height=Toolkit.getDefaultToolkit().getScreenSize().height;
	// 公用颜色值
	Color color = new Color(22, 120, 195);
	// 显示信息的面板
	JPanel findproduct, showtabel, showinfo, handle;
	// 添加产品信息的面板控件
	JLabel IdorName, type;
	JTextField getIdorName;
	JComboBox<String> gettype;
	JButton find;
	// 装载信息面板的面板
	JPanel showjp;
	JTable producttable = null;
	
	JButton add, modify, delete;
	
	// 右侧面板组件
	JButton put_in_storage, record, look_stcok;
	JPanel jbuttonpanel;
	
	//定义一个鼠标指针的类型
	Cursor myCursor=new Cursor(Cursor.HAND_CURSOR);//手型鼠标
	
	JScrollPane jsp;
	
	String[] updateparas = {"1"};
	String updatesql = "select * from productinfo where 1 = ? order by Ptype";
	ProductModel pm = new ProductModel();
	public void setbutton(JButton jb, int type) {
		
		if (type == 1) {
			
			jb.setContentAreaFilled(false);
			jb.setBorderPainted(false);
			
		}
		if (type == 2) {
			
			jbuttonpanel.add(jb);
		}
		jb.setFocusPainted(false);
		jb.addMouseListener(this);
		jb.setCursor(myCursor);
		jb.setOpaque(false);
	}
	private void setlab(JLabel jlb, int i) {
		
		if (i == 1) {
			
			findproduct.add(jlb);
			
		}
		if (i == 2) {
			
			showinfo.add(jlb);
			jlb.setHorizontalAlignment(JLabel.CENTER);
			jlb.setFont(MyFont.Infolab);
			
		}
		jlb.setForeground(Color.WHITE);
	}
	private void setjtf(final JTextField jtf, int i) {
		
		if (i == 1) {
			
			findproduct.add(jtf);
			MatteBorder ubderline0 = new MatteBorder(0, 1, 1, 1, color);
			jtf.setBorder(ubderline0);
		}
		if (i == 2) {
			
			showinfo.add(jtf);
			jtf.setForeground(Color.white);
			MatteBorder ubderline = new MatteBorder(0, 0, 0, 0, Color.white);
			jtf.setBorder(ubderline);
		}

		jtf.setOpaque(false);
		jtf.setHorizontalAlignment(JTextField.CENTER);
		jtf.setFont(MyFont.Infotext);
	}
	
	
	public ProductInfo() {
		
		// 处理左侧
		findproduct = new JPanel(new GridLayout(1, 4));
		findproduct.setOpaque(false);
		findproduct.setPreferredSize(new Dimension((int)(width*0.8)-255, 80));
		IdorName = new JLabel("<html>　产品编号或产品名称<br/>&nbsp　(不区分大小写)");
		IdorName.setFont(new Font("新宋体",Font.PLAIN,15));
		setlab(IdorName, 1);
		
		getIdorName = new JTextField(10);
		getIdorName.addFocusListener(this);
		setjtf(getIdorName, 1);
		
		find = new JButton(new ImageIcon("image/find.png"));
		setbutton(find, 1);
		findproduct.add(find);
		
		type = new JLabel("产品种类");
		type.setFont(MyFont.Infolab);
		setlab(type, 1);
		Vector<String> temp=new Vector<String>();
		temp.add("--所有产品--");
		// 从产品表中查询类别
		String typesql = "select distinct Ptype from productinfo where 1 = ?";
		pm.query(typesql, updateparas);
		// 循环的加如temp中
		for (int i = 0; i < pm.getRowCount(); i++) {
			
			temp.add((String) pm.getValueAt(i, 0));
		}
		
		gettype = new JComboBox<String>(temp);
		gettype.setFocusable(false);
		gettype.setOpaque(false);
		gettype.setBounds(0, 20, 150, 40);
		gettype.setBackground(Color.white);
		gettype.setFont(new Font("新宋体",Font.PLAIN,15));
		
		gettype.addActionListener(this);
		
		JPanel jtype = new JPanel(null);
		jtype.setOpaque(false);
		jtype.add(gettype);
		
		findproduct.add(jtype);
		
		showtabel = new JPanel();
		showtabel.setBackground(Color.white);
		// 设置面板的大小
		showtabel.setPreferredSize(new Dimension((int)(width*0.8)-250, (int)(height*0.8)-155));
		
		// 设计表格
		pm.query(updatesql, updateparas);
		producttable = new JTable(pm);
		
		// 调用工具Tools类中的设置表格样式方法
		Tools.setTableStyle(producttable);
		producttable.addMouseListener(this);
		
		// 滚动面板
		jsp=new JScrollPane(producttable);
		jsp.setBorder(new MatteBorder(1, 1, 1, 1, color));
		Tools.setJspStyle(jsp);
		
		showtabel = new JPanel(new BorderLayout());
		showtabel.setBackground(Color.white);
		// 设置面板的大小
		showtabel.setPreferredSize(new Dimension((int)(width*0.8)-250, (int)(height*0.8)-155));
		
		showtabel.add(jsp, "Center");
		
		handle = new JPanel(new GridLayout(1, 3, ((int)(width*0.8)-625)/6, 10));
		handle.setPreferredSize(new Dimension((int)(width*0.8)-250, 91));
		handle.setOpaque(false);
		add = new JButton(new ImageIcon("image/add.png"));
		//add.setToolTipText("添加一条员工信息");
		setbutton(add, 1);
		modify = new JButton(new ImageIcon("image/modify.png"));
		//modify.setToolTipText("修改员工的信息");
		setbutton(modify, 1);
		delete = new JButton(new ImageIcon("image/del.png"));
		//delete.setToolTipText("删除选中的员工");
		setbutton(delete, 1);
		
		handle.add(add);
		handle.add(modify);
		handle.add(delete);
		
		showjp = new JPanel(new BorderLayout());
		showjp.setOpaque(false);
		// 用于设置面板的边框
		showjp.setBorder(new MatteBorder(0, 1, 0, 1, color));
		showjp.add(findproduct, "North");
		showjp.add(showtabel, "Center");
		showjp.add(handle, "South");
		
		// 处理右侧		
		// 装载按钮面板
		jbuttonpanel = new JPanel(new GridLayout(3, 1, 10, 50));
		jbuttonpanel.setPreferredSize(new Dimension(217, (int)(height*0.8)-354));
		//jbuttonpanel.setBounds(x, y, width, height)
		jbuttonpanel.setOpaque(false);
		
		put_in_storage = new JButton(new ImageIcon("./image/put_in_storage.png"));
		setbutton(put_in_storage, 2);
		put_in_storage.setRolloverIcon(new ImageIcon("./image/put_in_storageC.png"));
		
		record = new JButton(new ImageIcon("./image/record.png"));
		setbutton(record, 2);
		record.setRolloverIcon(new ImageIcon("./image/recordC.png"));
		
		look_stcok = new JButton(new ImageIcon("./image/lookstcok.png"));
		setbutton(look_stcok, 2);
		look_stcok.setRolloverIcon(new ImageIcon("./image/lookstcokC.png"));
		
		showinfo = new JPanel();
		showinfo.setPreferredSize(new Dimension(350, (int)(height*0.8)));
		showinfo.setOpaque(false);
		
		JPanel nulljp = new JPanel();
		nulljp.setPreferredSize(new Dimension(350, 100));
		nulljp.setOpaque(false);
		
		showinfo.add(nulljp);
		showinfo.add(jbuttonpanel);
		
		// 设置窗体的样式为当前系统的样式
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.add(showjp, "Center");
		this.add(showinfo, "East");
		this.setVisible(true);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == find) {
			
			// 调用查找函数
			findproductinfo();
		}
		// 添加产品信息操作
		if (e.getSource() == add) {
			
			// 创建添加窗口
			new Add_Product_View((width-560)/2,(height-498)/2);
			
			// 更新表格
			updatetable(updatesql, updateparas);
		}
		// 修改产品信息操作
		if (e.getSource() == modify) {
			
			int selrow=producttable.getSelectedRow();
			if(selrow == -1)
			{
				JOptionPane.showMessageDialog(this, "请选中一条需要修改的产品信息");
				return;
			}
			
			// 获得要修改的内容，同时创建修改操作窗口
			new Chang_Product_View(pm, selrow);
			
			// 更新表格
			updatetable(updatesql, updateparas);
		}
		// 双击表格修改
		if (e.getSource() == producttable) {
			
			if (e.getClickCount() == 2) {
				
				int selrow=producttable.getSelectedRow();
				
				// 获得要修改的内容，同时创建修改操作窗口
				new Chang_Product_View(pm, selrow);
				
				// 更新表格
				updatetable(updatesql, updateparas);
			}
		}
		// 删除产品信息操作
		if (e.getSource() == delete) {
			
			int selrow = producttable.getSelectedRow();
			if(selrow == -1)
			{
				JOptionPane.showMessageDialog(this, "请选择一行，再进行操作");
				return;
			}
			int i = JOptionPane.showConfirmDialog(this, "<html><font size = '5'>是否要删除选中的产品信息？<br /><br /><font size = '5' color = 'red'>请注意慎重操作<br /><br />", "温馨提示", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (i == 0) {
				
				String[] pid={(String)pm.getValueAt(selrow, 0)};
				String sql = "delete from productinfo where Pid = ?";
				boolean result = ProductModel.update(sql, pid);
				if (result) {
					
					JOptionPane.showMessageDialog(this, "<html><font size = '5' color = 'blue'>恭喜您，删除成功啦");
					updatetable(updatesql, updateparas);
				}else {
					
					JOptionPane.showMessageDialog(this, "<html><font size = '5' color = red>抱歉的通知您，删除没有成功!<br />请检查原因！");
				}
			}else {
				
				return;
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
		if (e.getSource() == find) {
			
			find.setIcon(new ImageIcon("image/findC.png"));
		}
		if (e.getSource() == add) {
			
			add.setIcon(new ImageIcon("image/addC.png"));
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
		if (e.getSource() == find) {
			
			find.setIcon(new ImageIcon("image/find.png"));
		}
		if (e.getSource() == add) {
			
			add.setIcon(new ImageIcon("image/add.png"));
		}
		if (e.getSource() == modify) {
			
			modify.setIcon(new ImageIcon("image/modify.png"));
		}
		if (e.getSource() == delete) {
			
			delete.setIcon(new ImageIcon("image/del.png"));
		}
	}
	// 更新表格模型函数，当表格需要更新是调用
	public void updatetable(String sql, String[] paras) {
		
		pm = new ProductModel();
		pm.query(sql, paras);
		producttable.setModel(pm);
		Tools.setTableStyle(producttable);
	}
	
	// 查找优化函数
	private void findproductinfo() {
		
		// 得到查找的条件
		String tiaojian = getIdorName.getText().trim();
		
		while (tiaojian.isEmpty()) {
			
			JOptionPane.showMessageDialog(this, "请输入要查找的内容");
			return;
		}
		
		String[] tiaojianparas = {tiaojian, tiaojian};
		
		// 如果是所有产品，则不带上产品类别的查询条件， 否则else
		if (gettype.getSelectedItem().equals("--所有产品--")) {
			
			// 对SellInfo ProductInfo表进行联合查询
	 		if (!ProductModel.checknum("select count(*) from productinfo where Pid like '%'+?+'%' or Pname like '%'+?+'%'", tiaojianparas)) {
				
				JOptionPane.showMessageDialog(this, "抱歉，没有找到相关的产品信息");
				return;
				
			}else {
				
				updatetable("select * from productinfo where Pid like '%'+?+'%' or Pname like '%'+?+'%'", tiaojianparas);
			}
		}
	}
	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		Component comp =e.getComponent();
		if (comp instanceof JTextField) {
			
			Tools.selsectAll(getIdorName);
		}
	}
	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource() == this.gettype) {

			if (getIdorName.getText().trim().isEmpty()) {

				if (gettype.getSelectedItem().equals("--所有产品--")) {

					updatetable(updatesql, updateparas);

				} else {

					String[] parasnew = { gettype.getSelectedItem().toString() };
					updatetable(
							"select * from productinfo where Ptype = ? order by Ptype",
							parasnew);
				}
			} else {
				
				findproductinfo();
			}
		}
	}
	
}
