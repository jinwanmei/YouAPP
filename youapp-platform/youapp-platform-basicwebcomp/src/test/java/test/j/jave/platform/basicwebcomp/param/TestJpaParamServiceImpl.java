package test.j.jave.platform.basicwebcomp.param;

import j.jave.kernal.jave.random.JSimpleObjectPopulate;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.login.model.User;
import j.jave.platform.basicwebcomp.param.model.ParamCode;
import j.jave.platform.basicwebcomp.param.model.ParamType;
import j.jave.platform.basicwebcomp.param.service.ParamService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="test.paramService.transation.jpa")
public class TestJpaParamServiceImpl{
	
	@Autowired
	private ParamService paramService;
	
	public TestJpaParamServiceImpl(){
		System.out.println("TestParamServiceImpl");
	}
	
	@Test
	public void testJpa(){
		try{
			ServiceContext context=new ServiceContext();
			User user=new User();
			user.setId("SYSTEM-TEST");
			context.setUser(user);
			
			ParamType paramType=new ParamType();
			new JSimpleObjectPopulate().populate(paramType);
			paramType.setId(JUniqueUtils.unique().replaceAll("-", ""));
			
			ParamCode paramCode=new ParamCode();
			new JSimpleObjectPopulate().populate(paramCode);
			paramCode.setId(JUniqueUtils.unique().replaceAll("-", ""));
			paramService.saveParam(context, paramType,paramCode);
			
			paramCode.setDescription("JIA.ZHONG.JIN");
			paramService.updateParam(context, paramType,paramCode);
			
			
			System.out.println("count : ");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
