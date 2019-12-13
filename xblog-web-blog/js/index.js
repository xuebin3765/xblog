//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.use(['element', 'layer','laypage'],  function () {
    var element = layui.element;
    var $ = layui.jquery;
    var laypage = layui.laypage;
    var layer = layui.layer;
    var limit = 10;
    // 加载文章数据
    $.get(serverUrl + '/api/article/findAll?page=1&limit='+limit, function (result) {
        res = result.data;
        setHtmlType(res); // 设置文章显示的列表样式
        pages(result.count)//切换分类时候，调用页码，重新渲染
    });

    // 分页组件渲染
    function pages(count, typeId) {
        layui.laypage.render({
            elem: 'page-navigator'
            , count: count
            , theme: '#4A90E2'
            , layout: [ 'prev', 'page', 'next', 'limit']
            , limit: limit
            , jump: function (obj, first) {
                limit = obj.limit;
                if (!first) {
                    $.get(serverUrl + '/api/article/findAll?page='+obj.curr+'&limit='+obj.limit
                        , function (result) {
                            setHtmlType(result.data);
                        });
                }
            }
        })
    }

    // 页面样式渲染
    function setHtmlType(data) {
        var strHtml = "";
        $.each(data, function (index, item) {
            strHtml += '<div class="title-article list-card">'+
                '<div class="list-pic">'+
                '<a href="https://www.echo.so/technique/49.html" title='+item.title+'>'+
                '<img src='+item.imgUrl+' alt='+item.title+' class="img-full">'+
                '</a>'+
                '</div>'+
                '<a href="https://www.echo.so/technique/49.html">'+
                '<h1>'+item.title+'</h1>'+
                '<p>'+item.decoration+'</p>'+
                '</a>'+
                '<div class="title-msg">'+
                '<span><i class="layui-icon">&#xe705;</i> <a href="https://www.echo.so/category/technique/">技术杂谈</a></span>'+
                '<span><i class="layui-icon">&#xe60e;</i>2019-08-21 PM </span>'+
                '<span class="layui-hide-xs"><i class="layui-icon">&#xe62c;</i> 1333℃</span>'+
                '<span class="layui-hide-xs"><i class="layui-icon">&#xe63a;</i> 14条</span>'+
                '</div>'+
                '</div>';
        });
        $("#articleList").html(strHtml);
    }
});