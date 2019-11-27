layui.use(['form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    //标签列表列表
    var tableIns = table.render({
        elem: '#tagsList',
        url : serverUrl+'/admin/tag/findAll',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 20,
        limits : [10,15,20,25],
        id : "tagsListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: 'ID', width:60, align:"center"},
            {field: 'name', title: '标签名称', align:'center'},
            {title: '操作', width:170, templet:'#tagsListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
            table.reload("tagsListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    key: $(".searchVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的内容");
        }
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".resetTags_btn").on("click",function(){
        //标签列表列表
        var tableIns = table.render({
            elem: '#tagsList',
            url : serverUrl+'/admin/tag/findAll',
            cellMinWidth : 95,
            page : true,
            height : "full-125",
            limit : 20,
            limits : [10,15,20,25],
            id : "tagsListTable",
            cols : [[
                {type: "checkbox", fixed:"left", width:50},
                {field: 'id', title: 'ID', width:60, align:"center"},
                {field: 'name', title: '标签名称', align:'center'},
                {title: '操作', width:170, templet:'#tagsListBar',fixed:"right",align:"center"}
            ]]
        });
    });

    //添加文章
    function addTags(edit){
        var index = layui.layer.open({
            title : "添加文章",
            type : 0,
            content : "newsAdd.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".newsName").val(edit.newsName);
                    body.find(".abstract").val(edit.abstract);
                    body.find(".thumbImg").attr("src",edit.newsImg);
                    body.find("#news_content").val(edit.content);
                    body.find(".newsStatus select").val(edit.newsStatus);
                    body.find(".openness input[name='openness'][title='"+edit.newsLook+"']").prop("checked","checked");
                    body.find(".newsTop input[name='newsTop']").prop("checked",edit.newsTop);
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        })
    }

    $(".addTags_btn").click(function(){
        layui.layer.open({
            type: 1,
            title: "新增标签",
            area:['30%','30%'],
            btn: ['确定', '取消'],
            content: $("#addTagDiv"),
            yes:function(index,layero){

                params = {'name':$('#tagName').val()};
                $.ajax({
                    type: 'POST',
                    url: serverUrl + '/admin/tag/addTag',
                    contentType: 'application/json;charset=utf-8',
                    data: JSON.stringify(params),
                    dataType: "json",
                    success:function (message) {
                        var mesg = message.respMsg;
                        var icon = 1;
                        if ('00000' !== message.respCode){
                            icon = 2;
                        }
                        layer.confirm(mesg,{btn:'关闭', icon:icon, title:'提示信息'},function(index){
                            tableIns.reload();
                            layer.close(index);
                            layui.layer.close(index);
                        });
                    },
                    error:function (message) {
                        layer.confirm(message.respMsg,{btn:'关闭', icon:2, title:'提示信息'},function(index){
                            tableIns.reload();
                            layer.close(index);
                            layui.layer.closeAll();
                        });
                    }
                });
            }
            ,btn2: function(){
                layer.closeAll();
            }
        });
    });

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('tagsListTable'),
            data = checkStatus.data,
            ids = [];
        if(data.length > 0) {
            for (var i in data) {
                ids.push(data[i].id);
            }
            layer.confirm('确定删除选中的文章？', {icon: 3, title: '提示信息'}, function (index) {
                $.get(serverUrl+'/admin/tag/deleteBatch',{
                    ids : ids  //将需要删除的newsId作为参数传入
                },function(data){
                    tableIns.reload();
                    layer.close(index);
                })
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    })

    //列表操作
    table.on('tool(tagsList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addNews(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此文章？',{icon:3, title:'提示信息'},function(index){
                $.get(serverUrl+'/admin/tag/delete',{
                    id : data.id  //将需要删除的newsId作为参数传入
                },function(data){
                    tableIns.reload();
                    layer.close(index);
                })
            });
        } else if(layEvent === 'look'){ //预览
            layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问")
        }
    });

})