layui.use(['form','layer','code','layedit','laydate','upload'],function(){

    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;

    //给分类 CheckBox赋值
    $(document).ready(function(){
        // 加载分类目录
        $.ajax({
            type: "GET",
            url: serverUrl+'/admin/navigate/findAllNavigate',
            success: function (data) {
                $.each(data.data, function(index,item) {
                    $(".navigate_checkbox").append('<input id="navigate_item" type="checkbox" value="'+item.id+'" name="'+item.name+'" title="'+item.name+'" lay-skin="primary" />');
                });
                form.render();
            }
        });
        // 加载标签
        $.ajax({
            type: "GET",
            url: serverUrl+'/admin/tag/findAllTag',
            success: function (data) {
                $.each(data.data, function(index,item) {
                    $(".tag_checkbox").append('<input id="tag_item" type="checkbox" value="'+item.id+'" name="'+item.name+'" title="'+item.name+'" lay-skin="primary" />');
                });
                form.render();
            }
        });
    });

    //上传缩略图
    upload.render({
        elem: '.thumbBox',
        url: serverUrl+'/admin/fileUpload/photo',
        method : "post",  //此处是为了演示之用，实际使用中请将此删除，默认用post方式提交
        done: function(res, index, upload){
            var num = parseInt(4*Math.random());  //生成0-4的随机数，随机显示一个头像信息
            $('.thumbImg').attr('src',res.data.src);
            $('.thumbBox').css("background","#fff");
        }
    });

    //格式化时间
    function filterTime(val){
        if(val < 10){
            return "0" + val;
        }else{
            return val;
        }
    }

    //定时发布
    var time = new Date();
    var submitTime = time.getFullYear()+'-'+filterTime(time.getMonth()+1)+'-'+filterTime(time.getDate())+' '+filterTime(time.getHours())+':'+filterTime(time.getMinutes())+':'+filterTime(time.getSeconds());
    laydate.render({
        elem: '#release',
        type: 'datetime',
        trigger : "click",
        done : function(value, date, endDate){
            submitTime = value;
        }
    });

    form.on("radio(release)",function(data){
        if(data.elem.title === "定时发布"){
            $(".releaseDate").removeClass("layui-hide");
            $(".releaseDate #release").attr("lay-verify","required");
        }else{
            $(".releaseDate").addClass("layui-hide");
            $(".releaseDate #release").removeAttr("lay-verify");
            submitTime = time.getFullYear()+'-'+(time.getMonth()+1)+'-'+time.getDate()+' '+time.getHours()+':'+time.getMinutes()+':'+time.getSeconds();
        }
    });

    form.verify({
        articleName : function(val){
            if(val === ''){
                return "文章标题不能为空";
            }
        },
        content : function(val){
            layedit.sync(editIndex);
            val = layedit.getContent(editIndex);
            if(val === ''){
                return "文章内容不能为空";
            }
        }
    });

    form.on("submit(addArticle)",function(data){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});

        alert( $(".articleName").val());
        alert( $(".articleTop").val());

        var params = {
            title: $(".articleName").val(),  //文章标题
            //文章摘要,截取文章内容中的一部分文字放入文章摘要
            decoration: layedit.getText(editIndex).substring(0, 50),
            //文章内容
            context:
                layedit.getContent(editIndex).split('<audio controls="controls" style="display: none;"></audio>')[0],
            imgUrl: $(".thumbImg").attr("src"),  //缩略图
            navigateIds: '1',    //文章分类
            tagIds: '1',    //文章标签
            type: $('.articleType select').val(),    //文章类型
            loadUrl: $(".loadUrl").val(),    //转载或翻译的原文地址
            status: $('#status input[checked]').val(),    //发布状态
            stick: $('#articleTop input[checked]').val() === 1    //是否置顶
        };
        $.ajax({
            type: 'POST',
            url: serverUrl+'/admin/article/addOrUpdate',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(params),
            dataType: "json",
            success:function (message) {
                var mesg = message.msg;
                var icon = 1;
                if (0 !== message.code){
                    icon = 2;
                }
                layer.confirm(mesg,{btn:'关闭', icon:icon, title:'提示信息'},function(index){
                    tableIns.reload();
                    layer.close(index);
                    if (message.code === 0)
                        layui.layer.closeAll();
                });
            },
            error:function (message) {
                layer.confirm(message.msg,{btn:'关闭', icon:2, title:'提示信息'},function(index){
                    tableIns.reload();
                    layer.close(index);
                    if (message.code === 0)
                        layui.layer.closeAll();
                });
            }
        });
        setTimeout(function(){
            top.layer.close(index);
            top.layer.msg("文章添加成功！");
            layer.closeAll("iframe");
            //刷新父页面
            parent.location.reload();
        },500);
        return false;
    });

    //预览
    form.on("submit(look)",function(){
        layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问");
        return false;
    });

    // // 上传图片配置
    // layedit.set({
    //     uploadImage: {
    //         url: '' //接口url
    //         ,type: '' //默认post
    //     }
    // });

    layui.layedit.set({
        uploadImage: {
            url: serverUrl+'/admin/fileUpload/uploadImage' //接口url
            ,type: 'post' //默认post
        }
    });

    //创建一个编辑器
    var editIndex = layedit.build('articleContent',{
        height : 600,
        uploadImage : {
            url : serverUrl+'/admin/fileUpload/photo'
        },
        tool: [
            'strong' //加粗
            ,'italic' //斜体
            ,'underline' //下划线
            ,'del' //删除线
            ,'|' //分割线
            ,'left' //左对齐
            ,'center' //居中对齐
            ,'right' //右对齐
            ,'link' //超链接
            ,'unlink' //清除链接
            ,'image' //插入图片
            ,'help' //帮助
            ,'code'
        ]
    });
    //用于同步编辑器内容到textarea
    layedit.sync(editIndex);

});