<html><head><link>
    <title>test登陆注册页面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script src="http://libs.baidu.com/jquery/1.9.0/jquery.min.js"></script>
    <!-- 包括所有bootstrap的js插件或者可以根据需要使用的js插件调用　-->
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>
<body>
<div>
    <div>
        <div>
            <form>
                <div class="modal-body">
                    <div class="form-group has-feedback">
                        <label class="col-sm-3 control-label" for="zh1">用户名：</label>
                        <div class="col-sm-9 require">
                            <input type="text" class="form-control" name="username" id="zh1">
                        </div>
                    </div>
                    <div class="form-group has-feedback">
                        <label class="col-sm-3 control-label" for="pw1">密码：</label>
                        <div class="col-sm-9 require">
                            <input type="password" class="form-control" name="pwd" id="pw1">
                        </div>
                    </div>
                    <div class="form-group has-feedback">
                        <label class="col-sm-3 control-label" for="pw1">重复密码：</label>
                        <div class="col-sm-9 require">
                            <input type="password" class="form-control-two" name="pwd" id="pw2">
                        </div>
                    </div>
                    <div class="form-group has-feedback">
                        <label id="info" class="col-sm-4 control-label pull-left"></label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button"  onclick="form_regists();">登录</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function form_regists() {
        var  name = $("#zh1").val();
        var  pw1 = $("#pw1").val();
        var  pw2 = $("#pw2").val();
        $.ajax({
            type : 'get',
            url : "register?name="+name+"&pw1="+pw1+"&pw2="+pw2,
            async : false,//同步/异步
            contentType : "application/x-www-form-urlencoded; charset=utf8",
            dataType : 'json', //返回 JSON 数据
            success : function(data, status) {
                var rstate = data.code;
                if (rstate == "1") {
                    alert('接口调用成功！');
                } else {
                    alert(data.msg);
                }
            }
        });
    }
</script>
</body></html>