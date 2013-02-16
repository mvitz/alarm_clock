package de.mvitz.alarm_clock.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

public final class MainView {

    private static final DateTimeFormatter TIME_FMT = new DateTimeFormatterBuilder()
            .appendHourOfDay(2).appendLiteral(':').appendMinuteOfHour(2).appendLiteral(':')
            .appendSecondOfMinute(2).toFormatter();

    private final JPanel panel;
    private final JLabel timeLabel;

    public MainView() {
        panel = new JPanel();
        timeLabel = new JLabel();

        panel.add(new JLabel("Current time:"));
        panel.add(timeLabel);
    }

    void setTime(DateTime time) {
        timeLabel.setText(TIME_FMT.print(time));
    }

    JPanel getPanel() {
        return panel;
    }

}
