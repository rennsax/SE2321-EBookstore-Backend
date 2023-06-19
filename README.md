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
  };
  ```

  返回两种 status code: 200 (OK) or 401 (Unauthorized)。
  返回 OK 时，response body 包含当前用户类型：

  ```typescript
  type LoginResponseBody = {
    type: "SUPER" | "FORBIDDEN" | "NORMAL";
  };
  ```

### 用户信息 `/user`

暂时没有固定，等到后期加入了安全相关功能后再确定。

- PUT `/users`: 创建新用户，request body 须提供 account 和 passwd 字段。可能的两种 status: No Content, Conflict.

- PATCH `/users/{userId}`: 更新用户信息（目前只更新名字），返回 No Content 响应状态（目前没有做异常处理）。

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
