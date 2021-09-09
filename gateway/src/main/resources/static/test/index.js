var build = true;
var userToken = "";
var userId = "";
var restaurantToken = "";
var restaurantId = "";
var itemId = "";
var addressId = "";
var billId = "";

var menuItem1 = {
    "name": "test1",
    "value": 123.11,
    "ingredient": "test1",
    "comment": "test1",
    "startFirst": 11,
    "endFirst": 25,
    "startSecond": 11,
    "endSecond": 22
};

var menuItem2 = {
    "name": "test2",
    "value": 121.11,
    "ingredient": "test2",
    "comment": "test2",
    "startFirst": 10,
    "endFirst": 13,
    "startSecond": 19,
    "endSecond": 22
};

/**
----------------------BILL START-----------------------
**/

// Bill api for generate bill
const testGenerateBill = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/accounts/api/v1/bill" : "http://localhost:8086/api/v1/bill";

        var xhr = new XMLHttpRequest();
        xhr.open("PUT", url);

        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                billId = data.id;
                console.log("testGenerateBill", xhr.status, data);
                resolve("testGenerateBill successful");
            }
        };

        var data = {
            "userId":userId,
            "restaurantId": restaurantId,
            "addressId": addressId,
            "items":[itemId]
        };

        xhr.send(JSON.stringify(data));
    });


// Bill api for delete
const testBillDelete = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/accounts/api/v1/bill/" + billId : "http://localhost:8086/api/v1/bill/" + billId;

        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", url);

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testBillDelete", xhr.status, data);
                resolve("testBillDelete successful");
            }
        };

        xhr.send();
    });

// Bill api for add item to bill
const testAddItemToBill = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/accounts/api/v1/bill/" + billId : "http://localhost:8086/api/v1/bill/" + billId;

        var xhr = new XMLHttpRequest();
        xhr.open("PUT", url);

        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                billId = data.id;
                console.log("testAddItemToBill", xhr.status, data);
                resolve("testAddItemToBill successful");
            }
        };

        var data = {
            "itemId":itemId,
            "restaurantId": restaurantId
        };

        xhr.send(JSON.stringify(data));
    });

// Bill api for delete item from cart
const testBillDeleteItem = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/accounts/api/v1/bill/item/" + billId : "http://localhost:8086/api/v1/bill/item/" + billId;

        var xhr = new XMLHttpRequest();
        xhr.open("PATCH", url);

        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.setRequestHeader("Authorization", "Bearer " + userToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                billId = data.id;
                console.log("testBillDeleteItem", xhr.status, data);
                resolve("testBillDeleteItem successful");
            }
        };

        var data = {
            "itemId":itemId,
            "restaurantId": restaurantId
        };

        xhr.send(JSON.stringify(data));
    });

// update bill api
const testBillUpdate = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/accounts/api/v1/bill/" + billId : "http://localhost:8086/api/v1/bill/" + billId;
        var xhr = new XMLHttpRequest();
        xhr.open("PATCH", url);

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                billId = data.id;
                console.log("testBillUpdate", xhr.status, data);
                resolve("testBillUpdate successful");
            }
        };

        var data = {
            "userId":userId,
            "restaurantId": restaurantId,
            "addressId": addressId,
            "items":[itemId]
        };

        xhr.send(JSON.stringify(data));

    });

// Bill api for getting bill
const testGetBill = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/accounts/api/v1/bill/" + billId : "http://localhost:8086/api/v1/bill/" + billId;

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testGetBill", xhr.status, data);
                resolve("testGetBill successful");
            }
        };

        xhr.send();
    });

/**
----------------------RESTAURANT START-----------------------
**/
// Restaurant api for signup
const testRestaurantSignUp = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/signup" : "http://localhost:8082/api/v1/signup";

        var xhr = new XMLHttpRequest();
        xhr.open("PUT", url);

        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testRestaurantSignUp", xhr.status, data);
                resolve("testRestaurantSignUp successful");

            }
        };

        var data = `{
            "username":"rosewood",
            "password":"rosewood@shu.com",
            "email":"rosewood@shu.com",
            "phone":"1234567890",
            "roles":["ROLE_RESTAURANT"]
        }`;

        xhr.send(data);
    });

// Restaurant api for login
const testRestaurantLogin = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/login" : "http://localhost:8082/api/v1/login";

        var xhr = new XMLHttpRequest();
        xhr.open("POST", url);

        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                restaurantToken = data.token;
                restaurantId = data.id;
                console.log("testRestaurantLogin", xhr.status, data);
                resolve("testRestaurantLogin successful");
            }
        };

        var data = `{
            "username":"rosewood",
            "password":"rosewood@shu.com"
        }`;

        xhr.send(data);
    });

// Restaurant api for logout
const testRestaurantLogout = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/logmeout" : "http://localhost:8082/api/v1/logmeout";

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + restaurantToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testRestaurantLogout", xhr.status, data);
                resolve("testRestaurantLogout successful");
            }
        };

        xhr.send();
    });

// Restaurant api for logout
const testRestaurantLogoutAll = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/logoutall" : "http://localhost:8082/api/v1/logoutall";

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + restaurantToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testRestaurantLogoutAll", xhr.status, data);
                resolve("testRestaurantLogoutAll successful");
            }
        };

        xhr.send();
    });

// Restaurant api for logout
const testRestaurantDelete = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/delete" : "http://localhost:8082/api/v1/delete";

        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", url);

        xhr.setRequestHeader("Authorization", "Bearer " + restaurantToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testRestaurantDelete", xhr.status, data);
                resolve("testRestaurantDelete successful");
            }
        };

        xhr.send();
    });


/**
----------------------MENU START-----------------------
**/
const testDeleteItem = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/menu/item/" + itemId : "http://localhost:8082/api/v1/menu/item/" + itemId;

        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", url);

        xhr.setRequestHeader("Authorization", "Bearer " + restaurantToken);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testDeleteItem", xhr.status, data);
                resolve("testDeleteItem successful");
            }
        };

        xhr.send();

    });

const testAddItem = (item) =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/menu/item/" : "http://localhost:8082/api/v1/menu/item";

        var xhr = new XMLHttpRequest();
        xhr.open("PUT", url);

        xhr.setRequestHeader("Authorization", "Bearer " + restaurantToken);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                itemId = data.id;
                console.log("testAddItem", xhr.status, data);
                resolve("testAddItem successful");
            }
        };

        var data = JSON.stringify(item);

        xhr.send(data);
    });

const testPatchItem = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/menu/item/" + itemId : "http://localhost:8082/api/v1/menu/item/" + itemId;

        var xhr = new XMLHttpRequest();
        xhr.open("PATCH", url);

        xhr.setRequestHeader("Authorization", "Bearer " + restaurantToken);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testPatchItem", xhr.status, data);
                resolve("testPatchItem successful");
            }
        };

        var data = `{
        	 "name":"test",
             "value":123.1,
             "ingredient":"test",
             "comment":"test",
             "startFirst":1,
             "endFirst":2,
             "startSecond":1,
            "endSecond":2
        }`;

        xhr.send(data);
    });

const testGetItemByRestaurantId = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/menu" : "http://localhost:8082/api/v1/menu";

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + restaurantToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testGetItemByRestaurantId", xhr.status, data);
                resolve("testGetItemByRestaurantId successful");
            }
        };

        xhr.send();
    });

const testGetItemByItemId = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/menu/item/" + itemId : "http://localhost:8082/api/v1/menu/item/" + itemId;

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + restaurantToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testGetItemByItemId", xhr.status, data);
                resolve("testGetItemByItemId successful");
            }
        };

        xhr.send();
    });

const testGetMenuByRestaurantId = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/public/menu/" + restaurantId : "http://localhost:8082/api/v1/public/menu/" + restaurantId;

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testGetMenuByRestaurantId", xhr.status, data);
                resolve("testGetMenuByRestaurantId successful");
            }
        };

        xhr.send();
    });

const testGetItemByRestaurantIdAndItemId = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/public/menu/item?restaurantId=" + restaurantId + "&itemId=" + itemId : "http://localhost:8082/api/v1/public/menu/item?restaurantId=" + restaurantId + "&itemId=" + itemId;

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testGetItemByRestaurantIdAndItemId", xhr.status, data);
                resolve("testGetItemByRestaurantIdAndItemId successful");
            }
        };

        xhr.send();
    });

/**
----------------------USER START-----------------------
**/
// User api for signup
const testUserSignUp = (id = "") =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/signup" : "http://localhost:8081/api/v1/signup";

        var xhr = new XMLHttpRequest();
        xhr.open("PUT", url);

        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testUserSignUp", xhr.status, data);
                resolve("testUserSignUp successful");
            }
        };

        var data = {
            "username":"skl" + id,
            "password":"email@shu.com",
            "email":"email" + id + "@shu.com",
            "phone":"1234567890",
            "roles":["ROLE_USER"]
        };

        xhr.send(JSON.stringify(data));
    });

// User api for login
const testUserLogin = (id = "") =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/login" : "http://localhost:8081/api/v1/login";

        var xhr = new XMLHttpRequest();
        xhr.open("POST", url);

        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                userToken = data.token;
                userId = data.id;
                console.log("testUserLogin", xhr.status, data);
                resolve("testUserLogin successful");
            }
        };

        var data = {
            "username":"skl" + id,
            "password":"email@shu.com"
        };

        xhr.send(JSON.stringify(data));
    });

// User api for logout
const testUserLogout = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/logmeout" : "http://localhost:8081/api/v1/logmeout";

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testUserLogout", xhr.status, data);
                resolve("testUserLogout successful");
            }
        };

        xhr.send();
    });

// User api for logout
const testUserLogoutAll = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/logoutall" : "http://localhost:8081/api/v1/logoutall";

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testUserLogoutAll", xhr.status, data);
                resolve("testUserLogoutAll successful");
            }
        };

        xhr.send();
    });

// User api for delete
const testUserDelete = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/delete" : "http://localhost:8081/api/v1/delete";

        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", url);

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testUserDelete", xhr.status, data);
                resolve("testUserDelete successful");
            }
        };

        xhr.send();
    });

/**
----------------------ADDRESS START-----------------------
**/
// User api for Put address
const testPutAddress = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/address" : "http://localhost:8081/api/v1/address";

        var xhr = new XMLHttpRequest();
        xhr.open("PUT", url);

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                addressId = data.id;
                console.log("testPutAddress", xhr.status, data);
                resolve("testPutAddress successful");
            }
        };

        var data = `{
          "location":{
            "x":123.23,
            "y":123.543
          },
          "name":"test",
          "addressLineOne":"test",
          "addressLineTwo":"test",
          "pin":"412123",
          "city":"pune",
          "state":"mah",
          "isSelected":false
        }`;

        xhr.send(data);
    });

// User api for testGetAddressById
const testGetAddressById = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/address/" + addressId : "http://localhost:8081/api/v1/address/" + addressId;

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testGetAddressById", xhr.status, data);
                resolve("testGetAddressById successful");
            }
        };

        xhr.send();
    });

const testGetAddressByUserId = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/address/user/" + userId : "http://localhost:8081/api/v1/address/user/" + userId;

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testGetAddressByUserId", xhr.status, data);
                resolve("testGetAddressByUserId successful");
            }
        };

        xhr.send();
    });

const testDeleteAddress = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/address/" + addressId : "http://localhost:8081/api/v1/address/" + addressId;

        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", url);

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testDeleteAddress", xhr.status, data);
                resolve("testDeleteAddress successful");
            }
        };

        xhr.send();

    });

// update address api
const testPatchAddress = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/address/" + addressId : "http://localhost:8081/api/v1/address/" + addressId;
        var xhr = new XMLHttpRequest();
        xhr.open("PATCH", url);

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testPatchAddress", xhr.status, data);
                resolve("testPatchAddress successful");
            }
        };

        var data = `{
            "location":{
                "x":124.23,
                  "y":124.543
              },
              "name":"testOne",
              "addressLineOne":"testOne",
              "addressLineTwo":"testOne",
              "pin":"411111",
              "city":"puneOne",
              "state":"mahOne",
              "isSelected":false
        }`;

        xhr.send(data);

    });

/**
----------------------CART START-----------------------
**/

const testAddItemToCart = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/cart/item" : "http://localhost:8081/api/v1/cart/item";

        var xhr = new XMLHttpRequest();
        xhr.open("PUT", url);

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testAddItemToCart", xhr.status, data);
                resolve("testAddItemToCart successful");
            }
        };

        var data = JSON.stringify({
            "restaurantId": restaurantId,
            "itemId": itemId
        });

        xhr.send(data);

    });


const testRemoveItemFromCart = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/cart/item/remove/" + itemId : "http://localhost:8081/api/v1/cart/item/remove/" + itemId;

        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", url);

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testRemoveItemFromCart", xhr.status, data);
                resolve("testRemoveItemFromCart successful");
            }
        };

        xhr.send();

    });

const testDecreaseQuantityOfItemFromCart = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/cart/item/" + itemId : "http://localhost:8081/api/v1/cart/item/" + itemId;

        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", url);

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testDecreaseQuantityOfItemFromCart", xhr.status, data);
                resolve("testDecreaseQuantityOfItemFromCart successful");
            }
        };

        xhr.send();

    });

const testGetCart = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/cart" : "http://localhost:8081/api/v1/cart";

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + userToken);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testGetCart", xhr.status, data);
                resolve("testGetCart successful");
            }
        };

        xhr.send();
    });

/**
----------------------NOTIFICATION START-----------------------
**/
const loadNotifications = () => {
    var source = null;

    const start = () => {
        source = new EventSource(
            "http://localhost:8083/notification/restaurant/" + id
        );
        source.addEventListener("notification", (event) => {
            var data = event.data;
            console.log("Event data : ", data);
            // process the data
        });
        source.onerror = function() {
            console.log("Error");
        };
    };
};

//notifications = new loadNotifications();

//notifications.start();


/**
----------------------UTILITY START-----------------------
**/
const changeLinkColor = activeLink => {
    document.getElementById("menu-btn").style.borderBottomColor = "#1b1b1b";
    document.getElementById("user-btn").style.borderBottomColor = "#1b1b1b";
    document.getElementById("address-btn").style.borderBottomColor = "#1b1b1b";
    document.getElementById("cart-btn").style.borderBottomColor = "#1b1b1b";
    document.getElementById("bill-btn").style.borderBottomColor = "#1b1b1b";
    document.getElementById(activeLink).style.borderBottomColor = "#979595";
}

var content = document.getElementById("content");

const addContent = (msg) => {
    var para = document.createElement("P");
    var t = document.createTextNode(msg);
    para.appendChild(t);
    content.appendChild(para);
}


const hideLoader = () => {
    document.getElementById("loader").style.display = "none";
}

const showLoader = (msg) => {
    document.getElementById("loader").style.display = "block";
    document.getElementById("current-test-heading").innerHTML = msg;
    content.innerHTML = "";
}

/**
----------------------ADDRESS TESTER FUNCTION START-----------------------
**/
const userPerformanceTester = () => {
    changeLinkColor("user-btn");
    showLoader("User app performance testing");

    let promises = [];
    for (let i = 0; i < 2; i++) {
      promises.push(
        testUserSignUp(i).then((res) => {
            addContent(res);
            testUserLogin(i).then((res) => {
                addContent(res);
                testUserLogout().then((res) => {
                    addContent(res);
                    testUserDelete().then((res) => {
                        addContent(res);
                        return "done";
                    });
                });
            });
        })
      );
    }

    Promise.all(promises).then(() => hideLoader());
}


/**
----------------------ADDRESS TESTER FUNCTION START-----------------------
**/
const addressTester = () => {

    changeLinkColor("address-btn");

    showLoader("Address app test suit");
    testUserSignUp().then(res => {
        addContent(res);
        testUserLogin().then((res) => {
            addContent(res);
            testPutAddress().then((res) => {
                addContent(res);
                testGetAddressById().then((res) => {
                    addContent(res);
                    testGetAddressByUserId().then((res) => {
                        addContent(res);
                        testPatchAddress().then(res => {
                            addContent(res);
                            testGetAddressById().then(res => {
                                addContent(res);
                                testDeleteAddress().then(res => {
                                    addContent(res);
                                    //                                    testUserLogoutAll().then(res => {
                                    //                                        addContent(res)
                                    //                                        hideLoader();
                                    //                                    })
                                    testUserDelete().then(res => {
                                        addContent(res)
                                        hideLoader();
                                    })
                                })
                            })
                        })
                    })
                })
            })
        })
    });
}

/**
----------------------MENU TESTER FUNCTION START-----------------------
**/

const menuTester = () => {

    changeLinkColor("menu-btn");

    showLoader("Menu app test suit");
    testRestaurantSignUp().then(res => {
        addContent(res);
        testRestaurantLogin().then((res) => {
            addContent(res);
            testAddItem(menuItem1).then(res => {
                addContent(res)
                testAddItem(menuItem2).then(res => {
                    testGetItemByItemId().then(res => {
                        addContent(res)
                        testPatchItem().then(res => {
                            addContent(res)
                            testGetItemByRestaurantId().then(res => {
                                addContent(res)
                                testGetItemByItemId().then(res => {
                                    addContent(res)
                                    testGetMenuByRestaurantId().then(res => {
                                        addContent(res)
                                        testGetItemByRestaurantIdAndItemId().then(res => {
                                            addContent(res)
                                            testDeleteItem().then(res => {
                                                addContent(res)
                                                testRestaurantDelete().then(res => {
                                                    addContent(res)
                                                    hideLoader();
                                                })
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
        })
    });
};

/**
----------------------BILL TESTER FUNCTION START-----------------------
**/

const billTester = () => {
    changeLinkColor("bill-btn");
    showLoader("Bill app test suit");

    testRestaurantSignUp().then(res => {
        addContent(res);
        testRestaurantLogin().then(res => {
            addContent(res);
            testUserSignUp().then(res => {
                addContent(res);
                testUserLogin().then(res => {
                    addContent(res);
                    testPutAddress().then((res) => {
                        addContent(res);
                        testAddItem(menuItem1).then(res => {
                            addContent(res);
                            setTimeout(() => {
                                testGenerateBill().then((res) => {
                                    addContent(res);
                                    testPatchAddress().then(res => {
                                        addContent(res);
                                        testBillUpdate().then((res) => {
                                            addContent(res);
                                            testAddItem(menuItem2).then(res => {
                                                addContent(res);
                                                testGetBill().then((res) => {
                                                addContent(res);
                                                    testAddItemToBill().then((res) => {
                                                        addContent(res);
                                                        testGetBill().then((res) => {
                                                            addContent(res);
                                                            testBillDeleteItem().then((res) => {
                                                                addContent(res);
                                                                testGetBill().then((res) => {
                                                                    addContent(res);
                                                                    testBillDelete().then((res) => {
                                                                        addContent(res);
                                                                        testRestaurantDelete().then(res => {
                                                                            addContent(res);
                                                                            testUserDelete().then(res => {
                                                                                addContent(res);
                                                                                hideLoader();
                                                                            });
                                                                        });
                                                                    });
                                                                });
                                                            });
                                                        });
                                                    });
                                                });
                                            });
                                        });
                                    });
                                });
                             }, 3000);
                        });
                    });
                });
            });
        });
    });
}

/**
----------------------CART TESTER FUNCTION START-----------------------
**/
const cartTester = () => {

    changeLinkColor("cart-btn");

    showLoader("Cart app test suit");

    testRestaurantSignUp().then(res => {
        addContent(res);
        testRestaurantLogin().then(res => {
            addContent(res);
            testAddItem(menuItem1).then(res => {
                addContent(res);
                testUserSignUp().then(res => {
                    addContent(res);
                    testUserLogin().then(res => {
                        addContent(res);
                        testAddItemToCart().then(res => {
                            addContent(res);
                            testGetCart().then(res => {
                                addContent(res);
                                testAddItemToCart().then(res => {
                                    addContent(res);
                                    testDecreaseQuantityOfItemFromCart().then(res =>{
                                        addContent(res);
                                        testRemoveItemFromCart().then(res =>{
                                            addContent(res);
                                            testGetCart().then(res => {
                                                addContent(res);
                                                testAddItemToCart(menuItem2).then(res => {
                                                    addContent(res);
                                                    testUserDelete().then(res => {
                                                        addContent(res);
                                                        testRestaurantDelete().then(res => {
                                                            addContent(res);
                                                            hideLoader();
                                                        });
                                                    });
                                                });
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
        });
    });
};