//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.use(['element', 'layer','laypage'],  function () {
    var element = layui.element;
    var $ = layui.jquery;
    var laypage = layui.laypage;
    var layer = layui.layer;
    var limit = 10;

    var router = layui.router();

    // /**13位时间戳转换成 年月日 上午 时间  2018-05-23 */
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

    // 加载文章数据
    $.get(serverUrl + '/api/article/findById?id='+router.search.id, function (result) {
        res = result.data;
        var time = createTime(res.created);
        // 渲染页面内容
        var address = "";
        var articleTitle = '<a href="http://www.androidchina.net/10191.html">'+res.title+'</a>';
        var articleMeta = '' +
            '<span class="muted"><a href="http://www.androidchina.net/category/dev"><i class="icon-list-alt icon12"></i> '+res.typeName+'</a></span>' +
            '<span class="muted"><i class="icon-user icon12"></i> <a href="http://www.androidchina.net/author/loading">'+res.userName+'</a></span><time class="muted"><i class="ico icon-time icon12"></i> '+time+'</time>\n' +
            '<span class="muted"><i class="ico icon-eye-open icon12"></i> '+res.pageView+'</span>\n' +
            '<span class="muted"><i class="icon-comment icon12"></i> <a href="http://www.androidchina.net/10191.html#comments">'+res.commentNumber+'评论</a></span>';
        var article_author = '<p>转载请注明：<a href=\"http://www.androidchina.net\">程序员刊</a> &raquo; <a href=\"http://www.androidchina.net/10200.html\">打造高效小团队 &#8211; 团队代码提交流程 &#038;&#038; 规范</a></p>';
        $(".article-title").html(articleTitle);
        $(".article-meta").html(articleMeta);
        $("#output_wrapper_id").html(res.context);
        // 转载说明
        $(".article-author").html(article_author);
        var strHtml = "";
        // 添加文章标题信息
    });
});