/**
 * 
 */
package j.jave.platform.basicwebcomp.param.mapper;

import j.jave.kernal.jave.model.JPagination;
import j.jave.kernal.jave.model.support.JModelMapper;
import j.jave.platform.basicwebcomp.param.model.Param;
import j.jave.platform.basicwebcomp.param.repo.ParamRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author J
 */
@Component(value="ParamMapper")
@JModelMapper(component="ParamMapper",name=Param.class)
public interface ParamMapper extends JMapper<Param> ,ParamRepo<JMapper<Param>>{
	
	public List<Param> getParamsByPage(JPagination pagination) ;
	
	public Param getParamByFunctionIdAndCode(@org.apache.ibatis.annotations.Param(value="functionId")String functionId,
			@org.apache.ibatis.annotations.Param(value="code")String code);
	
	public List<Param> getParamByFunctionId(@org.apache.ibatis.annotations.Param(value="functionId")String functionId);
	
	
}
