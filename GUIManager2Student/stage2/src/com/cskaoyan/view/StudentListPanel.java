package com.cskaoyan.view;


import com.cskaoyan.controller.StudentController;
import com.cskaoyan.util.ShowWindowUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;

/**
 * 主页面的学生列表画布类
 * @since 15:40
 * @author wuguidong@com.com.cskaoyan.onaliyun.com
 */
public class StudentListPanel extends JPanel implements FocusListener {

    // 需要做Student相关业务处理
    private StudentController studentController = new StudentController();

    // 依赖于MainMenuFrame
    private MainMenuFrame menuFrame;

    // 按钮font
    private Font butFont;
    // 下拉组件
    private JComboBox<String> comboUnit = new JComboBox<>();
    // 查询条件数据输入框
    private JTextField queryTextField = new JTextField(8);
    // 确定检索按钮
    private JButton queryBut = new JButton("查询");
    // 检索框默认提示语句
    private String queryPrompt = "请输入检索条件";

    // 表格组件
    private JTable table = new JTable();
    // 动态刷新表格数据组件
    private MyTableModel model;

    // 存放学生信息数据的二维数组
    private String[][] data;
    // 表格组件的列标题
    private String[] tableTitle;

    // 自适应屏幕参数
    private int font85x;

    // 底部按钮
    // 返回首页
    private JButton returnBut = new JButton("返回首页");
    // 删除
    private JButton deleteBut = new JButton("删除选中");
    // 刷新
    private JButton refreshBut = new JButton("刷新表格");
    // 修改学生
    private JButton updateBut = new JButton("修改学生");

    public StudentListPanel(MainMenuFrame menuFrame) {
        this.menuFrame = menuFrame;
        font85x = (int) (menuFrame.adaptiveWidth * 0.04);
        butFont = new Font("黑体", Font.BOLD, (int) (menuFrame.adaptiveWidth * 0.016));
        init();
    }

    private void init() {
        // 1.设置下拉组件列表,用于查询学生时的选项
        JPanel queryPanel = new JPanel();
        comboUnit.addItem("按照学号查找");
        comboUnit.addItem("按照姓名查找");
        // 默认使用学号查询
        comboUnit.setSelectedIndex(0);
        comboUnit.setFont(new Font("黑体", Font.BOLD, (int) (menuFrame.adaptiveWidth * 0.015)));
        queryTextField.setFont(new Font("微软雅黑", Font.PLAIN, (int) (menuFrame.adaptiveWidth * 0.017)));
        queryTextField.setForeground(Color.GRAY);
        queryTextField.setText(queryPrompt);
        queryBut.setFont(butFont);
        // 查询按钮点击事件
        queryBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowWindowUtils.showWarning("待实现！");
            }
        });
        queryPanel.add(comboUnit);
        queryPanel.add(queryTextField);
        queryPanel.add(queryBut);
        this.add(queryPanel, BorderLayout.NORTH);

        // 2.创建表格组件
        initTable();

        // 3.底部按钮添加点击事件
        // 返回首页按钮,事件完成
        returnBut.setFont(butFont);
        this.add(returnBut, BorderLayout.SOUTH);
        returnBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                new MainMenuFrame(0);
            }
        });

        // 删除按钮添加事件
        deleteBut.setFont(butFont);
        this.add(deleteBut, BorderLayout.SOUTH);
        deleteBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowWindowUtils.showWarning("待实现！");
            }
        });

        // 修改学生按钮
        updateBut.setFont(butFont);
        this.add(updateBut, BorderLayout.SOUTH);
        updateBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowWindowUtils.showWarning("待实现！");
            }
        });

        // 刷新表格按钮
        refreshBut.setFont(butFont);
        this.add(refreshBut, BorderLayout.SOUTH);
        refreshBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.setVisible(false);
                MainMenuFrame newMain = new MainMenuFrame(-1);
                StudentListPanel newPanel = new StudentListPanel(newMain);
                newPanel.setVisible(true);
                newMain.add(newPanel);
            }
        });

        // 检索输入框回车搜索功能实现
        queryTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    queryBut.doClick();
                }
            }
        });

        // 检索输入框焦点检测
        queryTextField.addFocusListener(this);
        this.setVisible(true);
    }


    // 输入框聚焦事件
    @Override
    public void focusGained(FocusEvent e) {
        //获取焦点时，清空提示内容
        String temp = queryTextField.getText();
        if (temp.equals(queryPrompt)) {
            queryTextField.setText("");
            queryTextField.setFont(new Font("黑体", Font.BOLD, (int) (menuFrame.adaptiveWidth * 0.0162)));
            queryTextField.setForeground(Color.BLACK);
        }
    }

    // 输入框失焦事件
    @Override
    public void focusLost(FocusEvent e) {
        //失去焦点时，没有输入内容，显示提示内容
        String temp = queryTextField.getText();
        if (temp.equals("")) {
            queryTextField.setForeground(Color.GRAY);
            queryTextField.setText(queryPrompt);
        }
    }

    /**
     * 初始化表格数据,第一次查询时,获取数据源全部数据
     * @since 13:59
     * @author wuguidong@com.com.cskaoyan.onaliyun.com
     */
    private void initTable() {
        // 1.初始化数据
        data = studentController.getAllTableData();
        tableTitle = studentController.getTableColumns();
        model = new MyTableModel(data, tableTitle);
        table.setModel(model);

        // 2.设置表格样式
        setTableStyle();

        // 3.添加到滚动列表
        // 创建滚动面板并把表格加到该面板中
        JScrollPane jsPane = new JScrollPane(table);
        // 设置滚动面板的宽度、高度。需要在使用布局管理器的时候使用

        if (menuFrame.adaptiveWidth < 1400) {
            /*
            低分辨率时特殊处理
           */
            jsPane.setPreferredSize(new Dimension((int) (menuFrame.adaptiveWidth * 0.85), (int) (menuFrame.adaptiveHeight * 0.70)));
            this.add(jsPane, BorderLayout.CENTER);
            return;
        }
        if (menuFrame.adaptiveWidth > 1400 && menuFrame.adaptiveWidth < 1800) {
            // 1080P处理
            jsPane.setPreferredSize(new Dimension((int) (menuFrame.adaptiveWidth * 0.87), (int) (menuFrame.adaptiveHeight * 0.75)));
            // 把滚动面板加入到窗口
            this.add(jsPane, BorderLayout.CENTER);
            return;
        }
        jsPane.setPreferredSize(new Dimension((int) (menuFrame.adaptiveWidth * 0.885), (int) (menuFrame.adaptiveHeight * 0.788)));
        // 把滚动面板加入到窗口
        this.add(jsPane, BorderLayout.CENTER);
    }

    /**
     * 设置表格样式
     * @since 12:07
     * @author wuguidong@com.com.cskaoyan.onaliyun.com
     */
    private void setTableStyle() {
        // 设置特殊列的宽度
        // 写死学号列
        table.getColumnModel().getColumn(0).setMaxWidth(font85x);
        table.getColumnModel().getColumn(0).setPreferredWidth(font85x);
        table.getColumnModel().getColumn(0).setMinWidth(font85x);

        // 限制姓名列
        table.getColumnModel().getColumn(1).setPreferredWidth((int) (menuFrame.adaptiveWidth * 0.0833));
        table.getColumnModel().getColumn(1).setMinWidth((int) (menuFrame.adaptiveWidth * 0.0833));
        table.getColumnModel().getColumn(1).setMaxWidth((int) (menuFrame.adaptiveWidth * 0.115));

        // 写死性别列
        table.getColumnModel().getColumn(2).setMaxWidth(font85x);
        table.getColumnModel().getColumn(2).setMinWidth(font85x);
        table.getColumnModel().getColumn(2).setPreferredWidth(font85x);

        // 限制学校和专业列
        table.getColumnModel().getColumn(3).setMinWidth((int) (menuFrame.adaptiveWidth * 0.12));
        table.getColumnModel().getColumn(4).setMinWidth((int) (menuFrame.adaptiveWidth * 0.155));

        // 写死年龄列
        table.getColumnModel().getColumn(5).setMaxWidth(font85x);
        table.getColumnModel().getColumn(5).setPreferredWidth(font85x);
        table.getColumnModel().getColumn(5).setMinWidth(font85x);

        // 限制城市列
        table.getColumnModel().getColumn(6).setPreferredWidth((int) (menuFrame.adaptiveWidth * 0.068));
        table.getColumnModel().getColumn(6).setMinWidth((int) (menuFrame.adaptiveWidth * 0.068));
        table.getColumnModel().getColumn(6).setMaxWidth((int) (menuFrame.adaptiveWidth * 0.116));

        // 写死手机号列
        table.getColumnModel().getColumn(7).setPreferredWidth((int) (menuFrame.adaptiveWidth * 0.12));
        table.getColumnModel().getColumn(7).setMinWidth((int) (menuFrame.adaptiveWidth * 0.12));
        table.getColumnModel().getColumn(7).setMaxWidth((int) (menuFrame.adaptiveWidth * 0.12));

        // 限制邮箱列
        table.getColumnModel().getColumn(8).setMinWidth((int) (menuFrame.adaptiveWidth * 0.14));


        // 设置表头
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("宋体", Font.BOLD, (int) (menuFrame.adaptiveHeight * 0.025)));
        tableHeader.setForeground(Color.RED);
        // 表头不可改变列顺序
        tableHeader.setReorderingAllowed(false);
        // 表头不可改变列宽
        // tableHeader.setResizingAllowed(false);

        // 表格只支持选一行,不支持多选
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 设置行高
        table.setRowHeight((int) (menuFrame.adaptiveHeight * 0.05));
        // 设置表格字体
        table.setFont(new Font("黑体", Font.BOLD, (int) (menuFrame.adaptiveHeight * 0.024)));
        // 创建单元格渲染器
        MyTableCellRenderer renderer = new MyTableCellRenderer();

        // 遍历表格的每一列，分别给每一列设置单元格渲染器
        for (String s : tableTitle) {
            // 根据 列名 获取 表格列
            TableColumn tableColumn = table.getColumn(s);
            // 设置 表格列 的 单元格渲染器
            tableColumn.setCellRenderer(renderer);
        }
    }

    /*
      自定义表格单元渲染器
     */
    private static class MyTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // 偶数行背景设置为白色，奇数行背景设置为灰色
            if (row % 2 == 0) {
                setBackground(Color.WHITE);
            } else {
                setBackground(Color.LIGHT_GRAY);
            }

            // 所有数据居中排列
            setHorizontalAlignment(SwingConstants.CENTER);
            // 调用父类的该方法完成渲染器的其他设置
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    /*
        自定义table格式,主要是禁止首列id被直接修改
     */
    private static class MyTableModel extends DefaultTableModel {

        MyTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            // 首列学号禁止直接修改
            return column != 0;
        }
    }

}