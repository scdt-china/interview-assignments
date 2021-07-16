## 
### 一、版本控制
- 框架： phalcon 4.0
- php： 7.3
- mysql： 5.7
- 编辑器： phpStorm

### 二、文件夹说明
- user.sql是数据库文件
- public是入口文件
- app文件夹是应用代码

| 文件夹     | 说明           | 
| :--------: |   :--------:   |
|  config | 配置和初始化服务    |
|  controllers  | 控制器  |
|  library | 服务代码 |
|  models | 数据库模型 |
|  views | 视图 |

### 三、 作业要求
- 作业要求的相关php代码都在app目录下

| 要求     |     说明           | 
| :--------: |    :--------:   |
|  网页入口  | reg/index  |   
|  api/register  | 对应路由在config\service.php中37行 |


### 四 接口返回数据说明
> 接口统一返货JSON格式的数组，具体如下表格,参考地址app\library\ErrorMessage.class.php：

| 字段     | 格式           | 说明              |  可以为空  |
| :--------: |   :--------:   | ----------------  | :----:         |
|  code | int  |错误码，为200代表成功，其他代表失败          |  否     |
|  msg  | string  | code对应的信息          |  否     |
|  data | array  | 返回的数据          |  是     |

