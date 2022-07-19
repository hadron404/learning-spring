# AOP

## Execution：切点表达式
> execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
>

这里问号表示当前项可以有也可以没有，其中各项的语义如下：

* modifiers-pattern：方法的可见性，如public，protected 
* ret-type-pattern：方法的返回值类型，如int，void等 
* declaring-type-pattern：方法所在类的全路径名，如com.spring.Aspect
* name-pattern：方法名类型，如businessService()
* param-pattern：方法的参数类型，如java.lang.String；
* throws-pattern：方法抛出的异常类型，如java.lang.Exception

### 示例
#### "execution(* com.example.demo.controller.*.*(..))"
* `*` 返回值为任意类型
* `com.example.demo.controller.*.*` com.example.demo.controller格式的包结构
* `.*.*` 任意类任意方法
* `(..)` 方法参数个数为0个或多个参数

#### "execution(* com.*.*.controller.*.*(..))"
* `*` 返回值为任意类型
* `com.*.*.controller` 符合com.*.*.controller格式的包结构，例：com.example.demo.controller,com.example.demo2.controller
* `.*.*` 任意类任意方法
* `(..)` 方法参数个数为0个或多个参数

#### "execution(* com..*.controller.*.*(..))"
* `*` 返回值为任意类型
* `com..*.controller` 符合com..*.controller格式的包结构，例：com.example.controller,com.example.demo2.controller,com.example.example2.controller
* `.*.*` 任意类任意方法
* `(..)` 方法参数个数为0个或多个参数


# 参考

1. [Spring AOP 切点表达式用法总结](https://www.cnblogs.com/zhangxufeng/p/9160869.html)
2. [Aop获取方法上的注解信息](http://loveshisong.cn/%E7%BC%96%E7%A8%8B%E6%8A%80%E6%9C%AF/2016-06-01-AOP%E4%B8%AD%E8%8E%B7%E5%8F%96%E6%96%B9%E6%B3%95%E4%B8%8A%E7%9A%84%E6%B3%A8%E8%A7%A3%E4%BF%A1%E6%81%AF.html)