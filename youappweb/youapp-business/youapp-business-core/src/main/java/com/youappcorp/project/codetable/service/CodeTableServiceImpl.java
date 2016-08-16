/**
 * 
 */
package com.youappcorp.project.codetable.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.core.service.ServiceSupport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.codetable.model.ParamCode;
import com.youappcorp.project.codetable.model.ParamType;
import com.youappcorp.project.codetable.vo.ParamCriteriaInVO;
import com.youappcorp.project.websupport.model.CodeTableCacheModel;

/**
 * parameter basic service.
 * @author J
 */
@Service(value="paramService.transation.jpa")
public class CodeTableServiceImpl extends ServiceSupport implements CodeTableService{

	@Autowired
	private InternalParamTypeServiceImpl internalParamTypeServiceImpl;
	
	@Autowired
	private InternalParamCodeServiceImpl internalParamCodeServiceImpl;
	
	@Override
	public void saveParam(ServiceContext context, ParamType paramType,
			ParamCode paramCode) throws BusinessException {
		try{
			saveParamType(context, paramType);
			internalParamCodeServiceImpl.saveOnly(context, paramCode);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public void updateParam(ServiceContext context, ParamType paramType,
			ParamCode paramCode) throws BusinessException {
		try{
			internalParamTypeServiceImpl.updateOnly(context, paramType);
			internalParamCodeServiceImpl.updateOnly(context, paramCode);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public List<CodeTableCacheModel> getCodeTableCacheModels(ServiceContext context) {
		String nativeSql=
				"SELECT PT.CODE TYPE, PC.CODE,PC.NAME from PARAM_CODE PC , PARAM_TYPE PT"
				+ " WHERE PC.TYPE = PT.CODE AND PT.DELETED='N' AND PC.DELETED='N'";
		List<CodeTableCacheModel> codes=queryBuilder().nativeQuery().setSql(nativeSql)
				.setResultSetMapping("CodeTableQueryMapping")
		.models();
		
		nativeSql=
				"SELECT PT.CODE TYPE, PT.CODE , PT.NAME from PARAM_TYPE PT WHERE PT.DELETED='N'";
		List<CodeTableCacheModel> types=queryBuilder().nativeQuery().setSql(nativeSql)
				.setResultSetMapping("CodeTableQueryMapping")
		.models();
		codes.addAll(types);
		return codes;
	}

	@Override
	public void updateParamCode(ServiceContext context, ParamCode paramCode) 
			throws BusinessException {
		try{
			long count=internalParamCodeServiceImpl.singleEntityQuery()
					.conditionDefault().notEquals("id", paramCode.getId())
					.equals("type", paramCode.getType())
					.equals("code", paramCode.getCode())
					.ready().count();
					
			if(count>0){
				throw new BusinessException("param code already exists.");
			}
			
			ParamCode dbParamCode=getParamCodeById(context, paramCode.getId());
			dbParamCode.setCode(paramCode.getCode());
			dbParamCode.setName(paramCode.getName());
			dbParamCode.setDescription(paramCode.getDescription());
			
			internalParamCodeServiceImpl.updateOnly(context, dbParamCode);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public void updateParamType(ServiceContext context, ParamType paramType)
			throws BusinessException{
		try{
			
			long count=internalParamTypeServiceImpl.singleEntityQuery()
			.conditionDefault().notEquals("id", paramType.getId())
			.equals("code", paramType.getCode()).ready().count();
			
//			String jpql="select count(1) from ParamType p where p.deleted='N' "
//					+ " and p.id <> :id and p.code =:code";
//			Map<String , Object> params=new HashMap<String, Object>();
//			params.put("id", paramType.getId());
//			params.put("code", paramType.getCode());
//			
//			long count=queryBuilder()
//			.jpqlQuery().setJpql(jpql)
//			.setParams(params)
//			.model();
			if(count>0){
				throw new BusinessException("param type already exists.");
			}
			ParamType dbParamType=getParamTypeById(context, paramType.getId());
			String preCode=dbParamType.getCode();
			String nowCode=paramType.getCode();
			dbParamType.setCode(paramType.getCode());
			dbParamType.setName(paramType.getName());
			dbParamType.setDescription(paramType.getDescription());
			internalParamTypeServiceImpl.updateOnly(context, dbParamType);
			
			//modify param code
			if(!nowCode.equals(preCode)){
				List<ParamCode> paramCodes= getAllParamCodesByType(context, preCode);
				for(ParamCode paramCode:paramCodes){
					paramCode.setType(nowCode);
					internalParamCodeServiceImpl.updateOnly(context, paramCode);
				}
			}
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public boolean existsParamType(ServiceContext context, String code) {
		long count=internalParamTypeServiceImpl.singleEntityQuery()
				.conditionDefault().equals("code", code).ready().count();
		return count>0;
	}

	@Override
	public boolean existsParamCode(ServiceContext context, String type,String code) {
		long count=internalParamCodeServiceImpl.singleEntityQuery()
				.conditionDefault().equals("code", code)
				.equals("type", type).ready().count();
		return count>0;
	}

	@Override
	public void saveParamCode(ServiceContext context, ParamCode paramCode) 
			throws BusinessException{
		try{
			if(existsParamCode(context, paramCode.getType(), paramCode.getCode())){
				throw new BusinessException("param code already exists.");
			}
			internalParamCodeServiceImpl.saveOnly(context, paramCode);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public void saveParamType(ServiceContext context, ParamType paramType) 
			throws BusinessException {
		try{
			if(existsParamType(context, paramType.getCode())){
				throw new BusinessException("param type already exists.");
			}
			internalParamTypeServiceImpl.saveOnly(context, paramType);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public JPage<ParamType> getAllParamTypesByPage(ServiceContext context,
			ParamCriteriaInVO paramCriteria,JSimplePageable simplePageable) {
		return internalParamTypeServiceImpl.
				singleEntityQuery().conditionDefault()
				.likes("code", paramCriteria.getCode())
				.likes("name", paramCriteria.getName())
				.likes("description", paramCriteria.getDescription())
				.ready()
				.order().asc("code").desc("updateTime")
				.ready().modelPage(simplePageable);
	}
	
	@Override
	public List<ParamType> getAllParamTypes(ServiceContext context,
			ParamCriteriaInVO paramCriteria) {
		return internalParamTypeServiceImpl.
				singleEntityQuery().conditionDefault()
				.likes("code", paramCriteria.getCode())
				.likes("name", paramCriteria.getName())
				.likes("description", paramCriteria.getDescription())
				.ready().models();
	}
	
	@Override
	public JPage<ParamCode> getAllParamCodesByPage(ServiceContext context,
			ParamCriteriaInVO paramCriteria,JSimplePageable simplePageable) {
		return internalParamCodeServiceImpl.
				singleEntityQuery().conditionDefault()
				.likes("code", paramCriteria.getCode())
				.likes("name", paramCriteria.getName())
				.likes("description", paramCriteria.getDescription())
				.likes("type", paramCriteria.getType())
				.ready()
				.order().asc("type").asc("code").desc("updateTime")
				.ready().modelPage(simplePageable);
	}
	
	@Override
	public List<ParamCode> getAllParamCodes(ServiceContext context,
			ParamCriteriaInVO paramCriteria) {
		return internalParamCodeServiceImpl.
				singleEntityQuery().conditionDefault()
				.likes("code", paramCriteria.getCode())
				.likes("name", paramCriteria.getName())
				.likes("description", paramCriteria.getDescription())
				.ready().models();
	}
	
	@Override
	public JPage<ParamCode> getAllParamCodesByTypeByPage(ServiceContext context,
			String type,JSimplePageable simplePageable) {
		return internalParamCodeServiceImpl.singleEntityQuery()
				.conditionDefault().equals("type", type)
				.ready().modelPage(simplePageable);
	}
	
	@Override
	public List<ParamCode> getAllParamCodesByType(ServiceContext context,String type) {
		return internalParamCodeServiceImpl.singleEntityQuery()
				.conditionDefault().equals("type", type)
				.ready().models();
	}

	@Override
	public ParamType getParamTypeById(ServiceContext context, String id) {
		return internalParamTypeServiceImpl.getById(context, id);
	}

	@Override
	public ParamCode getParamCodeById(ServiceContext context, String id) {
		return internalParamCodeServiceImpl.getById(context, id);
	}

	@Override
	public void deleteParamTypeById(ServiceContext context, String id) {
		ParamType paramType=getParamTypeById(context, id);
		internalParamTypeServiceImpl.delete(context, paramType);
		List<ParamCode> paramCodes= getAllParamCodesByType(context, paramType.getCode());
		for(ParamCode paramCode:paramCodes){
			internalParamCodeServiceImpl.delete(context, paramCode);
		}
	}

	@Override
	public void deleteParamCodeById(ServiceContext context, String id) {
		internalParamCodeServiceImpl.delete(context, id);
	}
	
}
