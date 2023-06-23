console.clear();

function validate(){

  let pass = document.getElementById("login-pass").value
  let submit = document.getElementById("login-submit")

  if(checkLoginEmail() && pass.length>8) submit.disabled = false;
  else submit.disabled = true; 

}

function checkLoginEmail(){

  let email = document.getElementById("login-email").value

  if(email.length <= 10 || !email.toString().includes('@') || !email.toString().includes(".") ) return false;
  return true
}

function check(){
  let passWarn = document.getElementById("pass-check")  
  let confPass = document.getElementById("conf-pass")
  let password = document.getElementById("password").value.toString()
  document.getElementById("password").value.oldvalue = password
  if(password.length<8) passWarn.innerText = "Password size must be more than 8"
  else if(!password.match(/[a-zA-z]/g)) passWarn.innerText = "Password must have a letter"
  else if(!password.match(/[0-9]/g)) passWarn.innerText = "Password must have a number"
  else passWarn.innerText = ""
  if(password.length==0) passWarn.innerText = ""
}

function confirmPassword(){
  let passWarn = document.getElementById("pass-check")  
  let confPass = document.getElementById("conf-pass")
  let password = document.getElementById("password").value.toString()
  let confPassword = document.getElementById("confirm-password").value.toString()

  document.getElementById("password").value.oldvalue = password;
  document.getElementById("confirm-password").value.oldvalue = confPassword;

  if(password!=confPassword) confPass.innerText = "Password and Confirm Password must be same"
  else confPass.innerText = ""
}

function submitValidate(){
  let submit = document.getElementById("signup-submit")
  let password = document.getElementById("password").value.toString()
  document.getElementById("password").value.oldvalue = password
  let passWarn = document.getElementById("pass-check")
  let confPass = document.getElementById("conf-pass")
  if(checkEmailAndName() && password!="" && passWarn.outerText=="" && confPass.outerText=="") submit.disabled = false;
  else submit.disabled = true;
}

function checkEmailAndName(){
  let email = document.getElementById("email").value
  let name = document.getElementById("name").value
  if(email.length <= 10 || !email.toString().includes('@') || !email.toString().includes(".") ) return false;
  if(name.length < 3) return false
  return true

}


function togglePassword(){
    let showPass = document.getElementById("password-toggle")
    let password = document.getElementById("password")
    if(password.type=="password"){
        password.type="text"
        showPass.innerText = "hide"
    }
    else{
        password.type="password"
        showPass.innerText = "show"
    }
}

const loginInput = document.getElementById('login-popup')
loginInput.addEventListener('hidden.bs.modal', event => {
  document.getElementById("login-email").value = ""
  document.getElementById("login-pass").value = ""
})

const signupInput = document.getElementById('signup-popup')
signupInput.addEventListener('hidden.bs.modal', event => {
  document.getElementById("name").value = ""
  document.getElementById("email").value = ""
  document.getElementById("password").value = ""
  document.getElementById("confirm-password").value = ""
})

