
package sky.tool.sha256;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class Mainer
{
	public static void main(String[] args)
	{
		ExecutorService threadPool = Executors.newFixedThreadPool(Integer.valueOf(args[1]),new SkyThreadFactory());
		Util util = new Util(args[0]);
		for (int i = 0; i < Integer.valueOf(args[1]); i++)
		{
			threadPool.execute(new Worker(util));
		}
		threadPool.shutdown();
		// 每秒中查看一次是否已经扫描结束
		while (true)
		{
			if (threadPool.isTerminated())
			{
				System.out.println("扫描结束");
				return;
			}
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	private static class SkyThreadFactory implements ThreadFactory
	{
		private static final AtomicInteger poolNumber = new AtomicInteger(1);
		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;

		SkyThreadFactory()
		{
			SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			namePrefix = "sky-" + poolNumber.getAndIncrement();
		}

		public Thread newThread(Runnable r)
		{
			Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
			if (t.isDaemon())
				t.setDaemon(false);
			if (t.getPriority() != Thread.NORM_PRIORITY)
				t.setPriority(Thread.NORM_PRIORITY);
			return t;
		}
	}
}
