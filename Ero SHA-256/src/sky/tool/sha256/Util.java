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
		if(file.isDirectory() && file.list() != null)//��ǰ�ļ��Ǹ�����·���ҿ��Ի�ȡ���������ļ�������
		{
			if(file.list().length > 0)
			{
				for(String name : file.list())
				{
					if(!name.startsWith("$"))//$��ͷ����ϵͳ�ļ�����·�� ������
					{
						Collection<File> childFiles = getFiles(new File(file, name));//�ݹ�ȡ�ļ�
						if(childFiles != null)//�������п��ܷ���null 
							files.addAll(childFiles);
					}
				}
			}
			else
				return null;//���ؿմ�����Ҫ����
		}
		else if(file.isFile())//��ǰ�ļ��Ǹ������ļ�
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
