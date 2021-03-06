$.fn.extend({
	
	getSelectedRow:function(){
		return $(this.selector).data("datatables-checked").removeNull();
	}
	,
	
	initDataTable : function(options) {
		options.serverSide =true;
		options.processing=true;
		var _columns=[]
		var checkboxColumns=[{
            "orderable":      false,
            "data":           null,
            "width":"1%",
            "title":'<input class="minimal" name="all" value="0" type="checkbox" />',
            "render": function (data, type, row, meta) {
                return '<input class="minimal" name="sub" value="' + row.id + '" type="checkbox" />';
            }
        }];
		var opsColumns=[];
		if(options.ops){
			
			var opsGenColumns=[{
	            "orderable":      false,
	            "data":           null,
	            "width":"15%",
	            "title":'操作',
	            "render": function (data, type, row, meta) {
	            	var _ops=options.ops;
	            	var viewHtml='';
	            	var rowId=row.id;
	            	if(_ops.view){
	            		viewHtml='<button  data-rowId="'+rowId+'"  id="row_view_btn_'+rowId+'" name="row_view_btn" type="button" class="btn btn-primary btn-sm">view</button>';
	            	}
	            	var editHtml='';
	            	if(_ops.edit){
	            		editHtml='<button data-rowId="'+rowId+'"  id="row_edit_btn_'+rowId+'" name="row_edit_btn"  type="button" class="btn btn-primary btn-sm">Edit</button>';
	            	}
	            	var delHtml='';
	            	if(_ops.del){
	            		delHtml='<button data-rowId="'+rowId+'"  id="row_del_btn_'+rowId+'" name="row_del_btn"  type="button" class="btn btn-primary btn-sm">Delete</button>';
	            	}
	            	
	            	return viewHtml+editHtml+delHtml;
	            }
	        }];
			opsColumns=opsColumns.concat(opsGenColumns);
			
		}
		
		
		
		if(options.checkbox){
			_columns=_columns.concat(checkboxColumns).concat(options.columns);
		}
		else{
			_columns=_columns.concat(options.columns);
		}
		
		if(options.ops){
			_columns=_columns.concat(opsColumns);
		}
		
//		$wrap=$('#editable');
		var $wrap=$(this.selector);
		var dataTableObj= $(this.selector).DataTable({
			processing : options.processing,
			serverSide : options.serverSide,
			ajax : function (data, callback, settings) {
				//debugger;
				var HtmlMenuOpt ={endpoint:options.url,
				  		data:options.urlDataFn.apply(),
				  		success:function(data){
				  			callback(data);
				  			
				  			if(options.ops){
				  				var _ops=options.ops;
				  				if(_ops.view){
				  					$wrap.find('button[name=row_view_btn]')
				  						.on("click",function(){
					            		_ops.view($(this).data("rowid"),{});
					            	});
				            	}
				  				
				            	if(_ops.edit){
				  					$wrap.find('button[name=row_edit_btn]')
				  						.on("click",function(){
					            		_ops.edit($(this).data("rowid"),{});
					            	});
				            	}
				            	
				            	if(_ops.del){
				            		$wrap.find('button[name=row_del_btn]')
				            		.on("click",function(){
				            			
				            			$.confirm({
				            			    title: false,
				            			    content: '确定删除？',
				            			    confirm: function(){
				            			    	_ops.del($(this).data("rowid"),{});
				            			    },
				            			    cancel: function(){
				            			    },
				            			    confirmButton: '确定',
				            			    cancelButton: '撤销',
				            			    onOpen: function(){
				            			    }
				            			});
				            			
				            		});
				            	}
				  				
				  			}
				  			
				  			
				  			if(options.checkbox){
					  			
					  			$chk=$wrap.find('input[type="checkbox"].minimal');
					  			$allChk=$chk.filter('[name="all"]');
					  			$subChks=$chk.filter('[name="sub"]');
					  		//check-box
			  				  	//iCheck for checkbox and radio inputs
			  				    $chk.iCheck({
			  				      checkboxClass: 'icheckbox_minimal-blue',
			  				      radioClass: 'iradio_minimal-blue'
			  				    });
					  		
					  		
			  				  $allChk
			  				  .on('ifChecked', function(event){
			  					$subChks.iCheck('check');
			  				  }
			  				  );
			  				  
			  				 $allChk
			  				  .on('ifUnchecked', function(event){
			  					var list=$wrap.data("datatables-checked");
			  					if(list.size()==$subChks.length){
			  						$subChks.iCheck('uncheck');
			  					}
			  				  }
			  				  );
			  				  
					  		
			  				$subChks
			  				  .on('ifChecked', function(event){ 
			  					//debugger;
			  					var id=$(event.target).val();
			  					var list=$wrap.data("datatables-checked");
			  					if(list){
			  						if(!list.exists(id)){
				  						list.add(id);
				  					}
			  					}
			  					else{
			  						list=new List();
			  						list.add(id);
			  						$wrap.data("datatables-checked",list);
			  					}
			  					
			  					if(list.size()==$subChks.length){
			  						$allChk.iCheck('check');
			  					}
			  					
			  				  }
			  				  );
			  				 
			  				$subChks
			  				  .on('ifUnchecked', function(event){ 
			  					var id=$(event.target).val();
			  					var list=$wrap.data("datatables-checked");
			  					if(list){
			  						if(list.exists(id)){
				  						list.remove(id);
				  					}
			  					}
			  					$allChk.iCheck('uncheck');
			  					}
			  				  );
				  			}
				  		
				  			},
				  		page:data.start/data.length,
				  		size:data.length
				  		};
				
				
		    	kjcs.ajaxEnt.getRequest(HtmlMenuOpt);
				//debugger;
			},
			columns : _columns,
			sPaginationType: "full_numbers",
			language: {
				    paginate: {
				      first: "首页",
				      last:"尾页",
				      previous:"前一页",
				      next:"后一页"
				    },
				    info:"显示  _START_  到  _END_ 条记录, 共  _TOTAL_  条记录",
				    infoEmpty:"没有数据",
				    lengthMenu: "每页显示 _MENU_ 条记录",
				    infoFiltered: "(从 _MAX_ 条数据中检索)", 
				    loadingRecords: "Please wait - loading...",
				    zeroRecords: "No records to display",
				    processing: "处理中..."
			},
			dom: 
			//"<'row'<'col-sm-6'l><'col-sm-6'f>>" +
			//"<'row'<'col-sm-12'tr>>" +
			"tr"+	
			"<'row'<'col-sm-5'l><'col-sm-7'p>>",
			/* oLanguage: {  
				"sLengthMenu": "每页显示 _MENU_ 条记录",  
				"sZeroRecords": "抱歉， 没有找到",  
				"sInfo": "显示  _START_  到  _END_ 条记录, 共  _TOTAL_  条记录",  
				"sInfoEmpty": "没有数据",  
				"sInfoFiltered": "(从 _MAX_ 条数据中检索)", 
				"oPaginate": {  
				"sFirst": "首页",  
				"sPrevious": "前一页",  
				"sNext": "后一页",  
				"sLast": "尾页"  
				},
			sZeroRecords: "没有检索到数据"
			},
			*/
			bSort:false,
			searching:false
		});
		return dataTableObj;
	}
});