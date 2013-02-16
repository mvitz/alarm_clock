package de.mvitz.alarm_clock.app;

import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import de.mvitz.alarm_clock.contract.AlarmBell;
import de.mvitz.alarm_clock.contract.AlarmClock;
import de.mvitz.alarm_clock.contract.Clock;
import de.mvitz.alarm_clock.contract.UI;
import de.mvitz.alarm_clock.logic.DefaultAlarmClock;
import de.mvitz.alarm_clock.logic.SystemTimeClock;
import de.mvitz.alarm_clock.ui.MainWindow;
import de.mvitz.alarm_clock.ui.PopupAlarmBell;

public final class AlarmClockApplication implements Clock.ClockObserver, UI.Observer,
        AlarmClock.Observer {

    private final UI ui;
    private final Clock clock;
    private final AlarmClock alarmClock;
    private final AlarmBell alarmBell;

    private AlarmClockApplication() {
        // build
        ui = new MainWindow();
        clock = new SystemTimeClock();
        alarmClock = new DefaultAlarmClock();
        alarmBell = new PopupAlarmBell();

        // bind
        ui.addObserver(this);
        clock.addObserver(this);
        alarmClock.addObserver(this);
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
        //System.out.println("onTimeSignal(" + signal + ")");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                alarmClock.time(signal);
                ui.time(signal);
            }
        });
    }

    @Override
    public void onStart() {
        System.out.println("onStart()");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ui.start();
            }
        });
    }

    @Override
    public void onAlarmSet(final DateTime time, final Duration duration) {
        System.out.println("onAlarmSet(" + time + "," + duration + ")");
        new Thread(new Runnable() {
            @Override
            public void run() {
                alarmClock.start(time, duration);
            }
        }).start();
    }

    @Override
    public void onRemainingTime(final Duration remainingTime) {
        //System.out.println("onRemainingTime(" + remainingTime + ")");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ui.remainingTime(remainingTime);
            }
        });
    }

    @Override
    public void onExpired() {
        System.out.println("onExpired()");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ui.expire();
                alarmBell.ring();
            }
        });
    }

    @Override
    public void onStopped() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ui.stop();
            }
        });
    }

    @Override
    public void onStopRequested() {
        alarmClock.stop();
    }

}
