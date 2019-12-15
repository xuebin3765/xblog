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

    // 加载标题数据
    $.get(serverUrl + '/api/navigate/findAllNavigate', function (result) {
        res = result.data;
        // 渲染导航样式
        setHtmlNavigate(res);
    });

    // 导航样式渲染
    var setHtmlNavigate = function (res) {
        var html = [];
        html.push('<ul class="nav">');
        // 遍历加载所有导航
        if (res.length > 0) {
            for (var i = 0; i < res.length; i++) {
                var navigate = res[i];
                var url;
                if ("#" === navigate.url){
                    url = netUrl;
                }else {
                    url = navigate.url+'#/navId='+navigate.id;
                }
                html.push('<li id="menu-item-14" class="menu-item menu-item-type-taxonomy menu-item-object-category menu-item-14">');
                html.push('<a href="'+url+'">'+navigate.name+'</a>');
                var navigateSons = navigate.navigateList;
                if (navigateSons != null && navigateSons.length){
                    html.push('<ul class="sub-menu">');
                    for (var j = 0; j < navigateSons.length; j++) {
                        var navigateSon = navigateSons[j];
                        var urlSon;
                        if ("#" === navigateSon.url){
                            urlSon = netUrl;
                        }else {
                            urlSon = navigateSon.url+'#/navId='+navigateSon.id;
                        }
                        html.push('<li id="menu-item-13" class="menu-item menu-item-type-taxonomy menu-item-object-category menu-item-13"><a href="'+urlSon+'">'+navigateSon.name+'</a></li>');
                    }
                    html.push('</ul>');
                }
                html.push('</li>')
            }
        }
        html.push('</ul>');
        $(".navigateList").html(html.join(""));
    };
    var getUrl = function(url, id) {
        if ("#" === url){
            return netUrl;
        }else {
            return url+'#/navId='+id
        }
    }


    // 加载文章数据
    $.get(serverUrl + '/api/article/findById?id='+router.search.id, function (result) {
        if (result.code !== 0){
            layer.alert(result.msg);
            return;
        }
        res = result.data;
        var address = [];
        address.push('你的位置：<a href="http://www.androidchina.net" target="_blank">程序员刊</a> <small>></small> ');
        var navigate = res.navigate;
        if (typeof navigate != "undefined" && navigate != null && navigate !== ""){
            var navigateParent = navigate.parentNavigate;
            if (typeof navigateParent != "undefined" && navigateParent != null && navigateParent !== ""){
                address.push('<a href="'+getUrl(navigateParent.url, navigateParent.id)+'" title="'+navigateParent.name+'" target="_blank">'+navigateParent.title+'</a> ')
            }
            address.push('<a href="'+getUrl(navigate.url, navigate.id)+'" title="'+navigate.name+'" target="_blank">'+navigate.name+'</a> <small>></small> ')
        }
        address.push('<a href="articleDetail.html#/id='+res.id+'" title="'+res.title+'" target="_blank">'+res.title+'</a>')
        var time = createTime(res.created);
        // 渲染页面内容
        var breadcrumbs = address.join("");
        var articleTitle = '<a href="articleDetail.html#/id='+res.id+'" target="_blank">'+res.title+'</a>';
        var articleMeta = '' +
            '<span class="muted"><a href="http://www.androidchina.net/category/dev"><i class="icon-list-alt icon12"></i> '+res.typeName+'</a></span>' +
            '<span class="muted"><i class="icon-user icon12"></i> <a href="http://www.androidchina.net/author/loading">'+res.userName+'</a></span><time class="muted"><i class="ico icon-time icon12"></i> '+time+'</time>\n' +
            '<span class="muted"><i class="ico icon-eye-open icon12"></i> '+res.pageView+'</span>\n' +
            '<span class="muted"><i class="icon-comment icon12"></i> <a href="http://www.androidchina.net/10191.html#comments">'+res.commentNumber+'评论</a></span>';
        var article_author = '转载请注明：<a href=\"http://www.androidchina.net\">程序员刊</a> &raquo; <a href=\"http://www.androidchina.net/10200.html\">打造高效小团队 &#8211; 团队代码提交流程 &#038;&#038; 规范</a>';
        // 我的位置
        $(".breadcrumbs").html(breadcrumbs);
        // 标题
        $(".article-title").html(articleTitle);
        // 访问量，创建者，发布时间，评论
        $(".article-meta").html(articleMeta);
        // 文章正文
        $("#output_wrapper_id").html(res.context);
        // 转载说明
        $(".article-author").html(article_author);
        // 添加文章标题信息
    });
});