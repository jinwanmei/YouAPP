(function(){
	function _Config(){
		this.getEndpoint=function(){
			return "http://localhost:8585/youapp-html";
		}
		
		this.getHtmlEndpoint=function(){
			return "http://localhost:8585/youapp-html/get/gethtml/";
		}
		
		this.getDataEndpoint=function(){
			return "http://localhost:8585/youapp-html/get/getdata/";
		}
		
	}
	
	window.$_config=new _Config();
})(window);