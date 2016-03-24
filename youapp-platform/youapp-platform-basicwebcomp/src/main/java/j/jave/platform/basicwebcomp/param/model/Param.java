/**
 * 
 */
package j.jave.platform.basicwebcomp.param.model;

import j.jave.kernal.jave.model.support.JColumn;
import j.jave.kernal.jave.model.support.JSQLType;
import j.jave.kernal.jave.model.support.JTable;
import j.jave.platform.basicwebcomp.spirngjpa.JJpaBaseModel;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

/**
 * @author Administrator
 *
 */


@SqlResultSetMapping(name = "ParamQueryMapping",
		classes={@ConstructorResult(
				targetClass=ParamQuery.class,
				columns={
					@ColumnResult(name="NAME",type=String.class),
					@ColumnResult(name="CODE",type=String.class),
				}
				)}
)
@JTable(name="PARAM")
@Entity
@Table(name="PARAM")
public class Param extends JJpaBaseModel {
	
	public Param() {
		// TODO Auto-generated constructor stub
	}
	
	public Param(String name,String code) {
		this.name=name;
		this.code=code;
	}
	
	/**
	 * the id the type of funciton. 
	 */
	@JColumn(name="FUNCTIONID",type=JSQLType.VARCHAR,length=32)
	private String functionId;
	
	/**
	 * the code 
	 */
	@JColumn(name="CODE",type=JSQLType.VARCHAR,length=32)
	private String code;
	
	/**
	 * the nature name 
	 */
	@JColumn(name="NAME",type=JSQLType.VARCHAR,length=128)
	private String name;
	
	/**
	 * optional description 
	 */
	@JColumn(name="DESCRIPTION",type=JSQLType.VARCHAR,length=512)
	private String description;

	@Column(name="FUNCTIONID")
	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	@Column(name="CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name="NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
