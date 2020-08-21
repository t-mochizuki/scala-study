const method = "POST";
const headers = {
  Accept: "application/json",
  Origin: "http://localhost:8000"
};
const body = new FormData();
body.append("id", "admin");
body.append("password", "admin");
fetch(
  "http://localhost:8080/login",
  {
    method,
    headers,
    body
  }
).then(response => {
  console.log(response);
  return response;
}).then(response => {
  console.log(response.headers.get("set-authorization"));
  return response;
}).then(response => {
  console.log(response.status);
  console.log(response.headers.get("content-encoding"));
  return response;
}).then(response => response.headers.entries())
  .then(entries => {
    for (const e of entries) {
      console.log(e);
    }
  });
