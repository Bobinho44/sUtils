package fr.bobinho.sutils.utils.scheduler;

import fr.bobinho.sutils.sUtilsCore;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class sUtilsScheduler {

    /**
     * Scheduler types
     */
    public enum Type {
        SYNC,
        ASYNC
    }

    /**
     * Fields
     */
    private final Type type;
    private int delay;
    private TimeUnit delayType;
    private int repeatingDelay;
    private TimeUnit repeatingDelayType;
    private Runnable cachedRunnable;
    private int bukkitTaskId = -1;
    private boolean shouldWait = false;

    /**
     * Creates scheduler
     *
     * @param type the type
     */
    public sUtilsScheduler(@Nonnull Type type) {
        Validate.notNull(type, "type is null");

        //Root construction
        this.type = type;
    }

    /**
     * Creates sync scheduler builder.
     *
     * @return the sync scheduler builder.
     */
    @Nonnull
    public static sUtilsScheduler syncScheduler() {
        return new sUtilsScheduler(sUtilsScheduler.Type.SYNC);
    }

    /**
     * Creates async scheduler builder
     *
     * @return the async scheduler builder
     */
    @Nonnull
    public static sUtilsScheduler asyncScheduler() {
        return new sUtilsScheduler(sUtilsScheduler.Type.ASYNC);
    }

    /**
     * Runs scheduler after declared time
     *
     * @param delay the scheduler after delay
     * @return the scheduler builder
     */
    @Nonnull
    public sUtilsScheduler after(int delay) {
        this.delay = delay * 50;
        this.delayType = TimeUnit.MILLISECONDS;
        return this;
    }

    /**
     * Runs scheduler after declared time
     *
     * @param delay     the scheduler after delay
     * @param delayType the scheduler after delay type
     * @return the scheduler builder
     */
    @Nonnull
    public sUtilsScheduler after(int delay, @Nonnull TimeUnit delayType) {
        Validate.notNull(delayType, "delayType is null");

        this.delay = delay;
        this.delayType = delayType;
        return this;
    }

    /**
     * Runs scheduler every declared time
     *
     * @param repeatingDelay the scheduler repeating time
     * @return the scheduler builder
     */
    @Nonnull
    public sUtilsScheduler every(int repeatingDelay) {
        this.repeatingDelay = repeatingDelay * 50;
        this.repeatingDelayType = TimeUnit.MILLISECONDS;
        return this;
    }

    /**
     * Runs scheduler every declared time
     *
     * @param repeatingDelay     the scheduler repeating time
     * @param repeatingDelayType the scheduler repeating time type
     * @return the scheduler builder
     */
    @Nonnull
    public sUtilsScheduler every(int repeatingDelay, @Nonnull TimeUnit repeatingDelayType) {
        Validate.notNull(repeatingDelayType, "repeatingDelayType is null");

        this.repeatingDelay = repeatingDelay;
        this.repeatingDelayType = repeatingDelayType;
        return this;
    }

    /**
     * Gets cached runnable
     *
     * @return the runnable
     */
    public Runnable getCachedRunnable() {
        return cachedRunnable;
    }

    /**
     * Sets cached runnable
     *
     * @param cachedRunnable the runnable
     * @return the scheduler builder
     */
    @Nonnull
    public sUtilsScheduler setCachedRunnable(@Nonnull Runnable cachedRunnable) {
        Validate.notNull(cachedRunnable, "cachedRunnable is null!");

        this.cachedRunnable = cachedRunnable;
        return this;
    }

    /**
     * Waits task to complete
     *
     * @return the scheduler builder
     */
    @Nonnull
    public sUtilsScheduler block() {
        shouldWait = true;
        return this;
    }

    /**
     * Runs cached scheduler
     *
     * @return the bukkit task id
     */
    public int runCached() {
        return run(cachedRunnable);
    }

    /**
     * If there is an ongoing task, it will stop it
     */
    public void stop() {
        if (bukkitTaskId == -1) {
            return;
        }
        Bukkit.getScheduler().cancelTask(bukkitTaskId);
    }

    /**
     * Runs configured scheduler
     *
     * @param runnable the runnable
     * @return the bukkit task id.
     */
    public synchronized int run(@Nonnull Runnable runnable) throws IllegalArgumentException {
        Validate.notNull(runnable, "runnable is null");

        long delay = delayType == null ? 0 : Math.max(delayType.toMillis(this.delay) / 50, 0);
        long repeating_delay = repeatingDelayType == null ? 0 : Math.max(repeatingDelayType.toMillis(repeatingDelay) / 50, 0);

        if (type == Type.SYNC) {
            if (repeating_delay != 0)
                bukkitTaskId = Bukkit.getScheduler().runTaskTimer(sUtilsCore.getInstance(), runnable, delay, repeating_delay).getTaskId();
            else if (delay != 0)
                bukkitTaskId = Bukkit.getScheduler().runTaskLater(sUtilsCore.getInstance(), runnable, delay).getTaskId();
            else
                bukkitTaskId = Bukkit.getScheduler().runTask(sUtilsCore.getInstance(), runnable).getTaskId();
        } else {
            if (repeating_delay != 0)
                bukkitTaskId = Bukkit.getScheduler().runTaskTimerAsynchronously(sUtilsCore.getInstance(), runnable, delay, repeating_delay).getTaskId();
            else if (delay != 0)
                bukkitTaskId = Bukkit.getScheduler().runTaskLaterAsynchronously(sUtilsCore.getInstance(), runnable, delay).getTaskId();
            else
                bukkitTaskId = Bukkit.getScheduler().runTaskAsynchronously(sUtilsCore.getInstance(), runnable).getTaskId();
        }

        //Waits task to complete
        if (shouldWait) {
            while (true) {
                if (Bukkit.getScheduler().isCurrentlyRunning(bukkitTaskId) || Bukkit.getScheduler().isQueued(bukkitTaskId))
                    continue;
                break;
            }
        }

        return bukkitTaskId;
    }

    /**
     * Runs configured scheduler
     *
     * @param task the bukkit task
     */
    public synchronized void run(@Nonnull Consumer<BukkitTask> task) throws IllegalArgumentException {
        Validate.notNull(task, "task is null");

        long delay = delayType == null ? 0 : Math.max(delayType.toMillis(this.delay) / 50, 0);
        long repeating_delay = repeatingDelayType == null ? 0 : Math.max(repeatingDelayType.toMillis(this.repeatingDelay) / 50, 0);

        if (type == Type.SYNC) {
            if (repeating_delay != 0)
                Bukkit.getScheduler().runTaskTimer(sUtilsCore.getInstance(), task, delay, repeating_delay);
            else if (delay != 0)
                Bukkit.getScheduler().runTaskLater(sUtilsCore.getInstance(), task, delay);
            else
                Bukkit.getScheduler().runTask(sUtilsCore.getInstance(), task);
        } else {
            if (repeating_delay != 0)
                Bukkit.getScheduler().runTaskTimerAsynchronously(sUtilsCore.getInstance(), task, delay, repeating_delay);
            else if (delay != 0)
                Bukkit.getScheduler().runTaskLaterAsynchronously(sUtilsCore.getInstance(), task, delay);
            else
                Bukkit.getScheduler().runTaskAsynchronously(sUtilsCore.getInstance(), task);
        }
    }

}
