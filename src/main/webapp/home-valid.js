
const url = "http://localhost:8080/Youtube_Mimic_JS/"
let jwtToken = localStorage.getItem("jwtToken")

if(jwtToken == null){
    location.href = `${url}page.html`
}



