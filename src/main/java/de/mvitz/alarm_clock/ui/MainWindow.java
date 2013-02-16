package de.mvitz.alarm_clock.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.joda.time.DateTime;

import de.mvitz.alarm_clock.contract.UI;

public final class MainWindow implements UI {

    private final JFrame frame;
    private final MainView view;

    public MainWindow() {
        frame = new JFrame("AlarmClock");
        view = new MainView();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view.getPanel(), BorderLayout.CENTER);
    }

    @Override
    public void open() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void time(DateTime time) {
        view.setTime(time);
    }

}
