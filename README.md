# wechatdemo #
 * springboot for wechat

> ##开始开发
 ## 调试
 * 测试账号：
 > https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login 
 * 开发文档
 > https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1445241432 
  
  ## 验证url
  > /wechat (get请求)
  
  ## 请求接口
  > /wechat (post请求)
  
  ## access_token
   > 公众平台以access_token为接口调用凭据，来调用接口，所有接口的调用需要先获取access_token，access_token在2小时内有效，过期需要重新获取，但1天内获取次数有限(2000次)。
   > https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183
   
  ## 初始化appID及appsecret到redis中
  > 执行InitData中的main()
  * {"appID":"","appsecret":""} 
  
  