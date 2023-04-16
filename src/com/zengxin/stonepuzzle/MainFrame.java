package com.zengxin.stonepuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class MainFrame extends JFrame implements KeyListener {
    //使用数组来定义图片位置
    private int[][] img = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
    //胜利状态的数组
    private int[][] victory = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
    //0号元素的行和列
    private int row;
    private int column;
    //步数
    private int numOfSteps;

    public MainFrame() throws HeadlessException {
        //键盘监听
        this.addKeyListener(this);
        //初始化窗口
        initFrame();
        //打乱数组
        shuffleArrays();
        //绘制游戏界面
        paintView();
        //设置窗体可见
        setVisible(true);
    }

    //判断是否胜利
    public boolean isVectory() {
        for (int i = 0; i < img.length; i++) {
            for (int j = 0; j < img[i].length; j++) {
                if (img[i][j] != victory[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    //初始化窗口
    public void initFrame() {
        //设置窗体大小
        setSize(514, 595);
        //设置窗体的关闭模式
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置窗体置顶
        setAlwaysOnTop(true);
        //设置窗体居中
        setLocationRelativeTo(null);
        //取消窗体默认布局
        setLayout(null);
    }

    //此方法用于绘制游戏界面
    public void paintView() {
        getContentPane().removeAll();
        if (isVectory()) {
            JLabel jLabel = new JLabel(new ImageIcon("image/win.png"));
            jLabel.setBounds(124, 230, 266, 88);
            getContentPane().add(jLabel);
        }
        JLabel numberOfSteps = new JLabel("步数: " + numOfSteps);
        numberOfSteps.setBounds(50, 20, 100, 30);
        getContentPane().add(numberOfSteps);
        JButton resume = new JButton("重新开始");
        resume.setBounds(350, 20, 100, 20);
        getContentPane().add(resume);
        resume.setFocusable(false);
        resume.addActionListener(e -> {
            numOfSteps = 0;
            initFrame();
            shuffleArrays();
            paintView();
        });

        for (int i = 0; i < img.length; i++) {
            for (int j = 0; j < img[i].length; j++) {
                JLabel jLabel = new JLabel(new ImageIcon("image/" + img[i][j] + ".png"));
                jLabel.setBounds(50 + j * 100, 90 + i * 100, 100, 100);
                getContentPane().add(jLabel);
            }
        }

        JLabel background = new JLabel(new ImageIcon("image/background.png"));
        background.setBounds(26, 30, 450, 484);
        getContentPane().add(background);
        getContentPane().repaint();
    }

    //打乱二维数组
    public void shuffleArrays() {
        Random random = new Random();
        int temp = 0;
        for (int i = 0; i < img.length; i++) {
            for (int j = 0; j < img[i].length; j++) {
                int randomI = random.nextInt(4);
                int randomJ = random.nextInt(4);
                temp = img[i][j];
                img[i][j] = img[randomI][randomJ];
                img[randomI][randomJ] = temp;
            }
        }
        find0();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        move(keyCode);
        paintView();
    }

    //此方法用于处理移动业务
    private void move(int keyCode) {
        if (isVectory()) {
            return;
        }
        int temp = 0;
        if (keyCode == 37 && column != 3) {
            temp = img[row][column];
            img[row][column] = img[row][column + 1];
            img[row][column + 1] = temp;
            column++;
            numOfSteps++;
        } else if (keyCode == 38 && row != 3) {
            temp = img[row][column];
            img[row][column] = img[row + 1][column];
            img[row + 1][column] = temp;
            row++;
            numOfSteps++;
        } else if (keyCode == 39 && column != 0) {
            temp = img[row][column];
            img[row][column] = img[row][column - 1];
            img[row][column - 1] = temp;
            column--;
            numOfSteps++;
        } else if (keyCode == 40 && row != 0) {
            temp = img[row][column];
            img[row][column] = img[row - 1][column];
            img[row - 1][column] = temp;
            row--;
            numOfSteps++;
        } else if (keyCode == 32) {
            img = new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 0}
            };
        }
    }

    public void find0() {
        int[] site = new int[2];
        for (int i = 0; i < img.length; i++) {
            for (int j = 0; j < img[i].length; j++) {
                if (img[i][j] == 0) {
                    row = i;
                    column = j;
                    return;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    //
}
