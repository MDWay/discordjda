package de.romjaki.discord.jda;

import java.util.function.Supplier;

/**
 * Created by RGR on 22.06.2017.
 */
public class Cache<T> {
    private static final long DEFAULT_CACHE_LENGTH = 5 * 60 * 60l;
    T value;
    long cachestamp;
    private long maxCacheAge;
    private Supplier<T> supplier;

    public Cache(Supplier<T> o) {
        this(o, DEFAULT_CACHE_LENGTH);
    }

    public Cache(Supplier<T> o, long maxCacheAge) {
        supplier = o;
        this.cachestamp = System.currentTimeMillis();
        this.value = null;

        this.maxCacheAge = maxCacheAge;
    }

    public T get() {
        return get(false);
    }

    public T get(boolean forceRefresh) {
        if (value == null || cachestamp + maxCacheAge >= System.currentTimeMillis() || forceRefresh) {
            value = supplier.get();
            cachestamp = System.currentTimeMillis();
        }
        return value;
    }

    public long getTimeSinceLastRefresh() {
        return System.currentTimeMillis() - cachestamp;
    }

    public long getLastRefesh() {
        return cachestamp;
    }

    public long getCacheTimeout() {
        return maxCacheAge;
    }

    public long setCacheTimeout(long maxCacheAge) {
        long old = this.maxCacheAge;
        this.maxCacheAge = maxCacheAge;
        return old;
    }

    public Supplier<T> getSupplier() {
        return supplier;
    }

    public Supplier<T> setSupplier(Supplier<T> supplier) {
        Supplier<T> old = this.supplier;
        this.supplier = supplier;
        return old;
    }


}
