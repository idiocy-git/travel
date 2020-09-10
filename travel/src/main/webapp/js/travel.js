// 校验用户名,单词字符，8-20位长度
function checkUserName() {
    // 1.获取用户名
    var username = $("#username").val();
    // 2.定义正则
    var reg_username = /^\w{8,20}$/;
    var flag = reg_username.test(username);
    if (flag) {
        $.post("checkUsername", {username: $("#username").val()}, function (data) {
            flag = data.flag;
            if (!data.flag) {
                // 用户名重复
                $("#errorMsg").html(data.errorMsg);
                $("#username").css("border", "1px solid red");
            } else {
                $("#username").css("border", "");
                $("#errorMsg").empty();
            }
        });
    } else {
        $("#username").css("border", "1px solid red");
    }
    return flag;
}

// 校验用户密码,单词字符，8-20位长度
function checkPassWord() {
    // 1.获取用户名
    var password = $("#password").val();
    // 2.定义正则
    var reg_password = /^\w{8,20}$/;
    var flag = reg_password.test(password);
    if (flag) {
        $("#password").css("border", "");
    } else {
        $("#password").css("border", "1px solid red");
    }
    return flag;
}

// 校验邮箱
function checkEmail() {
    // 1.获取用户名
    var email = $("#email").val();
    // 2.定义正则
    var reg_email = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
    var flag = reg_email.test(email);
    if (flag) {
        $("#email").css("border", "");
    } else {
        $("#email").css("border", "1px solid red");
    }
    return flag;
}

// 校验手机号
function checkTelephone() {
    // 1.获取用户名
    var telephone = $("#telephone").val();
    // 2.定义正则
    var reg_telephone = /^1[3-9]\d{9}$/;
    var flag = reg_telephone.test(telephone);
    if (flag) {
        $("#telephone").css("border", "");
    } else {
        $("#telephone").css("border", "1px solid red");
    }
    return flag;
}

// 校验姓名
function checkName() {
    // 1.获取用户名
    var name = $("#name").val();
    // 2.定义正则
    var reg_name = /^[\s\S]*.*[^\s][\s\S]*$/;
    var flag = reg_name.test(name);
    if (flag) {
        $("#name").css("border", "");
    } else {
        $("#name").css("border", "1px solid red");
    }
    return flag;
}
// 校验验证码
function checkCheck() {
    // 1.获取用户名
    var check = $("#check").val();
    // 2.定义正则
    var reg_check = /^[\s\S]*.*[^\s][\s\S]*$/;
    var flag = reg_check.test(check);
    if (flag) {
        $.post("user/checkCheckCode", {check: $("#check").val()}, function (data) {
            flag = data.flag;
            if (!data.flag) {
                $("#check_img").prop("src", "checkCode?" + new Date().getTime());
                $("#check").css("border", "1px solid red");
            } else {
                $("#check").css("border", "");
            }
        });
    } else {
        $("#check").css("border", "1px solid red");
    }

    return flag;
}

// 校验出生日期
function checkBirthday() {
    // 1.获取用户名
    var birthday = $("#birthday").val();
    // 2.定义正则
    var reg_birthday = /^[\s\S]*.*[^\s][\s\S]*$/;
    var flag = reg_birthday.test(birthday);
    if (flag) {
        $("#birthday").css("border", "");
    } else {
        $("#birthday").css("border", "1px solid red");
    }
    return flag;
}

/**
 *  展示route数据
 * @param cid 类型ID
 * @param currentPage 当前页码
 * @param rname 类型名称
 */
function load(cid, currentPage, rname) {
    // 发送Ajax请求,请求route/pageQuery,传递id
    $.get("route/pageQuery", {cid: cid, currentPage: currentPage, rname: rname}, function (data) {
        // 解析data数据,展示到页面

        // 1.分页工具条展示
        // 1.1 展示总页面总记录数
        $("#totalPage").html(data.totalPage);
        $("#totalCount").html(data.totalCount);

        // 1.2 展示分页页码
        var lis = "";
        var fristPage = '<li onclick="javascript:load('+cid+','+1+',\''+rname+'\');"><a href="javascript:void(0)">首页</a></li>';
        // 如果当前页时第一页,上一页等于当前页
        var upNum = data.currentPage - 1 > 0 ? data.currentPage - 1 : 1;
        var upPage = '<li class="threeword" onclick="javascript:load('+cid+','+upNum+',\''+rname+'\');"><a href="javascript:void(0)">上一页</a></li>';
        lis += fristPage;
        lis += upPage;
        /**
         *  1.一共显示10个页码,能够达到前5后4的效果
         *  2.如果前面不够5个,后面补齐10个
         *  3.如果后面不够4个,前面补齐10个
         */
            // 定义开始位置
        var begin;
        // 定义结束位置
        var end;

        // 1.要显示10个页码
        if (data.totalPage < 10) {
            // 总页码不够10页
            begin = 1;
            end = data.totalPage;
        } else {
            // 总页码超过10页
            begin = data.currentPage - 5;
            end = data.currentPage + 4;
            // 如果前面不够5个,后面补齐10个
            if (begin < 1) {
                begin = 1;
                end = begin + 9;
            }
            if (end > data.totalPage) {
                end = data.totalPage;
                begin = end - 9;
            }
        }

        for (var i = begin; i <= end; i++) {
            var li;
            if (data.currentPage == i) {
                // 创建页码的li
                li = ' <li class="curPage" onclick="javascript:load(' + cid + ',' + i + ',\''+rname+'\');"><a href="javascript:void(0)">' + i + '</a></li>';
            } else {
                // 创建页码的li
                li = ' <li onclick="javascript:load(' + cid + ',' + i + ',\''+rname+'\');"><a href="javascript:void(0)">' + i + '</a></li>';
            }
            // 拼接字符串
            lis += li;
        }
        // 如果当前页时最后一页,那么下一页等于当前页
        var downNum = data.currentPage + 1 <= data.totalPage ? data.currentPage + 1 : data.totalPage;
        var downPage = '<li class="threeword" onclick="javascript:load('+cid+','+downNum+',\''+rname+'\');"><a href="javascript:void(0)">下一页</a></li>';
        var endPage = '<li class="threeword" onclick="javascript:load('+cid+','+ data.totalPage+',\''+rname+'\');"><a href="javascript:void(0)">末页</a></li>';
        lis += downPage;
        lis += endPage;
        $("#pageNum").html(lis);

        // 2.列表数据展示
        var route_lis = "";
        for (var i = 0; i < data.list.length; i++) {
            // 获取routeList中数据
            var route = data.list[i];
            var li = '<li>\n' +
                '         <div class="img"><img src="' + route.rimage + '" alt="" style="width: 299px;"></div>\n' +
                '         <div class="text1">\n' +
                '             <p>' + route.rname + '</p>\n' +
                '             <br/>\n' +
                '             <p>' + route.routeIntroduce + '</p>\n' +
                '         </div>\n' +
                '         <div class="price">\n' +
                '             <p class="price_num">\n' +
                '                 <span>&yen;</span>\n' +
                '                 <span>' + route.price + '</span>\n' +
                '                 <span>起</span>\n' +
                '             </p>\n' +
                '             <p><a href="route_detail.html?rid='+route.rid+'">查看详情</a></p>\n' +
                '         </div>\n' +
                '     </li>';
            route_lis += li;
        }
        $("#route_ul").html(route_lis);
        // 定位到页面顶部
        window.scrollTo(0, 0);
    });
}