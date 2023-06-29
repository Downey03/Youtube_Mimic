// const url = "http://localhost:8080/Youtube_Mimic_JS/";
// let jwtToken = localStorage.getItem("jwtToken")

// homeVideoSearchInput.addEventListener('keyup',function(e){
//     if(e.key == "Enter") requests.getHomeVideo()
// })

const writeFunctions = {

    writeHomeVideo : function(data,element){

        let writeData = "";
        for(x in data){
            writeData = writeData+`
                 <a href="${data[x].videoLink}">
                    <img src="${data[x].videoThumbnail}">
                    ${data[x].videoTitle}
                </a>`;
        }

        element.innerHTML = writeData;
    },
    writeHomeVideoNotFound : function(element){
        let writeData = "<h3>No Video Found</h3>";
        element.innerHTML = writeData
    },
    writePlayList : function(){
        
        let writeDocument = document.getElementById("select-playlist")
        let writeData = "";
        
        for(x in playLists) {

            writeData = writeData+`<div><button  class="playlists"  onclick="switchPlayList('${playLists[x]}')">${playLists[x]}</button> <i data-bs-toggle="modal" data-bs-target="#confirm-playlist-delete" onclick="changeCurrentPlayList('${playLists[x]}')" class="fa fa-circle-xmark delete-video"></i></div>`
            // writeData = writeData+`<button><div><a href="${url}playlist.html">${playLists[x]}</a></p><i data-bs-toggle="modal" data-bs-target="#confirm-playlist-delete" onclick="changeCurrentPlayList('${playLists[x]}')" class="fa fa-circle-xmark delete-video"></i></div></button>`
        }
        writeDocument.innerHTML = writeData
    },
    writePlayListContent : function(playListName,data,element){

        
        let writeData = "";
        // currentPlayList = [];
        for(x in data){
            // currentPlayList.push(`${data[x].videoTitle}`)
            writeData = writeData+`<div>
                <a href="${data[x].videoLink}">
                    <div>
                        <img src="${data[x].videoThumbnail}">
                        <h6>${data[x].videoTitle}</h6>
                    </div>
                </a>
                
            </div>`
        }

        element.innerHTML = writeData;
    },
    writePlayListVideo : function(data){

        let writeDocument = document.getElementById("search-results-in-playlist")

        let writeData = '<h6 id="already-found-item">Video Already Found In PlayList</h6>';

        ///need some cahnges
        for(x in data){
            writeData = writeData+`<div><button  onclick='clearPlayListSearchInput();checkAndAddItemToPlayList(\"${data[x].videoTitle}\")'  value="" >${data[x].videoTitle}</button></div>`
            // writeData = writeData+"<div><button onclick=checkAndAddItemToPlayList(\'"+`${data[x].videoTitle}`+"\')  >"+`${data[x].videoTitle}`+"</button></div>"

        }

        writeDocument.innerHTML = writeData
        
    }


}



function playVideo(videoLink){

    let writeData = `<iframe src="${videoLink}" width=720px height=1080px></iframe>`
    document.getElementById("video-player").innerHTML = writeData;
}

function switchPlayList(playListName){
    let x = window.location.pathname;
    let arr = x.split("/")
    window.location.href = window.location.origin+`/playlist.html?playListName=${playListName}`
//    window.location.href = window.location.origin+"/"+arr[1]+`/page.html`
}

function clearPlayListSearchInput(){
    document.getElementById("home-video-search-input").value = ""
}

const requests = {

    getHomeVideo : async function(){

        let element = document.getElementById("home-results")
        element.innerHTML = "";
        let searchKeyword = homeVideoSearchInput.value
        let response = await fetch(`${url}video/search`,{
            headers : {
                "Content-Type" : "application/json",
                "Authorization" : `bearer ${jwtToken}`,
                "Accept":"/*"
            },
            method : "POST",
            Accept : "/*",
            body : JSON.stringify({
                "searchKeyword" : `${searchKeyword}`
            })
        })

        if(response.status == 404){
            writeFunctions.writeHomeVideoNotFound(element)
        }else{
            let data = await response.json();
            writeFunctions.writeHomeVideo(data,element)
        }
    },

    createPlayList : async function(){

        let playListName = createPlayListInput.value

        playLists.push(playListName)

        writeFunctions.writePlayList()

        let response = await fetch(`${url}playlist/create`,{
            headers: {
                contentType: 'application/json',
                Authorization: `bearer ${(localStorage.getItem("jwtToken"))}`,
                Accept : "/*"
            },
            method:"POST",
            body : JSON.stringify({
                playListName : `${playListName}`
            })
        })

        playLists = await response.json()
    },
    getPlayLists : async function(){


        let response = await fetch(`${url}playlist/get`,{
            headers: { "Content-Type": "application/json",
            "Authorization" : `Bearer ${jwtToken}`,
            "Accept":"/*"
                },
            method : "POST",
            Accept:"/*",
            }).then(res => res.json())
        
        playLists = response
        writeFunctions.writePlayList()
        
    },

    viewPlayList : async function(playListName){

        currentPlayListName = playListName;

        let element = document.getElementById("playlist-items")
        currentPlayList = [];
        writeFunctions.writePlayListContent(playListName,currentPlayList,element);
        let response = await fetch(`${url}playlist/view`,{
            headers: { "Content-Type": "application/json",
            "Authorization" : `Bearer ${jwtToken}`,
            "Accept":"/*"
                },
            body : JSON.stringify({
                playListName : `${playListName}`
            }),
            method : "POST",
            Accept:"/*",
        }).then(res => res.json())

        currentPlayList = response
        writeFunctions.writePlayListContent(playListName,response,element);
        
    },
    
    deletePlayList :async function(){

        let playListName = currentPlayListName
        
        console.log(playLists)
        let idx = playLists.indexOf(playListName)
        playLists.splice(idx,1)
        console.log(playLists)
        writeFunctions.writePlayList()

        let response = await fetch(`${url}playlist/delete`,{
            headers: { "Content-Type": "application/json",
            "Authorization" : `Bearer ${jwtToken}`,
            "Accept":"/*"
                },
            body: JSON.stringify({
                playListName 
            }),
            method : "POST",
            Accept:"/*",
        }).then(res => res.json())

        // playLists = response

        // writeFunctions.writePlayList()
        
    },

    getPlayListVideo : async function(){

        let playListName = currentPlayListName
    let response = await fetch(`${url}playlist/view`,{
        headers: { "Content-Type": "application/json",
        "Authorization" : `Bearer ${jwtToken}`,
        "Accept":"/*"
            },
        body : JSON.stringify({
            playListName : `${playListName}`
        }),
        method : "POST",
        Accept:"/*",
    }).then(res => res.json())

    currentPlayList = response
    data = currentPlayList
    let writeData = "";

    currentPlayListVideoTitle = []
    for(x in data){
        currentPlayListVideoTitle.push(data[x].videoTitle)
        writeData = writeData+`<div>
             <a href="${data[x].videoLink}">
               
               <img src="${data[x].videoThumbnail}">
                <h6>${data[x].videoTitle}</h6>
            </a>
              
            </div>`
    }

    homeSearchResults.innerHTML = writeData
    },
    addItemToPlayList : async function(videoTitle){

        let playListName = currentPlayListName
        let element = document.getElementById("home-results")
        document.getElementById("home-video-search-input").value = ""
        document.getElementById("search-results-in-playlist").style.display = "none"
        currentPlayList = addItem(videoTitle);
        currentPlayListVideoTitle.push(videoTitle)
        writeFunctions.writeHomeVideo(currentPlayList,element)
        await fetch(`${url}playlist/add`,{
            headers : {
                "Content-Type" : "application/json",
                "Authorization" : `Bearer ${jwtToken}`,
                "Accept":"/*"
            },
            method : "POST",
            Accept : "/*",
            body : JSON.stringify({
                "playListName" : `${playListName}`,
                "videoTitle" : `${videoTitle}`
            })
        }).then(res => res.json())

        // currentPlayList = response   
        
    },
    removeItemFromPlayList : async function(videoTitle){

        let playListName = currentPlayListName
        let element = document.getElementById("home-results")

        document.getElementById("home-video-search-input").value = ""
        document.getElementById("search-results-in-playlist").style.display = "none"
        for(x in currentPlayListVideoTitle){
            if(videoTitle == currentPlayListVideoTitle[x]){
                currentPlayListVideoTitle.splice(x,1)
            }
        }
        currentPlayList = removeItem(videoTitle)
        writeFunctions.writeHomeVideo(currentPlayList,element)
        let response = await fetch(`${url}playlist/remove`,{
            headers: { 
                "Content-Type": "application/json",
                "Authorization" : `Bearer ${jwtToken}`,
                "Accept":"/*"
                },
            method : "POST",
            Accept:"/*",
            body: JSON.stringify({
                "playListName": `${playListName}`,
                "videoTitle" : `${videoTitle}`
              })   
        }).then(res => res.json())
    },


    logout : function(){
        localStorage.clear();
        window.location.href=url+"page.html"
    }
    
}


requests.getPlayListVideo()

// requests.getHomeVideo()
requests.getPlayLists()
