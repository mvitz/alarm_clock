package de.mvitz.alarm_clock.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public final class MainView implements ActionListener {

    private static final DateTimeFormatter TIME_FMT = new DateTimeFormatterBuilder()
            .appendHourOfDay(2).appendLiteral(':').appendMinuteOfHour(2).appendLiteral(':')
            .appendSecondOfMinute(2).toFormatter();
    private static final PeriodFormatter PERIOD_FMT = new PeriodFormatterBuilder()
            .minimumPrintedDigits(2).printZeroAlways().appendHours().appendLiteral(":")
            .appendMinutes().appendLiteral(":").appendSeconds().toFormatter();

    private final List<Observer> observers = new LinkedList<>();

    private final JPanel panel;
    private final JLabel timeLabel;
    private final JLabel remainingTimeLabel;
    private final JTextField alarmTimeField;
    private final JTextField idlePeriodField;

    public MainView() {
        panel = new JPanel(new GridLayout(0, 2));

        timeLabel = new JLabel();
        panel.add(new JLabel("Current time:"));
        panel.add(timeLabel);

        remainingTimeLabel = new JLabel();
        panel.add(new JLabel("Remaining time:"));
        panel.add(remainingTimeLabel);

        alarmTimeField = new JTextField(8);
        panel.add(new JLabel("Alarm time:"));
        panel.add(alarmTimeField);

        idlePeriodField = new JTextField(8);
        panel.add(new JLabel("Idle period:"));
        panel.add(idlePeriodField);

        final JButton startButton = new JButton("Start");
        startButton.addActionListener(this);
        panel.add(startButton);
    }

    void addObserver(Observer observer) {
        observers.add(observer);
    }

    void setTime(DateTime time) {
        timeLabel.setText(TIME_FMT.print(time));
    }

    void setRemainingTime(Duration remainingTime) {
        remainingTimeLabel.setText(PERIOD_FMT.print(remainingTime.toPeriod()));
    }

    DateTime getAlarmTime() {
        final String alarmTime = alarmTimeField.getText();
        if ("".equals(alarmTime)) {
            return null;
        }

        final MutableDateTime time = TIME_FMT.parseMutableDateTime(alarmTime);
        time.setYear(DateTime.now().getYear());
        time.setMonthOfYear(DateTime.now().getMonthOfYear());
        time.setDayOfMonth(DateTime.now().getDayOfMonth());

        return time.toDateTime();
    }

    Duration getIdlePeriod() {
        final String idlePeriod = idlePeriodField.getText();
        if ("".equals(idlePeriod)) {
            return null;
        }
        return PERIOD_FMT.parsePeriod(idlePeriod).toStandardDuration();
    }

    JPanel getPanel() {
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (final Observer observer : observers) {
            observer.onStart();
        }
    }

    interface Observer {
        void onStart();
    }

}
