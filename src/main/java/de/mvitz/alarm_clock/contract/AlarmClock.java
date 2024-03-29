package de.mvitz.alarm_clock.contract;

import org.joda.time.DateTime;
import org.joda.time.Duration;

public interface AlarmClock {

    void start(DateTime time, Duration duration);

    void time(DateTime time);

    void stop();

    void addObserver(Observer observer);

    interface Observer {
        void onStart();
        void onRemainingTime(Duration remainingTime);
        void onExpired();
        void onStopped();
    }

}
