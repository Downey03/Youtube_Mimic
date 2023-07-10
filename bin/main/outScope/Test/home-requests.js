
let pageurl="";
let sessionId ;
let searchElement = document.getElementById("searchKeyword") 
let playLists = document.getElementById("playlists")
let jwtToken = localStorage.getItem('jwtToken')
let userPlayLists = [];


console.clear()


searchElement.addEventListener('keypress',function(e){
    if(e.key=="Enter") getVideoContent()
})

getVideoContent();
getPlaylists()


///// function to write the home video search////

async function getVideoContent(){ 
        
    let searchKeyword = searchElement.value
    let response = await fetch(`${url}SearchController`,{
        headers: { "Content-Type": "application/json",
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
        let data = await response.json();
        writeVideoContentDocument(data);
    } 
    
}

function writeNotFound(){
    let table = document.getElementById("result-content")
    let writeData = "<h3>No Video Found</h3>"
    table.innerHTML = writeData
}


function writeVideoContentDocument(data){
    let result = document.getElementById("result-content")

    let write="";

    for(x in data){
        write = write+` 
        <a href="${data[x].videoLink}">
          <img src="${data[x].videoThumbnail}">
          ${data[x].videoTitle}
        </a> `
    }

    result.innerHTML = write;
}

// function writeVideoContentDocument(data){
//     let table = document.getElementById("result-table")
//     let writeData ="";
//     for(let x in data){
//         writeData = writeData+`<tr><td><a target="_blank" href="${data[x].VideoLink}">${data[x].videoTitle}</a></td></tr>`;
//     }
//     table.innerHTML=writeData;
// }

//////// functions for playlist/////


function writePlayListsDocument(data){

    let playListsContent = document.getElementById("playlists")
    userPlayLists = data;
    let write = "";
    for(x in data){

        write = write+`<button id="${data[x]}"  type="button" data-bs-toggle="modal" data-bs-target="#playlist-content-modal">${data[x]}</button>`
    }

    playListsContent.innerHTML = write;
}

async function createPlaylist(){
    let playListName = document.getElementById("create-playlist").value

    let response =await fetch(`${url}/CreatePlayList`,{
        headers: {
            contentType: 'application/json',
            Authorization: `bearer ${(localStorage.getItem("jwtToken"))}`,
            Accept : "/*"
        },
        method:"POST",
        body : JSON.stringify({
            playListName : `${playListName}`
        })
    }).then(res => res.json())

    console.log("create playlist",response)
    writePlayListsDocument(response)
}

async function getPlaylists(){

    let jwtToken = localStorage.getItem("jwtToken")

    let response =await fetch(`${url}GetPlayLists`,{
        headers: { "Content-Type": "application/json",
        "Authorization" : `Bearer ${jwtToken}`
            },
        method : "POST",
        Accept:"/*",
    }).then(res => res.json())

    writePlayListsDocument(response);

}

async function deletePlayList(){

    let jwtToken = localStorage.getItem("jwtToken")
    let playListName = document.getElementById("playListName").value

    console.log(playListName)

    let response = await fetch(`${url}DeletePlayList`,{
        headers: { "Content-Type": "application/json",
        "Authorization" : `Bearer ${jwtToken}`
            },
        body: JSON.stringify({
            playListName 
        }),
        method : "POST",
        Accept:"/*",
    }).then(res => res.json())
    
    ///relaoding is not working
    window.location.reload();

}


////functions to view playlist ////

let playlists = document.getElementById("playlists")
playlists.addEventListener('click',function(e){
    let target = e.target;
    if(target.id != "playlists"){
        let data = getPlayListContent(target.id);
        writePlayListContent(target.id,data);
        // checkSearchSongLength() //working fine
    }
    
})

async function getPlayListContent(id){

    let response = await fetch(`${url}GetPlayListItems`,{
        headers: { "Content-Type": "application/json",
        "Authorization" : `Bearer ${jwtToken}`
            },
        body:JSON.stringify({
            playListName : id
        }),
        method : "POST",
        Accept:"/*",
    }).then(res => res.json())

    writePlayListContent(id,response)
}


function writePlayListContent(id,data){
    currentPlayList = [];
    let element = document.getElementById("modal-content")
    console.log("this is dta",data)
    let write = "";
    write = write+`<div class="videoSearch">
    <button id="delete-playlist" onclick="deletePlayList()">
    <input type="hidden" id="playListName" value='${id}'>Delete Playlist</button>
    <input type="text" onkeyup="checkSearchSongLength()" id="video-search">
    <button class="search-btn"  id="search-video">search</button>
    </div>
    <div id="results">
        
    </div>
    <div class="modal-body playlist-content-modal" >`;
    
        for(x in data){
            currentPlayList.push(data[x].videoTitle)
            write = write+`<div>
            <a href="${data[x].videoLink}">
              <div>
                <img src="${data[x].videoThumbnail}" >
                ${data[x].videoTitle}
              </div>
            </a>
            <i id="deleteVideo" onclick="deleteItemFromPlayList('${data[x].videoTitle}')" class="fa fa-circle-xmark "></i>
          </div>`
        }
    write = write+"</div>"    
    element.innerHTML = write;

}



// function writePlayListContent(id,data){
//     currentPlayList = [];
//     let element = document.getElementById("modal-content")
//     console.log("this is dta",data)
//     let write = "";
//     write = write+`<div class="videoSearch">
//     <button id="delete-playlist" onclick="deletePlayList()">
//     <input type="hidden" id="playListName" value='${id}'>Delete Playlist</button>
//     <input type="text" onkeyup="checkSearchSongLength()" id="video-search">
//     <button class="search-btn"  id="search-video">search</button>
//     </div>
//     <div id="results">
        
//     </div>
//     <div class="modal-body playlist-content-modal" >`;
    
//         for(x in data){
//             currentPlayList.push(data[x].videoTitle)
//             write = write+`<div >
//             <button ><a href="${data[x].videoLink}">${data[x].videoTitle}</a></button>
//             <i id="deleteVideo" onclick="deleteItemFromPlayList('${data[x].videoTitle}')" class="fa fa-circle-xmark "></i>
//           </div>`
//         }
//     write = write+"</div>"    
//     element.innerHTML = write;

// }

//// functions to search song and add to playlist ////

let checkSearchSongLength = function(){

    let searchBtn = document.getElementById("search-video")
    let searchKeyword = document.getElementById("video-search").value

    if(searchKeyword.length >= 3 && searchKeyword.trim()!=""){
        searchSongForPlayList(searchKeyword);
        searchBtn.disabled = false
    }else{
        searchBtn.disabled = true
    }
}

let searchSongForPlayList =async function(searchKeyword){

    let response = await fetch(`${url}SearchController`,{
        headers: { 
            "Content-Type": "application/json",
            "Authorization" : `Bearer ${jwtToken}`
            },
        method : "POST",
        Accept:"/*",
        body: JSON.stringify({
            "searchKeyword": `${searchKeyword}`
          })   
    })
    if(response.status == 404){
        
    }else{
        let data = await response.json();
        writeSearchSongInPlayList(data)
    }
}


function writeSearchSongInPlayList(data){

    let writeElement = document.getElementById("results")

    let write = "";

    for(x in data){
        let value = `${data[x].videoTitle}`;
        value = value.toString()
        console.log(value)
        write = write+`<div><button onclick='addItemToPlaylist(\"${value}\")'  value='${value}' >${value}</button></div>`
    }
    writeElement.innerHTML = write;

}

async function addItemToPlaylist(videoTitle){

    console.log("add called",videoTitle)
    let playListName = document.getElementById("playListName").value
    
    let response = await fetch(`${url}AddItemToPlayList`,{
        headers: { 
            "Content-Type": "application/json",
            "Authorization" : `Bearer ${jwtToken}`
            },
        method : "POST",
        Accept:"/*",
        body: JSON.stringify({
            "playListName": `${playListName}`,
            "videoTitle" : `${videoTitle}`
          })   
    })

    let data = await response.json();
    writePlayListContent(playListName,data)
}

async function deleteItemFromPlayList(videoTitle){

    let playListName = document.getElementById("playListName").value
    
    let response = await fetch(`${url}RemoveItemFromPlayList`,{
        headers: { 
            "Content-Type": "application/json",
            "Authorization" : `Bearer ${jwtToken}`
            },
        method : "POST",
        Accept:"/*",
        body: JSON.stringify({
            "playListName": `${playListName}`,
            "videoTitle" : `${videoTitle}`
          })   

    })

    let data = await response.json();
    writePlayListContent(playListName,data)

}

////logout function///

function logout(){
    localStorage.clear();
    window.location.href = `${url}welcome.html`
}