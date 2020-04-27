/**
 * 获得访客量，除文章显示界面外其他界面访客量通用
 * @type {string}
 */
function getVisitorNum() {
    let pageName = window.location.pathname + window.location.search;
    $.ajax({
        type: 'GET',
        url: '/visitor/visitorNumByPageName',
        dataType: 'JSON',
        contentType: "application/json;charsetset=UTF-8",
        data: {
            pageName: pageName.substring(1)
        },
        // async: false,
        success: function (data) {
            // if(data.success){
            //     $("#totalVisitors").html(data['data']['totalVisitor']);
            //     $("#visitorVolume").html(data['data']['pageVisitor']);
            // } else {
            //     $("#totalVisitors").html(0);
            //     $("#visitorVolume").html(0);
            // }
        },
        error: function () {
        }
    });
}

/**
 * 获取验证码图片
 */
function getVerifyCode(selector) {
    let noticeBox = $('.login-form .notice-box');
    $.ajax({
        url: '/verify/imgCode',
        type: 'GET',
        // async: false,
        success: function (data) {
            if (data.success) {
                let imgUrl = data.data;
                $(selector).attr('src', imgUrl);
            }
        },
        error: function () {
            showNoticeBox(noticeBox, "网络异常，验证码获取失败，请稍后重试！")
        }
    })
}


$.extend({
    /**
     * 时间转换工具
     */
    dateFormat: function (date, fmt) {
        date = new Date(date);
        let ret;
        const opt = {
            "Y+": date.getFullYear().toString(),        // 年
            "m+": (date.getMonth() + 1).toString(),     // 月
            "d+": date.getDate().toString(),            // 日
            "H+": date.getHours().toString(),           // 时
            "M+": date.getMinutes().toString(),         // 分
            "S+": date.getSeconds().toString()          // 秒
            // 有其他格式化字符需求可以继续添加，必须转化成字符串
        };
        for (let k in opt) {
            ret = new RegExp("(" + k + ")").exec(fmt);
            if (ret) {
                fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
            }
        }
        return fmt;
    },

    /**
     * url实体类参数拼接
     *
     * @param obj
     * @returns {string}
     */
    params: function(obj) {
        let result = '';
        let item;
        for (item in obj) {
            if (obj[item] && String(obj[item])) {
                result += `&${item}=${obj[item]}`;
            }
        }
        if (result) {
            result = '?' + result.slice(1);
        }
        return result;
    }
});

/**
 * unicode转换
 */
$.extend({
    //unicode编码,例:$.enUnicode("中文,eng")
//结果:"\u4e2d\u6587,eng"
    enUnicode: function (v) {
        let ascii = "";
        for (let i = 0; i < v.length; i++) {
            let code = Number(v[i].charCodeAt(0));
            if (code > 127) {
                let charAscii = code.toString(16);
                charAscii = new String("0000").substring(charAscii.length, 4) + charAscii;
                ascii += "\\u" + charAscii;
            } else {
                ascii += v[i];
            }
        }
        return ascii;
    },
    //unicode解码,例:$.deUnicode("\u4e2d\u6587,eng")
//结果:"中文,eng"
    deUnicode: function (v) {
        let r = v.match(/\\u[0-9a-fA-F]{4}/g);
        if (r == null) {
            return v;
        }
        for (let i = 0; i < r.length; i++) {
            v = v.replace(r[i], unescape(r[i].replace("\\u", "%u")));
        }
        return v;
    }
});

/**
 * 提示盒子
 *
 * @param notice
 * @param msg
 */
function showNoticeBox(notice, msg) {
    notice.children('.notice-content').html(msg);
    notice.show();
    // 定时关闭错误提示框
    let closeNoticeBox = setTimeout(function () {
        notice.hide();
    }, 5000);
}

/**
 * 数字增加动画
 */
(function ($) {

    function isInt(num) {
        //作用:是否为整数
        //返回:true是 false否
        let res = false;
        try {
            if (String(num).indexOf(".") == -1 && String(num).indexOf(",") == -1) {
                res = parseInt(num) % 1 === 0 ? true : false;
            }
        } catch (e) {
            res = false;
        }
        return res;
    }

    function isFloat(num) {
        //作用:是否为小数
        //返回:小数位数(-1不是小数)
        let res = -1;
        try {
            if (String(num).indexOf(".") != -1) {
                let index = String(num).indexOf(".") + 1; //获取小数点的位置
                let count = String(num).length - index; //获取小数点后的个数
                if (index > 0) {
                    res = count;
                }
            }
        } catch (e) {
            res = -1;
        }
        return res;
    }

    $.fn.numScroll = function (options) {

        let settings = $.extend({
            'time': 1500,
            'delay': 0
        }, options);

        return this.each(function () {
            let $this = $(this);
            let $settings = settings;

            let num = $this.attr("data-num") || $this.text(); //实际值
            let temp = 0; //初始值
            $this.text(temp);
            let numIsInt = isInt(num);
            let numIsFloat = isFloat(num);
            let step = (num / $settings.time) * 10; //步长

            setTimeout(function () {
                let numScroll = setInterval(function () {
                    if (numIsInt) {
                        $this.text(Math.floor(temp));
                    } else if (numIsFloat != -1) {
                        $this.text(temp.toFixed(numIsFloat));
                    } else {
                        $this.text(num);
                        clearInterval(numScroll);
                        return;
                    }
                    temp += step;
                    if (temp > num) {
                        $this.text(num);
                        clearInterval(numScroll);
                    }
                }, 1);
            }, $settings.delay);

        });
    };

})(jQuery);

/**
 * 验证网址格式
 *
 * 例子：
 * var url ="sojson.com";
 * var parser = new URLParser(url);
 * //解析
 * parser.parse();
 * //判断是否正常
 * if (parser.hasError()) {
 *      //错误信息
 *      var msg = parser.getMessage();
 *      return;
 * }
 * //获取域名
 * domain = encodeURI(parser.getModifyName());
 *
 * @param url
 * @constructor
 */
function URLParser(url) {
    let input = url;
    let modifyName = url;
    let b_error = false;
    let message = "";
    URLParser.prototype.parse = function () {
        if (!input || input.length == 0) {
            failMessage('请填写域名，例如：www.jzh.plus');
            return;
        }
        let labels = parseLabels();
        if (hasError()) {
            return;
        }
        if (labels.length == 1) {
            failMessage('域名格式错误。请输入正确的域名格式，以“.”进行区分 ');
            return;
        }
        let topLabel = labels[labels.length - 1];
        if (isDigitLabels(topLabel)) {
            failMessage("域名格式错误。请输入正确的域名格式，以“.”进行区分 ");
            return;
        }
        if (input.length > 255) {
            failMessage('域名过长。每标号不得超过63个字符。由多个标号组成的完整域名总共不超过255个字符。');
            return;
        }
        let topLevel = parseTopLevel(labels);
        if (topLevel.labelIndex == 0) {
            failMessage(topLevel.name + '是域名后缀，不能查询。');
            return;
        }
        let secondLevel = parseSecondLevel(labels, topLevel);
        if (secondLevel.labelIndex != 0 && topLevel.recognized) {
            modifyName = secondLevel.name + "." + topLevel.name;
        }
    };

    URLParser.prototype.getModifyName = function () {
        return modifyName;
    };

    function hasError() {
        return b_error;
    }

    URLParser.prototype.hasError = hasError;

    URLParser.prototype.getMessage = function () {
        if (hasError()) {
            return message;
        } else {
            return null;
        }
    };

    function parseLabels() {
        let labels = new Array();
        let offset = 0;
        let regExpHttp = /^(http:\/{2})/;
        let regExpHttps = /^(https:\/{2})/;
        if (regExpHttp.test(input)) {
            // console.log("http");
            offset = 7;
        } else if (regExpHttps.test(url)) {
            // console.log("https");
            offset = 8;
        }
        while (offset < input.length) {
            let label = parseLabel();
            if (!hasError() && label) {
                labels.push(label);
            } else {
                return;
            }
        }
        return labels;

        function parseLabel() {
            let labelArr = new Array();
            let start = offset;
            while (offset < input.length) {
                let ch = input.charAt(offset);
                let invalid = false;
                if (start == offset && !isLetterOrDigit(ch)) {
                    invalid = true;
                } else if ((offset + 1 == input.length || input.charAt(offset + 1) == '.') && !isLetterOrDigit(ch)) {
                    invalid = true;
                } else if (!isLabelChar(ch)) {
                    invalid = true;
                }
                if (invalid) {
                    failMessage('格式错误。域名一般由英文字母、汉字、阿拉伯数字、"-"组成，用“.”分隔，且每段不能以“.”、"-”开头和结尾');
                    return;
                } else {
                    labelArr.push(ch);
                    offset++;
                    if ((offset < input.length && input.charAt(offset) == '.') || (offset == input.length)) {
                        if (offset < input.length && input.charAt(offset) == '.') {
                            offset++;
                        }
                        if (labelArr.length > 63) {
                            failMessage('域名过长。每标号不得超过63个字符。由多个标号组成的完整域名总共不超过255个字符。');
                            return;
                        }
                        return labelArr.join("");
                    }

                }
            }
        }
    }

    function isLabelChar(ch) {
        if (ch.charCodeAt(0) <= 127) {
            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || (ch == '-')) {
                return true;
            } else {
                return false;
            }
        } else {
            if ((ch.charCodeAt(0) >= 0xFF00 && ch.charCodeAt(0) <= 0xFFEF) ||
                (ch.charCodeAt(0) >= 0x3000 && ch.charCodeAt(0) <= 0x303F)
            ) {
                return false;
            } else {
                return true;
            }
        }
    }

    function isLetterOrDigit(ch) {
        if (ch.charCodeAt(0) <= 127) {
            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9')) {
                return true;
            } else {
                return false;
            }
        } else {
            if ((ch.charCodeAt(0) >= 0xFF00 && ch.charCodeAt(0) <= 0xFFEF) ||
                (ch.charCodeAt(0) >= 0x3000 && ch.charCodeAt(0) <= 0x303F)
            ) {
                return false;
            } else {
                return true;
            }
        }
    }

    function isDigitLabels(label) {
        let i = 0;
        while (i < label.length) {
            let ch = label.charAt(i);
            if (!(ch >= '0' && ch <= '9')) {
                return false;
            }
            i++;
        }
        return true;
    }

    function parseTopLevel(labels) {
        let topLevelName = "";
        let lowTopLevelName = "";
        let topLevel;
        let index;
        if (labels.length >= 2) {
            topLevelName = labels[labels.length - 2] + "." + labels[labels.length - 1];
            lowTopLevelName = topLevelName.toLowerCase();
            for (index = 0; index < tow_top_level.length; index++) {
                if (lowTopLevelName == tow_top_level[index]) {
                    topLevel = new TopLevel(topLevelName, 2, labels.length - 2, true);
                    break;
                }
            }
        }
        if (!topLevel) {
            topLevelName = labels[labels.length - 1];
            lowTopLevelName = topLevelName.toLowerCase();
            for (index = 0; index < one_top_level.length; index++) {
                if (lowTopLevelName == one_top_level[index]) {
                    topLevel = new TopLevel(topLevelName, 1, labels.length - 1, true);
                    break;
                }
            }
        }
        if (!topLevel) {
            topLevelName = labels[labels.length - 1];
            topLevel = new TopLevel(topLevelName, 1, labels.length - 1, false);
        }
        return topLevel;
    }

    function TopLevel(name, labelCount, labelIndex, recognized) {
        this.name = name;
        this.labelCount = labelCount;
        this.labelIndex = labelIndex;
        this.recognized = recognized;
        return this;
    }

    function parseSecondLevel(labels, topLevel) {
        let secondLevel = new SecondLevel(labels[topLevel.labelIndex - 1], 1, topLevel.labelIndex - 1);
        return secondLevel;
    }

    function SecondLevel(name, labelCount, labelIndex) {
        this.name = name;
        this.labelCount = labelCount;
        this.labelIndex = labelIndex;
        return this;
    }

    function failMessage(msg) {
        message = msg;
        b_error = true;
    }

    let one_top_level = ['ac', 'ad', 'ae', 'aero', 'af', 'ag', 'ai', 'al', 'am', 'an', 'ao', 'aq',
        'ar', 'arpa', 'as', 'asia', 'at', 'au', 'aw', 'ax', 'az', 'ba', 'bb', 'bd',
        'be', 'bf', 'bg', 'bh', 'bi', 'biz', 'bj', 'bl', 'bm', 'bn', 'bo', 'bq',
        'br', 'bs', 'bt', 'bv', 'bw', 'by', 'bz', 'ca', 'cat', 'cc', 'cd', 'cf',
        'cg', 'ch', 'ci', 'ck', 'cl', 'cm', 'cn', 'co', 'com', 'coop', 'cr', 'cu',
        'cv', 'cw', 'cx', 'cy', 'cz', 'de', 'dj', 'dk', 'dm', 'do', 'dz', 'ec',
        'edu', 'ee', 'eg', 'eh', 'er', 'es', 'et', 'eu', 'fi', 'fj', 'fk', 'fm',
        'fo', 'fr', 'ga', 'gb', 'gd', 'ge', 'gf', 'gg', 'gh', 'gi', 'gl', 'gm',
        'gn', 'gov', 'gp', 'gq', 'gr', 'gs', 'gt', 'gu', 'gw', 'gy', 'hk', 'hm',
        'hn', 'hr', 'ht', 'hu', 'id', 'ie', 'il', 'im', 'in', 'info', 'int',
        'io', 'iq', 'ir', 'is', 'it', 'je', 'jm', 'jo', 'jobs', 'jp', 'ke',
        'kg', 'kh', 'ki', 'km', 'kn', 'kp', 'kr', 'kw', 'ky', 'kz', 'la',
        'lb', 'lc', 'li', 'lk', 'lr', 'ls', 'lt', 'lu', 'lv', 'ly', 'ma', 'mc',
        'md', 'me', 'mf', 'mg', 'mh', 'mil', 'mk', 'ml', 'mm', 'mn', 'mo',
        'mobi', 'mp', 'mq', 'mr', 'ms', 'mt', 'mu', 'museum', 'mv', 'mw',
        'mx', 'my', 'mz', 'na', 'name', 'nc', 'ne', 'net', 'nf', 'ng',
        'ni', 'nl', 'no', 'np', 'nr', 'nu', 'nz', 'om', 'org', 'pa',
        'pe', 'pf', 'pg', 'ph', 'pk', 'pl', 'plus', 'pm', 'pn', 'post', 'pr',
        'pro', 'ps', 'pt', 'pw', 'py', 'qa', 're', 'ro', 'rs', 'ru',
        'rw', 'sa', 'sb', 'sc', 'sd', 'se', 'sg', 'sh', 'si', 'sj',
        'sk', 'sl', 'sm', 'sn', 'so', 'sr', 'ss', 'st', 'su', 'sv', 'sx',
        'sy', 'sz', 'tc', 'td', 'tel', 'tf', 'tg', 'th', 'tj', 'tk', 'tl',
        'tm', 'tn', 'to', 'tp', 'tr', 'travel', 'tt', 'tv', 'tw', 'tz',
        'ua', 'ug', 'uk', 'um', 'us', 'uy', 'uz', 'va', 'vc', 've', 'vg',
        'vi', 'vn', 'vu', 'wf', 'ws', '中国', '中國', '香港', '台湾', '台灣',
        '新加坡', 'xxx', 'ye', 'yt', 'za', 'zm', 'zw', 'zw', 'xn--fiqs8s',
        'xn--fiqz9s', 'xn--j6w193g', 'xn--kprw13d', 'xn--kpry57d',
        'xn--yfro4i67o', '公司', '网络', '網絡', 'xn--55qx5d', 'xn--io0a7i'];
    let tow_top_level = ['ac.cn', 'com.cn', 'edu.cn', 'gov.cn', 'mil.cn', 'net.cn', 'org.cn', 'bj.cn',
        'sh.cn', 'tj.cn', 'cq.cn', 'he.cn', 'sx.cn', 'nm.cn', 'ln.cn', 'jl.cn',
        'hl.cn', 'js.cn', 'zj.cn', 'ah.cn', 'fj.cn', 'jx.cn', 'sd.cn', 'ha.cn',
        'hb.cn', 'hn.cn', 'gd.cn', 'gx.cn', 'hi.cn', 'sc.cn', 'gz.cn', 'yn.cn',
        'xz.cn', 'sn.cn', 'gs.cn', 'qh.cn', 'nx.cn', 'xj.cn', 'tw.cn', 'hk.cn',
        'mo.cn', 'com.af', 'net.af', 'org.af', 'com.ag', 'net.ag', 'org.ag', 'co.at',
        'or.at', 'com.bi', 'edu.bi', 'info.bi', 'mo.bi', 'or.bi', 'org.bi', 'com.br',
        'net.br', 'org.br', 'co.bz', 'com.bz', 'net.bz', 'org.bz', 'co.cm', 'com.cm',
        'net.cm', 'com.co', 'net.co', 'nom.co', 'ar.com', 'br.com', 'cn.com', 'de.com',
        'eu.com', 'gb.com', 'gr.com', 'hu.com', 'jpn.com', 'kr.com', 'no.com',
        'ru.com', 'se.com', 'uk.com', 'us.com', 'za.com', 'com.de', 'co.dm', 'com.ec',
        'fin.ec', 'info.ec', 'med.ec', 'net.ec', 'pro.ec', 'com.es', 'nom.es',
        'org.es', 'co.gg', 'net.gg', 'org.gg', 'co.gl', 'com.gl', 'edu.gl', 'net.gl',
        'org.gl', 'com.gr', 'co.gy', 'com.gy', 'net.gy', 'com.hk', 'edu.hk', 'gov.hk',
        'idv.hk', 'net.hk', 'org.hk', 'com.hn', 'net.hn', 'org.hn', 'adult.ht', 'com.ht',
        'info.ht', 'net.ht', 'org.ht', 'org.il', 'co.in', 'firm.in', 'gen.in', 'ind.in',
        'net.in', 'org.in', 'bz.it', 'co.it', 'co.je', 'net.je', 'org.je', 'com.ki',
        'net.ki', 'org.ki', 'co.kr', 'ne.kr', 'or.kr', 'pe.kr', 're.kr', 'seoul.kr',
        'com.lc', 'net.lc', 'org.lc', 'co.mg', 'com.mg', 'net.mg', 'org.mg', 'ac.mu',
        'co.mu', 'com.mu', 'net.mu', 'org.mu', 'com.mx', 'gb.net', 'hu.net', 'jp.net',
        'se.net', 'uk.net', 'com.nf', 'net.nf', 'org.nf', 'co.nl', 'net.nz', 'org.nz',
        'ae.org', 'us.org', 'com.pe', 'net.pe', 'org.pe', 'com.ph', 'com.pk', 'net.pk',
        'org.pk', 'biz.pl', 'com.pl', 'info.pl', 'net.pl', 'org.pl', 'waw.pl', 'aaa.pro',
        'aca.pro', 'acct.pro', 'avocat.pro', 'bar.pro', 'cpa.pro', 'eng.pro', 'jur.pro',
        'law.pro', 'med.pro', 'recht.pro', 'com.ps', 'net.ps', 'org.ps', 'com.pt', 'edu.pt',
        'org.pt', 'com.ru', 'net.sb', 'org.sb', 'com.sc', 'net.sc', 'org.sc', 'com.sg',
        'com.so', 'net.so', 'org.so', 'club.tw', 'com.tw', 'ebiz.tw', 'game.tw', 'idv.tw',
        'org.tw', 'me.uk', 'org.uk', 'co.uz', 'com.uz', 'com.vc', 'net.vc', 'org.vc',
        'ac.vn', 'biz.vn', 'com.vn', 'edu.vn', 'gov.vn', 'health.vn', 'info.vn', 'int.vn',
        'name.vn', 'net.vn', 'org.vn', 'pro.vn'];
}

/**
 * textarea 限制输入字数
 * @param limit     限制字数
 * @param elem      要显示字数的对象
 */
(function ($) {
    $.fn.extend({
        limiter: function (limit, elem) {
            $(this).on("keyup focus", function () {
                setCount(this, elem);
            });

            function setCount(src, elem) {
                let chars = 0;
                if (src.value) {
                    chars = src.value.length;
                }
                if (chars > limit) {
                    src.value = src.value.substr(0, limit);
                    chars = limit;
                }
                elem.html(chars);
            }

            setCount($(this), elem);
        }
    });
})(jQuery);