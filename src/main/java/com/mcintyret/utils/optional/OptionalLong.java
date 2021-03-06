package com.mcintyret.utils.optional;

/**
 * User: mcintyret2
 * Date: 22/08/2013
 */
public abstract class OptionalLong implements Comparable<OptionalLong> {
    
    public static OptionalLong absent() {
        return Absent.INSTANCE;
    }
    
    public static OptionalLong of(long l) {
        return new Present(l);
    }
    
    public abstract boolean isPresent();
    
    public abstract long get();
    
    public abstract OptionalLong or(OptionalLong secondChoice);
    
    public abstract long or(long other);
    
    public abstract long orZero();

    @Override
    public int compareTo(OptionalLong o) {
        if (isPresent()) {
            return o.isPresent() ? Long.compare(get(), o.get()) : -1;
        } else {
            return o.isPresent() ? 1 : 0;
        }
    }
    
    private static class Present extends OptionalLong {

        private final long l;

        private Present(long l) {
            this.l = l;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public long get() {
            return l;
        }

        @Override
        public OptionalLong or(OptionalLong secondChoice) {
            return this;
        }

        @Override
        public long or(long other) {
            return l;
        }

        @Override
        public long orZero() {
            return l;
        }
    }
    
    private static class Absent extends OptionalLong {
        private static final OptionalLong INSTANCE = new Absent();

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public long get() {
            throw new IllegalStateException("OptionalLong.get() cannot be called on an absent value");
        }

        @Override
        public OptionalLong or(OptionalLong secondChoice) {
            return secondChoice;
        }

        @Override
        public long or(long other) {
            return other;
        }

        @Override
        public long orZero() {
            return 0L;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        // if other != this, it must be present
        return other instanceof OptionalLong && get() == ((OptionalLong) other).get();
    }

    @Override
    public int hashCode() {
        return isPresent() ? hashCode(get()) : 0;
    }

    private static int hashCode(long l) {
        return (int)(l ^ (l >>> 32));
    }
}