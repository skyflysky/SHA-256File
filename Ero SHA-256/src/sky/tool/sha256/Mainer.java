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
		
	}
}
