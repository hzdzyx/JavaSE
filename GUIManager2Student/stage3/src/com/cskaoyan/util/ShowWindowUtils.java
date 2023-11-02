package com.cskaoyan.util;

import javax.swing.*;
import java.awt.*;

/**
 * 弹窗工具类
 *
 * @since 21:05
 * @author wuguidong@cskaoyan.onaliyun.com
 */
public class ShowWindowUtils {

    private ShowWindowUtils() {
    }

    /*
      弹窗目前分为两类:
        1.错误警示弹窗
        2.一般信息弹窗
     */
    public static void showWarning(String message) {
        // 获取屏幕的分辨率
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        // 屏幕的宽和高
        int width = screenSize.width;

        // 设置确定按钮大小
        JButton okButton = new JButton("确定");
        okButton.setFont(new Font("Dialog", Font.BOLD, (int) (width * 0.01)));
        okButton.setPreferredSize(new Dimension(100, 50));

        // 对话框信息
        JLabel messageLabel = new JLabel(message);
        // 设置警告信息字体大小
        messageLabel.setFont(new Font("Dialog", Font.BOLD, (int) (width * 0.01)));

        Object[] options = {okButton};
        JOptionPane optionPane = new JOptionPane(messageLabel,
                JOptionPane.WARNING_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                options,
                options[0]);

        // 创建一个JDialog并设置大小
        JDialog dialog = optionPane.createDialog("错误提示");
        // 设置标题字体大小
        dialog.setFont(new Font("Dialog", Font.BOLD, (int) (width * 0.01)));
        dialog.pack();
        dialog.setModal(true);
        okButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    public static void showInfo(String message) {
        // 获取屏幕的分辨率
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        // 屏幕的宽
        int width = screenSize.width;

        // 设置确定按钮大小
        JButton okButton = new JButton("确定");
        okButton.setFont(new Font("Dialog", Font.BOLD, (int) (width * 0.01)));
        okButton.setPreferredSize(new Dimension(100, 50));

        // 对话框信息
        JLabel messageLabel = new JLabel(message);
        // 设置警告信息字体大小
        messageLabel.setFont(new Font("Dialog", Font.BOLD, (int) (width * 0.01)));

        Object[] options = {okButton};
        JOptionPane optionPane = new JOptionPane(messageLabel,
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                options,
                options[0]);

        // 创建一个JDialog并设置大小
        JDialog dialog = optionPane.createDialog("信息提示");
        // 设置标题字体大小
        dialog.setFont(new Font("Dialog", Font.BOLD, (int) (width * 0.01)));
        dialog.pack();
        dialog.setModal(true);
        okButton.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }
}
