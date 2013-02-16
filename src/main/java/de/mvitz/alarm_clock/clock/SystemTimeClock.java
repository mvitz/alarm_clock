package de.mvitz.alarm_clock.clock;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

import de.mvitz.alarm_clock.contract.Clock;

public final class SystemTimeClock implements Clock {

    private final List<ClockObserver> observers = new LinkedList<>();

    @Override
    public void addObserver(ClockObserver observer) {
        observers.add(observer);
    }

    @Override
    public void start() {
        while (true) {
            fireTimeChanged(DateTime.now());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (final InterruptedException ex) {
            }
        }
    }

    private void fireTimeChanged(DateTime time) {
        for (final ClockObserver observer : observers) {
            observer.onTimeSignal(time);
        }
    }

}
