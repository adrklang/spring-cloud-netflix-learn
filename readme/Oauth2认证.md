### 密码模式认证



![image-20200915162549550](.\image\uaa\image-20200915162549550.png)

![image-20200915162703707](.\image\uaa\image-20200915162703707.png)

```markdown
通过再加一个`scope`参数，设置认证时scope方式，如web,app等
http://localhost:9720/oauth/check_token?token=b65142a1-4f71-416f-9dda-d3bc07513e3a #检查当前token是否有效
```

![image-20200915165206597](.\image\uaa\image-20200915165047247.png)

#### 刷新token

![image-20200915170121221](.\image\uaa\image-20200915170121221.png)