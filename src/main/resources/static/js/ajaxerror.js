$.ajax({
    	url: "/err/getAjaxerror",
    	type: "POST",
    	async: false,
    	success: function(data) {
    		debugger;
            if(data.status == 200 && data.msg == "OK") {
            	alert("success");
            } else {
            	alert("发生异常：" + data.msg);
            }
    	},
        error: function (response, ajaxOptions, thrownError) {
        	debugger;
        	alert("error");       
        }
    });






function findUser() {
    var userid = $("userid").value;
    if (userid) {
        AjaxUtil.request({
            url:"servlet/UserJsonServlet",
            params:{id:userid，
	 				id2 : id , },
            type:'json',
            callback:process
        });
    }
}

function process(json){
    if(json){
        $("id").innerHTML = json.id;
        $("username").innerHTML = json.username;
        $("age").innerHTML = json.age;
    }
    else{
        $("msg").innerHTML = "用户不存在";
        $("id").innerHTML = "";
        $("username").innerHTML = "";
        $("age").innerHTML = "";
    }
}


function $(id) {
    return document.getElementById(id);
}