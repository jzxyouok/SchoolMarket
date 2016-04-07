package com.mytools;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class MySelfTabbedPane extends BasicTabbedPaneUI {

	private ColorSet selectedColorSet; // 选择颜色
	private ColorSet defaultColorSet; // 什么也没有做的时候的颜色
	private ColorSet hoverColorSet;  // 鼠标悬停时的颜色
	private boolean contentTopBorderDrawn = true;
	
	// 统一画线条的颜色
	private Color lineColor = new Color(158, 158, 158);
	private Color dividerColor = new Color(200, 200, 200);
	private Insets contentInsets = new Insets(1, 1, 1, 1);
	private int lastRollOverTab = -1;
	
	// 返回创建的自定义选项卡UI
	public static ComponentUI createUI(JComponent c) {
		
		return new MySelfTabbedPane();
	}

	// 创建一个布局管理器，用来重新控制选项卡的布局
	protected LayoutManager createLayoutManager() {
		if (tabPane.getTabLayoutPolicy() == JTabbedPane.SCROLL_TAB_LAYOUT) {
			
			return super.createLayoutManager();
		} else { /* WRAP_TAB_LAYOUT */
			
			return new TabbedPaneLayout();
		}
	}
	
	
	// 构造函数
	public MySelfTabbedPane() {
		
		// 选择的颜色
		selectedColorSet = new ColorSet();
		selectedColorSet.GradColor1 = new Color(186,231,253);
		
		// 没有操作时的颜色
		defaultColorSet = new ColorSet();
		defaultColorSet.GradColor1 = new Color(88,194,254);

		
		// 悬停时的颜色
		hoverColorSet = new ColorSet();
		hoverColorSet.GradColor1 = new Color(51,153,255);
		
		maxTabHeight = 10;

		setContentInsets(0);
	}
	
	public void setContentTopBorderDrawn(boolean b) {
		
		contentTopBorderDrawn = b;
	}

	public void setContentInsets(Insets i) {
		
		contentInsets = i;
	}

	public void setContentInsets(int i) {
		
		contentInsets = new Insets(i, i, i, i);
	}

	// 返回当前运行的用于显示选项卡的选项卡数
	public int getTabRunCount(JTabbedPane pane) {
		
		return 1;
	}
	
	// 可以改变一些BasicTabbedPaneUI中默认的属性
	protected void installDefaults() {
		
		super.installDefaults();

		RollOverListener l = new RollOverListener();
		tabPane.addMouseListener(l);
		tabPane.addMouseMotionListener(l);
		tabPane.setFocusable(false);
		
	}

	protected boolean scrollableTabLayoutEnabled() {
		
		return false;
	}

	protected Insets getContentBorderInsets(int tabPlacement) {
		
		return contentInsets;
	}

	 protected Insets getTabAreaInsets(int tabPlacement) {
	
		 return contentInsets;
	 }

	protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
		
		return 40;
	}

	protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
		
		int w = super.calculateTabWidth(tabPlacement, tabIndex, metrics);
		int wid = metrics.charWidth('M');
		w += wid * 2;
		return w;
	}

	protected int calculateMaxTabHeight(int tabPlacement) {
		
		return 40;
	}
	
	// 绘制整个选项卡区域
	protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
		
		 Graphics2D g2d = (Graphics2D) g;

		super.paintTabArea(g, tabPlacement, selectedIndex);

		if (contentTopBorderDrawn) {
			
			g2d.setColor(lineColor);
			g2d.drawLine(0, 40, tabPane.getWidth() - 1, 40);
		}
	}
	
	// 绘制某个选项卡的背景色
	protected void paintTabBackground(Graphics g, int tabPlacement,
		
		int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		
		Graphics2D g2d = (Graphics2D) g;
		ColorSet colorSet;

		Rectangle rect = rects[tabIndex];

		if (isSelected) {
			
			colorSet = selectedColorSet;
			
		} else if (getRolloverTab() == tabIndex) {
			
			colorSet = hoverColorSet;
			
		} else {
			
			colorSet = defaultColorSet;
		}

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int width = rect.width;
		int xpos = rect.x;
		int yPos = rect.y;
		
		g2d.setPaint(new GradientPaint(xpos, yPos, colorSet.GradColor1, width, h, colorSet.GradColor1));
		
		g2d.fill(this.getArea(xpos, yPos, width, h));

	}

	private Shape getArea(int x, int y, int w, int h) {
		
		RoundRectangle2D rect = new RoundRectangle2D.Float(x, y, w, h, 0, 0);
		Area a = new Area(rect);
		
		return a;
	}

	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		
		Rectangle rect = getTabBounds(tabIndex, new Rectangle(x, y, w, h));
		
		g.setColor(dividerColor);
		Graphics2D g2 = (Graphics2D) g;
		Composite old = g2.getComposite();
		AlphaComposite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f);
		g2.setComposite(comp);
		g2.setColor(dividerColor);
		g2.drawLine(rect.x + rect.width, 0, rect.x + rect.width, 40);
		g2.setComposite(old);
	}

	// 自定义一个颜色类，用来控制选项卡不同状态下的不同颜色值
	private class ColorSet {
		
		Color GradColor1;
	}

	private class RollOverListener implements MouseMotionListener, MouseListener {

		public void mouseDragged(MouseEvent e) {
		}

		public void mouseMoved(MouseEvent e) {
			
			checkRollOver();
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
			
			checkRollOver();
		}

		public void mouseExited(MouseEvent e) {
			
		}

		private void checkRollOver() {
			
			int currentRollOver = getRolloverTab();
			
			if (currentRollOver != lastRollOverTab) {
				
				lastRollOverTab = currentRollOver;
				Rectangle tabsRect = new Rectangle(0, 0, tabPane.getWidth(), 40);
				tabPane.repaint(tabsRect);
			}
		}
	}
}
