package com.ajaib.coin.market.maker.mutex.impl;

import com.ajaib.coin.market.maker.exception.ClientException;
import com.ajaib.coin.market.maker.exception.ServerException;
import com.ajaib.coin.market.maker.mutex.MutexUtil;
import com.ajaib.coin.market.maker.type.ErrorType;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MutexUtilImpl implements MutexUtil {

  private final RedisLockRegistry redisLockRegistry;

  @Override
  public <V> V run(String key, Duration duration, Callable<V> callable) {
    Lock lock = getLock(key);

    try {
      if (!lock.tryLock(duration.toMillis(), TimeUnit.MILLISECONDS)) {
        throw new ServerException(ErrorType.COIN_MARKET_MAKER_MUTEX_ERROR);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ServerException(ErrorType.COIN_MARKET_MAKER_MUTEX_ERROR);
    }

    try {
      return callable.call();
    } catch (ClientException | ServerException e) {
      log.error(e.getMessage(), e);
      throw new ServerException(e.getErrorType());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ServerException(ErrorType.INTERNAL_SERVER_ERROR);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public <V> void runIfAvailable(String key, Callable<V> callable) {
    Lock lock = getLock(key);

    if (!lock.tryLock()) {
      return;
    }

    try {
      callable.call();
    } catch (ClientException | ServerException e) {
      log.error(e.getMessage(), e);
      throw new ServerException(e.getErrorType());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new ServerException(ErrorType.INTERNAL_SERVER_ERROR);
    } finally {
      lock.unlock();
    }
  }

  private Lock getLock(String key) {
    return this.redisLockRegistry.obtain(key);
  }
}
