const method = "POST";
const body = new FormData();
body.append("id", "admin");
body.append("password", "admin");
const xhr = new XMLHttpRequest();
xhr.open(method, "http://localhost:8080/login");
xhr.setRequestHeader("Accept", "application/json");
xhr.setRequestHeader("Origin", "http://localhost:8000");
xhr.send(body);
xhr.onload = () => {
  console.log(xhr.getResponseHeader("set-authorization"));

  console.log(xhr.getResponseHeader("content-length"));
  console.log(xhr.getResponseHeader("content-type"));

  console.log(xhr.getAllResponseHeaders())
};
