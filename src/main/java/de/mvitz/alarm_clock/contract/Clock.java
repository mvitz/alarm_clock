package de.mvitz.alarm_clock.contract;

import org.joda.time.DateTime;

public interface Clock {

    void start();

    void addObserver(ClockObserver observer);

    interface ClockObserver {
        void onTimeSignal(DateTime signal);
    }

}
