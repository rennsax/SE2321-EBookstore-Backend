## Service 逻辑设计

### 基础逻辑

1. 用户登录，发送 POST 请求到后端 `/login` 路径。Response 正常，此时后端需要建立 Session/Cookie，client 端继续下一步。

2. 跳转到主页面，发送以下请求：

- GET `/books?limit=x`：获取指定数量的书籍用于主页呈现。
- GET `/user?{account}`：获取用户基础信息 `UserInfo`（包含用户主键和当前操作的订单主键），存储在 context 中。

3. 点击图书详情页，GET `/books/{uuid}` 获取某一本书籍的信息，呈现在页面。

### 订单相关逻辑

- 操作订单：BookDetailPage, CartPage
- 查看订单：CartPage, OrderPage

<hr/>

1. 在 BookDetailPage 点击加入购物车，PATCH `/orders/{orderId}`，请求体为书本的 `uuid`，添加一本书到订单中。不考虑恶意添加过多书本，这个操作总是成功。

2. 进入 CartPage，GET `/orders/{orderId}`，获取订单全部内容。返回为 JSON 数组，需要查询每个 JSON 对象 `uuid` 字段对应的书籍信息（GET `/books/{uuid}`），然后呈现在页面上。

3. 在 CartPage 中，可以依次增加/删除一本订单中的书籍，也可以删除所有的指定书籍。PATCH `/orders/{orderId}`。如果书本不够删除，应该拒绝操作。

4. 进入 OrderPage，GET `/orders?userId=...` 获取用户的所有订单信息，并依次 GET `/books/{uuid}` 获取书本的信息。呈现在页面上。

## Controller API 设计

采用 Restful 风格。

文档说明：如果不加说明，则 API 期待是实现为（Idempotent）的。

### 登录认证 `/login`

- POST：输入用户名和密码尝试登录。请求体实现接口：

  ```typescript
  type LoginInfo = {
    account: string;
    passwd: string;
  }
  ```
  返回两种 status code: 200 (OK) or 401 (Unauthorized)。
  返回 OK 时，response body 包含当前用户类型：

  ```typescript
  type LoginResponseBody = {
    type: "SUPER" | "FORBIDDEN" | "NORMAL";
  }
  ```

### 用户信息 `/user`

- GET `/{userId}`：通过用户数据库主键 id 获得用户数据，响应体内容：

  ```typescript
  type UserInfo = {
    readonly id: number;
    orderId: number;
    avatarId: number;
    name: string;
  }
  ```
  `orderId` 字段存储的是当前会话正在进行的 "pending" 订单主键，也就是购物车。每个用户必定会有一个对应购物车的订单，否则会在请求中创建。

- GET `?account=...`：通过用户账号 (unique) 获取用户数据，响应体内容同上。

### 书籍操作 `/books`

- GET `/books/?limit=x&offset=y`：获取 `x` 本书籍的信息，返回 `BookDTO`。

  ```java
  public class BookDTO {
      private UUID uuid;
      private String title;
      private String picId;
      private String price;
      private String author;
      private Date date;
      private String isbn;
      private String description;
  }
  ```

- GET `/books/{uuid}`：通过书籍表的 uuid 主键获取某一本书的信息，返回 `BookDTO`。

### 订单操作 `/orders`

URI `/orders` 是无效的（不允许直接获得数据库里的所有订单）。

- GET `/orders/{orderId}`：获取订单信息，返回 `OrderInfoDTO`。

  ```java
  public class OrderInfoDTO {
      private Integer id;
      private OrderState state;
      private Timestamp time;
      private String sumBudget;
      private List<BookOrderedDTO> bookOrderedList;
  }
  public class BookOrdered {
      private UUID uuid;
      private Integer quantity;
  }
  public class BookOrderedDTO extends BookOrdered {
      private String totalBudget;
  }
  ```

- PATCH `/orders/{orderId}`：更新订单，对于用户，用于增减书本或者提交订单。请求体：

  ```java
  public class OrderRequestBody {
      private String op;
      private List<BookOrdered> bookOrderedList;

      public static final String OP_UPDATE = "update items";
      public static final String OP_CHECKOUT = "checkout";
  }
  ```

- GET `?userId=...`：通过用户名主键获取用户的所有非 "pending" 状态订单，返回 `orderId` 的数组。