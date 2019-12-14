
package sky.tool.sha256;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class Mainer
{
	public static void main(String[] args)
	{
		ExecutorService threadPool = Executors.newFixedThreadPool(Integer.valueOf(10),new SkyThreadFactory());
		Util util = new Util("I:\\game");
		for (int i = 0; i < Integer.valueOf(10); i++)
		{
			threadPool.execute(new Worker(util));
		}
		threadPool.shutdown();
		// ÿ���в鿴һ���Ƿ��Ѿ�ɨ�����
		while (true)
		{
			if (threadPool.isTerminated())
			{
				System.out.println("ɨ�����");
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
