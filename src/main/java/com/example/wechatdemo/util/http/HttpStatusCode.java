package com.example.wechatdemo.util.http;

/**
 * @author Lee
 * @date 2016-05-04
 * @describe http状态码
 */
public class HttpStatusCode {
	/**
     * HTTP Status-Code 200: 正常;请求已完成。
     */
    public static final int HTTP_OK = 200;

    /**
     * HTTP Status-Code 201: 正常;紧接POST命令。
     */
    public static final int HTTP_CREATED = 201;

    /**
     * HTTP Status-Code 202: 正常;已接受用于处理，但处理尚未完成。
     */
    public static final int HTTP_ACCEPTED = 202;

    /**
     * HTTP Status-Code 203: 正常;部分信息，返回 的信息只是一部分。
     */
    public static final int HTTP_NOT_AUTHORITATIVE = 203;

    /**
     * HTTP Status-Code 204: 正常；无响应 — 已接收请求，但不存在要回送的信息。
     */
    public static final int HTTP_NO_CONTENT = 204;

    /**
     * HTTP Status-Code 205: Reset Content。
     */
    public static final int HTTP_RESET = 205;

    /**
     * HTTP Status-Code 206: Partial Content。
     */
    public static final int HTTP_PARTIAL = 206;

    /* 3XX: relocation/redirect */

    /**
     * HTTP Status-Code 300: Multiple Choices。
     */
    public static final int HTTP_MULT_CHOICE = 300;

    /**
     * HTTP Status-Code 301: 已移动 — 请求的数据具有新的位置且更改是永久的。
     */
    public static final int HTTP_MOVED_PERM = 301;

    /**
     * HTTP Status-Code 302: 已找到 — 请求的数据临时具有不同 URI。
     */
    public static final int HTTP_MOVED_TEMP = 302;

    /**
     * HTTP Status-Code 303: 请参阅其它 — 可在另一 URI 下找到对请求的响应，且应使用 GET 方法检索此响应。
     */
    public static final int HTTP_SEE_OTHER = 303;

    /**
     * HTTP Status-Code 304: 未修改 — 未按预期修改文档。
     */
    public static final int HTTP_NOT_MODIFIED = 304;

    /**
     * HTTP Status-Code 305: 使用代理 — 必须通过位置字段中提供的代理来访问请求的资源。
     */
    public static final int HTTP_USE_PROXY = 305;

    /* 4XX: client error */

    /**
     * HTTP Status-Code 400: 错误请求 — 请求中有语法问题，或不能满足请求。
     */
    public static final int HTTP_BAD_REQUEST = 400;

    /**
     * HTTP Status-Code 401: 未授权 — 未授权客户机访问数据。
     */
    public static final int HTTP_UNAUTHORIZED = 401;

    /**
     * HTTP Status-Code 402: 需要付款 — 表示计费系统已有效。
     */
    public static final int HTTP_PAYMENT_REQUIRED = 402;

    /**
     * HTTP Status-Code 403: 禁止 — 即使有授权也不需要访问。
     */
    public static final int HTTP_FORBIDDEN = 403;

    /**
     * HTTP Status-Code 404: 找不到 — 服务器找不到给定的资源；文档不存在。
     */
    public static final int HTTP_NOT_FOUND = 404;

    /**
     * HTTP Status-Code 405: Method Not Allowed。
     */
    public static final int HTTP_BAD_METHOD = 405;

    /**
     * HTTP Status-Code 406: Not Acceptable。
     */
    public static final int HTTP_NOT_ACCEPTABLE = 406;

    /**
     * HTTP Status-Code 407: 代理认证请求 — 客户机首先必须使用代理认证自身。
     */
    public static final int HTTP_PROXY_AUTH = 407;

    /**
     * HTTP Status-Code 408: Request Time-Out。
     */
    public static final int HTTP_CLIENT_TIMEOUT = 408;

    /**
     * HTTP Status-Code 409: Conflict。
     */
    public static final int HTTP_CONFLICT = 409;

    /**
     * HTTP Status-Code 410: Gone。
     */
    public static final int HTTP_GONE = 410;

    /**
     * HTTP Status-Code 411: Length Required。
     */
    public static final int HTTP_LENGTH_REQUIRED = 411;

    /**
     * HTTP Status-Code 412: Precondition Failed。
     */
    public static final int HTTP_PRECON_FAILED = 412;

    /**
     * HTTP Status-Code 413: Request Entity Too Large。
     */
    public static final int HTTP_ENTITY_TOO_LARGE = 413;

    /**
     * HTTP Status-Code 414: Request-URI Too Large。
     */
    public static final int HTTP_REQ_TOO_LONG = 414;

    /**
     * HTTP Status-Code 415: 介质类型不受支持 — 服务器拒绝服务请求，因为不支持请求实体的格式。
     */
    public static final int HTTP_UNSUPPORTED_TYPE = 415;

    /* 5XX: server error */

    /**
     * HTTP Status-Code 500: Internal Server Error。
     * @deprecated   it is misplaced and shouldn't have existed。
     */
    @Deprecated
    public static final int HTTP_SERVER_ERROR = 500;

    /**
     * HTTP Status-Code 500: 内部错误 — 因为意外情况，服务器不能完成请求。
     */
    public static final int HTTP_INTERNAL_ERROR = 500;

    /**
     * HTTP Status-Code 501: 未执行 — 服务器不支持请求的工具。
     */
    public static final int HTTP_NOT_IMPLEMENTED = 501;

    /**
     * HTTP Status-Code 502: 错误网关 — 服务器接收到来自上游服务器的无效响应。
     */
    public static final int HTTP_BAD_GATEWAY = 502;

    /**
     * HTTP Status-Code 503: 错误网关 — 服务器接收到来自上游服务器的无效响应。
     */
    public static final int HTTP_UNAVAILABLE = 503;

    /**
     * HTTP Status-Code 504: Gateway Timeout。
     */
    public static final int HTTP_GATEWAY_TIMEOUT = 504;

    /**
     * HTTP Status-Code 505: HTTP Version Not Supported。
     */
    public static final int HTTP_VERSION = 505;

	
}
