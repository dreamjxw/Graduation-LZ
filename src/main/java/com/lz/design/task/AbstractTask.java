package com.lz.design.task;


import com.lz.design.common.ThreadRecycles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * task抽象类
 *
 * @author Xingwu.Jia [xingwuj@tujia.com]
 * @date 2018/1/24 16:01
 */
public abstract class AbstractTask {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractTask.class);


    public static final String HOSTNAME;

    static {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        HOSTNAME = addr.getHostName().toString();
        logger.info("本机名称:{}", HOSTNAME);
    }

    /**
     * 任务名称
     *
     * @return
     */
    public abstract String taskName();

    /**
     * 任务执行逻辑
     */
    protected abstract void run();

    /**
     * 异常监控名称
     *
     * @return
     */
    public abstract String monitorTaskExceptionTag();

    /**
     * 任务入口
     */
    public void doSchedule() {
        long start = System.currentTimeMillis();
        try {
            ThreadRecycles.init();
            if (logger.isInfoEnabled()) {
                logger.info("{}[{}]开始执行...", this.taskName(), getClass().getCanonicalName());
            }
            this.run();
            if (logger.isInfoEnabled()) {
                logger.info("{}[{}]执行完毕,耗时[{}]ms", this.taskName()
                        , getClass().getCanonicalName(), (System.currentTimeMillis() - start));
            }
        } catch (Exception e) {
            logger.error("定时任务[{}:{}]运行出错", this.taskName(), getClass().getCanonicalName(), e);
        } finally {
            ThreadRecycles.release();
        }
    }
}

