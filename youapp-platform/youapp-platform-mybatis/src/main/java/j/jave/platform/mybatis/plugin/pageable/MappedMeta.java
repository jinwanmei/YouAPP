package j.jave.platform.mybatis.plugin.pageable;

import j.jave.kernal.jave.model.JModel;

public class MappedMeta implements JModel {

	private boolean pageable;
	
	private String mappedId;

	public boolean isPageable() {
		return pageable;
	}

	public void setPageable(boolean pageable) {
		this.pageable = pageable;
	}

	public String getMappedId() {
		return mappedId;
	}

	public void setMappedId(String mappedId) {
		this.mappedId = mappedId;
	}
}
