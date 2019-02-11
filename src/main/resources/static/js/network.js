/*
 *网络请求 公用的工具
 * author:wangqiang
 * createTime:20171205
 * 
 */
//数据请求
function httpRequestJsonBodyReturnJson(type, addr, data, callback, errcallback){
    $.ajax({
        type: type,
        url: addr,
        data: JSON.stringify(data),
        contentType: "application/json;charset=utf-8",
        timeout: 30000,
        dataType: "json",
        success: function (msg) {
            callback(msg);
        },
        error: function (msg) {
            errcallback(msg);
        }
    });
}

//无data数据请求
function httpDataRequest(type,addr,callback,errcallback){
    $.ajax({
        type: type,
        url: addr,
        contentType: "application/json;charset=utf-8",
        timeout: 30000,
        dataType: "json",
        success: function (msg) {
            callback(msg);
        },
        error: function (msg) {
            errcallback(msg);
        }
    });
}

//不指定dataType: "json",调用服务器端返回值是void的接口，不需要服务器返回 data
function httpNoDataTypeRequest(type, addr, callback, errcallback){
	    $.ajax({
        type: type,
        url: addr,
        contentType: "application/json;charset=utf-8",
        timeout: 30000,
//      dataType: "json",
        success: function (msg) {
            callback(msg);
        },
        error: function (msg) {
            errcallback(msg);
        }
    });
}

//调用服务器按JsonBody传参、返回void的接口
function httpServerRequestJsonBodyReturnVoid(type,addr,data,callback,errcallback){
	    $.ajax({
        type: type,
        url: addr,
        data: JSON.stringify(data),
        contentType: "application/json;charset=utf-8",
        timeout: 30000,
        success: function (msg) {
            callback(msg);
        },
        error: function (msg) {
            errcallback(msg);
        }
    });
}

//文件上传
function httpFileSubmit($ele,type,url,callback,errcallback){
	$ele.ajaxSubmit({
		dataType: "json",
		url: url,
		type: type,
		success: function (msg) {
	        callback(msg);
	    },
	    error: function (msg) {
	        errcallback(msg);
	    }
	});
}

/*
 * ***end*****
 */
