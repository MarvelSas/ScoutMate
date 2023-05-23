  function check() {
  var email1 = document.getElementById("email");
  var email2 = document.getElementById("email_repeat");

  if(email1.value != email2.value) {
  console.log("confirmation email doesn't matches! Enter Again");
  return false;
}
}
