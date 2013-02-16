package de.mvitz.alarm_clock.app;

import javax.swing.SwingUtilities;

import org.joda.time.DateTime;

import de.mvitz.alarm_clock.clock.SystemTimeClock;
import de.mvitz.alarm_clock.contract.Clock;
import de.mvitz.alarm_clock.contract.UI;
import de.mvitz.alarm_clock.ui.MainWindow;

public final class AlarmClockApplication implements Clock.ClockObserver {

    private final UI ui;
    private final Clock clock;

    private AlarmClockApplication() {
        // build
        ui = new MainWindow();
        clock = new SystemTimeClock();

        // bind
        clock.addObserver(this);
    }

    private void onStartup() {
        // run
        new Thread(new Runnable() {
            @Override
            public void run() {
                clock.start();
            }
        }).start();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ui.open();
            }
        });
    }

    public static void main(String[] args) {
        new AlarmClockApplication().onStartup();
    }

    @Override
    public void onTimeSignal(final DateTime signal) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ui.time(signal);
            }
        });
    }

}
