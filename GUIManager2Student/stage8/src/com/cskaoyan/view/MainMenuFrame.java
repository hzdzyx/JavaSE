package com.cskaoyan.view;

import com.cskaoyan.controller.StudentController;
import com.cskaoyan.util.ImageIconUtils;
import com.cskaoyan.util.ProportionEnum;
import com.cskaoyan.util.SelfAdaptiveScreen;
import com.cskaoyan.util.ShowWindowUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 学生管理系统-登陆成功后，主页面，菜单页面
 *
 * @since 12:01
 * @author wuguidong@cskaoyan.onaliyun.com
 */
public class MainMenuFrame extends JFrame implements ActionListener {

    // 中央王道logo
    private JPanel centerPanel;
    // 王道logo图片和label
    private ImageIcon logoIcon;
    private JLabel centerLabel;

    private StudentListPanel studentListPanel;

    // 自适应屏幕分辨率后的宽高
    public int adaptiveWidth = SelfAdaptiveScreen.getAdaptiveWidth(this, ProportionEnum.MAIN_RATIO);
    public int adaptiveHeight = SelfAdaptiveScreen.getAdaptiveHeight(this, ProportionEnum.MAIN_RATIO);

    public MainMenuFrame(int mark, StudentListPanel sl) {
        init(mark, sl);
    }

    // 调用控制层
    private StudentController studentController = new StudentController();

    private void init(int mark, StudentListPanel sl) {
        // 主界面自适应屏幕
        SelfAdaptiveScreen.setAdaptiveSize(this, ProportionEnum.MAIN_RATIO);
        // 1.界面
        // 设置背景为白色
        this.setForeground(Color.GRAY);
        // 设置窗口标题
        this.setTitle("王道学生信息管理系统");
        // 设置关闭窗口即关闭应用
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 2.添加欢迎语标签
        // 欢迎栏
        JPanel welcomePanel = new JPanel();
        JLabel welcomeLabel = new JLabel("欢迎光临学生管理系统");
        welcomeLabel.setFont(new Font("黑体", Font.BOLD, (int) (adaptiveHeight * 0.04)));
        welcomePanel.add(welcomeLabel);
        this.add(welcomePanel, BorderLayout.NORTH);

        // 3.添加功能按钮
        // 功能按钮
        JPanel menuPanel = new JPanel();
        GridLayout gl = new GridLayout(4, 1);
        gl.setVgap(0);
        menuPanel.setLayout(gl);
        String[] menus = {"查询学生", "新增学生", "保存数据至文件", "退出程序"};
        JButton[] menusButs = new JButton[menus.length];
        for (int i = 0; i < menus.length; i++) {
            menusButs[i] = new JButton(menus[i]);
            menusButs[i].setFont(new Font("微软雅黑", Font.BOLD, (int) (adaptiveHeight * 0.018)));
            // 按钮添加窗口监听
            menusButs[i].addActionListener(this);
            menuPanel.add(menusButs[i]);
        }
        this.add(menuPanel, BorderLayout.WEST);

        // 添加中央王道logo
        // 标志0代表第一次进入,需要欢迎界面
        if (mark == 0) {
            drawCenterPicture();
        }

        if (mark == -1) {
            studentListPanel = new StudentListPanel(this);
            this.add(studentListPanel);
        }

        if (mark == -2) {
            studentListPanel = new StudentListPanel(this, sl.studentController);
            this.add(studentListPanel);
        }

        /*
            开始实现左侧按钮功能
         */
        // 按钮1: 查询学生
        menusButs[0].addActionListener(e -> {
            // 由于showNetWindow是后面代码是多线程执行的,所以为了保证showNetWindow方法先执行完毕再继续执行,需要使用SwingWorker
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // 点击查询,先关闭欢迎logo
                    if (centerPanel != null) {
                        centerPanel.setVisible(false);
                    }
                    // 如果存在旧的 StudentListPanel，先将其移除
                    if (studentListPanel != null) {
                        MainMenuFrame.this.remove(studentListPanel);
                    }
                    // 在后台线程中运行 showNetWindow 方法
                    MainMenuFrame.this.showNetWindow();
                    return null;
                }

                @Override
                protected void done() {
                    if (studentListPanel != null) {
                        MainMenuFrame.this.add(studentListPanel, BorderLayout.CENTER);
                        // 重新布局并重绘 MainMenuFrame
                        MainMenuFrame.this.validate();
                        MainMenuFrame.this.repaint();
                    }
                }
            }.execute();
        });

        // 按钮2: 新增学生
        menusButs[1].addActionListener(e -> {
            MainMenuFrame.this.setVisible(false);
            MainMenuFrame newMain = new MainMenuFrame(0, null);
            AddStudentFrame add = new AddStudentFrame(newMain);
            newMain.setEnabled(false);
        });

        // 按钮3：保存至文件
        menusButs[2].addActionListener(e -> ShowWindowUtils.showWarning("待实现！"));

        // 按钮4: 退出系统
        menusButs[3].addActionListener(e -> {
            // 直接关闭虚拟机,退出程序
            System.exit(1);
        });

        // 启动窗口
        this.setVisible(true);
    }

    private void showNetWindow() {
        JButton overBut = new JButton("结束等待传输");
        overBut.setFont(new Font("Dialog", Font.BOLD, (int) (adaptiveWidth * 0.013)));

        JLabel label = new JLabel("正在等待网络传输数据...");
        label.setFont(new Font("Dialog", Font.BOLD, (int) (adaptiveWidth * 0.016)));

        JOptionPane optionPane = new JOptionPane(label, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{overBut}, null);
        // 创建一个 JDialog 并添加 JOptionPane
        JDialog dialog = new JDialog(this, "处理中");
        dialog.setModal(true);
        dialog.setContentPane(optionPane);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        // 启动一个线程,用于监听网络传输,该线程会阻塞
        Thread netThread = new Thread(() -> {
            switch (studentController.initDataFromNet()) {
                case 0:
                    ShowWindowUtils.showInfo("已从网络接收信息!");
                    // 创建新的 StudentListPanel 并添加到 MainMenuFrame
                    studentListPanel = new StudentListPanel(MainMenuFrame.this, studentController);
                    break;
                case -1:
                    // 网络传输错误!
                    ShowWindowUtils.showWarning("网络传输错误!");
                    break;
                case -2:
                    ShowWindowUtils.showInfo("已关闭网络连接!");
                    break;
            }
            // 关闭TCP 关闭弹窗
            studentController.closeTCPSocket();
            dialog.setVisible(false);
        }, "TCP接收数据线程");
        netThread.start();
        overBut.addActionListener(e2 -> {
            // 若存在表格将表格隐藏
            if (studentListPanel != null) {
                studentListPanel.setVisible(false);
            }
            studentListPanel = null;
            studentController.closeTCPSocket();
            dialog.setVisible(false);
            if (centerPanel == null) {
                drawCenterPicture();
            }
            centerPanel.setVisible(true);

            // 重新布局并重绘 MainMenuFrame
            MainMenuFrame.this.revalidate();
            MainMenuFrame.this.repaint();
        });
        dialog.setVisible(true);
    }

    private void drawCenterPicture() {
        centerPanel = new JPanel();
        logoIcon = ImageIconUtils.getHome(adaptiveWidth, adaptiveHeight);
        centerLabel = new JLabel(logoIcon);
        centerPanel.add(centerLabel);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }
}
