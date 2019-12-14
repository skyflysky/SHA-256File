package sky.tool.sha256;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mainer
{
	public static void main(String[] args)
	{
		ExecutorService threadPool = Executors.newCachedThreadPool();
		Util util = new Util(args[0]);
		for(int i = 0 ; i < Integer.valueOf(args[1]) ; i ++)
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
}
