package j.jave.web.htmlclient.plugins.jstree;

import java.util.List;

import me.bunny.kernel._c.model.JModel;

public class JSTreeData implements JModel {

	/**
	 * will be autogenerated if omitted
	 */
	private String id;
	
	/**
	 *  node text
	 */
	private String text;
	
	/**
	 * string for custom
	 */
	private String icon;
	
	/**
	 * is the node open
	 */
	private boolean opened;
	
	/**
	 * is the node disabled
	 */
	private boolean disabled;
	
	/**
	 * is the node selected
	 */
	private boolean selected;

	/**
	 * // array of strings or objects
	 */
	private List<JSTreeData> children;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(boolean opened) {
		this.opened = opened;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public List<JSTreeData> getChildren() {
		return children;
	}

	public void setChildren(List<JSTreeData> children) {
		this.children = children;
	}
	
}
