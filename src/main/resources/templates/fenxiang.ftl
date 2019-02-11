<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="renderer" content="webkit"/>

    <script type="application/javascript" src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-2.0.3.min.js"></script>

</head>
<body>

这是一个分享页面，666

<script type="application/javascript" src="http://res2.wx.qq.com/open/js/jweixin-1.4.0.js"></script>

<script type="application/javascript">
    wx.config({
        debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: '${jsSDK.appId}', // 必填，公众号的唯一标识
        timestamp: '${jsSDK.timestamp}', // 必填，生成签名的时间戳
        nonceStr: '${jsSDK.noncestr}', // 必填，生成签名的随机串
        signature: '${jsSDK.signature}',// 必填，签名
        jsApiList: ['onMenuShareAppMessage'] // 必填，需要使用的JS接口列表
    });

    // wx.ready(function () {   //需在用户可能点击分享按钮前就先调用
    //     wx.updateAppMessageShareData({
    //         title: '分享给我的朋友', // 分享标题
    //         desc: '这是一个神奇的链接', // 分享描述
    //         link: 'http://e7gjbp.natappfree.cc/fenxiang', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
    //         imgUrl: 'http://b198.photo.store.qq.com/psb?/V13aCDZF2zg2ID/RUAHCCCcSP4e8L6rRuvQHYbbAyiL0*8x9S8YUOskmO8!/b/dAkCBnZfIQAA&bo=vwOAAgAAAAABBx4!&rf=viewer_4&t=5', // 分享图标
    //         success: function () {
    //             // 设置成功
    //         }
    //     })
    // });

    wx.ready(function () {   //需在用户可能点击分享按钮前就先调用
        wx.onMenuShareAppMessage({
            title: '分享给我的朋友', // 分享标题
            desc: '这是一个神奇的链接', // 分享描述
            link: 'http://e7gjbp.natappfree.cc/fenxiang', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
            // imgUrl: 'http://imgsrc.baidu.com/imgad/pic/item/32fa828ba61ea8d34fd7b7109d0a304e251f5829.jpg', // 分享图标
            imgUrl :'http://b198.photo.store.qq.com/psb?/V13aCDZF2zg2ID/RUAHCCCcSP4e8L6rRuvQHYbbAyiL0*8x9S8YUOskmO8!/b/dAkCBnZfIQAA&bo=vwOAAgAAAAABBx4!&rf=viewer_4&t=5',
            success: function () {
                // 设置成功
                // alert(1212)
            },cancel: function () {
                // alert(4444)
            }

        })
    });


</script>


</body>
</html>