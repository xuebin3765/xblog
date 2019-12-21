//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.use(['element', 'layer', 'laypage', 'carousel'], function () {
    var element = layui.element;
    var $ = layui.jquery;
    var laypage = layui.laypage;
    var layer = layui.layer;
    var limit = 10;

    var carousel = layui.carousel;

    var router = layui.router();
    var navId = router.search.navId;
    console.log(navId);


    if (typeof navId == "undefined" || navId == null || navId === "") {
        $('.sticky').show();
    } else {
        $('.sticky').hide();
        // 需要设置文章列表title
        $.get(serverUrl + '/api/navigate/findNavigateById?id=' + navId, function (result) {
            res = result.data;
            // 渲染位置
            $('#listTitle').html("当前位置：" + res.name);
        });
    }

    // 导航样式渲染
    var setHtmlNavigate = function (res) {
        var html = [];
        html.push('<ul class="nav">');
        // 遍历加载所有导航
        if (res.length > 0) {
            for (var i = 0; i < res.length; i++) {
                var navigate = res[i];
                var url;
                if ("#" === navigate.url) {
                    url = netUrl;
                } else {
                    url = navigate.url + '#/navId=' + navigate.id;
                }
                html.push('<li id="menu-item-14" class="menu-item menu-item-type-taxonomy menu-item-object-category menu-item-14">');
                html.push('<a href="' + url + '">' + navigate.name + '</a>');
                var navigateSons = navigate.navigateList;
                if (navigateSons != null && navigateSons.length) {
                    html.push('<ul class="sub-menu">');
                    for (var j = 0; j < navigateSons.length; j++) {
                        var navigateSon = navigateSons[j];
                        var urlSon;
                        if ("#" === navigateSon.url) {
                            urlSon = netUrl;
                        } else {
                            urlSon = navigateSon.url + '#/navId=' + navigateSon.id;
                        }
                        html.push('<li id="menu-item-13" class="menu-item menu-item-type-taxonomy menu-item-object-category menu-item-13"><a href="' + urlSon + '">' + navigateSon.name + '</a></li>');
                    }
                    html.push('</ul>');
                }
                html.push('</li>')
            }
        }
        html.push('</ul>');
        $(".navigateList").html(html.join(""));
    };
    // 轮播图
    var setHtmlBanner = function (res) {
        var strHtml = "<div carousel-item>";
        $.each(res, function (index, item) {
            strHtml += '<div>' +
                '            <a href="' + item.url + '" target="_blank">' +
                '                 <img src="' + item.imageUrl + '">' +
                '            </a>' +
                '       </div>';
        });
        strHtml += '</div>';
        $("#layui-carousel").html(strHtml);
    };
    // 加载轮播图数据
    $.get(serverUrl + '/api/banner/banners', function (result) {
        res = result.data;
        console.log(res);
        if (res == null || res.length === 0) {
            $('.top-banner').hide();
        } else {
            $('.top-banner').show();
        }
        // 渲染导航样式
        setHtmlBanner(res);

        //建造实例
        carousel.render({
            elem: '#layui-carousel'
            , width: '100%' //设置容器宽度
            , arrow: 'always' //始终显示箭头
            //,anim: 'updown' //切换动画方式
        });
    });


    // 加载标题数据
    $.get(serverUrl + '/api/navigate/findAllNavigate', function (result) {
        res = result.data;
        // 渲染导航样式
        setHtmlNavigate(res);
    });
    // 加载标签数据
    $.get(serverUrl + '/api/tag/findAll', function (result) {
        res = result.data;
        // 渲染导航样式
        var html = [];
        // 遍历加载所有导航
        if (res.length > 0) {
            for (var i = 0; i < res.length; i++) {
                var tag = res[i];
                var url = "http://www.baidu.com/#/tagId=" + tag.id;
                html.push('<a href="' + url + '" target="_blank">' + tag.name + ' (' + tag.articleNum + ')</a>');
            }
        }
        $(".d_tags").html(html.join(""));
    });
    // 加载文章列表数据
    $.get(serverUrl + '/api/article/findAll?navId=' + navId + '&page=1&limit=' + limit, function (result) {
        res = result.data;
        setHtmlArticleList(res); // 设置文章显示的列表样式
        pages(result.count)//切换分类时候，调用页码，重新渲染
    });

    // 分页组件渲染
    function pages(count, typeId) {
        laypage.render({
            elem: 'page-navigator'
            , count: count
            , theme: '#4A90E2'
            , layout: ['prev', 'page', 'next', 'limit']
            , limit: limit
            , jump: function (obj, first) {
                limit = obj.limit;
                if (!first) {
                    $.get(serverUrl + '/api/article/findAll?navId=' + navId + '&page=' + obj.curr + '&limit=' + obj.limit
                        , function (result) {
                            setHtmlArticleList(result.data);
                        });
                }
            }
        })
    }

    /**13位时间戳转换成 年月日 上午 时间  2018-05-23 10:41:08 */
    function createTime(v) {
        return new Date(parseInt(v)).toLocaleString()
    }

    /**重写toLocaleString方法*/
    Date.prototype.toLocaleString = function () {
        var y = this.getFullYear();
        var m = this.getMonth() + 1;
        m = m < 10 ? '0' + m : m;
        var d = this.getDate();
        d = d < 10 ? ("0" + d) : d;
        var h = this.getHours();
        h = h < 10 ? ("0" + h) : h;
        var M = this.getMinutes();
        M = M < 10 ? ("0" + M) : M;
        var S = this.getSeconds();
        S = S < 10 ? ("0" + S) : S;
        return y + "-" + m + "-" + d;
    };

    // 页面样式渲染
    function setHtmlArticleList(data) {
        // alert("===========")
        var strHtml = "";
        $.each(data, function (index, item) {
            var time = createTime(item.created);
            strHtml += '<article class="excerpt">' +
                '    <div class="focus">' +
                '        <a href="view/article/articleDetail.html#/id=' + item.id + '" class="thumbnail">' +
                '            <img src="' + item.imgUrl + '" alt="' + item.title + '"/>' +
                '        </a>' +
                '    </div>' +
                '   <header style="height: 50px">' +
                '       <a class="label label-important" href="http://www.androidchina.net/category/info">' + item.typeName + '<i class="label-arrow"></i></a>' +
                '       <h2>' +
                '           <a href="view/article/articleDetail.html#/id=' + item.id + '" title=' + item.title + ' target="_blank">' + item.title + '</a>' +
                '       </h2>' +
                '   </header>' +
                '   <p class="note">' + item.decoration + '</p>' +
                '   <p>' +
                '       <span class="muted"><i class="icon-user icon12"></i> <a href="http://www.androidchina.net/author/loading">' + item.userName + '</a></span>' +
                '       <span class="muted"><i class="icon-time icon12"></i> ' + time + '</span> ' +
                '       <span class="muted"><i class="icon-eye-open icon12"></i> ' + item.pageView + '</span> ' +
                '       <span class="muted"><i class="icon-comment icon12"></i><a href="http://www.androidchina.net/10186.html#comments">' + item.commentNumber + '评论</a></span>' +
                '   </p>' +
                '</article>';
        });
        $("#articleList").html(strHtml);
    }


});