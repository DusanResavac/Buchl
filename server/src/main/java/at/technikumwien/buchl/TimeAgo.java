package at.technikumwien.buchl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * https://stackoverflow.com/questions/3859288/how-to-calculate-time-ago-in-java#answer-23215152
 */
public class TimeAgo {
    public static final List<Long> times = Arrays.asList(
            TimeUnit.DAYS.toMillis(365),
            TimeUnit.DAYS.toMillis(30),
            TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1) );
    public static final List<String> timesString = Arrays.asList("Jahr","Monat","Tag","Stunde","Minute","Sekunde");
    public static final List<String> timesPluralString = Arrays.asList("Jahren","Monaten","Tagen","Stunden","Minuten","Sekunden");

    public static String toDuration(long duration, boolean minDays) {

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < TimeAgo.times.size(); i++) {
            Long current = TimeAgo.times.get(i);
            long temp = duration/current;
            if (temp > 0) {
                res.append("vor ").append(temp).append(" ").append(temp == 1 ? TimeAgo.timesString.get(i) : TimeAgo.timesPluralString.get(i));
                break;
            }
        }
        if ("".equals(res.toString())) {
            return "Heute";
        } else {
            return res.toString();
        }
    }
}
