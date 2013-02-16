package de.mvitz.alarm_clock.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import de.mvitz.alarm_clock.contract.AlarmClock;

public final class DefaultAlarmClock implements AlarmClock {

    private final List<Observer> observers = new LinkedList<>();

    private boolean running = false;
    private DateTime time;

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void start(DateTime theAlarmTime, Duration duration) {
        System.out.println("start(" + theAlarmTime + "," + duration + ")");
        final DateTime alarmTime = calculateAlarmTime(theAlarmTime, duration);
        running = true;
        for (final Observer observer : observers) {
            observer.onStart();
        }
        while (running) {
            final Duration remainingTime = calculateRemainingTime(alarmTime);
            if (remainingTime.isShorterThan(new Duration(0))) {
                running = false;
            }
            for (final Observer observer : observers) {
                observer.onRemainingTime(remainingTime);
                if (!running) {
                    observer.onExpired();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (final InterruptedException ex) {
            }
        }
    }

    private DateTime calculateAlarmTime(DateTime alarmTime, Duration duration) {
        if (alarmTime != null) {
            return alarmTime;
        } else {
            return DateTime.now().plus(duration);
        }
    }

    private Duration calculateRemainingTime(DateTime alarmTime) {
        return new Duration(time, alarmTime);
    }

    @Override
    public void stop() {
        running = false;
        for (final Observer observer : observers) {
            observer.onStopped();
        }
    }

    @Override
    public void time(DateTime time) {
        this.time = time;
    }

}
