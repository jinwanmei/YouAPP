package test.com.youappcorp.project.tablemanager;

import j.jave.platform.webcomp.core.service.DefaultServiceContext;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.youappcorp.project.codetable.model.ParamCode;
import com.youappcorp.project.tablemanager.model.Record;
import com.youappcorp.project.tablemanager.model.Table;
import com.youappcorp.project.tablemanager.model.TableSearch;
import com.youappcorp.project.tablemanager.service.TableManagerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml"})
public class TestTableManager implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}
	@Test
	public void testTableMag(){
//		tableManagerServiceImpl
		TableManagerService tableManagerService=applicationContext.getBean("tableManagerServiceImpl", TableManagerService.class);
	
		List<Table> tables= tableManagerService.getTables();
	
		System.out.println(tables.size());
		
		ServiceContext context=DefaultServiceContext.getDefaultServiceContext();
		TableSearch tableSearch=new TableSearch();
		tableSearch.setModelName(ParamCode.class.getName());
		List<Record> records=tableManagerService.getRecords( tableSearch);
		System.out.println(records.size());
		
	}
	
	
}
