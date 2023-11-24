# 后端code返回值含义

***

## code: 0

1. 请求成功，正常获取返回值data
> 接口：
> firstRequest
> list
> getById
> listBy
> checkAccount
> Add
> insert
> update
> delete
> deleteByIds

``` Java
result = new Result(0, "Success", ...);
```

***

## code: 1

1. 用户未登录
> 拦截器

``` Java
Result result = new Result(1, "NOT_LOGIN", null);
```

2. 密码错误
> 接口：
> checkAccount

``` Java
result = new Result(1, "WrongPassword", null);
```

3. 缺少必要的条件
> 接口：
> insert

``` Java
result = new Result(1, "Error: missing 'msg' or 'startTime' or 'endTime'", null);
```

4. 权限不足
> 接口：
> Add
> insert
> update
> delete
> deleteByIds

``` Java
result = new Result(1,"PermissionDenied",null);
```

***

## Code: 2

1. 结束时间大于开始时间
> 接口：
> insert

``` Java
result = new Result(2, "Error: The endTime is earlier than startTime", null);
```
