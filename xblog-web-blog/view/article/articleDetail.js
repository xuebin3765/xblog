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
        laypage.render({
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
        return y+"-"+m+"-"+d;
    };

    // 页面样式渲染
    function setHtmlType(data) {
        var strHtml = "";
        $.each(data, function (index, item) {
            var time = createTime(item.created);
            strHtml += '<div class="title-article list-card" lay-filter="article">'+
                '<div class="list-pic">'+
                '<a href="view/article/articleDetail.html#id='+item.id+'" target="_blank" title='+item.title+'>'+
                '<img src='+item.imgUrl+' alt='+item.title+' class="img-full">'+
                '</a>'+
                '</div>'+
                '<a href="view/article/articleDetail.html#id='+item.id+'" target="_blank">'+
                '<h1>'+item.title+'</h1>'+
                '<p>'+item.decoration+'</p>'+
                '</a>'+
                '<div class="title-msg">'+
                '<span><i class="layui-icon">&#xe705;</i> <a href="https://www.echo.so/category/technique/">'+item.typeName+'</a></span>'+
                '<span><i class="layui-icon">&#xe637;</i> '+time+'</span>'+
                '<span class="layui-hide-xs"><i class="layui-icon">&#xe63a;</i> '+item.typeName+'条</span>'+
                '<span class="layui-hide-xs"><i class="layui-icon">&#xe60e;</i> '+item.pageView+'</span>'+
                '</div>'+
                '</div>';
        });
        $("#articleList").html(strHtml);
    }
});