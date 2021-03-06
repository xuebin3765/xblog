layui.use(['form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    /** 显示文章状态太 */
    function showStatus(v){
        if (v === -1) return "软删除";
        if (v === 0) return "草稿";
        if (v === 1) return "已发布";
        return "未知";
    }

    /**13位时间戳转换成 年月日 上午 时间  2018-05-23 10:41:08 */
    function createTime(v){
        return new Date(parseInt(v)).toLocaleString()
    }
    /**重写toLocaleString方法*/
    Date.prototype.toLocaleString = function() {
        var y = this.getFullYear();
        var m = this.getMonth()+1;
        m = m<10?'0'+m:m;
        var d = this.getDate();
        d = d<10?("0"+d):d;
        var h = this.getHours();
        h = h<10?("0"+h):h;
        var M = this.getMinutes();
        M = M<10?("0"+M):M;
        var S=this.getSeconds();
        S=S<10?("0"+S):S;
        return y+"-"+m+"-"+d+" "+h+":"+M;
    };

    //新闻列表
    var tableIns = table.render({
        elem: '#articleList',
        url : serverUrl+'/admin/article/findAll',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 20,
        limits : [10,15,20,25],
        id : "articleListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'id', title: 'ID', width:200, align:"center"},
            {field: 'title', title: '文章标题', width:350},
            {field: 'userName', title: '发布者', align:'center'},
            {field: 'status', title: '发布状态',  align:'center',templet: function (d) {
                return showStatus(d.status);
            }},
            {field: 'stick', title: '是否置顶', align:'center', templet:function(d){
                return '<input type="checkbox" name="articleTop" lay-filter="articleTop" lay-skin="switch" lay-text="是|否" '+(d.stick?'checked':'')+'>'
            }},
            {field: 'created', title: '发布时间', align:'center', minWidth:100, templet:function(d){
                return createTime(d.created);
            }},
            {field: 'modify', title: '发布时间', align:'center', minWidth:100, templet:function(d){
                return createTime(d.modify);
            }},
            {title: '操作', width:240, templet:'#articleListBar',fixed:"right",align:"center"}
        ]]
    });

    //是否置顶
    form.on('switch(articleTop)', function(data){
        var index = layer.msg('修改中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.close(index);
            if(data.elem.checked){
                layer.msg("置顶成功！");
            }else{
                layer.msg("取消置顶成功！");
            }
        },500);
    })

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
            table.reload("articleListTable",{
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

    //添加文章
    function addArticles(edit){
        var index = layui.layer.open({
            title : "添加文章",
            type : 2,
            content : "articleAdd.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".articleContent").innerHTML = edit.context;
                    body.find(".articleId").attr('value', edit.id);
                    body.find(".stick").attr('value', edit.stick);
                    body.find(".articleName").val(edit.title);
                    body.find(".abstract").html(edit.description);
                    body.find(".loadUrl").val(edit.loadUrl);
                    body.find(".thumbImg").attr("src",edit.imgUrl);
                    //获取子窗口的函数
                    // layero.find('iframe')[0].contentWindow.test();
                    // var iframeWin = window[layero.find('iframe')[0]['name']];
                    // iframeWin.test();
                    body.find("#articleContent").html(edit.context);
                    body.find("#status input[name=status][value=0]").attr("checked", edit.status === 0);
                    body.find("#status input[name=status][value=1]").attr("checked", edit.status === 1);
                    body.find(".articleTop input[name='articleTop']").prop("checked",(edit.stick?'checked':''));
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回文章列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500);
            }
        });
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(index);
        })
    }
    $(".addArticle_btn").click(function(){
        addArticles();
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('articleListTable'),
            data = checkStatus.data,
            ids = [];
        if(data.length > 0) {
            for (var i in data) {
                ids.push(data[i].id);
            }
            layer.confirm('确定删除选中的文章？', {icon: 3, title: '提示信息'}, function (index) {
                $.get(serverUrl+'/admin/article/deleteBatch',{
                    ids : ids  //将需要删除的newsId作为参数传入
                },function(data){
                    tableIns.reload();
                    layer.close(index);
                })
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    });

    //批量发布
    $(".delRelease_btn").click(function(){
        var checkStatus = table.checkStatus('articleListTable'),
            data = checkStatus.data,
            ids = [];
        if(data.length > 0) {
            for (var i in data) {
                ids.push(data[i].id);
            }
            layer.confirm('确定删除选中的文章？', {icon: 3, title: '提示信息'}, function (index) {
                $.get(serverUrl+'/admin/article/releaseBatch',{
                    ids : ids  //将需要删除的newsId作为参数传入
                },function(data){
                    tableIns.reload();
                    layer.close(index);
                })
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    });

    //列表操作
    table.on('tool(articleList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'release'){
            layer.confirm('确定发布此文章？',{icon:3, title:'提示信息'},function(index){
                $.get(serverUrl+'/admin/article/release',{
                    id : data.id  //将需要删除的newsId作为参数传入
                },function(data){
                    tableIns.reload();
                    layer.close(index);
                })
            });
        } else if(layEvent === 'edit'){ //编辑
            addArticles(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此文章？',{icon:3, title:'提示信息'},function(index){
                $.get(serverUrl+'/admin/article/delete',{
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

});