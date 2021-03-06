<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="youapp-content">
		<!-- Content Header (Page header) -->
	        <section class="content-header">
	          <h1>
	            账单信息
	            <small>
	            <a href="javascript:void(0)" 
	                onclick='GET("/bill.billaction/toViewAllBill")'
	                ><i class=" fa ion-ios-undo"></i></a>
	            </small>
	          </h1>
	          <ol class="breadcrumb">
	            <li>
	            <a 
	            	href="javascript:void(0)" 
	                 onclick='GET("/bill.billaction/toNavigate")'
	                ><i class=" fa ion-android-laptop"></i> 应用</a>
	            </li>
	            <li >
	            <a href="javascript:void(0)" 
	                onclick='GET("/bill.billaction/toViewAllBill")'
	                >历史账单</a>
	            </li>
	            <li class="active">View</li>
	          </ol>
	        </section>
	        
	        
	         <!-- Main content -->
	        <section class="content">
	        <div class="col-md-6">
		        <div class="box box-solid">
	                <div class="box-header with-border">
	                <a   href="javascript:void(0)"  class="youapp-edit" 
	                	onclick='GET("/bill.billaction/toEditBill","id=${bill.id}")'
	                >
						<i class="fa fa-edit"></i>
					</a>
	                  <h3 class="box-title">账单信息</h3>
	                  
	                </div><!-- /.box-header -->
	                <div class="box-body">
	                  <dl>
	                    <dt>商品名称</dt>
	                    <dd>${bill.goodName }</dd>
	                    <dt>金额</dt>
	                    <dd>${bill.money} </dd>
	                    <dt>描述</dt>
	                    <dd>${bill.userCodeName}  ${bill.billTime } 在 
	                    <c:if test="${bill.mallCode=='OTHERS' }">
	                      		${bill.mallName}
	                      	</c:if>
	                      <c:if test="${bill.mallCode!='OTHERS' }">
	                      		${bill.mallCodeName}
	                      	</c:if>
	                    购买的
	                    </dd>
	                    <dt>描述</dt>
	                    <dd>${bill.description} </dd>
	                  </dl>
	                </div><!-- /.box-body -->
	              </div><!-- /.box -->
	            </div><!-- ./col -->
					
	        </section>


</div>