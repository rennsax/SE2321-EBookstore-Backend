## Service 逻辑设计

### 基础逻辑

1. 用户登录，发送 POST 请求到后端 `/login` 路径。若响应体为真，此时后端需要建立 Session/Cookie，client 端继续下一步。

2. 跳转到主页面，发送以下请求：

- GET `/book?limit=x`：获取指定数量的书籍用于主页呈现。
- GET `/user?{account}`：获取用户基础信息 `UserInfo`（包含用户主键和当前操作的订单主键），存储在 context 中。

3. 点击图书详情页，GET `/book/{uuid}` 获取某一本书籍的信息，呈现在页面。

### 订单相关逻辑

- 操作订单：BookDetailPage, CartPage
- 查看订单：CartPage, OrderPage

<hr/>

1. 在 BookDetailPage 点击加入购物车，PATCH `/order/{orderId}`，请求体为书本的 `uuid`，添加一本书到订单中。不考虑恶意添加过多书本，这个操作总是成功。

2. 进入 CartPage，GET `/order/{orderId}`，获取订单全部内容。返回为 JSON 数组，需要查询每个 JSON 对象 `uuid` 字段对应的书籍信息（GET `/book/{uuid}`），然后呈现在页面上。

3. 在 CartPage 中，可以依次增加/删除一本订单中的书籍，也可以删除所有的指定书籍。PATCH `/order/{orderId}`。如果书本不够删除，应该拒绝操作。

4. 进入 OrderPage，GET `/order?userId=...` 获取用户的历史订单 id；依次 GET `/order/{orderId}` 获取订单信息为 `BookOrdered[]`；依次 GET `/book/{uuid}` 获取书本的信息。呈现在页面上。

## Controller API 设计

采用 Restful 风格。

### 登录认证 `/login`

- POST：输入用户名和密码尝试登录。请求体实现接口：

  ```typescript
  interface LoginInfo {
    account: string;
    passwd: string;
  }
  ```
  返回两种 status code: 204 (No Content) or 401 (Unauthorized)。

### 用户信息 `/user`

- GET `/{userId}`：通过用户数据库主键 id 获得用户数据，响应体内容：

  ```typescript
  interface UserInfo {
    readonly id: number;
    orderId: number;
    // TODO
  }
  ```
  `orderId` 字段存储的是当前会话正在进行的 "pending" 订单主键。

- GET `?account=...`：通过用户账号 (unique) 获取用户数据，响应体内容同上。

以下为注册、注销、更改信息等逻辑：
TODO

### 书籍操作 `/book`

- GET `?limit=x`：获取 `x` 本书籍的信息（数据库前 `x` 本），返回为 JSON 数组，数组中的 JSON 对象：

  ```typescript
  /** 这个接口基本和后端的 entity 一致 */
  interface Book {
    readonly uuid: string; // primary key
    title: string;
    author: string;
    price: number;
    picId: string;
    date: string;
    isbn: string;
    description: string;
    /** 暂时没有数据 */
    intro?: string;
  }
  ```

- GET `/{uuid}`：通过书籍表的 uuid 主键获取某一本书的信息。

### 订单操作 `/order`

- GET `/pending?account=...`：通过用户账户获取 pending 状态的订单，返回订单主键。

- GET `/{orderId}`：获取订单的全部信息，返回 JSON 数组，包含若干 JSON 对象，实现接口：

  ```typescript
  interface OrderInfo {
    readonly id: number; // 订单主键
    readonly time: Date; // 订单时间戳, Java 中为 java.sql.Timestamp
    readonly orderState: string;
    readonly bookOrderedList: BookOrdered[];
  }

  interface BookOrdered {
    readonly uuid: string;
    quantity: number;
  }
  ```

- PATCH `/{orderId}`：更新订单内容，一般是添加/删除书籍。请求体也实现 `BookOrdered` 接口，其中 `quantity` 可以为任意非零值。大于零时表示放入书本，小于零时表示删除书本。注意：只能更改 "pending" 状态的订单信息。

- POST `/submit`：请求体为 `orderId=...`，表示将订单提交。

- GET `?userId=...`：通过用户名主键获取用户的所有非 "pending" 状态订单（默认），返回一个 `orderId` 数组。<br/>可以额外实现参数 `limit=x`，表示至多返回多少订单（按时间戳从晚到早）。