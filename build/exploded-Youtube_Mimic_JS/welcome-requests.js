
let container = document.getElementById("container")
let searchElement = document.getElementById("searchKeyword")
let url = "http://localhost:8080/Youtube_Mimic_JS/";


 function getURL(){
    let str = ""
    let origin = window.location.origin
    let pathnameArray = window.location.pathname.split("/")
    let pathname = pathnameArray.splice(0,pathnameArray.length-1)
    pathname.unshift(origin)
    for(x of pathname){ 
    if(x!="") str = str+x+"/"
    } 
    return
}

//for search event
searchElement.addEventListener('keyup',function(e){
    if(e.key=="Enter") {getVideoContent(); searchElement.value="";}
})



getVideoContent();
async function getVideoContent(){ 
    let searchKeyword = searchElement.value;    
    let response = await fetch(`${url}SearchController`,{
        headers: {
            "Content-Type":"application/json",
            "Accept":"/*"
        },
        method : "POST",
        Accept:"/*",
        body: JSON.stringify({
            "searchKeyword": `${searchKeyword}`
          })
         
    })
    
    if(response.status == 404){
        writeNotFound();
    }else{
        let data = await response.json()
        writeVideoContentDocument(data);
    }
   
}

function writeNotFound(){
    let table = document.getElementById("result-table")

    let writeData = "<tr><td>No Video Found</td></tr>"

    table.innerHTML = writeData
}

function writeVideoContentDocument(data){
    
    let table = document.getElementById("result-table")
    
    let writeData ="";

    for(let x in data){
        writeData = writeData+`<tr><td><a target="_blank" href="${data[x].link}">${data[x].title}</a></td></tr>`;
    }
    table.innerHTML=writeData;
}



let functions = {


    login : async function(){
        let userEmail = document.getElementById("login-email").value;
        let password = document.getElementById("login-pass").value;
        
        let response =await fetch(`${url}home/LoginController`, {
            method: 'POST',
            headers: {
                "Content-Type":"application/json",
                "Accept":"/*"
            },
            body:JSON.stringify({
                userEmail : `${userEmail}`,
                password : `${password}`
            }),
            redirect:"follow"
        })

        if(response.status >= 200 && response.status <=299){
            let data = await response.json();
            console.log(data);
            localStorage.setItem("jwtToken",data);
            window.location.href=url+"home.html"
        }else{
            let data = await response.text();
            console.log(data)
        }  
    
    },

    submit: async function(){
        let userName = document.getElementById("name").value;
        let userEmail = document.getElementById("email").value;
        let password = document.getElementById("password").value;
        

        let response =await fetch(`${url}SignUpController`, {
            method: 'POST',
            headers: {
                "Content-Type":"application/json",
                "Accept":"/*"
            },
            body:{
                name : `${userName}`,
                email : `${userEmail}`,
                password : `${password}`
            },
            redirect:"follow"
        })

        if(response.status >= 200 && response.status <= 299){
            let data = await response.json();
            console.log(data);
            localStorage.setItem("jwtToken",data);
            window.location.href=url+"home.html"
        }else{
            let data = await response.json();
            console.log(data)
        }  
    
    }

}





