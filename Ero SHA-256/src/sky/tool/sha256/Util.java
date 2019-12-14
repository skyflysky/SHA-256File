package sky.tool.sha256;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class Util
{
	private Iterator<File> iter;
	
	public Util(String path)
	{
		super();
		this.iter = getFiles(new File(path)).iterator();
	}

	public Collection<File> getFiles(File file)
	{
		Collection<File> files = new LinkedHashSet<File>();
		if(file.isDirectory() && file.list() != null)//当前文件是个储存路径且可以获取到它的子文件名数组
		{
			if(file.list().length > 0)
			{
				for(String name : file.list())
				{
					if(!name.startsWith("$"))//$开头的是系统文件或者路径 不考虑
					{
						Collection<File> childFiles = getFiles(new File(file, name));//递归取文件
						if(childFiles != null)//本方法有可能返回null 
							files.addAll(childFiles);
					}
				}
			}
			else
				return null;//返回空代表不需要考虑
		}
		else if(file.isFile())//当前文件是个具体文件
		{
			files.add(file);//
		}
		if(files.size() > 0)
			return files;
		else
			return null;
	}
	
	public synchronized File getNextFile()
	{
		if(this.iter.hasNext())
			return this.iter.next();
		return null;
	}
}
