package de.mvitz.alarm_clock.ui;

import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import de.mvitz.alarm_clock.contract.UI;

public final class MainWindow implements UI, MainView.Observer {

    private final List<Observer> observers = new LinkedList<>();

    private final JFrame frame;
    private final MainView view;

    public MainWindow() {
        frame = new JFrame("AlarmClock");
        view = new MainView();
        view.addObserver(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view.getPanel(), BorderLayout.CENTER);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
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

    @Override
    public void remainingTime(Duration remainingTime) {
        view.setRemainingTime(remainingTime);
    }

    @Override
    public void expire() {
        view.setRemainingTime(new Duration(0));
    }

    @Override
    public void onStart() {
        final DateTime alarmTime = view.getAlarmTime();
        final Duration idlePeriod = view.getIdlePeriod();
        for (final Observer observer : observers) {
            observer.onAlarmSet(alarmTime, idlePeriod);
        }
    }

}
