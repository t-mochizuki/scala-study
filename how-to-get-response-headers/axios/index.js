const fetch = require("axios");
const FormData = require("form-data");

const headers = {
  Accept: "application/json"
};
const form = new FormData();
form.append("id", "admin");
form.append("password", "admin");
console.log(form.getHeaders());
fetch.post(
  "http://localhost:8080/login",
  form,
  {
    headers: {
      ...headers,
      ...form.getHeaders()
    }
  }
).then(response => {
  console.log(response);
  return response;
}).then(response => {
  for (const header of Object.entries(response.headers)) {
    console.log(header);
  }
});
