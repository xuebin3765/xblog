layui.use(['form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    $.ajax({
        url:serverUrl+'/admin/navigate/findAllNavigate',
        type:'get',
        dataType:'json',
        success:function(data){
            if(data.code === 0){
                $.each(data.data,function(index,item){
                    //第一个参数是页面显示的值，第二个参数是传递到后台的值
                    $('#navigate').append(new Option(item.name,item.id));//往下拉菜单里添加元素
                    //设置默认选中的value（这个值就可以是在更新的时候后台传递到前台的值）
                    //此时这个dynamicVal是后台动态要设置的默认值，可以先存变量然后再进行赋值
                    $('#navigate').val(1);
                    //如果是默认固定值可以直接再括号里填入要设置的数值，如$('#currency').val(1);
                });
                form.render();
            }
        }
    });

    //标签列表列表
    var tableIns = table.render({
        elem: '#navigateList',
        url : serverUrl+'/admin/navigate/findAll',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 20,
        limits : [10,15,20,25],
        id : "navigateListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: 'ID', width:200, align:"center"},
            {field: 'name', title: '导航名称', align:'center'},
            {field: 'url', title: '导航地址', align:'center'},
            {field: 'parentName', title: '上级导航', align:'center'},
            {title: '操作', width:170, templet:'#navigateListBar',fixed:"right",align:"center"}
        ]]
    });

    //刷新搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".resetNavigate_btn").on("click",function(){
        var tableIns = table.render({
            elem: '#navigateList',
            url : serverUrl+'/admin/navigate/findAll',
            cellMinWidth : 95,
            page : true,
            height : "full-125",
            limit : 20,
            limits : [10,15,20,25],
            id : "navigateListTable",
            cols : [[
                {type: "checkbox", fixed:"left", width:50},
                {field: 'id', title: 'ID', width:200, align:"center"},
                {field: 'name', title: '导航名称', align:'center'},
                {field: 'url', title: '导航地址', align:'center'},
                {field: 'parentName', title: '上级导航', align:'center'},
                {title: '操作', width:170, templet:'#navigateListBar',fixed:"right",align:"center"}
            ]]
        });

        $.ajax({
            url:serverUrl+'/admin/navigate/findAllNavigate',
            type:'get',
            dataType:'json',
            success:function(data){
                if(data.code === 0){
                    $.each(data.data,function(index,item){
                        //第一个参数是页面显示的值，第二个参数是传递到后台的值
                        $('#navigate').append(new Option(item.name,item.id));//往下拉菜单里添加元素
                        //设置默认选中的value（这个值就可以是在更新的时候后台传递到前台的值）
                        //此时这个dynamicVal是后台动态要设置的默认值，可以先存变量然后再进行赋值
                        $('#navigate').val(1);
                        //如果是默认固定值可以直接再括号里填入要设置的数值，如$('#currency').val(1);
                    });
                    form.render();
                }
            }
        });
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
            table.reload("navigateListTable",{
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

    $(".addNavigate_btn").click(function(){
        layui.layer.open({
            type: 1,
            title: "新增导航",
            area:['50%','65%'],
            btn: ['确定', '取消'],
            content: $("#addNavigateDiv"),
            yes:function(index,layero){
                params = {'name':$('#name').val(), 'url': $('#url').val(), 'parentId': $('#navigate').val()};
                $.ajax({
                    type: 'POST',
                    url: serverUrl + '/admin/navigate/add',
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