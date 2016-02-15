/**
 * 
 */
package j.jave.platform.basicwebcomp.login.mapper;

import j.jave.kernal.jave.model.support.JModelMapper;
import j.jave.platform.basicwebcomp.login.model.UserTracker;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 */
@Component(value="UserTrackerMapper")
@JModelMapper(component="UserTrackerMapper",name=UserTracker.class)
public interface UserTrackerMapper extends JMapper<UserTracker> {

	
	public List<UserTracker> getUserTrackerByName(
			@Param(value="userName")String userName);
	
	
}