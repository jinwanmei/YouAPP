package j.jave.kernal.jave.model;

import java.io.Serializable;
import java.util.List;

public interface JPage<T>  extends Serializable{

	JPageable getPageable();
	
	List<T> getContent();
	
	int getTotalPageNumber();
	
	long getTotalRecordNumber();
	
	public void setContent(List<?> content);
	
}
