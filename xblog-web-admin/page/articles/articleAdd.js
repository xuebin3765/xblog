layui.use(['form','layer','code','layedit','laydate','upload'],function(){

    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        laypage = layui.laypage,
        upload = layui.upload,
        layedit = layui.layedit,
        laydate = layui.laydate,
        $ = layui.jquery;
    var E = window.wangEditor;

    var editor = new E('#articleContent');
    editor.customConfig.uploadImgServer = serverUrl+'/admin/fileUpload/photos';
    // 关闭粘贴内容中的样式
    editor.customConfig.pasteFilterStyle = false
    // 忽略粘贴内容中的图片
    editor.customConfig.pasteIgnoreImg = true
    // 使用 base64 保存图片
    //editor.customConfig.uploadImgShowBase64 = true
    // 上传图片到服务器
    editor.customConfig.uploadFileName = 'file'; //设置文件上传的参数名称
    editor.customConfig.uploadImgMaxSize = 10 * 1024 * 1024; // 将图片大小限制为 3M
    editor.customConfig.uploadImgMaxLength = 5;
    editor.customConfig.uploadImgHooks = {

        success: function (xhr, editor, result) {
            // 图片上传并返回结果，图片插入成功之后触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
        },
        fail: function (xhr, editor, result) {
            // 图片上传并返回结果，但图片插入错误时触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
        },
        error: function (xhr, editor) {
            // 图片上传出错时触发
            // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
        },
        customInsert: function (insertImg, result, editor) {
            // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
            // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果
            // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
            var data = result.data;
            for (var i = 0; i < data.length; i++){
                insertImg(data[i].newFile);
            }
            // result 必须是一个 JSON 格式字符串！！！否则报错
        }

    };
    editor.create();


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
            val = editor.txt.html();
            if(val === ''){
                return "文章内容不能为空";
            }
        }
    });
    var stick = $('.stick').val();
    form.on('switch(articleTop)', function(data){
        //开关value值，也可以通过data.elem.value得到
        stick = data.elem.checked;
    });

    var status = "0";
    form.on('radio(status)', function (data) {
        status = data.field.status;
        // alert(status);
        $("input:radio[name=status][value='0']").attr("checked", data.value === 0);
        $("input:radio[name=status][value='1']").attr("checked", data.value === 1);
    });

    form.on("submit(addArticle)",function(data){
        //弹出loading
        ;
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //将页面全部复选框选中的值拼接到一个数组中
        var navigate_checkbox = [];
        $('.navigate_checkbox input[type=checkbox]:checked').each(function() {
            navigate_checkbox.push($(this).val());
        });

        var tag_checkbox = [];
        $('.tag_checkbox input[type=checkbox]:checked').each(function() {
            tag_checkbox.push($(this).val());
        });
        var content = editor.txt.html();
        var params = {
            id: $(".articleId").val(),
            title: $(".articleName").val(),  //文章标题
            //文章摘要,截取文章内容中的一部分文字放入文章摘要
            decoration: content.substring(0, 200),
            //文章内容
            context: content,
            imgUrl: $(".thumbImg").attr("src"),  //缩略图
            navigateIds: navigate_checkbox,    //文章分类
            tagIds: tag_checkbox,    //文章标签
            type: $('.articleType select').val(),    //文章类型
            loadUrl: $('.loadUrl input[type=text]').val(),    //转载或翻译的原文地址
            status: $('#status input[name=status]:checked').val(),    //发布状态
            stick: stick    //是否置顶
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
                top.layer.close(index);
                top.layer.msg("文章添加成功！");
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            },
            error:function (message) {
                top.layer.close(index);
                top.layer.msg("文章添加失败！");
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            }
        });
        return false;
    });

    //预览
    form.on("submit(look)",function(){
        layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问");
        return false;
    });



    // layui.layedit.set({
    //     uploadImage: {
    //         url: serverUrl+'/admin/fileUpload/uploadImage' //接口url
    //         ,type: 'post' //默认post
    //     }
    // });
    //
    // //创建一个编辑器
    // var editIndex = layedit.build('articleContent',{
    //     height : 600,
    //     uploadImage : {
    //         url : serverUrl+'/admin/fileUpload/photo'
    //     },
    //     tool: [
    //         'strong' //加粗
    //         ,'italic' //斜体
    //         ,'underline' //下划线
    //         ,'del' //删除线
    //         ,'|' //分割线
    //         ,'left' //左对齐
    //         ,'center' //居中对齐
    //         ,'right' //右对齐
    //         ,'link' //超链接
    //         ,'unlink' //清除链接
    //         ,'image' //插入图片
    //         ,'help' //帮助
    //         ,'code'
    //     ]
    // });
    // //用于同步编辑器内容到textarea
    // layedit.sync(editIndex);

});