var LoginPage = {
	contextPath: '',
	init: function(contextPath) {
		this.contextPath = contextPath;
		this.initLocation();
	},
	initLocation: function() {
		if(self.location && top.location != self.location){
    	    top.location.replace('loginpage');
			return;
    	}
	}
}