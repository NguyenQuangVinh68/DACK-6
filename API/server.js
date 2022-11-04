const fs = require("fs");
const bodyParser = require("body-parser");
const jwt = require("jsonwebtoken");
const jsonServer = require("json-server");

const server = jsonServer.create();
const router = jsonServer.router("./data.json");
const db = JSON.parse(fs.readFileSync("./data.json", "UTF-8"));

server.use(bodyParser.urlencoded({ extended: true }));
server.use(bodyParser.json());
server.use(jsonServer.defaults());

const SECRET_KEY = "123456789";
const expiresIn = "1h";

// Create a token from a payload
function createToken(payload) {
  return jwt.sign(payload, SECRET_KEY, { expiresIn });
}

// Verify the token
function verifyToken(token) {
  return jwt.verify(token, SECRET_KEY, (err, decode) =>
    decode !== undefined ? decode : err
  );
}

// kiểm tra token nếu đúng thì trả về đúng cho ta và dữ liệu
function getDataFromToken(authorization) {
  // kiểm tra xem đã gắn bearer token chưa
  if (authorization === undefined || authorization.split(" ")[0] !== "Bearer")
    return 1;

  let verifyTokenResult;
  verifyTokenResult = verifyToken(authorization.split(" ")[1]);

  // kiểm tra token có được cung cấp hay ko
  if (verifyTokenResult instanceof Error) return 2;
  // trả về thông tin người dùng đăng nhập
  else return verifyTokenResult;
}

// Check if the user exists in database
function isAuthenticated({ email, password }) {
  return (
    db.users.findIndex(
      (user) => user.email === email && user.password === password
    ) !== -1
  );
}

// find email in data
function verificationEmail({ email }) {
  return db.users.findIndex((user) => user.email === email) !== -1;
}
// find password in data

// register
server.post("/auth/register", (req, res) => {
  console.log("register endpoint called; request body:");
  const { firstName, lastName, email, password } = req.body;

  if (verificationEmail({ email }) === true) {
    const status = 401;
    const message = "Email  already exist";
    res.status(status).json({ status, message });
    return;
  }

  // Get the id of last user
  var id = 1;
  if (db.users.length >= 1) {
    id = db.users.length + 1;
  }
  //Add new user
  db.users.push({
    id: id,
    firstName: firstName,
    lastName: lastName,
    email: email,
    password: password,
  }); //add some data
  fs.writeFileSync("./data.json", JSON.stringify(db), () => {
    if (err) return console.log(err);
  });
  res.status(200).json({
    status: 200,
    message: "Register sucessfully",
  });
});

// login
server.post("/auth/login", (req, res) => {
  console.log("login endpoint called; request body:");
  console.log(req.body);
  const { email, password } = req.body;
  if (isAuthenticated({ email, password }) == false) {
    const status = 401;
    const message = "Incorrect email or password";
    res.status(status).json({ status, message });
    return;
  }
  const access_token = createToken({ email, password });
  console.log("Access Token:" + access_token);
  res.status(200).json({
    status: 200,
    message: "Login sucessfully",
    user: req.body,
    accessToken: access_token,
  });
});

// logout
server.post("/auth/logout", (req, res) => {
  console.log("this is logout");
  let getTokenFormHeader = req.headers.authorization;
  let authorization = getDataFromToken(getTokenFormHeader);

  if (authorization == 1) {
    return res.status(401).json({
      status: 401,
      message: "Missing Authorization header.",
    });
  } else if (authorization == 2) {
    return res.status(401).json({
      status: 401,
      message:
        "Access token not provided. You can't logout when you not logged in",
    });
  } else {
    return res.status(200).json({
      status: 200,
      message: "logouted",
    });
  }
});

// forgot password
server.put("/auth/forgotpassword", (req, res) => {
  console.log("forgot password");

  const { email, password } = req.body;
  if (verificationEmail({ email }) == false) {
    res.status(401).json({
      status: 401,
      message: "Email does not exist",
    });
    return;
  }
  if (isAuthenticated({ email, password }) == true) {
    res.status(401).json({
      status: 401,
      message: "you need change new password",
    });
    return;
  }

  fs.readFile("./data.json", (err, data) => {
    if (err) {
      const status = 401;
      const message = err;
      res.status(status).json({ status, message });
      return;
    }

    // Get current users data
    var data = JSON.parse(data.toString());

    const email_exist = data.users.findIndex((item) => item.email == email);
    if (email_exist != -1) {
      data.users[email_exist].password = password;
    }
    console.log(data.users);
    var writeData = fs.writeFile(
      "./data.json",
      JSON.stringify(data),
      (err, result) => {
        // WRITE
        if (err) {
          const status = 401;
          const message = err;
          res.status(status).json({ status, message });
          return;
        }
      }
    );
  });

  res.status(200).json({
    status: 200,
    message: "Update sucessfully",
    YourAccount: {
      email: email,
      password: password,
    },
  });
});

server.post("/auth/account/edit_account", (req, res) => {
  console.log("this is edit users");
  let getTokenFormHeader = req.headers.authorization;
  let authorization = getDataFromToken(getTokenFormHeader);

  if (authorization == 1) {
    return res.status(401).json({
      status: 401,
      message: "Missing Authorization header.",
    });
  } else if (authorization == 2) {
    return res.status(401).json({
      status: 401,
      message: "Access token not provided",
    });
  } else {
    // tồn tại token
    // tìm vị trí trong data
    let userCurrent = db.users.findIndex(
      (item) => item.email == authorization.email
    );

    if (userCurrent == -1) {
      return res.status(401).json({
        status: "401",
        message: "acount doesn't not exist",
      });
    } else {
      const { phone, address } = req.body;
      db.users[userCurrent]["phone"] = phone;
      db.users[userCurrent]["address"] = address;

      fs.writeFileSync("./data.json", JSON.stringify(db), () => {
        if (err) return console.log(err);
      });

      console.log(db.users[userCurrent]);

      return res.status(200).json({
        status: 200,
        message: "update sucessfully",
      });
    }
  }
});

// list products

server.get("/products", (req, res) => {
  res.status(200).json({
    status: 200,
    products: db.products,
  });
});

// find product by id
server.get("/products/:id", (req, res) => {
  const idProduct = req.params.id;
  const IdProduct_exits = db.products.findIndex((item) => item.id == idProduct);

  if (IdProduct_exits != -1) {
    res.status(200).json({
      status: 200,
      product: db.products[IdProduct_exits],
    });
  } else {
    return res.status(401).json({
      status: 401,
      message: "id product not found!!",
    });
  }
});

// order products

/**
 * No account
 */
server.post("/order", (req, res) => {
  console.log("this is order");
  let indexOutOfStock = 0;

  let { username, email, phone, address, cart } = req.body;

  let tmpCart = db.products.filter((element) =>
    cart.some((item) => item == element.id)
  );

  // nếu length tmpCart == 0 => có có phần tử nào được tìm thấy
  if (tmpCart.length != cart.length) {
    return res.status(401).json({
      status: 401,
      message: "product not found",
    });
  }

  // nếu dữ liệu mảng cart truyền vào mà có sản phẩm hết hàng thì hiện thị thông báo lỗi là ngưng
  // ngược lại thì cho thêm vào data
  if (
    tmpCart.some((item) => {
      indexOutOfStock = item.id;
      return item.stock == 0;
    })
  ) {
    return res.status(401).json({
      status: 401,
      message: "The product with " + indexOutOfStock + " is out of stock!!",
    });
  } else {
    // lọc lại mảng nhưng sản phẩm còn hàng
    cart = tmpCart.filter((item) => item.stock != 0);

    cart = cart.map((item) => {
      return {
        id: item.id,
        name: item.name,
        type: item.type,
        code: item.code,
        price: item.price,
        size: item.size,
        quantity: 1,
      };
    });

    // set order data lấy từ người dùng cung cấp
    const order = {
      id: db.orders.length + 1,
      username: username,
      email: email,
      phone: phone,
      address: address,
      productOrders: cart,
      date: new Date(),
    };

    db.orders.push(order);

    fs.writeFileSync("./data.json", JSON.stringify(db), () => {
      if (err) return console.log(err);
    });
    return res.status(200).json({
      status: 200,
      message: "Order sucessfully",
      productOrders: order,
    });
  }
});

/**
 * have account
 */
server.post("/auth/order", (req, res) => {
  console.log("this is order have account");

  let getTokenFormHeader = req.headers.authorization;
  let authorization = getDataFromToken(getTokenFormHeader);

  if (authorization == 1) {
    return res.status(401).json({
      status: 401,
      message: "Missing Authorization header.",
    });
  } else if (authorization == 2) {
    return res.status(401).json({
      status: 401,
      message: "Access token not provided, so you can login",
    });
  } else {
    let indexOutOfStock = 0;
    let { cart } = req.body;

    let tmpCart = db.products.filter((element) =>
      cart.some((item) => item == element.id)
    );

    /**
     * xử lý giỏ hàng
     *    - kiểm tra xem mã số mặt hàng có tồn tại hay không
     *    - kiểm tra xem số lượng tồn của đơn hàng đó có đủ số lượng hay không
     */
    if (tmpCart.length != cart.length) {
      return res.status(401).json({
        status: 401,
        message: "product not found",
      });
    }

    if (
      tmpCart.some((item) => {
        indexOutOfStock = item.id;
        return item.stock == 0;
      })
    ) {
      return res.status(401).json({
        status: 401,
        message: "The product with " + indexOutOfStock + " is out of stock!!",
      });
    } else {
      cart = tmpCart.filter((item) => item.stock != 0);

      cart = cart.map((item) => {
        return {
          id: item.id,
          name: item.name,
          type: item.type,
          code: item.code,
          price: item.price,
          size: item.size,
          quantity: 1,
        };
      });

      // lấy dữ liệu người dùng từ data theo email
      const userIndex = db.users.findIndex(
        (item) => item.email == authorization.email
      );
      let { firstName, lastName, address, phone } = db.users[userIndex];

      const order = {
        id: db.orders.length + 1,
        username: lastName + firstName,
        email: authorization.email,
        phone: phone,
        address: address,
        productOrders: cart,
        dateOrder: new Date(),
      };

      db.orders.push(order);
      fs.writeFileSync("./data.json", JSON.stringify(db), () => {
        if (err) return console.log(err);
      });
      return res.status(200).json({
        status: 200,
        message: "Order sucessfully",
        productOrders: order,
      });
    }
  }
});

// get all products have account

server.get("/auth/order", (req, res) => {
  console.log("this is get all order");
  let getTokenFormHeader = req.headers.authorization;
  let authorization = getDataFromToken(getTokenFormHeader);

  if (authorization == 1) {
    return res.status(401).json({
      status: 401,
      message: "Missing Authorization header.",
    });
  } else if (authorization == 2) {
    return res.status(401).json({
      status: 401,
      message:
        "Access token not provided. You can't logout when you not logged in",
    });
  } else {
    let orderByEmail = db.orders.filter(
      (item) => item.email == authorization.email
    );
    if (orderByEmail.length <= 0) {
      return res.status(401).json({
        status: "401",
        message: "Bạn chưa mua sản phẩm nào",
      });
    } else {
      return res.status(200).json({
        status: 200,
        orders: orderByEmail,
      });
    }
  }
});

// get order by id

server.get("/auth/order/:id", (req, res) => {
  let getTokenFormHeader = req.headers.authorization;
  let authorization = getDataFromToken(getTokenFormHeader);

  if (authorization == 1) {
    return res.status(401).json({
      status: 401,
      message: "Missing Authorization header.",
    });
  } else if (authorization == 2) {
    return res.status(401).json({
      status: 401,
      message:
        "Access token not provided. You can't logout when you not logged in",
    });
  } else {
    const idOrder = req.params.id;
    const idOrder_exist = db.orders.findIndex(
      (item) => item.id == idOrder && item.email == authorization.email
    );
    if (idOrder_exist != -1) {
      return res.status(200).json({
        status: 200,
        orders: db.orders[idOrder_exist],
      });
    } else {
      return res.status(401).json({
        status: 401,
        message: "id không tồn tại",
      });
    }
  }
});

server.use(/^(?!\/auth).*$/, (req, res, next) => {
  if (
    req.headers.authorization === undefined ||
    req.headers.authorization.split(" ")[0] !== "Bearer"
  ) {
    const status = 401;
    const message = "Error in authorization format";
    res.status(status).json({ status, message });
    return;
  }
  try {
    let verifyTokenResult;
    verifyTokenResult = verifyToken(req.headers.authorization.split(" ")[1]);

    if (verifyTokenResult instanceof Error) {
      const status = 401;
      const message = "Access token not provided";
      res.status(status).json({ status, message });
      return;
    }
    next();
  } catch (err) {
    const status = 401;
    const message = "Error access_token is revoked";
    res.status(status).json({ status, message });
  }
});

server.use(router);

server.listen(3000, () => {
  console.log("Run Auth API Server");
});
