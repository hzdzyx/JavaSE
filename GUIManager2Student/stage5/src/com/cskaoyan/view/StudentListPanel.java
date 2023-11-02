package com.cskaoyan.view;

import com.cskaoyan.controller.StudentController;
import com.cskaoyan.util.CheckAndHandleUtils;
import com.cskaoyan.util.ShowWindowUtils;
import com.cskaoyan.view.listener.TableCellListener;

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
 * @author wuguidong@cskaoyan.onaliyun.com
 */
public class StudentListPanel extends JPanel implements FocusListener {

    // 需要做Student相关业务处理
    private StudentController studentController = new StudentController();

    // 依赖于MainMenuFrame
    private MainMenuFrame menuFrame;

    // 按钮font
    private Font butFont;

    // 搜索下拉组件
    private JComboBox<String> queryComboUnit = new JComboBox<>();
    // 查询条件数据输入框
    private JTextField queryTextField = new JTextField(8);
    // 确定检索按钮
    private JButton queryBut = new JButton("查询");
    // 检索框默认提示语句
    private String queryPrompt = "请输入检索条件";
    // 查询的条件
    private String queryCondition = "";

    // 排序下拉组件
    private JComboBox<String> sortComboUnit = new JComboBox<>();
    // 确定排序按钮
    private JButton sortBut = new JButton("确定排序");

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

    // 删除逻辑后,更新查询表格数据的标志,默认为0,表示直接查询数据源数据,不做任何处理
    private int delQueryFlag = 0;

    public StudentListPanel(MainMenuFrame menuFrame) {
        this.menuFrame = menuFrame;
        font85x = (int) (menuFrame.adaptiveWidth * 0.04);
        butFont = new Font("黑体", Font.BOLD, (int) (menuFrame.adaptiveWidth * 0.016));
        init();
    }

    private void init() {
        // 1.设置搜索下拉组件列表,用于查询学生时的选项
        JPanel queryPanel = new JPanel();
        queryComboUnit.addItem("按照学号查询");
        queryComboUnit.addItem("按照姓名模糊查询");
        // 默认使用学号查询
        queryComboUnit.setSelectedIndex(0);
        queryComboUnit.setFont(new Font("黑体", Font.BOLD, (int) (menuFrame.adaptiveWidth * 0.015)));
        queryTextField.setFont(new Font("微软雅黑", Font.PLAIN, (int) (menuFrame.adaptiveWidth * 0.017)));
        queryTextField.setForeground(Color.GRAY);
        queryTextField.setText(queryPrompt);
        queryBut.setFont(butFont);
        // 查询按钮点击事件
        queryBut.addActionListener(e -> {
            queryCondition = queryTextField.getText();
            if ("".equals(queryCondition) || "请输入检索条件".equals(queryCondition)) {
                // 未输入查询条件,默认显示全部数据
                refreshBut.doClick();
                return;
            }
            // 按照学号查询
            if (queryComboUnit.getSelectedIndex() == 0) {
                data = studentController.getResultByStuId(queryCondition);
                queryResultHandler(data);
            }
            // 按照姓名查询
            if (queryComboUnit.getSelectedIndex() == 1) {
                data = studentController.getResultByName(queryCondition);
                queryResultHandler(data);
                // 删除后查询标志置为1
                delQueryFlag = 1;
            }
        });
        queryPanel.add(queryComboUnit);
        queryPanel.add(queryTextField);
        queryPanel.add(queryBut);
        this.add(queryPanel, BorderLayout.NORTH);


        // 2.添加顶部排序下拉框
        JPanel sortPanel = new JPanel();
        sortComboUnit.addItem("---------------");
        sortComboUnit.addItem("按照学号升序排序");
        sortComboUnit.addItem("按照年龄降序排序");
        sortComboUnit.addItem("综合排序");
        // 默认不选择排序方式
        sortComboUnit.setSelectedIndex(0);
        sortComboUnit.setFont(new Font("黑体", Font.BOLD, (int) (menuFrame.adaptiveWidth * 0.015)));
        sortBut.setFont(butFont);
        sortBut.addActionListener(e -> {
            // 如果没选中排序方式，不做任何操作
            if (sortComboUnit.getSelectedIndex() == 0) {
                return;
            }
            int delFlag = JOptionPane.showConfirmDialog(StudentListPanel.this, "确认要进行排序吗？",
                    "确认对话框", JOptionPane.YES_NO_OPTION);
            // 若用户没有选择是，则结束函数的执行
            if (delFlag != JOptionPane.YES_OPTION) {
                return;
            }
            switch (sortComboUnit.getSelectedIndex()) {
                // 按照年龄从小到大排序，升序排列
                case 1:
                    data = studentController.ascendingSortById();
                    sortResultHandler(data);
                    // 删除后更新查询标志改为2
                    delQueryFlag = 2;
                    break;
                case 2:
                    data = studentController.descendingSortByAge();
                    sortResultHandler(data);
                    // 删除后更新查询标志改为3
                    delQueryFlag = 3;
                    break;
                case 3:
                    data = studentController.totalSort();
                    sortResultHandler(data);
                    // 删除后更新查询标志改为3
                    delQueryFlag = 4;
            }
        });
        sortPanel.add(sortComboUnit);
        sortPanel.add(sortBut);
        this.add(sortPanel, BorderLayout.NORTH);

        // 3.创建表格组件
        initTable();

        // 4.底部按钮添加点击事件
        // 返回首页按钮,事件完成
        returnBut.setFont(butFont);
        this.add(returnBut, BorderLayout.WEST);
        returnBut.addActionListener(e -> {
            menuFrame.setVisible(false);
            new MainMenuFrame(0);
        });

        // 删除按钮添加事件
        deleteBut.setFont(butFont);
        this.add(deleteBut, BorderLayout.CENTER);
        deleteBut.addActionListener(e -> {
            // 获取当前选中的行数
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                // 未选中行数,点击按钮无反应
                return;
            }
            // 选中行后,开始确认删除的提示
            // 弹出提示框
            int delFlag = JOptionPane.showConfirmDialog(StudentListPanel.this, "确认删除？",
                    "确认对话框", JOptionPane.YES_NO_OPTION);
            // 若用户没有选择是，则结束函数的执行。
            if (delFlag != JOptionPane.YES_OPTION) {
                return;
            }
            // 用户点击确定删除,则开始删除
            // 根据行数获取要删除用户id
            String delId = data[selectedRow][0];
            if (studentController.delStudent(delId)) {
                // 删除表格这一行
                model.removeRow(selectedRow);
                // 刷新表格数据
                model.fireTableDataChanged();
                /*
                    删除成功后刷新表格数据,为了避免后续删除逻辑混乱,所以对delQueryFlag做判断:
                        a,delQueryFlag为0,正常删除,仍然查询默认所有数据
                        b.delQueryFlag为1,说明是查询姓名得到后删除,继续调用查询姓名接口
                        c.delQueryFlag为2,说明是升序排列id学号后删除,继续调用升序排列学号接口
                        d.delQueryFlag为3,说明是降序排列age后删除,继续调用降序排列age接口
                        e.delQueryFlag为4,说明是综合排序后删除,继续调用综合排列接口
               */
                switch (delQueryFlag) {
                    case 0:
                        data = studentController.getAllTableData();
                        break;
                    case 1:
                        data = studentController.getResultByName(queryCondition);
                        break;
                    case 2:
                        data = studentController.ascendingSortById();
                        break;
                    case 3:
                        data = studentController.descendingSortByAge();
                        break;
                    case 4:
                        data = studentController.totalSort();
                        break;
                }
                // 删除成功弹窗提醒
                ShowWindowUtils.showInfo("删除成功！");
                if (table.getRowCount() == 0) {
                    // 表格已经没数据了,可能是检索后删除,直接刷新表格数据
                    refreshBut.doClick();
                    return;
                }
                return;
            }
            ShowWindowUtils.showInfo("删除失败！");
        });

        // 修改学生按钮
        updateBut.setFont(butFont);
        this.add(updateBut, BorderLayout.CENTER);
        updateBut.addActionListener(e -> {
            if (table.getSelectedRow() != -1) {
                // 选中一条数据,开始修改
                ShowWindowUtils.showInfo("开始修改id为:" + table.getValueAt(table.getSelectedRow(), 0) + " 的学生!");
                // 主页面失焦
                menuFrame.setEnabled(false);
                new UpdateStudentFrame(menuFrame, table);
            }
        });

        // 刷新表格按钮
        refreshBut.setFont(butFont);
        this.add(refreshBut, BorderLayout.CENTER);
        refreshBut.addActionListener(e -> {
            menuFrame.setVisible(false);
            MainMenuFrame newMain = new MainMenuFrame(-1);
            StudentListPanel newPanel = new StudentListPanel(newMain);
            newPanel.setVisible(true);
            newMain.add(newPanel);
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

    /**
     * 有结果就刷新表格数据展示出来,没数据就给出提示
     * @since 14:00
     * @param data 查询获取的结果
     * @author wuguidong@cskaoyan.onaliyun.com
     */
    private void queryResultHandler(String[][] data) {
        if (data == null) {
            // 未找到
            // 表格置空
            model = new MyTableModel(null, tableTitle);
            table.setModel(model);
            setTableStyle();
            ShowWindowUtils.showWarning("未找到学生!");
            return;
        }
        // 找到了,刷新表格
        model = new MyTableModel(data, tableTitle);
        table.setModel(model);
        setTableStyle();
        ShowWindowUtils.showInfo("已找到学生!");
    }

    /**
     * 对排序结果处理,如果表格已经为空了,要提示.有数据则刷新表格即可
     * @since 14:00
     * @param data 查询获取的结果
     * @author wuguidong@cskaoyan.onaliyun.com
     */
    private void sortResultHandler(String[][] data) {
        if (data == null || data.length == 0) {
            model = new MyTableModel(null, tableTitle);
            table.setModel(model);
            setTableStyle();
            ShowWindowUtils.showWarning("表格已经没有数据了!");
            return;
        }
        // 完成排序,刷新表格
        model = new MyTableModel(data, tableTitle);
        table.setModel(model);
        setTableStyle();
        ShowWindowUtils.showInfo("已完成排序!");
    }


    // 聚焦事件
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

    // 失焦事件
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
     * @author wuguidong@cskaoyan.onaliyun.com
     */
    private void initTable() {
        // 1.初始化数据
        data = studentController.getAllTableData();
        tableTitle = studentController.getTableColumns();
        model = new MyTableModel(data, tableTitle);
        table.setModel(model);

        // 双击修改单元格数据业务实现
        // 监听表格单元格数据变更,如有修改一并修改数据源
        Action action = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                TableCellListener tcl = (TableCellListener) e.getSource();
                // 数据源是学生数组的形式,所以需要提供id去修改
                String targetStuId = (String) table.getValueAt(tcl.getRow(), 0);
                int targetCol = tcl.getColumn();
                String newValue = ((String) tcl.getNewValue());
                // 校验数据
                switch (targetCol) {
                    case 1:
                        // 校验姓名
                        if (CheckAndHandleUtils.CheckAndHandleName(newValue)) {
                            refreshBut.doClick();
                            return;
                        }
                        break;
                    case 2:
                        // 校验性别
                        if (CheckAndHandleUtils.CheckAndHandleGender(newValue)) {
                            refreshBut.doClick();
                            return;
                        }
                        break;
                    case 5:
                        // 校验年龄
                        if (CheckAndHandleUtils.CheckAndHandleAge(newValue)) {
                            refreshBut.doClick();
                            return;
                        }
                        break;
                    case 6:
                        // 校验城市
                        if (CheckAndHandleUtils.CheckAndHandleCity(newValue)) {
                            refreshBut.doClick();
                            return;
                        }
                        break;
                    case 7:
                        // 校验手机号
                        if (CheckAndHandleUtils.CheckAndHandlePhoneNum(newValue)) {
                            refreshBut.doClick();
                            return;
                        }
                        break;
                    case 8:
                        // 校验电子邮箱
                        if (CheckAndHandleUtils.CheckAndHandleEmail(newValue)) {
                            refreshBut.doClick();
                            return;
                        }
                        break;
                }
                if (studentController.updateCellByStuId(targetStuId, targetCol, newValue)) {
                    // 修改成功,弹窗
                    ShowWindowUtils.showInfo("修改成功!");
                    return;
                }
                ShowWindowUtils.showWarning("修改失败!");
            }
        };
        new TableCellListener(table, action);

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
     * @author wuguidong@cskaoyan.onaliyun.com
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