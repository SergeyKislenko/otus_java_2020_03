package ru.otus;

import com.sun.management.GarbageCollectionNotificationInfo;
import ru.otus.bench.TestObj;


import javax.management.*;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static long wTime;
    private static ArrayList<GarbageCollectionNotificationInfo> allInfoGc = new ArrayList<>();

    public static void main(String... args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=TestObj");

        TestObj mbean = new TestObj();
        mbs.registerMBean(mbean, name);
        long startTime = System.currentTimeMillis();

        try {
            mbean.run();
        } catch (OutOfMemoryError e) {
            wTime = (System.currentTimeMillis() - startTime) / 1000;
            System.out.println("\nJava heap space overload. Worked time: " + wTime + " sec");
        }
        printReport(mbean);
    }

    private static void printReport(TestObj mbean) {
        System.out.println("==============================Report==============================");
        LongSummaryStatistics allGsDurations = allInfoGc.stream().collect(Collectors.summarizingLong(value -> value.getGcInfo().getDuration()));
        Map<Long, LongSummaryStatistics> gcPerMinute = allInfoGc.stream().collect(Collectors.groupingBy(
                gc -> gc.getGcInfo().getStartTime() / 1000 / 60,
                Collectors.summarizingLong(value -> value.getGcInfo().getDuration())));
        Map<String, Long> countsGc = allInfoGc.stream().collect(Collectors.groupingBy(e -> e.getGcName(), Collectors.counting()));

        System.out.println("All GC worked: " + allGsDurations.getSum() + " ms");
        countsGc.forEach((name, count)-> System.out.println(name + ": " + count + " times" ));
        gcPerMinute.forEach((minute, duration)-> System.out.println((minute + 1) + " minute GC worked --> " + duration.getSum() + " ms"));
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    allInfoGc.add(info);
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
