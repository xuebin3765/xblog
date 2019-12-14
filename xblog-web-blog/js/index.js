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
        // alert("===========")
        var strHtml = "";
        $.each(data, function (index, item) {
            var time = createTime(item.created);
            strHtml += '<article class="excerpt">'+
                '    <div class="focus">'+
                '        <a href="view/article/articleDetail.html#/id='+item.id+'" class="thumbnail">'+
                '            <img src='+item.imgUrl+' alt='+item.title+'/>'+
                '        </a>'+
                '    </div>'+
                '   <header>'+
            '       <a class="label label-important" href="http://www.androidchina.net/category/info">'+item.typeName+'<i class="label-arrow"></i></a>' +
            '       <h2>' +
            '           <a href="view/article/articleDetail.html#/id='+item.id+'" title='+item.title+' target="_blank">'+item.title+'</a>' +
            '       </h2>' +
            '   </header>' +
            '   <p>' +
            '       <span class="muted"><i class="icon-user icon12"></i> <a href="http://www.androidchina.net/author/loading">'+item.userName+'</a></span>' +
            '       <span class="muted"><i class="icon-time icon12"></i> '+time+'</span> ' +
            '       <span class="muted"><i class="icon-eye-open icon12"></i> '+item.pageView+'</span> ' +
            '       <span class="muted"><i class="icon-comment icon12"></i><a href="http://www.androidchina.net/10186.html#comments">'+item.commentNumber+'评论</a></span>' +
            '   </p>' +
            '   <p class="note">' + item.decoration+ '</p>' +
            '</article>';
        });
        $("#articleList").html(strHtml);
    }
});