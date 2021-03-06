/**
 * 
 */
package j.jave.platform.webcomp.web.youappmvc.plugins.pageable.jquerydatatable;

import j.jave.kernal.jave.model.JModel;

public class DataTable implements JModel{
	
	private int draw;
	
	private Object data;
	
	private int recordsTotal;
	
	private int recordsFiltered;

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
}
