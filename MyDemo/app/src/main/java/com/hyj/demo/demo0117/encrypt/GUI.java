//package com.hyj.demo.demo0117.encrypt;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.beans.PropertyChangeListener;
//
//public class GUI {
//
//    private static void createAndShowGUI() {
//        // 确保一个漂亮的外观风格
//        JFrame.setDefaultLookAndFeelDecorated(true);
//
//        // 创建及设置窗口
//        JFrame frame = new JFrame("影片名转换工具");
//        frame.setSize(550, 400);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        /* 创建面板，这个类似于 HTML 的 div 标签
//         * 我们可以创建多个面板并在 JFrame 中指定位置
//         * 面板中我们可以添加文本字段，按钮及其他组件。
//         */
//        JPanel panel = new JPanel();
//        // 添加面板
//        frame.add(panel);
//        /*
//         * 调用用户定义的方法并添加组件到面板
//         */
//        placeComponents(panel);
//
//        // 设置界面可见
//        frame.setVisible(true);
//    }
//
//    private static void placeComponents(JPanel panel) {
//        /* 布局部分我们这边不多做介绍
//         * 这边设置布局为 null
//         */
//        panel.setLayout(null);
//        // 创建 JLabel
//        JLabel userLabel = new JLabel("影片中文名:");
//        /* 这个方法定义了组件的位置。
//         * setBounds(x, y, width, height)
//         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
//         */
//        userLabel.setFont(new java.awt.Font("Dialog", 1, 15));
//        userLabel.setBounds(10,20,120,45);
//        panel.add(userLabel);
//
//        /*
//         * 创建文本域用于用户输入
//         */
//        JTextField movieName = new JTextField(20);
//        movieName.setFont(new java.awt.Font("Dialog", 1, 15));
//        movieName.setBounds(100,20,365,45);
//        panel.add(movieName);
//        // 输入密码的文本域
//        JLabel passwordLabel = new JLabel("转换结果:");
//        passwordLabel.setFont(new java.awt.Font("Dialog", 1, 15));
//        passwordLabel.setBounds(10,80,120,45);
//        panel.add(passwordLabel);
//
//        /*
//         *这个类似用于输入的文本域
//         * 但是输入的信息会以点号代替，用于包含密码的安全性
//         */
//        JTextField result = new JTextField(20);
//        result.setFont(new java.awt.Font("Dialog", 1, 15));
//        result.setBounds(100,80,365,45);
//        panel.add(result);
//
//        // 创建登录按钮
//        JButton loginButton = new JButton("生成");
//        loginButton.setBounds(10, 150, 80, 25);
//        panel.add(loginButton);
//        loginButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                   System.out.println("movieName:"+movieName.getText());
//                   String encrypt = "base64-"+Base64Util.encode(movieName.getText().getBytes());
//                   result.setText(encrypt);
//
//            }
//        });
//
//
//    }
//
//    public static void main(String[] args) {
//        // 显示应用 encrypt.GUI
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI();
//            }
//        });
//    }
//
//}
