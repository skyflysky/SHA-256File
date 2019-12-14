package sky.tool.sha256;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Worker implements Runnable
{
	private MessageDigest md;
	
	private Util util;
	
	private static final char[] DIGITS_UPPER ={'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	
	public Worker(Util util)
	{
		super();
		try
		{
			this.md = MessageDigest.getInstance("SHA-256");
			this.util = util;
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		md.reset();
		File f = util.getNextFile();
		while(f != null)
		{
			try
			{
				FileInputStream fis = new FileInputStream(f);
				byte[] buffer = new byte[8192];
				int length;
				while((length = fis.read(buffer)) != -1)
				{
					md.update(buffer, 0, length);
				}
				fis.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			System.out.println(new String(toHex(md.digest())));
			md.reset();
			f = util.getNextFile();
		}
		System.out.println("Ïß³ÌÍê±Ï");
		return;
	}

	private char[] toHex(byte[] data)
	{
		final int l = data.length;
		final char[] out = new char[l << 1];
		for (int i = 0, j = 0; i < l; i++)
		{
			out[j++] = DIGITS_UPPER[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS_UPPER[0x0F & data[i]];
		}
		return out;
	}
}
