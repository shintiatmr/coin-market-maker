package com.ajaib.coin.market.maker.mutex;

import java.time.Duration;
import java.util.concurrent.Callable;

public interface MutexUtil {

  <V> V run(String key, Duration duration, Callable<V> callable);

  <V> void runIfAvailable(String key, Callable<V> callable);

}
